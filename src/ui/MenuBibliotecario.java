package ui;

import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Emprestimo;
import entidades.Livro;

public class MenuBibliotecario {
    private final Bibliotecario bibliotecarioLogado;
    private final MenuInicial menuInicial;

    public MenuBibliotecario(Bibliotecario bibliotecarioLogado, MenuInicial menuInicial) {
        this.bibliotecarioLogado = bibliotecarioLogado;
        this.menuInicial = menuInicial;
    }

    public void iniciar() {
        while (true) {
            System.out.println("===================================");
            System.out.printf("Bem-vindo, bibliotecário %s\n\n", this.bibliotecarioLogado.getNomeAbreviado());
            System.out.println("0. Sair");
            System.out.println("1. Consultar livros");
            System.out.println("2. Emprestar livro para aluno");
            System.out.println("3. Ver empréstimos de um aluno");
            System.out.println("4. Gestão de livros");
            System.out.println("5. Gestão de alunos");

            int escolha = MenuUtils.lerOpcaoMenu(5, true);
            switch (escolha) {
                case 0:
                    // retorna ao menu inicial
                    return;
                case 1:
                    MenusGlobais.menuConsultarLivro(menuInicial.getBiblioteca());
                    break;
                case 2:
                    menuEmprestarLivro();
                    break;
                case 3:
                    // todo
                    menuVerEmprestimosAluno();
                    break;
                case 4:
                    // todo
                    menuGestaoLivros();
                    break;
                case 5:
                    // todo
                    menuGestaoAlunos();
                    break;
            }
        }
    }

    public void menuEmprestarLivro() {
        // lê matrícula do aluno para qual vai emprestar o livro
        Aluno aluno = MenuUtils.lerAluno(menuInicial.getBiblioteca());

        if (aluno == null) {
            // volta ao menu anterior
            return;
        }

        System.out.println("================================");
        System.out.printf("Emprestar livro para o aluno '%s' de matrícula %d\n", aluno.getNomeAbreviado(), aluno.getMatricula());
        Livro livro = MenusGlobais.menuBuscarPorLivro(menuInicial.getBiblioteca(), "Selecione um livro (pelo número da lista) para emprestá-lo:");

        if (livro != null) {
            // todo: remover codigoEmprestimo random
            Emprestimo emprestimo = new Emprestimo((int) (Math.random()*100), aluno.getMatricula(), livro.getIsbn());
            // todo: fazerEmprestimo não altera a qtdDisponivel do livro
            boolean incluiu = menuInicial.getBiblioteca().fazerEmprestimo(emprestimo);

            if (incluiu) {
                System.out.println("O livro foi emprestado para o aluno.");
            } else {
                System.out.println("O livro não foi emprestado para o aluno.");
            }
        }
    }

    public void menuVerEmprestimosAluno() {

    }

    public void menuGestaoLivros() {
        while (true) {
            System.out.println("===================================");
            System.out.println("0. Voltar");
            System.out.println("1. Adicionar livro na biblioteca");
            System.out.println("2. Editar livro da biblioteca");
            System.out.println("3. Remover livro da biblioteac");

            int escolha = MenuUtils.lerOpcaoMenu(3, true);
            switch (escolha) {
                case 0:
                    // retorna ao menu inicial
                    return;
                case 1:
                    menuCadastrarLivro();
                    break;
                case 2:
                    // todo
                    break;
                case 3:
                    // todo
                    break;
            }
        }
    }

    public void menuCadastrarLivro() {
        Livro livro = new Livro();

        System.out.println("Informe dados para inclusão de um novo livro:");
        livro.setNome(MenuUtils.lerString("Título: "));
        livro.setAutor(MenuUtils.lerString("Nome do autor: "));
        livro.setEditora(MenuUtils.lerString("Editora: "));
        livro.setIsbn(MenuUtils.lerInt("ISBN: "));
        //livro.setQtdTotal(MenuUtils.lerInt("Número de cópias: "));
        //livro.setQtdDisponivel(livro.getQtdTotal());

        if (menuInicial.getBiblioteca().cadastrarLivro(livro)) {
            System.out.println("Livro incluído com sucesso.");
        } else {
            System.out.println("Algum erro ocorreu impediu a inclusão do livro. Tente novamente.");
        }
    }

    public void menuEditarLivro() {

    }

    public void menuRemoverLivro() {

    }

    public void menuGestaoAlunos() {
        while (true) {
            System.out.println("===================================");
            System.out.println("0. Voltar");
            System.out.println("1. Adicionar aluno");
            System.out.println("2. Editar aluno");
            System.out.println("3. Remover aluno");

            int escolha = MenuUtils.lerOpcaoMenu(3, true);
            switch (escolha) {
                case 0:
                    // retorna ao menu inicial
                    return;
                case 1:
                    menuCadastrarAluno();
                    break;
                case 2:
                    // todo
                    break;
                case 3:
                    // todo
                    break;
            }
        }
    }

    public void menuCadastrarAluno() {
        Aluno aluno = new Aluno();

        System.out.println("Informe dados para inclusão de um novo aluno:");
        aluno.setMatricula(MenuUtils.lerLong("Matrícula: "));
        aluno.setNome(MenuUtils.lerString("Nome: "));
        aluno.setCurso(MenuUtils.lerString("Curso: "));
        aluno.setTelefone(MenuUtils.lerLong("Telefone: "));
        aluno.setEmail(MenuUtils.lerString("Email: "));
        aluno.setSenha(MenuUtils.lerString("Senha: "));

        if (menuInicial.getBiblioteca().cadastrarAluno(aluno)) {
            System.out.println("Aluno incluído com sucesso.");
        } else {
            System.out.println("Algum erro ocorreu impediu a inclusão do aluno. Tente novamente.");
        }
    }

    public void menuEditarAluno() {

    }

    public void menuRemoverAluno() {

    }
}
