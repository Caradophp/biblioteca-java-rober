package ui;

import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Pessoa;

import java.util.Scanner;

public class MenuInicial {
    // scanner usado em todas as classes
    public static final Scanner reader = new Scanner(System.in);
    private final Biblioteca biblioteca;

    public MenuInicial(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void iniciar() {
        System.out.println("============================");
        System.out.println("     SISTEMA BIBLIOTECA     ");
        System.out.println("============================");
        System.out.println();
        System.out.println("0. Encerrar programa");
        System.out.println("1. Fazer login");

        int opcao = MenuUtils.lerOpcaoMenu(1, true);
        switch (opcao) {
            case 0:
                reader.close();
                System.exit(0);
            case 1:
                this.menuLogin();
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
            this.iniciar();
        } else if (pessoaLogada instanceof Aluno) {
            MenuAluno menuAluno = new MenuAluno((Aluno) pessoaLogada);
            menuAluno.iniciar();
        } else if (pessoaLogada instanceof Bibliotecario) {
            MenuBibliotecario menuBibliotecario = new MenuBibliotecario((Bibliotecario) pessoaLogada);
            menuBibliotecario.iniciar();
        }
    }
}
