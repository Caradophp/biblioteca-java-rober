package ui;

import biblioteca.Biblioteca;
import entidades.Emprestimo;
import entidades.Livro;
import utils.Utils;

import java.util.List;

/**
 * Classe que contém menus reutilizáveis e globais do sistema,
 * como consulta de livros e seleção de empréstimos.
 */
public class MenusGlobais {

    /**
     * Exibe as informações de um livro selecionado pelo usuário.
     * O método apresenta um menu interno para buscar o livro,
     * imprime seus detalhes e aguarda o usuário pressionar Enter.
     *
     * @param biblioteca instância da biblioteca usada para realizar buscas
     */
    public static void menuConsultarLivro(Biblioteca biblioteca) {
        Livro l = menuBuscarPorLivro(biblioteca, "Selecione um livro (pelo número da lista) para ver mais informaçõe sobre ele: ");

        if (l != null) {
            System.out.println(l);
            System.out.println("\n(Enter para continuar)");
            MenuUtils.lerString("> ");
        }
    }

    /**
     * Exibe um menu permitindo ao usuário buscar livros por nome,
     * categoria ou ISBN. Após a busca, exibe outro menu para que
     * o usuário selecione um livro da lista retornada.
     *
     * @param biblioteca   instância da biblioteca usada para realizar buscas
     * @param textoSelecao texto exibido na tela para solicitar a seleção do livro
     * @return o livro selecionado pelo usuário ou {@code null} caso retorne ao menu anterior
     */
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
                    if (!nomeLivro.isEmpty()) {
                        livro = menuSelecaoLivro(biblioteca.buscarLivrosPorNome(nomeLivro), textoSelecao, true);
                    }
                    break;
                case 2:
                    String categoria = MenuUtils.lerCategoria();
                    if (!categoria.isEmpty()) {
                        livro = menuSelecaoLivro(biblioteca.buscarLivrosPorCategoria(categoria), textoSelecao, true);
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

    /**
     * Exibe uma lista de livros para seleção, numerando cada item.
     * Permite retornar ao menu anterior caso {@code temOpcaoVoltar} seja verdadeiro.
     *
     * @param livros        lista de livros a serem exibidos para escolha
     * @param textoSelecao  texto exibido antes da seleção final
     * @param temOpcaoVoltar se {@code true}, inclui a opção "0 - Voltar"
     * @return o livro selecionado ou {@code null} se o usuário escolher voltar
     */
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
            System.out.println("\n" + textoSelecao);
        }

        int escolha = MenuUtils.lerOpcaoMenu(livros.size(), temOpcaoVoltar);
        return escolha == 0 ? null : livros.get(escolha-1);
    }

    /**
     * Exibe uma lista de empréstimos para seleção, mostrando detalhes como
     * datas, renovações e possíveis atrasos. Permite voltar ao menu anterior.
     *
     * @param emprestimos    lista de empréstimos a serem exibidos
     * @param textoSelecao   texto apresentado antes da seleção
     * @param temOpcaoVoltar indica se a opção de voltar deve ser exibida
     * @return o empréstimo selecionado ou {@code null} caso o usuário volte
     */
    public static Emprestimo menuSelecaoEmprestimo(List<Emprestimo> emprestimos, String textoSelecao, boolean temOpcaoVoltar) {
        System.out.println("=====================================");
        if (emprestimos.isEmpty()) {
            System.out.println("Nenhum empréstimo de livro encontrado");
            return null;
        }

        if (temOpcaoVoltar) {
            System.out.println("0. Voltar");
        }

        for (int i = 0; i < emprestimos.size(); i++) {
            Emprestimo e = emprestimos.get(i);
            Livro l = e.getLivro();
            boolean estaAtrasado = e.estaAtrasado();

            System.out.println();
            if (estaAtrasado) {
                System.out.println("  (EM ATRASO)");
            }
            System.out.printf("%d. %s\n", i+1, l.getNome());
            System.out.printf("  Emprestado em: %s\n", e.getDataEmprestimoFormatada());
            System.out.printf("  Deve devolver até: %s\n", e.getDataDevolucaoPrevistaFormatada());
            System.out.printf("  Renovações: %d de %d\n", e.getQtdRenovacoes(), (int) Utils.QTD_MAXIMA_RENOVACOES.getVal());
            if (estaAtrasado) {
                System.out.printf("  Multa a ser paga: %.2f\n", e.calcularMulta());
            }
        }

        if (!textoSelecao.isEmpty()) {
            System.out.println("\n" + textoSelecao);
        }

        int escolha = MenuUtils.lerOpcaoMenu(emprestimos.size(), temOpcaoVoltar);
        return escolha == 0 ? null : emprestimos.get(escolha-1);
    }
}
