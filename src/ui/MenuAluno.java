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
            Emprestimo emprestimo = MenusGlobais.menuSelecaoEmprestimo(emprestimosAluno, "Selecione um livro (pelo número da lista) para renová-lo:", true);

            if (emprestimo == null) {
                // volta ao menu anterior
                return;
            }

            boolean confirmado = MenuUtils.aguardarConfirmacao("===============================\n" +
                    String.format("Confirma a renovação do livro '%s'?", emprestimo.getLivro().getNome()));
            if (!confirmado) {
                System.out.println("Renovação do livro cancelada.");
                // volta ao menu anterior
                return;
            }

            boolean renovado = menuInicial.getBiblioteca().renovarEmprestimo(emprestimo.getCodigoEmprestimo());
            if (renovado) {
                System.out.printf("Livro renovado para a data %s.\n", emprestimo.getDataDevolucaoPrevistaFormatada());
            }
        }
    }
}
