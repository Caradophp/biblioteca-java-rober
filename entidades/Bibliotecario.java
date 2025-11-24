package entidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Bibliotecario extends Pessoa {

    private long registro;
    private LocalDate dataAdmissao;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Bibliotecario() {
        this.dataAdmissao = LocalDate.now();
    }

    public Bibliotecario(String nome, String email, String senha, int telefone, long registro, LocalDate dataAdmissao) {
        super(nome, email, senha, telefone);
        this.registro = registro;
        this.dataAdmissao = dataAdmissao;
    }

    public long getRegistro() {
        return registro;
    }

    public void setRegistro(long registro) {
        this.registro = registro;
    }

    public String getDataAdmissao() {
        return dataAdmissao.format(formatter);
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Bibliotecario that = (Bibliotecario) object;
        return registro == that.registro && Objects.equals(dataAdmissao, that.dataAdmissao) && Objects.equals(formatter, that.formatter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registro, dataAdmissao, formatter);
    }

    @Override
    public String toString() {
        return super.toString() + ", registro=" + registro + ", data de adimissão=" + dataAdmissao.format(formatter);
    }
}
