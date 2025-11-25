package manipulators;

import entidades.Aluno;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class AlunoManipulator {

    /**
     * lista de alunos
     */

    private List<Aluno> alunos = new ArrayList<>();

    /**
     * Caminho para o arquivo de persistência dos alunos.
     */
    private Path arquivoAlunos = Paths.get("alunos.txt");

    public AlunoManipulator() {
        alunos = buscarTodosAlunos();
    }


    /**
     * Cadastra um novo aluno no arquivo de persistência.
     *
     * @param aluno O objeto Aluno a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarAluno(Aluno aluno) {

        String linhaIncerida = aluno.getMatricula() + ";" + aluno.getNome() + ";" + aluno.getEmail() + ";" + aluno.getSenha() + ";" + aluno.getTelefone() + ";" + aluno.getCurso();

        try {
            Files.write(arquivoAlunos, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoAlunos, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove um aluno do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os alunos, exceto o removido.
     *
     * @param matricula A matrícula do aluno a ser removido.
     */
    public void removerAluno(long matricula) {
        limparArquivo(arquivoAlunos);
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() != matricula) {
                cadastrarAluno(aluno);
            }
        }
    }

    /**
     * Busca um aluno na lista em memória pela matrícula.
     *
     * @param matricula A matrícula do aluno a ser buscado.
     * @return O objeto Aluno encontrado ou null se não for encontrado.
     */
    public Aluno buscarAlunoPorMatricula(long matricula) {

        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() == matricula) {
                return aluno;
            }
        }

        return null;

    }

    /**
     * Carrega todos os alunos do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Alunos.
     */
    public List<Aluno> buscarTodosAlunos() {

        List<Aluno> alunoList = new ArrayList<>();

        try {

            /* Se o arquivo de alunos não existir, ele é criado */
            if (Files.notExists(arquivoAlunos)) {
                Files.createFile(arquivoAlunos);
            }

            List<String> linhas = Files.readAllLines(arquivoAlunos);
            for (String linha : linhas) {
                String[] l = linha.split(";");
                Aluno aluno = new Aluno();
                aluno.setMatricula(Long.parseLong(l[0]));
                aluno.setNome(l[1]);
                aluno.setEmail(l[2]);
                aluno.setSenha(l[3]);
                aluno.setTelefone(Long.parseLong(l[4]));
                aluno.setCurso(l[5]);
                alunoList.add(aluno);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return alunoList;
    }

    public boolean atualizarAluno(Aluno alunoAtualizado, long matricula) {
        limparArquivo(arquivoAlunos);
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() == matricula) {
                aluno.setNome(alunoAtualizado.getNome());
                aluno.setEmail(alunoAtualizado.getEmail());
                aluno.setSenha(alunoAtualizado.getSenha());
                aluno.setTelefone(alunoAtualizado.getTelefone());
                aluno.setCurso(alunoAtualizado.getCurso());
                cadastrarAluno(aluno);
            } else {
                cadastrarAluno(aluno);
            }
        }

        return true;
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
