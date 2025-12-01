package manipulators;

import entidades.Aluno;
import entidades.Emprestimo;
import entidades.Livro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoManipulator {

    private final LivroManipulator livroManipulator;
    private final AlunoManipulator alunoManipulator;

    private List<Emprestimo> emprestimos = new ArrayList<>();
    private final Path arquivoEmprestimos = Paths.get("emprestimos.txt");

    public EmprestimoManipulator(LivroManipulator livroManipul, AlunoManipulator alunoManipul) {
        this.livroManipulator = livroManipul;
        this.alunoManipulator = alunoManipul;
        emprestimos = buscarTodosEmprestimos();
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    /* ===========================
       FAZER NOVO EMPRÉSTIMO
       ========================== */
    public boolean fazerEmprestimo(Emprestimo emprestimo) {

        Livro livro = livroManipulator.buscarLivroPorCodigo(emprestimo.getCodigoLivro());
        if (livro == null) {
            System.out.println("Não existe um livro com o código indicado");
            return false;
        }

        Aluno aluno = alunoManipulator.buscarAlunoPorMatricula(emprestimo.getCodigoAluno());
        if (aluno == null) {
            System.out.println("Aluno não encontrado");
            return false;
        }

        emprestimo.setLivro(livro);

        int qtdDisponivel = livro.getQtdDisponivel() - 1;
        livro.setQtdDisponivel(qtdDisponivel);
        emprestimos.add(emprestimo);
        escreverEmprestimoNoArquivo(emprestimo);
        livroManipulator.atualizarLivro(livro);

        return true;
    }

    public void atualizarEmprestimo(Emprestimo emprestimo) {

        Emprestimo em = emprestimos.stream()
                .filter(e -> e.getCodigoEmprestimo() == emprestimo.getCodigoEmprestimo())
                .findFirst()
                .orElseThrow();

        if (em != null) {
            emprestimos.remove(em);
            em = emprestimo;
            emprestimos.add(em);
        }

        limparArquivo(arquivoEmprestimos);
        emprestimos.forEach(this::escreverEmprestimoNoArquivo);


    }

    /* ===========================
       RENOVAR EMPRÉSTIMO
       ========================== */
    public void renovarEmprestimo(long codigoEmprestimo) {

        Emprestimo emprestimo = null;
        List<Emprestimo> novaLista = new ArrayList<>();
        limparArquivo(arquivoEmprestimos);

        for (Emprestimo e : emprestimos) {

            if (e.getCodigoEmprestimo() == codigoEmprestimo) {
               e.renovarEmprestimo();
            }

            novaLista.add(e);
            atualizarEmprestimo(e);

        }

        emprestimos = novaLista;
    }

    /* ===========================
       DEVOLVER LIVRO
       ========================== */
    public boolean devolverLivro(long codigoEmprestimo) {

        boolean devolvido = false;

        Livro livro = null;
        List<Emprestimo> novaLista = new ArrayList<>();
        limparArquivo(arquivoEmprestimos);

        for (Emprestimo e : emprestimos) {

            if (e.getCodigoEmprestimo() == codigoEmprestimo) {
                livro = e.devolverLivro();
                devolvido = true;
            }

            novaLista.add(e);
            if (livro != null) {
                livroManipulator.atualizarLivro(livro);
            }
            escreverEmprestimoNoArquivo(e);
        }

        emprestimos = novaLista;
        return devolvido;
    }


    /* ===========================
       BUSCAR TODOS NO ARQUIVO
       ========================== */
    private List<Emprestimo> buscarTodosEmprestimos() {

        List<Emprestimo> lista = new ArrayList<>();

        try {
            if (Files.notExists(arquivoEmprestimos)) {
                Files.createFile(arquivoEmprestimos);
            }

            List<String> linhas = Files.readAllLines(arquivoEmprestimos);

            for (String linha : linhas) {

                if (linha.trim().isEmpty()) continue;

                String[] l = linha.split(";");

                Emprestimo e = new Emprestimo();
                e.setCodigoEmprestimo(Integer.parseInt(l[0]));
                e.setCodigoLivro(Integer.parseInt(l[1]));
                e.setCodigoAluno(Integer.parseInt(l[2]));
                e.setDevolvido(l[5].equals("sim"));

                String[] d1 = l[3].split("/");
                e.setDataEmprestimo(LocalDate.of(Integer.parseInt(d1[2]),
                        Integer.parseInt(d1[1]),
                        Integer.parseInt(d1[0])));

                String[] d2 = l[4].split("/");
                e.setDataDevolucaoPrevista(LocalDate.of(Integer.parseInt(d2[2]),
                        Integer.parseInt(d2[1]),
                        Integer.parseInt(d2[0])));

                e.setLivro(livroManipulator.buscarLivroPorCodigo(e.getCodigoLivro()));

                lista.add(e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }


    /* ===========================
       MÉTODOS AUXILIARES
       ========================== */

    /**
     * Limpa o conteúdo do arquivo
     */
    private void limparArquivo(Path arquivo) {
        try (OutputStream os = new FileOutputStream(arquivo.toString())) {
            // Somente abrir já limpa
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve um empréstimo individual no arquivo
     */
    private void escreverEmprestimoNoArquivo(Emprestimo e) {

        String devolvido = e.isDevolvido() ? "sim" : "nao";

        String linha = e.getCodigoEmprestimo() + ";" +
                e.getCodigoLivro() + ";" +
                e.getCodigoAluno() + ";" +
                e.getDataEmprestimoFormatada() + ";" +
                e.getDataDevolucaoPrevistaFormatada() + ";" +
                devolvido + ";" +
                e.getQtdRenovacoes();

        try {
            Files.write(arquivoEmprestimos, linha.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoEmprestimos, "\n".getBytes(), StandardOpenOption.APPEND);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
