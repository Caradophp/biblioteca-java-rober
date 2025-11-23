package pessoas_biblioteca;

//Criação do pacote pessoas que pertencem ao sistema da biblioteca.

public class Pessoas {
	//Declaração de atributos com modificadores privados da ClassePai Pessoas.
	
	private String nome;
	private String email;
	private String telefone;
	private String senha;
	
	//
	public Pessoas(String nome, String email, String telefone, String senha) {
	    this.nome = nome;
	    this.email = email;
	    this.telefone = telefone;
	    this.senha = senha;
	}
	
	//Implementação dos metodos Getters e Setters para podermos ler e modificar os atributos privados da ClassePai(Pessoas).
	
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	//O atributo senha é importante e nunca deve ser exposto pelo metodo getter, na verdade retornamos uma validação de senha comparando strings com metodo equals.
	public boolean validarSenha(String senhaDigitada) {
		return this.senha.equals(senhaDigitada);
	}
}
