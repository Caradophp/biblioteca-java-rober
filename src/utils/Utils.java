package utils;

/**
 * ENUM com constantes que são usadas em mais de um lugar no projeto
 * */
public enum Utils {
    MULTA_DIA(4),
    AUMENTO_DIAS_RENOVACAO(10),
    QTD_MAXIMA_RENOVACOES(3)
    ;

    private final double val;

    Utils(double val) {
        this.val = val;
    }

    public double getVal() {
        return val;
    }
}
