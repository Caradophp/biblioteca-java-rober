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

    /**
     * lista de emprestimos
     */

    private List<Emprestimo> emprestimos = new ArrayList<>();

    /**
     * Caminho para o arquivo de persistência dos livros.
     */
    private Path arquivoEmprestimos = Paths.get("emprestimos.txt");

    public EmprestimoManipulator(LivroManipulator livroManipul, AlunoManipulator alunoManipul) {
        livroManipulator = livroManipul;
        alunoManipulator = alunoManipul;
        emprestimos = buscarTodosEmprestimos();
    }
    
    public List<Emprestimo> getEmprestimos() {
    	return emprestimos;
    }

    /**
     * Registra um novo empréstimo no arquivo de persistência.
     * Antes de registrar, verifica se o livro e o aluno existem.
     *
     * @param emprestimo O objeto Emprestimo a ser registrado.
     * @return true se o registro for bem-sucedido, false caso contrário.
     */
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

        emprestimos.add(emprestimo);
        String linhaIncerida = emprestimo.getCodigoEmprestimo() + ";" + emprestimo.getCodigoLivro() + ";" + emprestimo.getCodigoAluno() + ";" + emprestimo.getDataEmprestimoFormatada() + ";" + emprestimo.getDataDevolucaoFormatada() + ";" + "nao";

        try {
            Files.write(arquivoEmprestimos, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoEmprestimos, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void renovarEmprestimo(long codigoEmprestimo) {
        limparArquivo(arquivoEmprestimos);
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getCodigoEmprestimo() == codigoEmprestimo) {
                emprestimo.renovarEmprestimo();
            }

            fazerEmprestimo(emprestimo);
        }
    }

    /**
     * Carrega todos os empréstimos do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Emprestimos.
     */
    private List<Emprestimo> buscarTodosEmprestimos() {

        List<Emprestimo> emprestimoList = new ArrayList<>();

        try {

            if (Files.notExists(arquivoEmprestimos)) {
                Files.createFile(arquivoEmprestimos);
            }

            List<String> linhas = Files.readAllLines(arquivoEmprestimos);
            for (String linha : linhas) {
                if (!linha.equals("")) {
                    String[] l = linha.split(";");
                    Emprestimo emprestimo = new Emprestimo();
                    emprestimo.setCodigoEmprestimo(Integer.parseInt(l[0]));
                    emprestimo.setCodigoLivro(Integer.parseInt(l[1]));
                    emprestimo.setCodigoAluno(Integer.parseInt(l[2]));
                    emprestimo.setDevolvido(l[5].equals("sim"));

                    // Divide a string de data para montar o objeto LocalDate (formato dd/MM/yyyy)

                    String[] dataEmprestimo = l[3].split("/");
                    emprestimo.setDataEmprestimo(LocalDate.of(Integer.parseInt(dataEmprestimo[2]), Integer.parseInt(dataEmprestimo[1]), Integer.parseInt(dataEmprestimo[0])));

                    String[] dataDevolucao = l[4].split("/");
                    emprestimo.setDataDevolucao(LocalDate.of(Integer.parseInt(dataDevolucao[2]), Integer.parseInt(dataDevolucao[1]), Integer.parseInt(dataDevolucao[0])));

                    emprestimo.setLivro(livroManipulator.buscarLivroPorCodigo(emprestimo.getCodigoLivro()));

                    emprestimoList.add(emprestimo);

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return emprestimoList;
    }

    /**
     * Marca o livro como devolvido e atualiza a data de devolução para a data atual.
     */
    public boolean devolverLivro(long codigoEmprestimo) {
        boolean result = false;
        try {
            limparArquivo(arquivoEmprestimos);
            for (Emprestimo emprestimo : emprestimos) {
                if (emprestimo.getCodigoEmprestimo() == codigoEmprestimo) {

                    emprestimo.devolverLivro();

                    String linhaAlterada= emprestimo.getCodigoEmprestimo() + ";" + emprestimo.getCodigoLivro() + ";" + emprestimo.getCodigoAluno() + ";" + emprestimo.getDataEmprestimoFormatada() + ";" + emprestimo.getDataDevolucaoFormatada() + ";" + "sim";

                    Files.write(arquivoEmprestimos, linhaAlterada.getBytes(), StandardOpenOption.APPEND);

                    result = true;
                } else {
                    String linhaIncerida = emprestimo.getCodigoEmprestimo() + ";" + emprestimo.getCodigoLivro() + ";" + emprestimo.getCodigoAluno() + ";" + emprestimo.getDataEmprestimoFormatada() + ";" + emprestimo.getDataDevolucaoFormatada() + ";" + "nao";
                    Files.write(arquivoEmprestimos, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;

    }

    // Método para limpar arquivo

    /**
     * Limpa o conteúdo de um arquivo, preparando-o para uma reescrita.
     *
     * @param arquivo O caminho do arquivo a ser limpo.
     */
    private void limparArquivo(Path arquivo) {
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(arquivo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
