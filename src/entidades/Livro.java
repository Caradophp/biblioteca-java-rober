package entidades;

import java.time.Year;
import java.util.List;

/**
 * Representa um Livro no acervo da biblioteca.
 */
public class Livro {

    /** Título do livro. */
    private String nome;
    /** Categoria ou gênero do livro. */
    private String categoria;
    /** Autor do livro. */
    private String autor;
    /** Editora responsável pela publicação. */
    private String editora;
    /** Número ISBN (International Standard Book Number). */
    private long isbn;
    /** Quantidade de exemplares disponíveis para empréstimo. */
    private int qtdDisponivel;
    
    private Year anoPublicacao;

    /** Lista de categorias aceitas */
    public static final List<String> categoriasPermitidas = List.of("Ação", "Aventura", "Romance", "T.I.");

    /**
     * Construtor padrão.
     */
    public Livro() {
    }

    /**
     * Construtor com todos os atributos.
     * @param codigo Código único do livro.
     * @param nome Título do livro.
     * @param categoria Categoria/gênero.
     * @param autor Autor do livro.
     * @param editora Editora.
     * @param isbn Número ISBN.
     * @param qtdDisponivel Quantidade disponível.
     */
    public Livro(String nome, String categoria, String autor, String editora, int isbn, int qtdDisponivel) {
        this.nome = nome;
        this.categoria = categoria;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.qtdDisponivel = qtdDisponivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {

        if (possuiCategoriaValida(categoria)) {
            this.categoria = categoria;
            return;
        }

        System.out.println("Essa categoria não é suportada pelo sistema");
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(int qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    public Year getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(Year anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	/**
     * Sobrescreve o método toString para representação em string do objeto.
     * @return Uma string contendo os dados do Livro.
     */
    @Override
    public String toString() {
        return "Livro{" +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", isbn=" + isbn +
                ", qtdDisponivel=" + qtdDisponivel +
                '}';
    }

    /**
     * Verifica se a categoria informada está nas lista de categorias aceitas pelo sistema
     * @return true se estiver na lista e false caso não esteja
     */
    public boolean possuiCategoriaValida(String categoria) {
        return categoriasPermitidas.contains(categoria);
    }

    public static String getStringCategoriasPermitidas() {
        String categorias = "(";
        for (int i = 0; i < categoriasPermitidas.size(); i++) {
            if (i == categoriasPermitidas.size()-1) {
                categorias += categoriasPermitidas.get(i) + ")";
            } else {
                categorias += categoriasPermitidas.get(i) + "|";
            }
        }
        return categorias;
    }
}