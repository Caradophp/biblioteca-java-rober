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

/**
 * Classe responsável por gerenciar operações relacionadas a empréstimos,
 * incluindo criação, renovação, devolução e persistência em arquivo.
 */
public class EmprestimoManipulator {

    private final LivroManipulator livroManipulator;
    private final AlunoManipulator alunoManipulator;

    private List<Emprestimo> emprestimos = new ArrayList<>();
    private final Path arquivoEmprestimos = Paths.get("emprestimos.txt");

    /**
     * Construtor que recebe manipuladores auxiliares e carrega os empréstimos do arquivo.
     */
    public EmprestimoManipulator(LivroManipulator livroManipul, AlunoManipulator alunoManipul) {
        this.livroManipulator = livroManipul;
        this.alunoManipulator = alunoManipul;
        emprestimos = buscarTodosEmprestimos();
    }

    /**
     * Retorna a lista atual de empréstimos em memória.
     */
    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    /* ============================================================
       FAZER NOVO EMPRÉSTIMO
       ============================================================ */

    /**
     * Realiza um novo empréstimo se o livro existir, houver disponibilidade
     * e o aluno estiver cadastrado.
     *
     * @param emprestimo objeto contendo informações do novo empréstimo
     * @return true se o empréstimo foi realizado com sucesso
     */
    public boolean fazerEmprestimo(Emprestimo emprestimo) {

        Livro livro = livroManipulator.buscarLivroPorCodigo(emprestimo.getCodigoLivro());
        if (livro == null) {
            System.out.println("Não existe um livro com o código indicado.");
            return false;
        }

        if (livro.getQtdDisponivel() <= 0) {
            System.out.println("Não existe nenhuma cópia disponível do livro.");
            return false;
        }

        Aluno aluno = alunoManipulator.buscarAlunoPorMatricula(emprestimo.getCodigoAluno());
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return false;
        }

        emprestimo.setLivro(livro);

        livro.setQtdDisponivel(livro.getQtdDisponivel() - 1);
        emprestimos.add(emprestimo);
        escreverEmprestimoNoArquivo(emprestimo);

        livroManipulator.atualizarLivro(livro);
        return true;
    }

    /* ============================================================
       RENOVAR EMPRÉSTIMO
       ============================================================ */

    /**
     * Renova o empréstimo correspondente ao código informado,
     * respeitando o limite máximo de renovações definido na classe Emprestimo.
     *
     * @param codigoEmprestimo código do empréstimo a renovar
     * @return true se a renovação ocorreu; false se não foi possível
     */
    public boolean renovarEmprestimo(long codigoEmprestimo) {

        boolean renovou = false;

        List<Emprestimo> novaLista = new ArrayList<>();
        limparArquivo(arquivoEmprestimos);

        for (Emprestimo e : emprestimos) {

            if (e.getCodigoEmprestimo() == codigoEmprestimo) {
                renovou = e.renovarEmprestimo();
            }

            novaLista.add(e);
            escreverEmprestimoNoArquivo(e);
        }

        emprestimos = novaLista;
        return renovou;
    }

    /* ============================================================
       DEVOLVER LIVRO
       ============================================================ */

    /**
     * Registra a devolução de um livro e atualiza sua quantidade disponível.
     *
     * @param codigoEmprestimo código do empréstimo
     * @return true se o livro foi devolvido
     */
    public boolean devolverLivro(long codigoEmprestimo) {

        boolean devolvido = false;
        List<Emprestimo> novaLista = new ArrayList<>();
        limparArquivo(arquivoEmprestimos);

        for (Emprestimo e : emprestimos) {

            if (e.getCodigoEmprestimo() == codigoEmprestimo) {
                devolvido = e.devolverLivro();

                if (devolvido && e.getLivro() != null) {
                    livroManipulator.atualizarLivro(e.getLivro());
                }
            }

            novaLista.add(e);
            escreverEmprestimoNoArquivo(e);
        }

        emprestimos = novaLista;
        return devolvido;
    }

    /* ============================================================
       CARREGAR EMPRÉSTIMOS DO ARQUIVO
       ============================================================ */

    /**
     * Lê todos os empréstimos armazenados no arquivo e reconstrói os objetos.
     *
     * @return lista com todos os empréstimos carregados
     */
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
                e.setQtdRenovacoes(Integer.parseInt(l[6]));

                String[] d1 = l[3].split("/");
                e.setDataEmprestimo(LocalDate.of(
                        Integer.parseInt(d1[2]),
                        Integer.parseInt(d1[1]),
                        Integer.parseInt(d1[0])));

                String[] d2 = l[4].split("/");
                e.setDataDevolucaoPrevista(LocalDate.of(
                        Integer.parseInt(d2[2]),
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

    /* ============================================================
       MÉTODOS AUXILIARES
       ============================================================ */

    /**
     * Apaga todo o conteúdo de um arquivo, limpando-o completamente.
     */
    private void limparArquivo(Path arquivo) {
        try (OutputStream os = new FileOutputStream(arquivo.toString())) {
            // Abrir o arquivo já remove o conteúdo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve um empréstimo no arquivo no formato de linha de texto.
     *
     * @param e empréstimo a ser gravado
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
