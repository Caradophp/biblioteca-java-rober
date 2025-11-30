import java.time.Year;

import biblioteca.Biblioteca;
import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Emprestimo;
import entidades.Livro;

public class Main {
    public static void main(String[] args) {
        Biblioteca b = new Biblioteca();

        Aluno a = new Aluno("Luciano Silva Friebe Feigl", "luciano@mail.com", "123Mudar", 27988610153L, 25110463L, "Ciência da Computação");
        Bibliotecario f = new Bibliotecario("Maria", "mariana@mail.com", "Mudar123", 27889161035L, 1234);
        Livro l = new Livro("Guerra dos Mundos dois", "Ação", "Desconhecido", "minha", 111222, 30);
        l.setAnoPublicacao(Year.parse("1988"));
        l.setQtdTotal(30);
        
        
        b.cadastrarLivro(l);
//        Emprestimo e = new Emprestimo(1, 25110463L, 1);

//        b.cadastrarAluno(a);
//        b.inserirBibliotecario(f);
//        b.cadastrarLivro(l);
//        b.fazerEmprestimo(e);
        
//        b.atualizarBibliotecario(f, 1234);

//        b.atualizarBibliotecario(f, 12345);
//        b.atualizarAluno(a, 25110463L);
//        b.atualizarLivroPorCodigo(l, 111222);

//        System.out.println(b.buscarLivroPorNome("Guerra dos Mundos"));
//        System.out.println(b.buscarLivroPorCodigo(111222));
//        System.out.println(b.buscarAlunoPorMatricula(25110463L));

//        Object o = b.fazerLogin("luciano@mail.com", "123Mudar");
//
//        if (o instanceof Aluno aluno) {
//            System.out.println("Bem vindo, aluno " + aluno.getNome());
//        }
//
//        Object mudar123 = b.fazerLogin("maria@mail.com", "Mudar123");
//
//        if (mudar123 instanceof Bibliotecario bibliotecario) {
//            System.out.println("Bem vindo, bibliotecario " + bibliotecario.getNome());
//        }
//
//        b.removerAluno(25110463L);
//        b.removerLivro(111222);
//        b.removerFuncionario(12345);

//        b.devolverLivro(1);
//        double v = e.calcularMulta();
//        System.out.println("Valor da multa: R$ " + v);
    }
}