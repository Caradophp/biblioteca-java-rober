package manipulators;

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

/**
 * Classe responsável por manipular dados de bibliotecários,
 * permitindo inserir, atualizar, remover e carregar registros
 * armazenados em um arquivo texto.
 */
public class BibliotecarioManipulator {

    private List<Bibliotecario> bibliotecarios = new ArrayList<>();
    private final Path arquivoFuncionarios = Paths.get("funcionarios.txt");

    /**
     * Construtor que carrega todos os bibliotecários do arquivo.
     */
    public BibliotecarioManipulator() {
        bibliotecarios = buscarTodosBibliotecarios();
    }

    /**
     * Retorna a quantidade de bibliotecários cadastrados.
     */
    public int getNumBibliotecarios() {
        return this.bibliotecarios.size();
    }

    /**
     * Retorna a lista atual de bibliotecários em memória.
     */
    public List<Bibliotecario> getBibliotecarios() {
        return bibliotecarios;
    }

    /**
     * Insere um novo bibliotecário, evitando registros e e-mails duplicados.
     *
     * @param bibliotecario novo bibliotecário a ser adicionado
     * @return true se o cadastro foi inserido; false se já existe
     */
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

    /**
     * Remove um bibliotecário pelo registro e reescreve o arquivo.
     *
     * @param registro número de registro do bibliotecário
     * @return true se removido com sucesso
     */
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

    /**
     * Atualiza os dados de um bibliotecário existente.
     *
     * @param bibliotecarioAtualizado objeto contendo os novos dados
     * @return true após atualização
     */
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

    /**
     * Lê todos os bibliotecários do arquivo e os retorna como lista.
     *
     * @return lista de bibliotecários carregados do arquivo
     */
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
                        LocalDate.of(
                                Integer.parseInt(data[2]),
                                Integer.parseInt(data[1]),
                                Integer.parseInt(data[0])
                        )
                );

                lista.add(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Limpa todo o conteúdo de um arquivo texto.
     *
     * @param arquivo caminho do arquivo a ser limpo
     */
    private void limparArquivo(Path arquivo) {
        try (OutputStream os = new FileOutputStream(arquivo.toString())) {
            // Abrir já apaga o conteúdo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escreve um bibliotecário no arquivo em formato de linha.
     *
     * @param b bibliotecário a ser gravado
     */
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
