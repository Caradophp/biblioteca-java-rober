package ui;

import entidades.Aluno;
import entidades.Emprestimo;

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
                    MenusGlobais.menuConsultarLivro(menuInicial.getBiblioteca());
                    break;
                case 2:
                    menuLivrosEmprestados();
                    break;
            }
        }
    }

    private void menuLivrosEmprestados() {
        while (true) {
            List<Emprestimo> emprestimosAluno = menuInicial.getBiblioteca().buscarTodosEmprestimos(alunoLogado);

            System.out.println("===============================\n");
            if (emprestimosAluno.isEmpty()) {
                System.out.println("Você não tem nenhum livro emprestado");
                return;
            } else {
                System.out.println("0. Voltar");

                MenusGlobais.menuSelecaoEmprestimo(emprestimosAluno);

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
