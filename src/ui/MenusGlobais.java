package ui;

import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Emprestimo;
import entidades.Livro;

import java.awt.*;
import java.util.List;

public class MenusGlobais {
    public static void menuConsultarLivro(Biblioteca biblioteca) {
        System.out.println("============================");
        Livro l = menuBuscarPorLivro(biblioteca, "Selecione um livro (pelo número da lista) para ver mais informaçõe sobre ele: ");

        if (l != null) {
            System.out.println(l);
            // apenas espera que o usuário digite qualquer coisa para voltar ao menu
            MenuUtils.lerString("> ");
        }
    }

    public static Livro menuBuscarPorLivro(Biblioteca biblioteca, String textoSelecao) {
        Livro livro = null;
        while (true) {
            System.out.println("==================================");
            System.out.println("0. Voltar");
            System.out.println("1. Buscar livros por nome");
            System.out.println("2. Buscar livros por categoria");
            System.out.println("3. Buscar livro por ISBN");

            int escolha = MenuUtils.lerOpcaoMenu(3, true);
            switch (escolha) {
                case 0:
                    return null;
                case 1:
                    String nomeLivro = MenuUtils.lerString("Nome do livro:\n> ");
                    //livro = menuSelecaoLivro(biblioteca.buscarLivrosPorNome(nomeLivro), textoSelecao, true);
                    break;
                case 2:
                    String categoria = MenuUtils.lerCategoria("> ");
                    if (!categoria.isEmpty()) {
                        //livro = menuSelecaoLivro(biblioteca.buscarLivrosPorCategoria(nomeLivro), textoSelecao, true);
                    }
                    break;
                case 3:
                    livro = MenuUtils.lerLivro(biblioteca);
                    break;
            }

            if (livro != null) {
                return livro;
            }
        }
    }

    public static Livro menuSelecaoLivro(List<Livro> livros, String textoSelecao, boolean temOpcaoVoltar) {
        System.out.println("=====================================");
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado com esse filtro");
            return null;
        }

        if (temOpcaoVoltar) {
            System.out.println("0. Voltar");
        }

        for (int i = 0; i < livros.size(); i++) {
            Livro l = livros.get(i);
            System.out.printf("\n%d. %s (%s)\n", i+1, l.getNome(), l.getAnoPublicacao());
            System.out.printf("  Editora: %s\n", l.getEditora());
            System.out.print("  Cópias disponíveis: ");
            if (l.getQtdDisponivel() == 0) {
                System.out.print("0 (Indisponível)\n");
            } else {
                System.out.printf("%d de %d\n", l.getQtdDisponivel(), l.getQtdTotal());
            }
        }

        if (!textoSelecao.isEmpty()) {
            System.out.println(textoSelecao);
        }
        int escolha = MenuUtils.lerOpcaoMenu(livros.size(), temOpcaoVoltar);
        return escolha == 0 ? null : livros.get(escolha-1);
    }

    public static void menuSelecaoEmprestimo(List<Emprestimo> emprestimos) {
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
