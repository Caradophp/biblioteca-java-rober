package ui;

import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Pessoa;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por exibir o menu inicial do sistema de biblioteca.
 * Permite login de alunos e bibliotecários e controla o fluxo inicial
 * do programa, incluindo a criação do primeiro bibliotecário caso não exista.
 */
public class MenuInicial {

    /** Scanner utilizado globalmente pelo sistema para leitura de entrada do usuário. */
    public static final Scanner reader = new Scanner(System.in);

    /** Instância da biblioteca utilizada para operações de cadastro, busca e login. */
    private final Biblioteca biblioteca;

    /**
     * Construtor para inicializar o menu inicial com a biblioteca carregada.
     *
     * @param biblioteca instância da biblioteca do sistema
     */
    public MenuInicial(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    /**
     * Retorna a instância da biblioteca associada ao menu.
     *
     * @return biblioteca do sistema
     */
    public Biblioteca getBiblioteca() {
        return this.biblioteca;
    }

    /**
     * Inicia o menu inicial, exibindo as opções principais.
     * Caso não haja bibliotecários cadastrados, força o cadastro do primeiro.
     * Caso existam, permite login e navegação.
     */
    public void iniciar() {
        while (true) {
            System.out.println("============================");
            System.out.println("     SISTEMA BIBLIOTECA     ");
            System.out.println("============================");

            if (!this.biblioteca.possuiBibliotecarios()) {
                // fluxo padrão do sistema
                System.out.println("0. Encerrar programa");
                System.out.println("1. Fazer login");

                int opcao = MenuUtils.lerOpcaoMenu(1, true);
                switch (opcao) {
                    case 0:
                        // fecha o Scanner e encerra o programa corretamente
                        reader.close();
                        System.exit(0);
                    case 1:
                        this.menuLogin();
                }
            } else {
                // fluxo para cadastro de bibliotecário quando não há nenhum registrado
                System.out.println("Nenhum bibliotecário cadastrado no momento.");
                this.cadastrarBibliotecario();
                this.iniciar();
            }
        }
    }

    /**
     * Exibe o menu de login, solicitando email e senha.
     * Permite o acesso de alunos e bibliotecários,
     * encaminhando-os aos respectivos menus.
     */
    private void menuLogin() {
        System.out.println("\n==========================");
        System.out.println("          LOGIN");
        System.out.print("Email: ");
        String email = reader.nextLine();

        System.out.print("Senha: ");
        String senha = reader.nextLine();

        Pessoa pessoaLogada = this.biblioteca.fazerLogin(email, senha);

        if (pessoaLogada == null) {
            System.out.println("\nLogin inválido. Favor tentar novamente.");
            // retorna ao menu inicial
            return;
        } else if (pessoaLogada instanceof Aluno) {
            MenuAluno menuAluno = new MenuAluno((Aluno) pessoaLogada, this);
            menuAluno.iniciar();
        } else if (pessoaLogada instanceof Bibliotecario) {
            MenuBibliotecario menuBibliotecario = new MenuBibliotecario((Bibliotecario) pessoaLogada, this);
            menuBibliotecario.iniciar();
        }
    }

    /**
     * Realiza o cadastro do primeiro bibliotecário.
     * Esse método é chamado automaticamente caso o sistema não possua nenhum bibliotecário cadastrado.
     */
    private void cadastrarBibliotecario() {
        Bibliotecario bibliotecario = new Bibliotecario();

        System.out.println("Informe dados para inclusão de um novo bibliotecário.");
        bibliotecario.setRegistro(MenuUtils.lerLong("Número de registro: "));
        bibliotecario.setNome(MenuUtils.lerString("Nome completo: "));
        bibliotecario.setTelefone(MenuUtils.lerLong("Telefone: "));
        bibliotecario.setEmail(MenuUtils.lerString("Email: "));
        bibliotecario.setSenha(MenuUtils.lerString("Senha: "));

        if (this.biblioteca.inserirBibliotecario(bibliotecario)) {
            System.out.println("Bibliotecário incluído com sucesso");
        } else {
            System.out.println("Algum erro ocorreu que não permitiu a inclusão do bibliotecário. Tente novamente.");
        }
    }
}
