package ui;

import biblioteca.Biblioteca;
import entidades.Livro;

import java.util.List;

public class MenusGlobais {
    public static List<Livro> menuConsultarLivros(Biblioteca biblioteca, Runnable metodoVoltar) {
        System.out.println("0. Voltar");
        System.out.println("1. Buscar livros por título");
        System.out.println("2. Buscar livros por categoria");
        System.out.println("3. Buscar livro por ISBN");

        int escolha = MenuUtils.lerOpcaoMenu(3, true);
        switch (escolha) {
            case 0:
                metodoVoltar.run();
                break;
            case 1:
                // todo: busca livros filtrados por título
                // menuLivrosFiltrados(livros);
                break;
            case 2:
                // todo: busca livros filtrados por categoria
                // menuLivrosFiltrados(livros);
                break;
            case 3:
                // todo: busca livro por ISBN
                // menuLivrosFiltrados(livros);
                break;
        }

        return null;
    }

    public static Livro menuSelecaoLivrosListados(List<Livro> livros) {
        return null;
    }
}
