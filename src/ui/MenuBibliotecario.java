package ui;

import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Emprestimo;
import entidades.Livro;

import java.time.Year;
import java.util.List;

/**
 * Classe responsável por exibir e gerenciar o menu de operações disponíveis
 * para o bibliotecário. Permite realizar empréstimos, devoluções, além da
 * gestão de livros e alunos cadastrados na biblioteca.
 */
public class MenuBibliotecario {

    /** Objeto que representa o bibliotecário atualmente logado. */
    private final Bibliotecario bibliotecarioLogado;

    /** Referência ao menu inicial, utilizada para acessar a biblioteca. */
    private final MenuInicial menuInicial;

    /**
     * Construtor que inicializa o menu do bibliotecário.
     *
     * @param bibliotecarioLogado bibliotecário autenticado
     * @param menuInicial         referência ao menu inicial do sistema
     */
    public MenuBibliotecario(Bibliotecario bibliotecarioLogado, MenuInicial menuInicial) {
        this.bibliotecarioLogado = bibliotecarioLogado;
        this.menuInicial = menuInicial;
    }

    /**
     * Inicia o menu principal do bibliotecário, exibindo opções como
     * consultar livros, emprestar e devolver livros, e gerenciar o cadastro
     * de livros e alunos.
     *
     * O menu permanece em loop até que o bibliotecário escolha sair.
     */
    public void iniciar() {
        while (true) {
            System.out.println("===================================");
            System.out.printf("Bem-vindo, bibliotecário %s\n\n", this.bibliotecarioLogado.getNomeAbreviado());
            System.out.println("0. Sair");
            System.out.println("1. Consultar livros");
            System.out.println("2. Emprestar livro para aluno");
            System.out.println("3. Ver empréstimos de aluno e devolver livro");
            System.out.println("4. Gestão de livros");
            System.out.println("5. Gestão de alunos");

            int escolha = MenuUtils.lerOpcaoMenu(5, true);
            switch (escolha) {
                case 0:
                    return;
                case 1:
                    MenusGlobais.menuConsultarLivro(menuInicial.getBiblioteca());
                    break;
                case 2:
                    menuEmprestarLivro();
                    break;
                case 3:
                    menuVerEmprestimosAluno();
                    break;
                case 4:
                    menuGestaoLivros();
                    break;
                case 5:
                    menuGestaoAlunos();
                    break;
            }
        }
    }

    /**
     * Exibe opções para selecionar um aluno e emprestar um livro a ele.
     * Caso o aluno ou o livro não sejam encontrados, retorna ao menu anterior.
     */
    public void menuEmprestarLivro() {
        Aluno aluno = MenuUtils.lerAluno(menuInicial.getBiblioteca());
        if (aluno == null) {
            return;
        }

        while (true) {
            System.out.printf("\nEmprestar livro para o aluno '%s' de matrícula %d:\n", aluno.getNomeAbreviado(), aluno.getMatricula());
            Livro livro = MenusGlobais.menuBuscarPorLivro(menuInicial.getBiblioteca(),
                    "Selecione um livro (pelo número da lista) para emprestá-lo:");
            if (livro == null) {
                return;
            }

            boolean confirmado = MenuUtils.aguardarConfirmacao("===============================\n" +
                    String.format("Confirma o empréstimo do livro '%s' para o aluno '%s'?",
                            livro.getNome(), aluno.getNomeAbreviado()));

            if (!confirmado) {
                System.out.println("Empréstimo do livro cancelado.");
                return;
            }

            Emprestimo emprestimo = new Emprestimo(aluno.getMatricula(), livro.getIsbn());
            if (menuInicial.getBiblioteca().fazerEmprestimo(emprestimo)) {
                System.out.println("O livro foi emprestado com sucesso.");
            }
        }
    }

    /**
     * Exibe os empréstimos pendentes de um aluno selecionado e permite
     * realizar a devolução de um livro.
     */
    public void menuVerEmprestimosAluno() {
        Aluno aluno = MenuUtils.lerAluno(menuInicial.getBiblioteca());
        if (aluno == null) {
            return;
        }

        while (true) {
            List<Emprestimo> emprestimosAluno = menuInicial.getBiblioteca().buscarEmprestimosPendentes(aluno);
            Emprestimo emprestimo = MenusGlobais.menuSelecaoEmprestimo(
                    emprestimosAluno,
                    "Selecione um livro para devolvê-lo à biblioteca: ",
                    true
            );

            if (emprestimo == null) {
                return;
            }

            boolean confirmado = MenuUtils.aguardarConfirmacao("===================================\n" +
                    String.format("Confirma a devolução do livro '%s'?", emprestimo.getLivro().getNome()) +
                    (emprestimo.estaAtrasado() ? " Tenha certeza que a multa de atraso foi paga" : ""));

            if (!confirmado) {
                continue;
            }

            boolean devolvido = menuInicial.getBiblioteca().devolverLivro(emprestimo.getCodigoEmprestimo());
            if (devolvido) {
                System.out.println("Livro devolvido com sucesso.");
            }
        }
    }

