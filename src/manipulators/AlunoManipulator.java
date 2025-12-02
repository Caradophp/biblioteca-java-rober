package manipulators;

import entidades.Aluno;
import entidades.Livro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por manipular os dados de alunos,
 * realizando operações de cadastro, remoção, atualização
 * e leitura de registros armazenados em arquivo texto.
 */
public class AlunoManipulator {

    private List<Aluno> alunos = new ArrayList<>();
    private final Path arquivoAlunos = Paths.get("alunos.txt");

    /**
     * Construtor que carrega todos os alunos já cadastrados no arquivo.
     */
    public AlunoManipulator() {
        alunos = buscarTodosAlunos();
    }

    /**
     * Retorna a lista atual de alunos carregada em memória.
     */
    public List<Aluno> getAlunos() {
        return alunos;
    }

    /**
     * Cadastra um novo aluno, impedindo matrículas ou e-mails duplicados.
     *
     * @param aluno novo aluno a ser cadastrado
     * @return true se o cadastro foi realizado; false caso já exista
     */
    public boolean cadastrarAluno(Aluno aluno) {
        for (Aluno a : alunos) {
            if (a.getMatricula() == aluno.getMatricula()
                    || a.getEmail().equalsIgnoreCase(aluno.getEmail())) {
                System.out.println("Já existe um aluno com esses dados cadastrados");
                return false;
            }
        }

        alunos.add(aluno);
        escreverAlunoNoArquivo(aluno);
        return true;
    }

    /**
     * Remove um aluno pela matrícula e reescreve o arquivo sem ele.
     *
     * @param matricula matrícula do aluno a ser removido
     * @return true se removido com sucesso
     */
    public boolean removerAluno(long matricula) {

        List<Aluno> novaLista = new ArrayList<>();

        try {
            limparArquivo(arquivoAlunos);

            for (Aluno a : alunos) {
                if (a.getMatricula() != matricula) {
                    novaLista.add(a);
                    escreverAlunoNoArquivo(a);
                }
            }

            alunos = novaLista;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Atualiza os dados de um aluno já existente.
     *
     * @param alunoAtualizado objeto contendo os novos dados
     * @return true após atualizar e gravar no arquivo
     */
    public boolean atualizarAluno(Aluno alunoAtualizado) {

        List<Aluno> novaLista = new ArrayList<>();
        limparArquivo(arquivoAlunos);

        for (Aluno a : alunos) {

            if (a.getMatricula() == alunoAtualizado.getMatricula()) {
                a.setNome(alunoAtualizado.getNome());
                a.setEmail(alunoAtualizado.getEmail());
                a.setSenha(alunoAtualizado.getSenha());
                a.setTelefone(alunoAtualizado.getTelefone());
                a.setCurso(alunoAtualizado.getCurso());
            }

            novaLista.add(a);
            escreverAlunoNoArquivo(a);
        }

        alunos = novaLista;
        return true;
    }

    /**
     * Busca um aluno específico pela matrícula.
     *
     * @param matricula matrícula desejada
     * @return o aluno encontrado ou null caso não exista
     */
    public Aluno buscarAlunoPorMatricula(long matricula) {
        for (Aluno a : alunos) {
            if (a.getMatricula() == matricula) {
                return a;
            }
        }
        return null;
    }

    /**
     * Lê todos os alunos armazenados no arquivo e retorna como lista.
     *
     * @return lista de alunos carregada do arquivo
     */
    private List<Aluno> buscarTodosAlunos() {

        List<Aluno> lista = new ArrayList<>();

        try {
            if (Files.notExists(arquivoAlunos)) {
                Files.createFile(arquivoAlunos);
            }

            List<String> linhas = Files.readAllLines(arquivoAlunos);

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) continue;

                String[] l = linha.split(";");

                Aluno aluno = new Aluno();
                aluno.setMatricula(Long.parseLong(l[0]));
                aluno.setNome(l[1]);
                aluno.setEmail(l[2]);
                aluno.setSenha(l[3]);
                aluno.setTelefone(Long.parseLong(l[4]));
                aluno.setCurso(l[5]);

                lista.add(aluno);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Limpa todo o conteúdo do arquivo de alunos.
     *
     * @param arquivo caminho do arquivo a ser esvaziado
     */
    private void limparArquivo(Path arquivo) {
        try (OutputStream os = new FileOutputStream(arquivo.toString())) {
            // Abrir já limpa o arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve um aluno no arquivo em formato de linha separada por ponto e vírgula.
     *
     * @param a aluno a ser gravado
     */
    private void escreverAlunoNoArquivo(Aluno a) {

        String linha = a.getMatricula() + ";" +
                a.getNome() + ";" +
                a.getEmail() + ";" +
                a.getSenha() + ";" +
                a.getTelefone() + ";" +
                a.getCurso();

        try {
            Files.write(arquivoAlunos, linha.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoAlunos, "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
