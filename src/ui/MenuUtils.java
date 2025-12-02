package ui;

import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Livro;

import java.util.List;

/**
 * Classe utilitária responsável por centralizar métodos de leitura e validação
 * de entradas no console, além de auxiliar na seleção de categorias, alunos,
 * livros e opções de menu.
 */
public class MenuUtils {

    /**
     * Lê uma opção de menu garantindo que ela seja válida.
     * Aceita ou não a opção 0 dependendo do parâmetro {@code temOpcaoZero}.
     *
     * @param numMaiorOpcao maior número permitido no menu
     * @param temOpcaoZero se {@code true}, permite que o usuário escolha 0
     * @return a opção escolhida pelo usuário, garantidamente válida
     */
    public static int lerOpcaoMenu(int numMaiorOpcao, boolean temOpcaoZero) {
        while (true) {
            int escolha = lerInt("> ");
            if ((escolha <= numMaiorOpcao && escolha > 0) || (temOpcaoZero && escolha == 0)) {
                return escolha;
            } else {
                System.out.print("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Lê um número de matrícula que ainda não esteja cadastrado na biblioteca.
     * Caso o usuário digite 0, retorna 0 para indicar cancelamento.
     *
     * @param biblioteca instância da biblioteca usada para verificar duplicidades
     * @return a nova matrícula digitada ou 0 caso o usuário cancele
     */
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

    /**
     * Lê uma matrícula existente na biblioteca e retorna o aluno correspondente.
     * Caso o usuário digite 0, retorna {@code null}.
     *
     * @param biblioteca instância da biblioteca usada para buscar alunos
     * @return o aluno encontrado ou {@code null} caso o usuário volte
     */
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

    /**
     * Lê um ISBN que ainda não esteja cadastrado. Caso o usuário digite 0,
     * retorna 0 para indicar cancelamento.
     *
     * @param biblioteca instância da biblioteca usada para verificar duplicidade
     * @return o novo ISBN válido ou 0 caso o usuário volte
     */
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

    /**
     * Lê um ISBN que já esteja cadastrado e retorna o livro correspondente.
     * Caso o usuário digite 0, retorna {@code null}.
     *
     * @param biblioteca instância utilizada para buscar o livro
     * @return o livro encontrado ou {@code null} caso o usuário volte
     */
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

    /**
     * Exibe as categorias permitidas e permite ao usuário selecionar uma delas.
     * Retorna uma string vazia caso o usuário escolha 0.
     *
     * @return a categoria selecionada ou "" caso o usuário cancele
     */
    public static String lerCategoria() {
        System.out.println("\nCategorias válidas:");
        for (int i = 0; i < Livro.categoriasPermitidas.size(); i++) {
            System.out.printf("%d. %s\n", i+1, Livro.categoriasPermitidas.get(i));
        }
        System.out.println("Digite o número de uma categoria válida (0 para cancelar).");

        int escolha = lerOpcaoMenu(Livro.categoriasPermitidas.size(), true);
        return escolha == 0 ? "" : Livro.categoriasPermitidas.get(escolha-1);
    }

    /**
     * Exibe um prompt de confirmação e aguarda a resposta do usuário.
     * Qualquer resposta diferente de "n" é interpretada como confirmação.
     *
     * @param stringPrompt texto exibido antes da confirmação
     * @return {@code true} caso o usuário confirme, {@code false} caso digite "n"
     */
    public static boolean aguardarConfirmacao(String stringPrompt) {
        if (!stringPrompt.isEmpty()) {
            System.out.println(stringPrompt + " (S/n)");
        }
        System.out.print("> ");

        String escolha = MenuInicial.reader.nextLine().trim().toLowerCase();
        return !escolha.equals("n");
    }

    /**
     * Lê uma string digitada pelo usuário.
     *
     * @param stringPrompt mensagem a ser exibida antes da leitura
     * @return a string digitada (com espaços externos removidos)
     */
    public static String lerString(String stringPrompt) {
        if (!stringPrompt.isEmpty()) {
            System.out.print(stringPrompt);
        }

        return MenuInicial.reader.nextLine().trim();
    }

    /**
     * Lê um número inteiro digitado pelo usuário, garantindo que seja válido.
     *
     * @param stringPrompt mensagem exibida antes da leitura
     * @return o número inteiro digitado
     */
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

    /**
     * Lê um número long digitado pelo usuário, garantindo que seja válido.
     *
     * @param stringPrompt mensagem exibida antes da leitura
     * @return o número long digitado
     */
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
