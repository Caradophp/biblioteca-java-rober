package ui;

public class MenuUtils {
    public static int lerOpcaoMenu(int numMaiorOpcao, boolean temOpcaoZero) {
        while (true) {
            int escolha = lerInt("\n> ");
            // a escolha precisa ser menor ou igual ao número de opções existentes e maior que 0,
            // ou então igual a 0, caso exista a opção "Voltar"
            if ((escolha <= numMaiorOpcao && escolha > 0) || (temOpcaoZero && escolha == 0)) {
                return escolha;
            } else {
                System.out.println("Opção inválida. Tente novamente. ");
            }
        }
    }

    public static boolean aguardarConfirmacao(String stringPrompt) {
        if (!stringPrompt.isEmpty()) {
            System.out.print(stringPrompt);
        }

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
                System.out.println("Valor inválido. Tente novamente apenas com números. ");
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
                System.out.println("Valor inválido. Tente novamente apenas com números. ");
            }
        }
    }
}
