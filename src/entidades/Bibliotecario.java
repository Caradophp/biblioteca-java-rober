package entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Representa um Bibliotecário, que é um tipo de Pessoa, no sistema da biblioteca.
 * Adiciona atributos específicos como registro e data de admissão.
 */
public class Bibliotecario extends Pessoa {

    /** Número de registro do bibliotecário. */
    private long registro;
    /** Data de admissão do bibliotecário. */
    private LocalDate dataAdmissao;

    /** Formatador de data para exibição no formato dd/MM/yyyy. */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor padrão.
     * Inicializa a data de admissão com a data atual.
     */
    public Bibliotecario() {
        this.dataAdmissao = LocalDate.now();
    }

    /**
     * Construtor com todos os atributos.
     * @param nome Nome do bibliotecário.
     * @param email E-mail do bibliotecário.
     * @param senha Senha de acesso.
     * @param telefone Telefone do bibliotecário.
     * @param registro Número de registro.
     */
    public Bibliotecario(String nome, String email, String senha, long telefone, long registro) {
        super(nome, email, senha, telefone);
        this.registro = registro;
        this.dataAdmissao = LocalDate.now();
    }

    public long getRegistro() {
        return registro;
    }

    public void setRegistro(long registro) {
        this.registro = registro;
    }

    /**
     * Retorna a data de admissão formatada como String.
     * @return Data de admissão no formato dd/MM/yyyy.
     */
    public String getDataAdmissao() {
        return dataAdmissao.format(formatter);
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    /**
     * Sobrescreve o método equals para comparação de objetos Bibliotecario.
     * @param object O objeto a ser comparado.
     * @return true se os objetos forem considerados iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Bibliotecario that = (Bibliotecario) object;
        return registro == that.registro && Objects.equals(dataAdmissao, that.dataAdmissao) && Objects.equals(formatter, that.formatter);
    }

    /**
     * Sobrescreve o método hashCode.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(registro, dataAdmissao, formatter);
    }

    /**
     * Sobrescreve o método toString para representação em string do objeto.
     * @return Uma string contendo os dados do Bibliotecário.
     */
    @Override
    public String toString() {
        return "============================\n" +
                "Dados do Bibliotecário\n\n" +
                "Registro: " + registro + "\n" +
                super.toString() + "\n" +
                "Data de Adimissão: " + dataAdmissao.format(formatter);
    }
}