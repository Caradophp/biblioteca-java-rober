package entidades;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Representa um Empréstimo de um Livro para um Aluno.
 * Contém a lógica para cálculo de multa e renovação.
 */
public class Emprestimo {

    /** Valor da multa por dia de atraso. */
    private static final double MULTA_DIA = 4.0;

    /** Código único do empréstimo. */
    private int codigoEmprestimo;
    /** Código (matrícula) do aluno que realizou o empréstimo. */
    private long codigoAluno;
    /** Código (registro) do funcionário que registrou o empréstimo. */
    private int codigoFuncionario;
    /** Código do livro emprestado. */
    private int codigoLivro;
    /** Data em que o empréstimo foi realizado. */
    private LocalDate dataEmprestimo;
    /** Data prevista para a devolução. */
    private LocalDate dataDevolucao;
    /** Indica se o livro já foi devolvido. */
    private boolean devolvido;

    /** Formatador de data para exibição no formato dd/MM/yyyy. */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor padrão.
     * Define a data de empréstimo como hoje e a data de devolução para 10 dias.
     */
    public Emprestimo() {
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = this.dataEmprestimo.plusDays(10);
    }

    /**
     * Construtor com parâmetros essenciais.
     * @param codigoEmprestimo Código único do empréstimo.
     * @param codigoAluno Código do aluno.
     * @param codigoLivro Código do livro.
     */
    public Emprestimo(int codigoEmprestimo, long codigoAluno, int codigoLivro) {
        this.codigoEmprestimo = codigoEmprestimo;
        this.codigoAluno = codigoAluno;
        this.codigoLivro = codigoLivro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = LocalDate.now().plusDays(10);
    }

    public int getCodigoEmprestimo() {
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

    public int getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(int codigoLivro) {
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

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    /**
     * Retorna a data de devolução formatada.
     * @return Data de devolução no formato dd/MM/yyyy.
     */
    public String getDataDevolucaoFormatada() {
        return dataDevolucao.format(formatter);
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
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

    /**
     * Calcula o valor da multa com base nos dias de atraso.
     * @return O valor total da multa.
     */
    public double calcularMulta() {
        Period p = Period.between(dataDevolucao, LocalDate.now());
        if (p.getDays() < 0) {
            return 0.0;
        }

        return p.getDays() * MULTA_DIA;
    }

    /**
     * Renova o empréstimo, estendendo a data de devolução por mais 10 dias.
     */
    public void renovarEmprestimo() {
        if (this.dataDevolucao != null) {
            System.out.println("Não é possível renovar, livro já devolvido.");
            return;
        }
        this.dataDevolucao = this.dataDevolucao.plusDays(10);
    }

    public void devolverLivro() {

        if (this.devolvido) {
            System.out.println("Esse livro já foi devolvido");
            return;
        }

        this.devolvido = true;
        System.out.println("Livro devolvido com sucesso");
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
        return codigoEmprestimo == that.codigoEmprestimo && codigoAluno == that.codigoAluno && codigoFuncionario == that.codigoFuncionario && codigoLivro == that.codigoLivro && devolvido == that.devolvido && Objects.equals(dataEmprestimo, that.dataEmprestimo) && Objects.equals(dataDevolucao, that.dataDevolucao) && Objects.equals(formatter, that.formatter);
    }

    /**
     * Sobrescreve o método hashCode.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigoEmprestimo, codigoAluno, codigoFuncionario, codigoLivro, dataEmprestimo, dataDevolucao, devolvido, formatter);
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
                ", dataDevolucao=" + dataDevolucao +
                ", devolvido=" + devolvido +
                ", formatter=" + formatter +
                '}';
    }
}