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

public class AlunoManipulator {

    private List<Aluno> alunos = new ArrayList<>();
    private final Path arquivoAlunos = Paths.get("alunos.txt");

    public AlunoManipulator() {
        alunos = buscarTodosAlunos();
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    /** CADASTRAR ALUNO */
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

    /** REMOVER ALUNO */
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

    /** ATUALIZAR ALUNO */
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

    /** BUSCAR ALUNO POR MATRÍCULA */
    public Aluno buscarAlunoPorMatricula(long matricula) {
        for (Aluno a : alunos) {
            if (a.getMatricula() == matricula) {
                return a;
            }
        }
        return null;
    }

    /** CARREGAR ALUNOS DO ARQUIVO */
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

    /** LIMPAR ARQUIVO */
    private void limparArquivo(Path arquivo) {
        try (OutputStream os = new FileOutputStream(arquivo.toString())) {
            // Abrir já limpa o arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** ESCREVER ALUNO NO ARQUIVO */
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
