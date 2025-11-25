package entidades;

import java.util.Objects;

/**
 * Representa um Aluno, que é um tipo de Pessoa, no sistema da biblioteca.
 * Adiciona atributos específicos como matrícula e curso.
 */
public class Aluno extends Pessoa {

    /**
     * Número de matrícula do aluno.
     */
    private long matricula;
    /**
     * Curso em que o aluno está matriculado.
     */
    private String curso;

    /**
     * Construtor padrão.
     */
    public Aluno() {
    }

    /**
     * Construtor com atributos da Pessoa e a matrícula.
     *
     * @param nome      Nome do aluno.
     * @param email     E-mail do aluno.
     * @param senha     Senha de acesso.
     * @param telefone  Telefone do aluno.
     * @param matricula Número de matrícula.
     */
    public Aluno(String nome, String email, String senha, long telefone, long matricula, String curso) {
        super(nome, email, senha, telefone);
        this.matricula = matricula;
        this.curso = curso;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Sobrescreve o método equals para comparação de objetos Aluno.
     *
     * @param object O objeto a ser comparado.
     * @return true se os objetos forem considerados iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Aluno aluno = (Aluno) object;
        return matricula == aluno.matricula && Objects.equals(curso, aluno.curso);
    }

    /**
     * Sobrescreve o método hashCode.
     *
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(matricula, curso);
    }

    /**
     * Sobrescreve o método toString para representação em string do objeto.
     *
     * @return Uma string contendo os dados do Aluno.
     */
    @Override
    public String toString() {
        return super.toString() + ", matricula" + matricula;
    }
}