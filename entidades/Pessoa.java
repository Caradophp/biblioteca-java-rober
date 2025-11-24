package entidades;

import java.util.Objects;

/**
 * Classe base que representa uma Pessoa no sistema da biblioteca.
 * Contém atributos comuns a Alunos e Bibliotecários.
 */
public class Pessoa {

    /**
     * Nome completo da pessoa.
     */
    private String nome;
    /**
     * Endereço de e-mail, usado para login.
     */
    private String email;
    /**
     * Senha de acesso.
     */
    private String senha;
    /**
     * Número de telefone para contato.
     */
    private int telefone;

    /**
     * Construtor padrão.
     */
    public Pessoa() {
    }

    /**
     * Construtor com todos os atributos.
     *
     * @param nome     Nome da pessoa.
     * @param email    E-mail da pessoa.
     * @param senha    Senha de acesso.
     * @param telefone Telefone da pessoa.
     */
    public Pessoa(String nome, String email, String senha, int telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    /**
     * Verifica se a senha digitada corresponde à senha armazenada.
     *
     * @param senhaDigitada A senha fornecida pelo usuário.
     * @return true se as senhas coincidirem, false caso contrário.
     */
    public boolean validarSenha(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    /**
     * Sobrescreve o método equals para comparação de objetos Pessoa.
     *
     * @param object O objeto a ser comparado.
     * @return true se os objetos forem considerados iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Pessoa pessoa = (Pessoa) object;
        return telefone == pessoa.telefone && Objects.equals(nome, pessoa.nome) && Objects.equals(email, pessoa.email) && Objects.equals(senha, pessoa.senha);
    }

    /**
     * Sobrescreve o método hashCode.
     *
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nome, email, senha, telefone);
    }

    /**
     * Sobrescreve o método toString para representação em string do objeto.
     *
     * @return Uma string contendo os dados da Pessoa.
     */
    @Override
    public String toString() {
        return "Pessoa{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'';
    }
}