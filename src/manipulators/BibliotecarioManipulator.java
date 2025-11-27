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

public class BibliotecarioManipulator {

    /**
     * lista de bibliotecarios
     */

    private List<Bibliotecario> bibliotecarios = new ArrayList<>();

    /**
     * Caminho para o arquivo de persistência dos livros.
     */
    private Path arquivoFuncionarios = Paths.get("funcionarios.txt");

    public BibliotecarioManipulator() {
        bibliotecarios = buscarTodosBibliotecarios();
    }

    public int getNumBibliotecarios() {
        return this.bibliotecarios.size();
    }
    
    public List<Bibliotecario> getBibliotecarios() {
    	return bibliotecarios;
    }

    /**
     * Cadastra um novo bibliotecário no arquivo de persistência.
     *
     * @param bibliotecario O objeto Bibliotecario a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean inserirBibliotecario(Bibliotecario bibliotecario) {

    	bibliotecarios.add(bibliotecario);
        String linhaIncerida = bibliotecario.getRegistro() + ";" + bibliotecario.getNome() + ";" + bibliotecario.getEmail() + ";" + bibliotecario.getSenha() + ";" + bibliotecario.getTelefone() + ";" + bibliotecario.getDataAdmissao();

        try {
            Files.write(arquivoFuncionarios, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoFuncionarios, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove um bibliotecário do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os bibliotecários, exceto o removido.
     *
     * @param registro O registro do bibliotecário a ser removido.
     */
    public void removerFuncionario(int registro) {
        limparArquivo(arquivoFuncionarios);
        for (Bibliotecario bibliotecario : bibliotecarios) {
            if (bibliotecario.getRegistro() != registro) {
                inserirBibliotecario(bibliotecario);
            }
        }
    }

    /**
     * Carrega todos os bibliotecários do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Bibliotecarios.
     */
    private List<Bibliotecario> buscarTodosBibliotecarios() {

        List<Bibliotecario> bibliotecarioList = new ArrayList<>();

        try {

            /* Se o arquivo de bibliotecarios não existir, ele é criado */
            if (Files.notExists(arquivoFuncionarios)) {
                Files.createFile(arquivoFuncionarios);
            }

            List<String> linhas = Files.readAllLines(arquivoFuncionarios);
            for (String linha : linhas) {
                String[] l = linha.split(";");
                Bibliotecario bibliotecario = new Bibliotecario();
                bibliotecario.setRegistro(Integer.parseInt(l[0]));
                bibliotecario.setNome(l[1]);
                bibliotecario.setEmail(l[2]);
                bibliotecario.setSenha(l[3]);
                bibliotecario.setTelefone(Long.parseLong(l[4]));

                // Divide a string de data para montar o objeto LocalDate (formato dd/MM/yyyy)
                String[] data = l[5].split("/");
                bibliotecario.setDataAdmissao(LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0])));

                bibliotecarioList.add(bibliotecario);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bibliotecarioList;
    }

    public boolean atualizarBibliotecario(Bibliotecario bibliotecarioAtualizado, long registro) {

        limparArquivo(arquivoFuncionarios);
        for (Bibliotecario bibliotecario : bibliotecarios) {
            if (bibliotecario.getRegistro() == registro) {
                bibliotecario.setNome(bibliotecarioAtualizado.getNome());
                bibliotecario.setEmail(bibliotecarioAtualizado.getEmail());
                bibliotecario.setSenha(bibliotecarioAtualizado.getSenha());
                bibliotecario.setTelefone(bibliotecarioAtualizado.getTelefone());
                inserirBibliotecario(bibliotecario);
            } else {
                inserirBibliotecario(bibliotecario);
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
