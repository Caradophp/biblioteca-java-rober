package pessoas_biblioteca;

public class Alunos extends Pessoas{ //Extender os atributos privados da ClassePai(Pessoas) para a ClasseFilho(Alunos).
	
//Definição dos próprios atributos privados de Alunos.
	
	private final String matricula; //matricula é imutavel, existe somente uma por pessoa.
	private String curso;
	
	public Alunos(String nome, String email, String telefone, String senha, String matricula, String curso) {
		super(nome, email, telefone, senha);
		this.matricula = matricula;
		this.curso = curso;
		
	}
	
	//Implementação dos metodos Getters e Setters para podermos ler os atributos privados da ClasseFilho Alunos.

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getMatricula() {
		return matricula;
	}
		
}