    /**
     * Exibe o menu de gestão de livros, permitindo adicionar, editar
     * ou remover livros da biblioteca.
     */
    public void menuGestaoLivros() {
        while (true) {
            System.out.println("===================================");
            System.out.println("0. Voltar");
            System.out.println("1. Adicionar livro na biblioteca");
            System.out.println("2. Editar livro da biblioteca");
            System.out.println("3. Remover livro da biblioteca");

            int escolha = MenuUtils.lerOpcaoMenu(3, true);
            switch (escolha) {
                case 0:
                    return;
                case 1:
                    menuCadastrarLivro();
                    break;
                case 2:
                    menuEditarLivro();
                    break;
                case 3:
                    menuRemoverLivro();
                    break;
            }
        }
    }

    /**
     * Coleta dados via entrada do usuário para criar um novo livro
     * e enviá-lo para cadastro na biblioteca.
     */
    public void menuCadastrarLivro() {
        Livro livro = new Livro();

        System.out.println("Informe dados para inclusão de um novo livro:");
        long isbn = MenuUtils.lerNovoISBN(menuInicial.getBiblioteca());
        if (isbn == 0) {
            return;
        }

        livro.setIsbn(isbn);
        livro.setNome(MenuUtils.lerString("Nome do livro: "));
        livro.setEditora(MenuUtils.lerString("Editora: "));
        livro.setAnoPublicacao(Year.of(MenuUtils.lerInt("Ano de Publicação: ")));
        livro.setAutor(MenuUtils.lerString("Nome do autor: "));
        livro.setCategoria(MenuUtils.lerCategoria());
        livro.setQtdTotal(MenuUtils.lerInt("Número de cópias: "));
        livro.setQtdDisponivel(livro.getQtdTotal());

        if (menuInicial.getBiblioteca().cadastrarLivro(livro)) {
            System.out.println("Livro incluído com sucesso.");
        } else {
            System.out.println("Algum erro ocorreu que impediu a inclusão do livro. Tente novamente.");
        }
    }

