package entidades;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Representa um Empréstimo de um Livro para um Aluno.
 * Contém a lógica para cálculo de multa e renovação.
 */
public class Emprestimo {

    /** Valor da multa por dia de atraso. */
    private static final double MULTA_DIA = 4.0;

    /** Código único do empréstimo. */
    private long codigoEmprestimo;
    /** Código (matrícula) do aluno que realizou o empréstimo. */
    private long codigoAluno;
    /** Código (registro) do funcionário que registrou o empréstimo. */
    private int codigoFuncionario;
    /** Código do livro emprestado. */
    private long codigoLivro;
    /** Data em que o empréstimo foi realizado. */
    private LocalDate dataEmprestimo;
    /** Data prevista para a devolução. */
    private LocalDate dataDevolucaoPrevista;
    /** Indica se o livro já foi devolvido. */
    private boolean devolvido;

    private int qtdRenovacoes;

    private Livro livro;

    /** Formatador de data para exibição no formato dd/MM/yyyy. */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Random geradorId = new Random();

    private final int QTD_MAXIMA_RENOVACOES = 3;

    /**
     * Construtor padrão.
     * Define a data de empréstimo como hoje e a data de devolução para 10 dias.
     */
    public Emprestimo() {
    	this.codigoEmprestimo = geradorId.nextLong(10000);
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = this.dataEmprestimo.plusDays(10);
    }

    /**
     * Construtor com parâmetros essenciais.
     * @param codigoAluno Código do aluno.
     * @param codigoLivro Código do livro.
     */
    public Emprestimo(long codigoAluno, long codigoLivro) {
        this.codigoEmprestimo = geradorId.nextLong(10000);
        this.codigoAluno = codigoAluno;
        this.codigoLivro = codigoLivro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = this.dataEmprestimo.plusDays(10);
    }

    public long getCodigoEmprestimo() {
        return codigoEmprestimo;
    }

    public void setCodigoEmprestimo(int codigoEmprestimo) {
        this.codigoEmprestimo = codigoEmprestimo;
    }

    public long getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(long codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public long getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(long codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    /**
     * Retorna a data de empréstimo formatada.
     * @return Data de empréstimo no formato dd/MM/yyyy.
     */
    public String getDataEmprestimoFormatada() {
        return dataEmprestimo.format(formatter);
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    /**
     * Retorna a data de devolução formatada.
     * @return Data de devolução no formato dd/MM/yyyy.
     */
    public String getDataDevolucaoPrevistaFormatada() {
        return dataDevolucaoPrevista.format(formatter);
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }

    public int getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(int codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getQtdRenovacoes() {
        return qtdRenovacoes;
    }

    public void setQtdRenovacoes(int qtdRenovacoes) {
        this.qtdRenovacoes = qtdRenovacoes;
    }

    /**
     * Verifica se a data atual já ultrapassou a data de devolução do empréstimo.
     * @return true se o empréstimo está atrasado.
     */



    public boolean estaAtrasado() {
        return dataDevolucaoPrevista.isBefore(LocalDate.now());
    }

    /**
     * Calcula o valor da multa com base nos dias de atraso.
     * @return O valor total da multa.
     */
    public double calcularMulta() {
        Period p = Period.between(dataDevolucaoPrevista, LocalDate.now());
        if (p.getDays() < 0) {
            return 0.0;
        }

        return p.getDays() * MULTA_DIA;
    }

    /**
     * Renova o empréstimo, estendendo a data de devolução por mais 10 dias.
     */
    public boolean renovarEmprestimo() {
        WeekFields wf = WeekFields.of(Locale.getDefault());
        LocalDate hoje = LocalDate.now();

        if (dataDevolucaoPrevista.get(wf.weekOfWeekBasedYear()) == hoje.get(wf.weekOfWeekBasedYear())) {
            System.out.println("Faz pouco tempo que você renovou esse empréstimo, portanto não pode renová-lo novamente.");
            return false;
        }

        if (qtdRenovacoes >= 3) {
            System.out.println("Números máximos de renovações atingido.");
            return false;
        }
        this.setQtdRenovacoes(qtdRenovacoes+1);
        this.dataDevolucaoPrevista = this.dataDevolucaoPrevista.plusDays(10);
        return true;
    }

    public Livro devolverLivro() {

        if (this.devolvido) {
            System.out.println("Esse livro já foi devolvido");
            return null;
        }

        this.devolvido = true;
        int qtdDisponivel = livro.getQtdDisponivel() + 1;
        livro.setQtdDisponivel(qtdDisponivel);
        System.out.println("Livro devolvido com sucesso");
        return livro;
    }

    /**
     * Sobrescreve o método equals para comparação de objetos Emprestimo.
     * @param object O objeto a ser comparado.
     * @return true se os objetos forem considerados iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Emprestimo that = (Emprestimo) object;
        return codigoEmprestimo == that.codigoEmprestimo && codigoAluno == that.codigoAluno && codigoFuncionario == that.codigoFuncionario && codigoLivro == that.codigoLivro && devolvido == that.devolvido && Objects.equals(dataEmprestimo, that.dataEmprestimo) && Objects.equals(dataDevolucaoPrevista, that.dataDevolucaoPrevista) && Objects.equals(formatter, that.formatter);
    }

    /**
     * Sobrescreve o método hashCode.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigoEmprestimo, codigoAluno, codigoFuncionario, codigoLivro, dataEmprestimo, dataDevolucaoPrevista, devolvido, formatter);
    }

    /**
     * Sobrescreve o método toString para representação em string do objeto.
     * @return Uma string contendo os dados do Empréstimo.
     */
    @Override
    public String toString() {
        return "Emprestimo{" +
                "codigoEmprestimo=" + codigoEmprestimo +
                ", codigoAluno=" + codigoAluno +
                ", codigoFuncionario=" + codigoFuncionario +
                ", codigoLivro=" + codigoLivro +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucaoPrevista +
                ", devolvido=" + devolvido +
                ", formatter=" + formatter +
                '}';
    }
}