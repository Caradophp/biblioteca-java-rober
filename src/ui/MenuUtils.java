package ui;

import java.util.Scanner;

public class MenuUtils {
    public static int lerOpcaoMenu(int numMaiorOpcao, boolean temOpcaoZero) {
        while (true) {
            System.out.print("\n> ");

            try {
                int escolha = Integer.parseInt(MenuInicial.reader.nextLine());

                // a escolha precisa ser menor ou igual ao número de opções existentes e maior que 0,
                // ou então igual a 0, caso exista a opção "Voltar"
                if ((escolha <= numMaiorOpcao && escolha > 0) || (temOpcaoZero && escolha == 0)) {
                    return escolha;
                } else {
                    System.out.println("Opção inválida. Tente novamente. ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Tente novamente apenas com números. ");
            }
        }
    }
}
