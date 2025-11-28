package ui;

import biblioteca.Biblioteca;
import entidades.Emprestimo;
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

    public static void listarEmprestimos(List<Emprestimo> emprestimos) {
        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo e = emprestimos.get(i);
            Livro l = e.getLivro();
            boolean estaAtrasado = e.estaAtrasado();

            if (estaAtrasado) {
                System.out.println("  (EM ATRASO)");
            }
            System.out.printf("%d. %s\n", i+1, l.getNome());
            System.out.printf("  Emprestado em: %s\n", e.getDataEmprestimo());
            System.out.printf("  Deve devolver em: %s\n", e.getDataDevolucao());
            // todo: adicionar o campo qtdRenovacoes
            //System.out.printf("Renovações: %d de %d", e.getQtdRenovacoes(), QTD_MAXIMA_RENOVACOES);
            if (estaAtrasado) {
                System.out.printf("  Multa a ser paga: %.2f\n", e.calcularMulta());
            }
        }
    }
}
