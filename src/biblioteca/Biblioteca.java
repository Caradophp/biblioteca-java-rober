package biblioteca;

import entidades.Aluno;
import entidades.Bibliotecario;
import entidades.Emprestimo;
import entidades.Livro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal que gerencia o sistema da biblioteca.
 * Responsável por gerenciar o acervo de livros, o cadastro de alunos e bibliotecários,
 * e o registro de empréstimos, utilizando arquivos de texto como persistência de dados.
 */
public class Biblioteca {

    /**
     * Caminho para o arquivo de persistência dos livros.
     */
    private Path arquivoLivros = Paths.get("livros.txt");
    /**
     * Caminho para o arquivo de persistência dos alunos.
     */
    private Path arquivoAlunos = Paths.get("alunos.txt");
    /**
     * Caminho para o arquivo de persistência dos bibliotecários.
     */
    private Path arquivoFuncionarios = Paths.get("funcionarios.txt");
    /**
     * Caminho para o arquivo de persistência dos empréstimos.
     */
    private Path arquivoEmprestimos = Paths.get("emprestimos.txt");

    /**
     * Nome da biblioteca.
     */
    private String nome;
    /**
     * Endereço da biblioteca.
     */
    private String endereco;
    /**
     * Lista de livros carregados em memória.
     */
    List<Livro> livros = new ArrayList<>();
    /**
     * Lista de alunos carregados em memória.
     */
    List<Aluno> alunos = new ArrayList<>();
    /**
     * Lista de bibliotecários carregados em memória.
     */
    List<Bibliotecario> bibliotecarios = new ArrayList<>();
    /**
     * Lista de empréstimos carregados em memória.
     */
    List<Emprestimo> emprestimos = new ArrayList<>();

    /**
     * Construtor da classe Biblioteca.
     * Ao ser instanciada, carrega todos os dados dos arquivos para as listas em memória.
     */
    public Biblioteca() {
        livros = buscarTodosLivros();
        alunos = buscarTodosAlunos();
        bibliotecarios = buscarTodosBibliotecarios();
        emprestimos = buscarTodosEmprestimos();
    }

    /**
     * Realiza o login no sistema, verificando as credenciais de alunos e bibliotecários.
     *
     * @param email O e-mail do usuário.
     * @param senha A senha do usuário.
     * @return O objeto Aluno ou Bibliotecario se o login for bem-sucedido, ou null caso contrário.
     */
    public Object fazerLogin(String email, String senha) {
        for (Aluno aluno : alunos) {
            if (aluno.getEmail().equals(email) && aluno.getSenha().equals(senha)) {
                return aluno;
            }
        }

        for (Bibliotecario funcionario : bibliotecarios) {
            if (funcionario.getEmail().equals(email) && funcionario.getSenha().equals(senha)) {
                return funcionario;
            }
        }

        return null;
    }

