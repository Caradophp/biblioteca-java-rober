package manipulators;

import entidades.Livro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por manipular operações relacionadas a livros.
 * Inclui cadastro, busca, atualização, remoção e persistência em arquivo texto.
 *
 * O arquivo utilizado para armazenamento é "livros.txt", localizado no diretório raiz do projeto.
 */
public class LivroManipulator {

    /** Lista de livros atualmente carregada na memória. */
    private List<Livro> livros = new ArrayList<>();

    /** Caminho do arquivo físico utilizado para armazenar os livros. */
    private final Path arquivoLivros = Paths.get("livros.txt");

    /**
     * Construtor que inicializa o manipulador carregando todos os livros do arquivo.
     */
    public LivroManipulator() {
        livros = buscarTodosLivros();
    }

    /**
     * Retorna a lista de livros carregada na memória.
     *
     * @return lista de livros
     */
    public List<Livro> getLivros() {
        return livros;
    }

    /* ==========================================================
       CADASTRAR LIVRO
       ========================================================== */

    /**
     * Cadastra um novo livro caso ainda não exista outro com o mesmo ISBN.
     * O livro é adicionado à lista interna e persistido no arquivo.
     *
     * @param livro objeto livro a ser cadastrado
     * @return true se o cadastro for bem-sucedido, false caso o ISBN já exista
     */
    public boolean cadastrarLivro(Livro livro) {

        for (Livro l : livros) {
            if (l.getIsbn() == livro.getIsbn()) {
                System.out.println("Já existe um livro com esse ISBN cadastrado");
                return false;
            }
        }

        livros.add(livro);
        escreverLivroNoArquivo(livro);

        return true;
    }

    /* ==========================================================
       BUSCAR TODOS OS LIVROS NO ARQUIVO
       ========================================================== */

    /**
     * Lê todos os livros presentes no arquivo "livros.txt" e retorna a lista completa.
     * Caso o arquivo não exista, ele é criado.
     *
     * @return lista de livros carregados do arquivo
     */
    private List<Livro> buscarTodosLivros() {

        List<Livro> listaLivros = new ArrayList<>();

        try {
            if (Files.notExists(arquivoLivros)) {
                Files.createFile(arquivoLivros);
            }

            List<String> linhas = Files.readAllLines(arquivoLivros);

            for (String s : linhas) {
                if (s.trim().isEmpty()) continue;

                String[] linha = s.split(";");

                Livro livro = new Livro();
                livro.setAnoPublicacao(Year.parse(linha[0]));
                livro.setNome(linha[1]);
                livro.setCategoria(linha[2]);
                livro.setAutor(linha[3]);
                livro.setEditora(linha[4]);
                livro.setIsbn(Long.parseLong(linha[5]));
                livro.setQtdDisponivel(Integer.parseInt(linha[6]));
                livro.setQtdTotal(Integer.parseInt(linha[7]));

                listaLivros.add(livro);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return listaLivros;
    }

    /* ==========================================================
       BUSCAR POR NOME
       ========================================================== */

    /**
     * Busca livros cujo nome contém o texto informado (case insensitive).
     *
     * @param nome parte do nome do livro para buscar
     * @return lista de livros encontrados
     */
    public List<Livro> buscarLivrosPorNome(String nome) {
        List<Livro> livrosEncontrados = new ArrayList<>();

        nome = nome.toLowerCase();
        for (Livro livro : livros) {
            if (livro.getNome().toLowerCase().contains(nome)) {
                livrosEncontrados.add(livro);
            }
        }

        return livrosEncontrados;
    }

    /**
     * Busca livros por categoria exata (case insensitive).
     *
     * @param categoria categoria desejada
     * @return lista de livros encontrados
     */
    public List<Livro> buscarLivrosPorCategoria(String categoria) {
        List<Livro> livrosEncontrados = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getCategoria().equalsIgnoreCase(categoria)) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    /* ==========================================================
       BUSCAR POR ISBN
       ========================================================== */

    /**
     * Busca um livro específico pelo seu ISBN.
     *
     * @param isbn código ISBN a ser pesquisado
     * @return livro encontrado ou null se não existir
     */
    public Livro buscarLivroPorCodigo(long isbn) {
        for (Livro livro : livros) {
            if (livro.getIsbn() == isbn) {
                return livro;
            }
        }
        return null;
    }

    /* ==========================================================
       ATUALIZAR LIVRO
       ========================================================== */

    /**
     * Atualiza um livro existente com base no ISBN.
     * O ISBN não pode ser alterado.
     *
     * Após atualizar os dados, o arquivo é reescrito completamente.
     *
     * @param livroAtualizado objeto contendo os novos dados
     * @return true se o livro foi atualizado, false caso contrário
     */
    public boolean atualizarLivro(Livro livroAtualizado) {

        List<Livro> novaLista = new ArrayList<>();
        limparArquivo(arquivoLivros);

        for (Livro l : livros) {
            if (l.getIsbn() == livroAtualizado.getIsbn()) {
                // isbn não é editável
                l.setNome(livroAtualizado.getNome());
                l.setCategoria(livroAtualizado.getCategoria());
                l.setAutor(livroAtualizado.getAutor());
                l.setEditora(livroAtualizado.getEditora());
                l.setQtdDisponivel(livroAtualizado.getQtdDisponivel());
                l.setQtdTotal(livroAtualizado.getQtdTotal());
                l.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
            }
            novaLista.add(l);
            escreverLivroNoArquivo(l);
        }

        livros = novaLista;
        return true;
    }

    /* ==========================================================
       REMOVER LIVRO
       ========================================================== */

    /**
     * Remove um livro com o ISBN informado.
     * Após a remoção, o arquivo é reescrito completamente.
     *
     * @param isbn código ISBN do livro a ser removido
     * @return true se a remoção foi bem-sucedida, false caso ocorra algum erro
     */
    public boolean removerLivro(long isbn) {

        List<Livro> novaLista = new ArrayList<>();

        try {
            limparArquivo(arquivoLivros);

            for (Livro l : livros) {
                if (l.getIsbn() != isbn) {
                    novaLista.add(l);
                    escreverLivroNoArquivo(l);
                }
            }

            livros = novaLista;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ==========================================================
       LIMPAR ARQUIVO
       ========================================================== */

    /**
     * Limpa completamente o conteúdo do arquivo informado.
     * Abrir o arquivo no modo padrão já apaga o conteúdo.
     *
     * @param arquivo caminho do arquivo a ser limpo
     */
    private void limparArquivo(Path arquivo) {
        try (OutputStream os = new FileOutputStream(arquivo.toString())) {
            // Abrir já apaga o arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ==========================================================
       ESCREVER LIVRO NO ARQUIVO
       ========================================================== */

    /**
     * Escreve um único livro no arquivo de armazenamento, adicionando-o ao final.
     *
     * @param livro livro a ser gravado
     */
    public void escreverLivroNoArquivo(Livro livro) {

        String linha = livro.getAnoPublicacao() + ";" +
                livro.getNome() + ";" +
                livro.getCategoria() + ";" +
                livro.getAutor() + ";" +
                livro.getEditora() + ";" +
                livro.getIsbn() + ";" +
                livro.getQtdDisponivel() + ";" +
                livro.getQtdTotal();

        try {
            Files.write(arquivoLivros, linha.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoLivros, "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
