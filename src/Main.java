import java.awt.*;

import biblioteca.Biblioteca;
import ui.MenuInicial;

public class Main {
    public static void main(String[] args) {
        Biblioteca b = new Biblioteca();

        MenuInicial menuInicial = new MenuInicial(b);
        menuInicial.iniciar();
    }
}