    /**
     * Cadastra um novo livro no arquivo de persistência.
     *
     * @param livro O objeto Livro a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarLivro(Livro livro) {

        String linhaIncerida = livro.getCodigo() + ";" + livro.getNome() + ";" + livro.getCategoria() + ";" + livro.getAutor() + ";" + livro.getEditora() + ";" + livro.getIsbn() + ";" + livro.getQtdDisponivel();

        try {
            Files.write(arquivoLivros, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoLivros, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carrega todos os livros do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Livros.
     */
    public List<Livro> buscarTodosLivros() {
        List<Livro> listaLivros = new ArrayList<>();

        try {
            List<String> listaLivrosString = Files.readAllLines(arquivoLivros);

            for (String s : listaLivrosString) {
                String[] linha = s.split(";");
                Livro livro = new Livro();
                livro.setCodigo(Integer.parseInt(linha[0]));
                livro.setNome(linha[1]);
                livro.setCategoria(linha[2]);
                livro.setAutor(linha[3]);
                livro.setEditora(linha[4]);
                livro.setIsbn(Integer.parseInt(linha[5]));
                livro.setQtdDisponivel(Integer.parseInt(linha[6]));

                listaLivros.add(livro);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listaLivros;
    }

    /**
     * Busca um livro na lista em memória pelo nome.
     *
     * @param nome O nome do livro a ser buscado.
     * @return O objeto Livro encontrado ou null se não for encontrado.
     */
    public Livro buscarLivroPorNome(String nome) {

        for (Livro livro : livros) {
            if (livro.getNome().equals(nome)) {
                return livro;
            }
        }

        return null;

    }

    /**
     * Busca um livro na lista em memória pelo código.
     *
     * @param codigo O código do livro a ser buscado.
     * @return O objeto Livro encontrado ou null se não for encontrado.
     */
    public Livro buscarLivroPorCodigo(int codigo) {

        for (Livro livro : livros) {
            if (livro.getCodigo() == codigo) {
                return livro;
            }
        }

        return null;

    }

    /**
     * Atualiza as informações de um livro.
     * O método limpa o arquivo e o reescreve com as informações atualizadas.
     *
     * @param livroParaAtualizar O objeto Livro com as novas informações.
     * @param isbn               O ISBN do livro a ser atualizado.
     * @return true se a atualização for bem-sucedida.
     */
    public boolean atualizarLivroPorCodigo(Livro livroParaAtualizar, long isbn) {

        limparArquivo(arquivoAlunos);
        for (Livro livro : livros) {
            if (livro.getIsbn() == isbn) {
                livro.setNome(livroParaAtualizar.getNome());
                livro.setCategoria(livroParaAtualizar.getCategoria());
                livro.setAutor(livroParaAtualizar.getAutor());
                livro.setEditora(livroParaAtualizar.getEditora());
                livro.setIsbn(livroParaAtualizar.getIsbn());
                livro.setQtdDisponivel(livroParaAtualizar.getQtdDisponivel());
                cadastrarLivro(livro);
            } else {
                cadastrarLivro(livro);
            }
        }

        return true;
    }

    /**
     * Remove um livro do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os livros, exceto o removido.
     *
     * @param isbn O ISBN do livro a ser removido.
     * @return true se a remoção for bem-sucedida, false caso contrário.
     */
    public boolean removerLivro(long isbn) {

        try {

            limparArquivo(arquivoLivros);
            for (Livro livro : livros) {
                if (livro.getIsbn() != isbn) {
                    cadastrarLivro(livro);
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Métodos para trabalhar com alunos

    /**
     * Cadastra um novo aluno no arquivo de persistência.
     *
     * @param aluno O objeto Aluno a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarAluno(Aluno aluno) {

        String linhaIncerida = aluno.getMatricula() + ";" + aluno.getNome() + ";" + aluno.getEmail() + ";" + aluno.getSenha() + ";" + aluno.getTelefone() + ";" + aluno.getCurso();

        try {
            Files.write(arquivoAlunos, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoAlunos, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove um aluno do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os alunos, exceto o removido.
     *
     * @param matricula A matrícula do aluno a ser removido.
     */
    public void removerAluno(int matricula) {
        limparArquivo(arquivoAlunos);
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() != matricula) {
                cadastrarAluno(aluno);
            }
        }
    }

    /**
     * Busca um aluno na lista em memória pela matrícula.
     *
     * @param matricula A matrícula do aluno a ser buscado.
     * @return O objeto Aluno encontrado ou null se não for encontrado.
     */
    public Aluno buscarAlunoPorMatricula(int matricula) {

        for (Aluno aluno : alunos) {
            if (aluno.getMatricula() == matricula) {
                return aluno;
            }
        }

        return null;

    }

    /**
     * Carrega todos os alunos do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Alunos.
     */
    public List<Aluno> buscarTodosAlunos() {
        try {
            List<String> linhas = Files.readAllLines(arquivoAlunos);
            for (String linha : linhas) {
                String[] l = linha.split(";");
                Aluno aluno = new Aluno();
                aluno.setMatricula(Integer.parseInt(l[0]));
                aluno.setNome(l[1]);
                aluno.setEmail(l[2]);
                aluno.setSenha(l[3]);
                aluno.setTelefone(Integer.parseInt(l[4]));
                aluno.setCurso(l[5]);
                alunos.add(aluno);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return alunos;
    }

    // Métodos para trabalhar com funcionários

    /**
     * Cadastra um novo bibliotecário no arquivo de persistência.
     *
     * @param bibliotecario O objeto Bibliotecario a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean inserirBibliotecario(Bibliotecario bibliotecario) {

        String linhaIncerida = bibliotecario.getRegistro() + ";" + bibliotecario.getNome() + ";" + bibliotecario.getEmail() + ";" + bibliotecario.getSenha() + ";" + bibliotecario.getDataAdmissao();

        try {
            Files.write(arquivoFuncionarios, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoFuncionarios, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove um bibliotecário do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os bibliotecários, exceto o removido.
     *
     * @param registro O registro do bibliotecário a ser removido.
     */
    public void removerFuncionario(int registro) {
        limparArquivo(arquivoFuncionarios);
        for (Bibliotecario bibliotecario : bibliotecarios) {
            if (bibliotecario.getRegistro() != registro) {
                inserirBibliotecario(bibliotecario);
            }
        }
    }

    /**
     * Carrega todos os bibliotecários do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Bibliotecarios.
     */
    public List<Bibliotecario> buscarTodosBibliotecarios() {
        try {
            List<String> linhas = Files.readAllLines(arquivoFuncionarios);
            for (String linha : linhas) {
                String[] l = linha.split(";");
                Bibliotecario bibliotecario = new Bibliotecario();
                bibliotecario.setRegistro(Integer.parseInt(l[0]));
                bibliotecario.setNome(l[1]);
                bibliotecario.setEmail(l[2]);
                bibliotecario.setSenha(l[3]);

                // Divide a string de data para montar o objeto LocalDate (formato dd/MM/yyyy)
                String[] data = l[4].split("/");
                bibliotecario.setDataAdmissao(LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0])));

                bibliotecarios.add(bibliotecario);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bibliotecarios;
    }

    // Métodos para trabalhar com empréstimos

    /**
     * Registra um novo empréstimo no arquivo de persistência.
     * Antes de registrar, verifica se o livro e o aluno existem.
     *
     * @param emprestimo O objeto Emprestimo a ser registrado.
     * @return true se o registro for bem-sucedido, false caso contrário.
     */
    public boolean fazerEmprestimo(Emprestimo emprestimo) {

        Livro livro = buscarLivroPorCodigo(emprestimo.getCodigoLivro());
        if (livro == null) {
            System.out.println("Não existe um livro com o código indicado");
            return false;
        }

        Aluno aluno = buscarAlunoPorMatricula(emprestimo.getCodigoAluno());
        if (aluno == null) {
            System.out.println("Aluno não encontrado");
            return false;
        }

        String linhaIncerida = emprestimo.getCodigoEmprestimo() + ";" + emprestimo.getCodigoLivro() + ";" + emprestimo.getCodigoAluno() + ";" + emprestimo.getDataEmprestimo() + ";" + emprestimo.getDataDevolucao() + ";" + "nao";

        try {
            Files.write(arquivoEmprestimos, linhaIncerida.getBytes(), StandardOpenOption.APPEND);
            Files.write(arquivoEmprestimos, "\n".getBytes(), StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Carrega todos os empréstimos do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Emprestimos.
     */
    public List<Emprestimo> buscarTodosEmprestimos() {
        try {
            List<String> linhas = Files.readAllLines(arquivoEmprestimos);
            for (String linha : linhas) {
                String[] l = linha.split(";");
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setCodigoEmprestimo(Integer.parseInt(l[0]));
                emprestimo.setCodigoLivro(Integer.parseInt(l[1]));
                emprestimo.setCodigoAluno(Integer.parseInt(l[2]));
                emprestimo.setDevolvido(Boolean.parseBoolean(l[5]));

                // Divide a string de data para montar o objeto LocalDate (formato dd/MM/yyyy)

                String[] dataEmprestimo = l[3].split("/");
                emprestimo.setDataEmprestimo(LocalDate.of(Integer.parseInt(dataEmprestimo[2]), Integer.parseInt(dataEmprestimo[1]), Integer.parseInt(dataEmprestimo[0])));

                String[] dataDevolucao = l[4].split("/");
                emprestimo.setDataDevolucao(LocalDate.of(Integer.parseInt(dataDevolucao[2]), Integer.parseInt(dataDevolucao[1]), Integer.parseInt(dataDevolucao[0])));

                emprestimos.add(emprestimo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return emprestimos;
    }

    // Método para limpar arquivo

    /**
     * Limpa o conteúdo de um arquivo, preparando-o para uma reescrita.
     *
     * @param arquivo O caminho do arquivo a ser limpo.
     */
    private void limparArquivo(Path arquivo) {
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(arquivo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}