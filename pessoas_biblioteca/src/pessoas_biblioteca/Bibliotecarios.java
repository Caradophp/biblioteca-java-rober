package pessoas_biblioteca; 

import java.time.LocalDate; //Importar LocalDate uma classe integrada com API de data e hora.

public class Bibliotecarios extends Pessoas{ //Extender os atributos privados da ClassePai(Pessoas) para a ClasseFilho(Bibliotecarios).
	
	//Definição dos próprios atributos privados de Bibliotecarios.
	
	private final String registroAdmissao; //imutavel
	private final LocalDate dataAdmissao;
	
	public Bibliotecarios(String nome, String email, String telefone, String senha, String registroAdmissao, LocalDate dataAdmissao) {
		super(nome, email, telefone, senha);
		this.registroAdmissao = registroAdmissao;
		this.dataAdmissao = dataAdmissao;
	}
	
	//Implementação dos metodos Getters e Setters para podermos ler e modificar os atributos privados da ClasseFilho Bibliotecarios.
	
	public String getRegistroAdmissao() {
		return registroAdmissao;
	}

	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}
}
