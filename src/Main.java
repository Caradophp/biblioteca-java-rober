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
        Livro l1 = new Livro("Guerra dos Mundos Dois", "Ação", "Desconhecido", "Minha", 111222, 30);
        l1.setAnoPublicacao(Year.parse("1988"));
        l1.setQtdTotal(30);
        b.cadastrarLivro(l1);

        Livro l2 = new Livro("O Senhor dos Anéis", "Fantasia", "J.R.R. Tolkien", "HarperCollins", 100001, 50);
        l2.setAnoPublicacao(Year.parse("1954"));
        l2.setQtdTotal(50);
        b.cadastrarLivro(l2);

        Livro l3 = new Livro("1984", "Distopia", "George Orwell", "Companhia das Letras", 100002, 40);
        l3.setAnoPublicacao(Year.parse("1949"));
        l3.setQtdTotal(40);
        b.cadastrarLivro(l3);

        Livro l4 = new Livro("Dom Quixote", "Romance", "Miguel de Cervantes", "Planeta", 100003, 25);
        l4.setAnoPublicacao(Year.parse("1605"));
        l4.setQtdTotal(25);
        b.cadastrarLivro(l4);

        Livro l5 = new Livro("O Pequeno Príncipe", "Fábula", "Saint-Exupéry", "Harper", 100004, 60);
        l5.setAnoPublicacao(Year.parse("1943"));
        l5.setQtdTotal(60);
        b.cadastrarLivro(l5);

        Livro l6 = new Livro("A Revolução dos Bichos", "Sátira", "George Orwell", "Cia das Letras", 100005, 45);
        l6.setAnoPublicacao(Year.parse("1945"));
        l6.setQtdTotal(45);
        b.cadastrarLivro(l6);

        Livro l7 = new Livro("Moby Dick", "Aventura", "Herman Melville", "Penguin", 100006, 20);
        l7.setAnoPublicacao(Year.parse("1851"));
        l7.setQtdTotal(20);
        b.cadastrarLivro(l7);

        Livro l8 = new Livro("Sherlock Holmes", "Mistério", "Arthur Conan Doyle", "Abril", 100007, 35);
        l8.setAnoPublicacao(Year.parse("1887"));
        l8.setQtdTotal(35);
        b.cadastrarLivro(l8);

        Livro l9 = new Livro("Harry Potter e a Pedra Filosofal", "Fantasia", "J.K. Rowling", "Rocco", 100008, 80);
        l9.setAnoPublicacao(Year.parse("1997"));
        l9.setQtdTotal(80);
        b.cadastrarLivro(l9);

        Livro l10 = new Livro("Percy Jackson: O Ladrão de Raios", "Aventura", "Rick Riordan", "Intrínseca", 100009, 55);
        l10.setAnoPublicacao(Year.parse("2005"));
        l10.setQtdTotal(55);
        b.cadastrarLivro(l10);

        Livro l11 = new Livro("Jogos Vorazes", "Distopia", "Suzanne Collins", "Rocco", 100010, 48);
        l11.setAnoPublicacao(Year.parse("2008"));
        l11.setQtdTotal(48);
        b.cadastrarLivro(l11);

        Livro l12 = new Livro("O Código Da Vinci", "Suspense", "Dan Brown", "Arqueiro", 100011, 42);
        l12.setAnoPublicacao(Year.parse("2003"));
        l12.setQtdTotal(42);
        b.cadastrarLivro(l12);

        Livro l13 = new Livro("IT: A Coisa", "Terror", "Stephen King", "Suma", 100012, 33);
        l13.setAnoPublicacao(Year.parse("1986"));
        l13.setQtdTotal(33);
        b.cadastrarLivro(l13);

        Livro l14 = new Livro("O Iluminado", "Terror", "Stephen King", "Record", 100013, 29);
        l14.setAnoPublicacao(Year.parse("1977"));
        l14.setQtdTotal(29);
        b.cadastrarLivro(l14);

        Livro l15 = new Livro("Drácula", "Terror", "Bram Stoker", "Darkside", 100014, 18);
        l15.setAnoPublicacao(Year.parse("1897"));
        l15.setQtdTotal(18);
        b.cadastrarLivro(l15);

        Livro l16 = new Livro("Frankenstein", "Terror", "Mary Shelley", "Penguin", 100015, 22);
        l16.setAnoPublicacao(Year.parse("1818"));
        l16.setQtdTotal(22);
        b.cadastrarLivro(l16);

        Livro l17 = new Livro("O Hobbit", "Fantasia", "J.R.R. Tolkien", "Harper", 100016, 70);
        l17.setAnoPublicacao(Year.parse("1937"));
        l17.setQtdTotal(70);
        b.cadastrarLivro(l17);

        Livro l18 = new Livro("A Menina que Roubava Livros", "Drama", "Markus Zusak", "Intrínseca", 100017, 41);
        l18.setAnoPublicacao(Year.parse("2005"));
        l18.setQtdTotal(41);
        b.cadastrarLivro(l18);

        Livro l19 = new Livro("Orgulho e Preconceito", "Romance", "Jane Austen", "Martin Claret", 100018, 30);
        l19.setAnoPublicacao(Year.parse("1813"));
        l19.setQtdTotal(30);
        b.cadastrarLivro(l19);

        Livro l20 = new Livro("O Morro dos Ventos Uivantes", "Romance", "Emily Brontë", "Penguin", 100019, 26);
        l20.setAnoPublicacao(Year.parse("1847"));
        l20.setQtdTotal(26);
        b.cadastrarLivro(l20);

        Livro l21 = new Livro("A Metamorfose", "Drama", "Franz Kafka", "Companhia das Letras", 100020, 32);
        l21.setAnoPublicacao(Year.parse("1915"));
        l21.setQtdTotal(32);
        b.cadastrarLivro(l21);

        Livro l22 = new Livro("O Processo", "Drama", "Franz Kafka", "Penguin", 100021, 28);
        l22.setAnoPublicacao(Year.parse("1925"));
        l22.setQtdTotal(28);
        b.cadastrarLivro(l22);

        Livro l23 = new Livro("O Nome do Vento", "Fantasia", "Patrick Rothfuss", "Arqueiro", 100022, 52);
        l23.setAnoPublicacao(Year.parse("2007"));
        l23.setQtdTotal(52);
        b.cadastrarLivro(l23);

        Livro l24 = new Livro("Duna", "Sci-Fi", "Frank Herbert", "Aleph", 100023, 34);
        l24.setAnoPublicacao(Year.parse("1965"));
        l24.setQtdTotal(34);
        b.cadastrarLivro(l24);

        Livro l25 = new Livro("Neuromancer", "Cyberpunk", "William Gibson", "Aleph", 100024, 21);
        l25.setAnoPublicacao(Year.parse("1984"));
        l25.setQtdTotal(21);
        b.cadastrarLivro(l25);

        Livro l26 = new Livro("Fundação", "Sci-Fi", "Isaac Asimov", "Aleph", 100025, 37);
        l26.setAnoPublicacao(Year.parse("1951"));
        l26.setQtdTotal(37);
        b.cadastrarLivro(l26);

        Livro l27 = new Livro("O Chamado de Cthulhu", "Terror", "H.P. Lovecraft", "Darkside", 100026, 24);
        l27.setAnoPublicacao(Year.parse("1928"));
        l27.setQtdTotal(24);
        b.cadastrarLivro(l27);

        Livro l28 = new Livro("O Retrato de Dorian Gray", "Drama", "Oscar Wilde", "Penguin", 100027, 31);
        l28.setAnoPublicacao(Year.parse("1890"));
        l28.setQtdTotal(31);
        b.cadastrarLivro(l28);

        Livro l29 = new Livro("A Divina Comédia", "Poesia", "Dante Alighieri", "Martin Claret", 100028, 17);
        l29.setAnoPublicacao(Year.parse("1320"));
        l29.setQtdTotal(17);
        b.cadastrarLivro(l29);

        Livro l30 = new Livro("O Conde de Monte Cristo", "Aventura", "Alexandre Dumas", "Zahar", 100029, 38);
        l30.setAnoPublicacao(Year.parse("1844"));
        l30.setQtdTotal(38);
        b.cadastrarLivro(l30);

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