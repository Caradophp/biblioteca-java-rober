package ui;

import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Pessoa;

import java.util.List;
import java.util.Scanner;

public class MenuInicial {
    // scanner usado em todas as classes
    public static final Scanner reader = new Scanner(System.in);
    private final Biblioteca biblioteca;

    public MenuInicial(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Biblioteca getBiblioteca() {
        return this.biblioteca;
    }

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
                        // fecha o Scanner e então garante que o programa encerre
                        reader.close();
                        System.exit(0);
                    case 1:
                        this.menuLogin();
                }
            } else {
                // como não existem bibliotecários cadastrados, o usuário recebe o prompt para criar o primeiro
                System.out.println("Nenhum bibliotecário cadastrado no momento.");
                this.cadastrarBibliotecario();
                this.iniciar();
            }
        }
    }

    private void menuLogin() {
        System.out.println("\n==========================");
        System.out.println("          LOGIN\n");
        System.out.print("Email: ");
        String email = reader.nextLine();

        System.out.print("Senha: ");
        String senha = reader.nextLine();

        Pessoa pessoaLogada = this.biblioteca.fazerLogin(email, senha);
        if (pessoaLogada == null) {
            System.out.println("Login inválido. Favor tentar novamente.\n");
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

    private void cadastrarBibliotecario() {
        Bibliotecario bibliotecario = new Bibliotecario();

        System.out.println("Informe dados para inclusão de um novo bibliotecário.");
        bibliotecario.setRegistro(MenuUtils.lerLong("Número de registro: "));
        bibliotecario.setNome(MenuUtils.lerString("Nome: "));
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
