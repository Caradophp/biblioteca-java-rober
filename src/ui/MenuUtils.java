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

    public static String lerString(String stringPrompt) {
        if (!stringPrompt.isEmpty()) {
            System.out.print(stringPrompt);
        }

        return MenuInicial.reader.nextLine();
    }

    public static int lerInt(String stringPrompt) {
        while (true) {
            if (!stringPrompt.isEmpty()) {
                System.out.print(stringPrompt);
            }

            try {
                return Integer.parseInt(MenuInicial.reader.nextLine());
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
                return Long.parseLong(MenuInicial.reader.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente apenas com números. ");
            }
        }
    }
}
