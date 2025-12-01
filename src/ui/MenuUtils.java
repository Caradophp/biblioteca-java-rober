package ui;

import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Livro;

import java.util.List;

public class MenuUtils {
    public static int lerOpcaoMenu(int numMaiorOpcao, boolean temOpcaoZero) {
        while (true) {
            int escolha = lerInt("> ");
            // a escolha precisa ser menor ou igual ao número de opções existentes e maior que 0,
            // ou então igual a 0, caso exista a opção "Voltar"
            if ((escolha <= numMaiorOpcao && escolha > 0) || (temOpcaoZero && escolha == 0)) {
                return escolha;
            } else {
                System.out.print("Opção inválida. Tente novamente.");
            }
        }
    }

    public static long lerNovaMatricula(Biblioteca biblioteca) {
        while (true) {
            long matricula = MenuUtils.lerLong("Matrícula do aluno (0 para voltar): ");

            if (matricula == 0) {
                return 0;
            }

            Aluno aluno = biblioteca.buscarAlunoPorMatricula(matricula);
            if (aluno == null) {
                return matricula;
            } else {
                System.out.println("Já existe um aluno cadastrado com essa matrícula.");
            }
        }
    }

    public static Aluno lerAluno(Biblioteca biblioteca) {
        while (true) {
            System.out.println("============================");
            long matricula = MenuUtils.lerLong("Matrícula do aluno (0 para voltar): ");

            if (matricula == 0) {
                return null;
            }

            Aluno aluno = biblioteca.buscarAlunoPorMatricula(matricula);
            if (aluno != null) {
                return aluno;
            } else {
                System.out.println("Nenhum aluno encontrado com essa matrícula.");
            }
        }
    }

    public static long lerNovoISBN(Biblioteca biblioteca) {
        while (true) {
            long isbn = MenuUtils.lerLong("ISBN do livro (0 para voltar): ");

            if (isbn == 0) {
                return 0;
            }

            Livro livro = biblioteca.buscarLivroPorCodigo(isbn);
            if (livro == null) {
                return isbn;
            } else {
                System.out.println("Já existe um livro cadastrado com esse ISBN.");
            }
        }
    }

    public static Livro lerLivro(Biblioteca biblioteca) {
        while (true) {
            System.out.println("============================");
            long isbn = MenuUtils.lerLong("ISBN do livro (0 para voltar): ");

            if (isbn == 0) {
                return null;
            }

            Livro livro = biblioteca.buscarLivroPorCodigo(isbn);
            if (livro != null) {
                return livro;
            } else {
                System.out.println("Nenhum livro encontrado com esse ISBN.");
            }
        }
    }

    public static String lerCategoria() {
        System.out.println("\nCategorias válidas:");
        for (int i = 0; i < Livro.categoriasPermitidas.size(); i++) {
            System.out.printf("%d. %s\n", i+1, Livro.categoriasPermitidas.get(i));
        }
        System.out.println("Digite o número de uma categoria válida (0 para cancelar).");

        int escolha = lerOpcaoMenu(Livro.categoriasPermitidas.size(), true);
        return escolha == 0 ? "" : Livro.categoriasPermitidas.get(escolha-1);
    }

    public static boolean aguardarConfirmacao(String stringPrompt) {
        if (!stringPrompt.isEmpty()) {
            System.out.println(stringPrompt + " (S/n)");
        }
        System.out.print("> ");

        String escolha = MenuInicial.reader.nextLine().trim().toLowerCase();
        // retorna true para qualquer valor que não seja "n" ou "N"
        return !escolha.equals("n");
    }

    public static String lerString(String stringPrompt) {
        if (!stringPrompt.isEmpty()) {
            System.out.print(stringPrompt);
        }

        return MenuInicial.reader.nextLine().trim();
    }

    public static int lerInt(String stringPrompt) {
        while (true) {
            if (!stringPrompt.isEmpty()) {
                System.out.print(stringPrompt);
            }

            try {
                return Integer.parseInt(MenuInicial.reader.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente apenas com números.");
            }
        }
    }

    public static long lerLong(String stringPrompt) {
        while (true) {
            if (!stringPrompt.isEmpty()) {
                System.out.print(stringPrompt);
            }

            try {
                return Long.parseLong(MenuInicial.reader.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente apenas com números.");
            }
        }
    }
}
