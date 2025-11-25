import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Emprestimo;
import entidades.Livro;

public class Main {
    public static void main(String[] args) {
        Biblioteca b = new Biblioteca();

        Aluno a = new Aluno("Luciano", "luciano@mail.com", "123Mudar", 27988610153L, 25110463L, "Ciência da Computação");
        Bibliotecario f = new Bibliotecario("Rhaique", "rhaique@mail.com", "Mudar123", 27889161035L, 12345);
        Livro l = new Livro(1, "Guerra dos Mundos", "Ação", "Desconhecido", "minha", 111222, 10);
        Emprestimo e = new Emprestimo(1, 25110463L, 1);

//        b.cadastrarAluno(a);
//        b.inserirBibliotecario(f);
//        b.cadastrarLivro(l);
//        b.fazerEmprestimo(e);

        b.devolverLivro(1);
//        double v = e.calcularMulta();
//        System.out.println("Valor da multa: R$ " + v);
    }
}