package ui;

import entidades.Aluno;
import entidades.Emprestimo;

import java.util.List;

/**
 * Classe responsável pelo menu apresentado ao aluno após realizar login.
 * Permite consultar livros disponíveis, visualizar empréstimos pendentes
 * e realizar a renovação de empréstimos.
 */
public class MenuAluno {

    /** Objeto que representa o aluno atualmente logado no sistema. */
    private final Aluno alunoLogado;

    /** Referência ao menu inicial, utilizada para retornar ou acessar a biblioteca. */
    private final MenuInicial menuInicial;

    /**
     * Construtor para inicializar o menu do aluno.
     *
     * @param alunoLogado aluno que efetuou login
     * @param menuInicial referência ao menu inicial do sistema
     */
    public MenuAluno(Aluno alunoLogado, MenuInicial menuInicial) {
        this.alunoLogado = alunoLogado;
        this.menuInicial = menuInicial;
    }

    /**
     * Inicia o menu principal do aluno, permitindo escolher entre consultar livros
     * ou consultar seus empréstimos pendentes.
     *
     * O menu permanece em loop até que o aluno selecione a opção de sair.
     */
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

    /**
     * Exibe o menu de empréstimos do aluno, permitindo selecionar um empréstimo
     * e realizar a renovação, caso permitido pelas regras do sistema.
     *
     * Caso não existam empréstimos ou o usuário cancele, retorna ao menu anterior.
     */
    private void menuLivrosEmprestados() {
        while (true) {
            List<Emprestimo> emprestimosAluno =
                    menuInicial.getBiblioteca().buscarEmprestimosPendentes(alunoLogado);

            Emprestimo emprestimo = MenusGlobais.menuSelecaoEmprestimo(
                    emprestimosAluno,
                    "Selecione um livro (pelo número da lista) para renová-lo:",
                    true
            );

            if (emprestimo == null) {
                // volta ao menu anterior
                return;
            }

            boolean confirmado = MenuUtils.aguardarConfirmacao("===============================\n" +
                    String.format("Confirma a renovação do livro '%s'?", emprestimo.getLivro().getNome()));

            if (!confirmado) {
                System.out.println("Renovação do livro cancelada.");
                return;
            }

            boolean renovado = menuInicial.getBiblioteca().renovarEmprestimo(
                    emprestimo.getCodigoEmprestimo()
            );

            if (renovado) {
                System.out.printf(
                        "Livro renovado para a data %s.\n",
                        emprestimo.getDataDevolucaoPrevistaFormatada()
                );
            }
        }
    }
}