    /**
     * Permite selecionar um livro e editar seus dados.
     * Campos vazios não são alterados.
     */
    public void menuEditarLivro() {
        Livro livro = MenusGlobais.menuBuscarPorLivro(menuInicial.getBiblioteca(),
                "Selecione um livro (pelo número da lista) para editar os dados dele: ");
        if (livro == null) { return; }

        String nome, editora, autor, categoria, anoPublicacao, qtdTotal;
        System.out.println(livro);
        System.out.println("\nInforme dados para alterar as informações acima (Enter para não alterar): ");
        System.out.println("(Não é possível editar o ISBN nem a quantidade de cópias disponíveis)");

        nome = MenuUtils.lerString("Nome: ");
        if (!nome.isEmpty()) {
            livro.setNome(nome);
        }

        editora = MenuUtils.lerString("Editora: ");
        if (!editora.isEmpty()) {
            livro.setEditora(editora);
        }

        anoPublicacao = MenuUtils.lerString("Ano de publicação: ");
        if (!anoPublicacao.isEmpty()) {
            try {
                livro.setAnoPublicacao(Year.of(Integer.parseInt(anoPublicacao)));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Ano de publicação não alterado.");
            }
        }

        autor = MenuUtils.lerString("Nome do autor: ");
        if (!autor.isEmpty()) {
            livro.setAutor(autor);
        }

        categoria = MenuUtils.lerCategoria();
        if (!categoria.isEmpty()) {
            livro.setCategoria(categoria);
        }

        qtdTotal = MenuUtils.lerString("Quantidade total de cópias: ");
        if (!qtdTotal.isEmpty()) {
            try {
                int intQtdTotal = Integer.parseInt(qtdTotal);
                int qtdEmprestada = livro.getQtdTotal() - livro.getQtdDisponivel();
                if (intQtdTotal >= qtdEmprestada) {
                    livro.setQtdTotal(Integer.parseInt(qtdTotal));
                } else {
                    System.out.println("Existem mais livros emprestados do que a quantidade total informada. Não foi possível alterar o valor.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Quantidade total de cópias não alterada.");
            }
        }

        if (menuInicial.getBiblioteca().atualizarLivro(livro)) {
            System.out.println("Os dados do livro foram atualizados com sucesso.");
        } else {
            System.out.println("Algum erro impediu a atualização dos dados do livro. Tente novamente.");
        }
    }

    /**
     * Permite selecionar um livro e removê-lo da biblioteca,
     * com confirmação do bibliotecário.
     */
    public void menuRemoverLivro() {
        Livro livro = MenusGlobais.menuBuscarPorLivro(menuInicial.getBiblioteca(),
                "Selecione um livro (pelo número da lista) para removê-lo da biblioteca: ");
        if (livro == null) { return; }

        boolean confirmado = MenuUtils.aguardarConfirmacao("=================================\n" +
                String.format("Confirma a exclusão do livro '%s'?", livro.getNome()));

        if (confirmado) {
            if (menuInicial.getBiblioteca().removerLivro(livro.getIsbn())) {
                System.out.println("Livro excluído com sucesso.");
            } else {
                System.out.println("Algum erro impediu a exclusão do livro. Tente novamente.");
            }
        } else {
            System.out.println("Exclusão do livro cancelada.");
        }
    }

    /**
     * Exibe o menu de gestão de alunos, permitindo cadastrar, editar
     * ou remover alunos da biblioteca.
     */
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
                    return;
                case 1:
                    menuCadastrarAluno();
                    break;
                case 2:
                    menuEditarAluno();
                    break;
                case 3:
                    menuRemoverAluno();
                    break;
            }
        }
    }

    /**
     * Coleta dados via entrada do usuário para cadastrar um novo aluno.
     */
    public void menuCadastrarAluno() {
        Aluno aluno = new Aluno();

        System.out.println("Informe dados para inclusão de um novo aluno:");
        long matricula = MenuUtils.lerNovaMatricula(menuInicial.getBiblioteca());
        if (matricula == 0) {
            return;
        }

        aluno.setMatricula(matricula);
        aluno.setNome(MenuUtils.lerString("Nome completo: "));
        aluno.setCurso(MenuUtils.lerString("Curso: "));
        aluno.setTelefone(MenuUtils.lerLong("Telefone: "));
        aluno.setEmail(MenuUtils.lerString("Email: "));
        aluno.setSenha(MenuUtils.lerString("Senha: "));

        if (menuInicial.getBiblioteca().cadastrarAluno(aluno)) {
            System.out.println("Aluno incluído com sucesso.");
        } else {
            System.out.println("Algum erro ocorreu que impediu a inclusão do aluno. Tente novamente.");
        }
    }

    /**
     * Permite editar os dados de um aluno selecionado.
     * Campos vazios não são alterados.
     */
    public void menuEditarAluno() {
        Aluno aluno = MenuUtils.lerAluno(menuInicial.getBiblioteca());
        if (aluno == null) { return; }

        String nome, curso, email, senha, telefone;
        System.out.println(aluno);
        System.out.println("\nInforme dados para alterar as informações acima (Enter para não alterar): ");
        System.out.println("(Não é possível editar a matrícula)");

        nome = MenuUtils.lerString("Nome completo: ");
        if (!nome.isEmpty()) {
            aluno.setNome(nome);
        }

        curso = MenuUtils.lerString("Curso: ");
        if (!curso.isEmpty()) {
            aluno.setCurso(curso);
        }

        telefone = MenuUtils.lerString("Telefone: ");
        if (!telefone.isEmpty()) {
            try {
                aluno.setTelefone(Long.parseLong(telefone));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Telefone não alterado.");
            }
        }

        email = MenuUtils.lerString("Email: ");
        if (!email.isEmpty()) {
            aluno.setEmail(email);
        }

        senha = MenuUtils.lerString("Nova senha: ");
        if (!senha.isEmpty()) {
            aluno.setSenha(senha);
        }

        if (menuInicial.getBiblioteca().atualizarAluno(aluno)) {
            System.out.println("Os dados do aluno foram atualizados com sucesso.");
        } else {
            System.out.println("Algum erro impediu a atualização dos dados do aluno. Tente novamente.");
        }
    }

    /**
     * Permite remover um aluno do sistema, mediante confirmação.
     */
    public void menuRemoverAluno() {
        Aluno aluno = MenuUtils.lerAluno(menuInicial.getBiblioteca());
        if (aluno == null) { return; }

        boolean confirmado = MenuUtils.aguardarConfirmacao("=================================\n" +
                String.format("Confirma a exclusão do aluno '%s'?", aluno.getNome()));

        if (confirmado) {
            if (menuInicial.getBiblioteca().removerAluno(aluno.getMatricula())) {
                System.out.println("Aluno excluído com sucesso.");
            } else {
                System.out.println("Algum erro impediu a exclusão do aluno. Tente novamente.");
            }
        } else {
            System.out.println("Exclusão do aluno cancelada.");
        }
    }
}
