package biblioteca;

import entidades.*;
import manipulators.AlunoManipulator;
import manipulators.BibliotecarioManipulator;
import manipulators.EmprestimoManipulator;
import manipulators.LivroManipulator;

import java.util.List;

/**
 * Classe principal que gerencia o sistema da biblioteca.
 * Responsável por gerenciar o acervo de livros, o cadastro de alunos e bibliotecários,
 * e o registro de empréstimos, utilizando arquivos de texto como persistência de dados.
 */
public class Biblioteca {

    private final static LivroManipulator livroManipulator = new LivroManipulator();
    private final static AlunoManipulator alunoManipulator = new AlunoManipulator();
    private final static BibliotecarioManipulator bibliotecarioManipulator = new BibliotecarioManipulator();
    private final static EmprestimoManipulator emprestimoManipulator = new EmprestimoManipulator();

    /**
     * Nome da biblioteca.
     */
    private String nome;
    /**
     * Endereço da biblioteca.
     */
    private String endereco;

    /**
     * Construtor da classe Biblioteca.
     * Ao ser instanciado carrega todos os dados em memória
     */
    public Biblioteca() {
        buscarTodosLivros();
        buscarTodosAlunos();
        buscarTodosBibliotecarios();
        buscarTodosEmprestimos();
    }

    /**
     * Realiza o login no sistema, verificando as credenciais de alunos e bibliotecários.
     *
     * @param email O e-mail do usuário.
     * @param senha A senha do usuário.
     * @return O objeto Aluno ou Bibliotecario se o login for bem-sucedido, ou null caso contrário.
     */
    public Pessoa fazerLogin(String email, String senha) {
        for (Aluno aluno : alunoManipulator.buscarTodosAlunos()) {
            if (aluno.getEmail().equals(email) && aluno.getSenha().equals(senha)) {
                return aluno;
            }
        }

        for (Bibliotecario bibliotecario : bibliotecarioManipulator.buscarTodosBibliotecarios()) {
            if (bibliotecario.getEmail().equals(email) && bibliotecario.getSenha().equals(senha)) {
                return bibliotecario;
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
        return livroManipulator.cadastrarLivro(livro);
    }

    /**
     * Carrega todos os livros do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Livros.
     */
    public List<Livro> buscarTodosLivros() {
        return livroManipulator.buscarTodosLivros();
    }

    /**
     * Busca um livro na lista em memória pelo nome.
     *
     * @param nome O nome do livro a ser buscado.
     * @return O objeto Livro encontrado ou null se não for encontrado.
     */
    public Livro buscarLivroPorNome(String nome) {
        return livroManipulator.buscarLivroPorNome(nome);
    }

    /**
     * Busca um livro na lista em memória pelo código.
     *
     * @param isbn O ISBN do livro a ser buscado.
     * @return O objeto Livro encontrado ou null se não for encontrado.
     */
    public Livro buscarLivroPorCodigo(long isbn) {
        return livroManipulator.buscarLivroPorCodigo(isbn);
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
        return livroManipulator.atualizarLivroPorCodigo(livroParaAtualizar, isbn);
    }

    /**
     * Remove um livro do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os livros, exceto o removido.
     *
     * @param isbn O ISBN do livro a ser removido.
     * @return true se a remoção for bem-sucedida, false caso contrário.
     */
    public boolean removerLivro(long isbn) {
        return livroManipulator.removerLivro(isbn);
    }

    // Métodos para trabalhar com alunos

    /**
     * Cadastra um novo aluno no arquivo de persistência.
     *
     * @param aluno O objeto Aluno a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarAluno(Aluno aluno) {
        return alunoManipulator.cadastrarAluno(aluno);
    }

    /**
     * Remove um aluno do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os alunos, exceto o removido.
     *
     * @param matricula A matrícula do aluno a ser removido.
     */
    public void removerAluno(long matricula) {
       alunoManipulator.removerAluno(matricula);
    }

    /**
     * Busca um aluno na lista em memória pela matrícula.
     *
     * @param matricula A matrícula do aluno a ser buscado.
     * @return O objeto Aluno encontrado ou null se não for encontrado.
     */
    public Aluno buscarAlunoPorMatricula(long matricula) {
        return alunoManipulator.buscarAlunoPorMatricula(matricula);
    }

    /**
     * Carrega todos os alunos do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Alunos.
     */
    public List<Aluno> buscarTodosAlunos() {
        return alunoManipulator.buscarTodosAlunos();
    }

    public boolean atualizarAluno(Aluno alunoAtualizado, long matricula) {
        return alunoManipulator.atualizarAluno(alunoAtualizado, matricula);
    }

    // Métodos para trabalhar com funcionários

    /**
     * Cadastra um novo bibliotecário no arquivo de persistência.
     *
     * @param bibliotecario O objeto Bibliotecario a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean inserirBibliotecario(Bibliotecario bibliotecario) {
        return bibliotecarioManipulator.inserirBibliotecario(bibliotecario);
    }

    /**
     * Remove um bibliotecário do arquivo de persistência.
     * O método limpa o arquivo e o reescreve com todos os bibliotecários, exceto o removido.
     *
     * @param registro O registro do bibliotecário a ser removido.
     */
    public void removerFuncionario(int registro) {
       bibliotecarioManipulator.removerFuncionario(registro);
    }

    /**
     * Carrega todos os bibliotecários do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Bibliotecarios.
     */
    public List<Bibliotecario> buscarTodosBibliotecarios() {
        return bibliotecarioManipulator.buscarTodosBibliotecarios();
    }

    public boolean atualizarBibliotecario(Bibliotecario bibliotecarioAtualizado, long registro) {
        return bibliotecarioManipulator.atualizarBibliotecario(bibliotecarioAtualizado, registro);
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
        return emprestimoManipulator.fazerEmprestimo(emprestimo);
    }

    public void renovarEmprestimo(long codigoEmprestimo) {
        emprestimoManipulator.renovarEmprestimo(codigoEmprestimo);
    }

    /**
     * Carrega todos os empréstimos do arquivo de persistência para a memória.
     *
     * @return Uma lista de todos os Emprestimos.
     */
    public List<Emprestimo> buscarTodosEmprestimos() {
        return emprestimoManipulator.buscarTodosEmprestimos();
    }

    /**
     * Marca o livro como devolvido e atualiza a data de devolução para a data atual.
     */
    public boolean devolverLivro(long codigoEmprestimo) {
        return emprestimoManipulator.devolverLivro(codigoEmprestimo);
    }

    /**
     * Verifica se existe algum bibliotecário cadastrado no sistema
     */
    public boolean possuiBibliotecarios() {
        return bibliotecarioManipulator.getNumBibliotecarios() == 0;
    }
}