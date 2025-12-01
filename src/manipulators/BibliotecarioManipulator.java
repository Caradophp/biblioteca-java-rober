package manipulators;

import entidades.Aluno;
import entidades.Bibliotecario;

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

public class BibliotecarioManipulator {

    private List<Bibliotecario> bibliotecarios = new ArrayList<>();
    private final Path arquivoFuncionarios = Paths.get("funcionarios.txt");

    public BibliotecarioManipulator() {
        bibliotecarios = buscarTodosBibliotecarios();
    }

    public int getNumBibliotecarios() {
        return this.bibliotecarios.size();
    }

    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    /** INSERIR BIBLIOTECARIO */
    public boolean inserirBibliotecario(Bibliotecario bibliotecario) {

        for (Bibliotecario b : bibliotecarios) {
            if (b.getRegistro() == bibliotecario.getRegistro()
                    || b.getEmail().equalsIgnoreCase(bibliotecario.getEmail())) {
                System.out.println("Já existe um bibliotecário com esses dados cadastrados");
                return false;
            }
        }

        bibliotecarios.add(bibliotecario);
        escreverBibliotecarioNoArquivo(bibliotecario);

        return true;
    }

    /** REMOVER BIBLIOTECARIO */
    public boolean removerFuncionario(int registro) {

        List<Bibliotecario> novaLista = new ArrayList<>();

        try {
            limparArquivo(arquivoFuncionarios);

            for (Bibliotecario b : bibliotecarios) {
                if (b.getRegistro() != registro) {
                    novaLista.add(b);
                    escreverBibliotecarioNoArquivo(b);
                }
            }

            bibliotecarios = novaLista;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** ATUALIZAR BIBLIOTECARIO */
    public boolean atualizarBibliotecario(Bibliotecario bibliotecarioAtualizado) {

        List<Bibliotecario> novaLista = new ArrayList<>();

        limparArquivo(arquivoFuncionarios);

        for (Bibliotecario b : bibliotecarios) {
            if (b.getRegistro() == bibliotecarioAtualizado.getRegistro()) {
                b.setNome(bibliotecarioAtualizado.getNome());
                b.setEmail(bibliotecarioAtualizado.getEmail());
                b.setSenha(bibliotecarioAtualizado.getSenha());
                b.setTelefone(bibliotecarioAtualizado.getTelefone());
            }

            novaLista.add(b);
            escreverBibliotecarioNoArquivo(b);
        }

        bibliotecarios = novaLista;
        return true;
    }

    /** BUSCAR TODOS DO ARQUIVO */
    private List<Bibliotecario> buscarTodosBibliotecarios() {

        List<Bibliotecario> lista = new ArrayList<>();

        try {
            if (Files.notExists(arquivoFuncionarios)) {
                Files.createFile(arquivoFuncionarios);
            }

            List<String> linhas = Files.readAllLines(arquivoFuncionarios);

            for (String linha : linhas) {

                if (linha.trim().isEmpty()) continue;

                String[] l = linha.split(";");

                Bibliotecario b = new Bibliotecario();
                b.setRegistro(Integer.parseInt(l[0]));
                b.setNome(l[1]);
                b.setEmail(l[2]);
                b.setSenha(l[3]);
                b.setTelefone(Long.parseLong(l[4]));

                String[] data = l[5].split("/");
                b.setDataAdmissao(
                        LocalDate.of(Integer.parseInt(data[2]),
                                Integer.parseInt(data[1]),
                                Integer.parseInt(data[0]))
                );

                lista.add(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /** LIMPAR ARQUIVO */
    private void limparArquivo(Path arquivo) {
        try (OutputStream os = new FileOutputStream(arquivo.toString())) {
            // Apenas abrir o arquivo já limpa o conteúdo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** ESCREVER REGISTRO NO ARQUIVO */
    private void escreverBibliotecarioNoArquivo(Bibliotecario b) {

        String linha = b.getRegistro() + ";" +
                       b.getNome() + ";" +
                       b.getEmail() + ";" +
                       b.getSenha() + ";" +
                       b.getTelefone() + ";" +
                       b.getDataAdmissao();

        try {
            Files.write(arquivoFuncionarios, linha.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoFuncionarios, "\n".getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
