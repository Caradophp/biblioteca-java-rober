package ui;

import entidades.Aluno;
import entidades.Emprestimo;
import entidades.Livro;

import java.util.ArrayList;
import java.util.List;

public class MenuAluno {
    private final Aluno alunoLogado;
    private final MenuInicial menuInicial;

    public MenuAluno(Aluno alunoLogado, MenuInicial menuInicial) {
        this.alunoLogado = alunoLogado;
        this.menuInicial = menuInicial;
    }

    public void iniciar() {
        while (true) {
            System.out.println("===================================");
            System.out.printf("Bem-vindo, aluno %s\n\n", alunoLogado.getNomeAbreviado());
            System.out.println("0. Sair");
            System.out.println("1. Consultar livros");
            System.out.println("2. Consultar livros emprestados");

            int escolha = MenuUtils.lerOpcaoMenu(2, true);
            switch (escolha) {
                case 0:
                    // retorna ao menu inicial
                    return;
                case 1:
                    MenusGlobais.menuConsultarLivros(menuInicial.getBiblioteca(), this::iniciar);
                    break;
                case 2:
                    menuLivrosEmprestados();
                    break;
            }
        }
    }

    private void menuLivrosFiltrados(ArrayList<Livro> livros) {
        System.out.println("==================================\n");
        System.out.println("0. Voltar\n");

        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            // todo: livro está sem o ano de publicação
            //System.out.printf("%d. %s (%d)\n", i+1, livro.getNome());
            //System.out.printf("Editora: %s (%d " + (livro.getQtdDisponivel() == 1 ? "cópia disponível" : "cópias disponíveis") + ")\n", livro.getEditora(), livro.getQtdDisponivel());
            //System.out.println();
        }

        System.out.println("Selecione um livro para ver mais informações sobre:");
        int escolha = MenuUtils.lerOpcaoMenu(livros.size(), true);
        if (escolha == 0) {
            MenusGlobais.menuConsultarLivros(menuInicial.getBiblioteca(), this::iniciar);
        } else {
            printDetalheslivro(livros.get(escolha - 1));
        }
    }

    private void printDetalheslivro(Livro livro) {
        // por enquanto usa apenas o método toString(),
        // mas é possível alterar do jeito que quiser
        System.out.println(livro);
        System.out.println("\n> ");

        // não importa o que for digitado, apenas volta para o menu de consulta de livros
        MenuInicial.reader.nextLine();
        MenusGlobais.menuConsultarLivros(menuInicial.getBiblioteca(), this::iniciar);
    }

    private void menuLivrosEmprestados() {
        while (true) {
            List<Emprestimo> emprestimosAluno = menuInicial.getBiblioteca().buscarTodosEmprestimos(alunoLogado);

            System.out.println("===============================\n");
            if (emprestimosAluno.isEmpty()) {
                System.out.println("Você não tem nenhum livro emprestado");
            } else {
                System.out.println("0. Voltar");

                MenusGlobais.listarEmprestimos(emprestimosAluno);

                System.out.println("\nSelecione um livro para renová-lo:");
                int escolha = MenuUtils.lerOpcaoMenu(2, true);
                if (escolha == 0) {
                    // retorna ao menu inicial do aluno
                    return;
                } else {
                    confirmarRenovacaoLivro(emprestimosAluno.get(escolha - 1));
                }
            }
        }
    }

    private void confirmarRenovacaoLivro(Emprestimo emprestimo) {
        System.out.println("===============================");
        System.out.printf("Confirma a renovação do livro '%s'? (S/n)\n", emprestimo.getLivro().getNome());

        boolean confirmado = MenuUtils.aguardarConfirmacao("> ");
        if (!confirmado) {
            menuInicial.getBiblioteca().renovarEmprestimo(emprestimo.getCodigoEmprestimo());
            // todo: testar se a data da variável emprestimo está sendo alterada
            System.out.printf("Livro renovado para a data %s.\n", emprestimo.getDataDevolucao().toString());
        } else {
            System.out.println("Renovação do livro cancelada.");
        }
    }
}
