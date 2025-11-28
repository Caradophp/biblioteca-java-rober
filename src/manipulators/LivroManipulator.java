package manipulators;

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

public class LivroManipulator {

    /**
     * lista de alunos
     */

    private List<Livro> livros = new ArrayList<>();

    /**
     * Caminho para o arquivo de persistência dos livros.
     */
    private Path arquivoLivros = Paths.get("livros.txt");

    public LivroManipulator() {
        livros = buscarTodosLivros();
    }
    
    public List<Livro> getLivros() {
    	return livros;
    }

    /**
     * Cadastra um novo livro no arquivo de persistência.
     *
     * @param livro O objeto Livro a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarLivro(Livro livro) {

    	livros.add(livro);
        String linhaIncerida = livro.getCodigo() + ";" + livro.getNome() + ";" + livro.getCategoria() + ";" + livro.getAutor() + ";" + livro.getEditora() + ";" + livro.getIsbn() + ";" + livro.getQtdDisponivel();

        try {
            Files.write(arquivoLivros, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoLivros, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carrega todos os livros do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Livros.
     */
    private List<Livro> buscarTodosLivros() {
        List<Livro> listaLivros = new ArrayList<>();

        try {

            /* Se o arquivo de livros não existir, ele é criado */
            if (Files.notExists(arquivoLivros)) {
                Files.createFile(arquivoLivros);
            }

            List<String> listaLivrosString = Files.readAllLines(arquivoLivros);

            for (String s : listaLivrosString) {
                String[] linha = s.split(";");
                Livro livro = new Livro();
                livro.setCodigo(Integer.parseInt(linha[0]));
                livro.setNome(linha[1]);
                livro.setCategoria(linha[2]);
                livro.setAutor(linha[3]);
                livro.setEditora(linha[4]);
                livro.setIsbn(Integer.parseInt(linha[5]));
                livro.setQtdDisponivel(Integer.parseInt(linha[6]));

                listaLivros.add(livro);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listaLivros;
    }

    /**
     * Busca um livro na lista em memória pelo nome.
     *
     * @param nome O nome do livro a ser buscado.
     * @return O objeto Livro encontrado ou null se não for encontrado.
     */
    public Livro buscarLivroPorNome(String nome) {

        for (Livro livro : livros) {
            if (livro.getNome().equalsIgnoreCase(nome)) {
                return livro;
            }
        }

        return null;

    }

    /**
     * Busca um livro na lista em memória pelo código.
     *
     * @param isbn O ISBN do livro a ser buscado.
     * @return O objeto Livro encontrado ou null se não for encontrado.
     */
    public Livro buscarLivroPorCodigo(long isbn) {

        for (Livro livro : livros) {
            if (livro.getIsbn() == isbn) {
                return livro;
            }
        }

        return null;

    }

    /**
     * Atualiza as informações de um livro.
     * O método limpa o arquivo e o reescreve com as informações atualizadas.
     *
     * @param livroParaAtualizar O objeto Livro com as novas informações.
     * @param isbn               O ISBN do livro a ser atualizado.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean atualizarLivroPorCodigo(Livro livroParaAtualizar, long isbn) {

        limparArquivo(arquivoLivros);
        for (Livro livro : livros) {
            livros.remove(livro);
            if (livro.getIsbn() == isbn) {
                livro.setNome(livroParaAtualizar.getNome());
                livro.setCategoria(livroParaAtualizar.getCategoria());
                livro.setAutor(livroParaAtualizar.getAutor());
                livro.setEditora(livroParaAtualizar.getEditora());
                livro.setIsbn(livroParaAtualizar.getIsbn());
                livro.setQtdDisponivel(livroParaAtualizar.getQtdDisponivel());
                cadastrarLivro(livro);
            } else {
                cadastrarLivro(livro);
            }
        }

        return true;
    }

    /**
     * Remove um livro do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os livros, exceto o removido.
     *
     * @param isbn O ISBN do livro a ser removido.
     * @return true se a remoção for bem-sucedida, false caso contrário.
     */
    public boolean removerLivro(long isbn) {

        try {

            limparArquivo(arquivoLivros);
            for (Livro livro : livros) {
                livros.remove(livro);
                if (livro.getIsbn() != isbn) {
                    cadastrarLivro(livro);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
