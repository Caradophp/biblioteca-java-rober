package ui;

import entidades.Aluno;
import entidades.Emprestimo;
import entidades.Livro;

import java.util.ArrayList;

public class MenuAluno {
    private final Aluno alunoLogado;

    public MenuAluno(Aluno alunoLogado) {
        this.alunoLogado = alunoLogado;
    }

    public void iniciar() {
        System.out.println("============================");
        System.out.println("Bem-vindo, aluno " + this.alunoLogado.getNome().split(" ")[0]);
        System.out.println("0. Sair");
        System.out.println("1. Consultar livros");
        System.out.println("2. Consultar livros emprestados");

        int escolha = MenuUtils.lerOpcaoMenu(2, true);
        switch (escolha) {
            case 0:
                break;
            case 1:
                this.menuConsultarlivros();
                break;
            case 2:
                this.menuLivrosEmprestados();
                break;
        }
    }

    private void menuConsultarlivros() {
        System.out.println("===================================\n");
        System.out.println("0. Voltar");
        System.out.println("1. Buscar livros por título");
        System.out.println("2. Buscar livros por categoria");
        System.out.println("3. Buscar livro por ISBN");

        int escolha = MenuUtils.lerOpcaoMenu(3, true);
        switch (escolha) {
            case 0:
                this.iniciar();
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
            this.menuConsultarlivros();
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
        this.menuConsultarlivros();
    }

    private void menuLivrosEmprestados() {
        // todo: busca os empréstimos do aluno
        // ArrayList<Emprestimo> emprestimos = this.alunoLogado.getEmprestimos();
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        System.out.println("===============================\n");
        System.out.println("0. Voltar");

        // todo: listar os empréstimos

        System.out.println("Selecione um livro para renová-lo:");
        int escolha = MenuUtils.lerOpcaoMenu(emprestimos.size(), true);
        if (escolha == 0) {
            this.iniciar();
        } else {
            confirmarRenovacaoLivro(emprestimos.get(escolha - 1));
        }
    }

    private void confirmarRenovacaoLivro(Emprestimo emprestimo) {
        System.out.println("===========================");
        // todo: pegar livro a partir do emprestimo
        //System.out.println("Confirma a renovação do livro '%s'? (S/n)\n", livro.getNome());

        System.out.println("> ");

        // não importa o que for digitado, apenas volta para o menu de consulta de livros
        String escolha = MenuInicial.reader.nextLine().trim();
        if (!escolha.equals("n")) {
            // todo: renovação do livro
            //emprestimo.renovar();
            System.out.printf("Livro renovado para a data %s.\n", emprestimo.getDataDevolucao().toString());
        } else {
            System.out.println("Renovação do livro cancelada.");
            this.menuLivrosEmprestados();
        }
    }
}
