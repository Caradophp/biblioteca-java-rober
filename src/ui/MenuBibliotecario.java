package ui;

import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Livro;

import java.util.List;

public class MenuBibliotecario {
    private final Bibliotecario bibliotecarioLogado;
    private final MenuInicial menuInicial;

    public MenuBibliotecario(Bibliotecario bibliotecarioLogado, MenuInicial menuInicial) {
        this.bibliotecarioLogado = bibliotecarioLogado;
        this.menuInicial = menuInicial;
    }

    public void iniciar() {
        System.out.println("===================================");
        System.out.println("Bem-vindo, bibliotecário " + this.bibliotecarioLogado.getNomeAbreviado());
        System.out.println();
        System.out.println("0. Sair");
        System.out.println("1. Consultar livros");
        System.out.println("2. Emprestar livro para aluno");
        System.out.println("3. Ver empréstimos de um aluno");
        System.out.println("4. Gestão de livros");
        System.out.println("5. Gestão de alunos");

        int escolha = MenuUtils.lerOpcaoMenu(5, true);
        switch (escolha) {
            case 0:
                this.menuInicial.iniciar();
                break;
            case 1:
                MenusGlobais.menuConsultarLivros(this.menuInicial.getBiblioteca(), this::iniciar);
                break;
            case 2:
                this.emprestarLivro();
                break;
        }
    }

    public void emprestarLivro() {
        // lê matrícula do aluno para qual vai emprestar o livro
        long matricula = this.lerMatricula();
        Aluno aluno = this.menuInicial.getBiblioteca().buscarAlunoPorMatricula(matricula);

        System.out.println("================================");
        System.out.printf("Emprestar livro para o aluno '%s' de matrícula %d", aluno.getNomeAbreviado(), aluno.getMatricula());
        List<Livro> livrosFiltrados = MenusGlobais.menuConsultarLivros(this.menuInicial.getBiblioteca(), this::iniciar);

        System.out.println("================================");
        Livro livroSelec = MenusGlobais.menuSelecaoLivrosListados(livrosFiltrados);

        // todo: emprestar livro
    }

    public long lerMatricula() {
        return -1;
    }
}
