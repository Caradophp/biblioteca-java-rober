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

public class LivroManipulator {

    private List<Livro> livros = new ArrayList<>();
    private final Path arquivoLivros = Paths.get("livros.txt");

    public LivroManipulator() {
        livros = buscarTodosLivros();
    }

    public List<Livro> getLivros() {
        return livros;
    }

    /* ==========================================================
       CADASTRAR LIVRO
       ========================================================== */
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
    public List<Livro> buscarLivrosPorNome(String nome) {
    	List<Livro> livrosEncontrados = new ArrayList<Livro>();

    	nome = nome.toLowerCase();
        for (Livro livro : livros) {
            if (livro.getNome().toLowerCase().contains(nome)) {
                livrosEncontrados.add(livro);
            }
        }

        return livrosEncontrados;
    }
    
    public List<Livro> buscarLivrosPorCategoria(String categoria) {
    	List<Livro> livrosEncontrados = new ArrayList<Livro>();
    	
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
