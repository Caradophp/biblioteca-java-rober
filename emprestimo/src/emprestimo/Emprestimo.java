package emprestimo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
	private final String registroBibliotecario;
	private final String matriculaAluno;
	private final String isbnLivro;
	private final LocalDate dataEmprestimo;

	private LocalDate dataDevolucaoPrevista;
	private LocalDate dataDevolucao;
	private int quantRenovacoes;

	private static final double multaDia = 4.0;
	private static final int diasRenovacao = 7;

	public Emprestimo(String registroBibliotecario, String matriculaAluno, String isbnLivro, LocalDate dataEmprestimo,
			LocalDate dataDevolucaoPrevista) {
		this.registroBibliotecario = registroBibliotecario;
		this.matriculaAluno = matriculaAluno;
		this.isbnLivro = isbnLivro;
		this.dataEmprestimo = dataEmprestimo;

		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
		this.dataDevolucao = null;
		this.quantRenovacoes = 0;
	}

	public String getRegistroBibliotecario() {
		return registroBibliotecario;
	}

	public String getMatriculaAluno() {
		return matriculaAluno;
	}

	public String getIsbnLivro() {
		return isbnLivro;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public LocalDate getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public int getQuantRenovacoes() {
		return quantRenovacoes;
	}

	public void devolverLivro() {
		if (this.dataDevolucao != null) {
			System.out.println("Livro já devolvido.");
			return;
		}
		this.dataDevolucao = LocalDate.now();
	}

	public double calcularMulta() {
		if (this.dataDevolucao == null) {
			return 0.0;
		}
		long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucao);
		if (diasAtraso <= 0) {
			return 0.0;
		}
		return diasAtraso * multaDia;
	}

	public void renovarEmprestimo() {
		if (this.dataDevolucao != null) {
			System.out.println("Não é possível renovar, livro já devolvido.");
			return;
		}
		this.dataDevolucaoPrevista = this.dataDevolucaoPrevista.plusDays(diasRenovacao);
		this.quantRenovacoes++;
	}

}
