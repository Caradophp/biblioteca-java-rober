# 📚 Sistema de Gerenciamento de Biblioteca

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Status](https://img.shields.io/badge/Status-Concluído-brightgreen?style=for-the-badge)](https://github.com/seu-usuario/seu-repositorio)
[![Licença](https://img.shields.io/badge/Licença-MIT-blue?style=for-the-badge)](LICENSE)

## ✨ Visão Geral do Projeto

Este projeto é um sistema de gerenciamento de biblioteca desenvolvido em Java, focado em demonstrar conceitos de **Programação Orientada a Objetos (POO)**, como Herança e Encapsulamento, e a implementação de um mecanismo simples de **Persistência de Dados** utilizando arquivos de texto.

O sistema permite o cadastro e gerenciamento de livros, alunos, bibliotecários e o registro de empréstimos, incluindo a lógica para cálculo de multas por atraso.

## ⚙️ Arquitetura e Estrutura

A arquitetura do sistema é dividida em classes de Entidade e uma classe de Gerenciamento central.

### 1. Entidades (Modelos)

As classes de entidade modelam os objetos do mundo real e utilizam **Herança** para compartilhar atributos comuns.

| Classe | Descrição | Herança | Atributos Chave |
| :--- | :--- | :--- | :--- |
| `Pessoa` | Classe base para todos os usuários. | N/A | `nome`, `email`, `senha` |
| `Aluno` | Usuário que realiza empréstimos. | `extends Pessoa` | `matricula`, `curso` |
| `Bibliotecario` | Usuário responsável pela gestão. | `extends Pessoa` | `registro`, `dataAdmissao` |
| `Livro` | Item do acervo. | N/A | `codigo`, `nome`, `isbn`, `qtdDisponivel` |
| `Emprestimo` | Registro da transação. | N/A | `codigoEmprestimo`, `dataDevolucao`, `devolvido` |

### 2. Classe de Gerenciamento (`Biblioteca.java`)

A classe `Biblioteca` atua como o **controlador** do sistema.

*   **Responsabilidade:** Gerencia as listas de todas as entidades em memória e coordena a persistência.
*   **Inicialização:** No construtor, carrega todos os dados dos arquivos de texto para as listas em memória.
*   **Funcionalidades Chave:**
    *   `fazerLogin(email, senha)`: Autentica Alunos e Bibliotecários.
    *   `cadastrarLivro(Livro)`: Adiciona um novo registro.
    *   `removerLivro(isbn)`: Remove um registro (utiliza reescrita total do arquivo).
    *   `fazerEmprestimo(Emprestimo)`: Registra a transação.

## 💾 Persistência de Dados

O sistema utiliza arquivos de texto simples (`.txt`) para armazenar os dados.

| Entidade | Arquivo | Formato de Persistência |
| :--- | :--- | :--- |
| Livros | `livros.txt` | Campos separados por **ponto e vírgula (`;`)** |
| Alunos | `alunos.txt` | Campos separados por **ponto e vírgula (`;`)** |
| Bibliotecários | `funcionarios.txt` | Campos separados por **ponto e vírgula (`;`)** |
| Empréstimos | `emprestimos.txt` | Campos separados por **ponto e vírgula (`;`)** |

> ⚠️ **Nota sobre CRUD:** As operações de **Atualização** e **Remoção** são realizadas através da **reescrita completa** do arquivo correspondente. O método `limparArquivo()` é usado para apagar o conteúdo, e em seguida, todos os registros da lista em memória (exceto o removido) são reescritos.

## 🔑 Lógica de Negócios

### Cálculo de Multa

A lógica de multa está encapsulada na classe `Emprestimo`:

```java
public double calcularMulta() {
    // Calcula a diferença em dias entre hoje e a data de devolução
    long diasAtraso = ChronoUnit.DAYS.between(LocalDate.now(), dataDevolucao);
    
    // Se a diferença for negativa (atraso), aplica a multa
    if (diasAtraso <= 0) {
        return 0.0;
    }
    
    // MULTA_DIA é uma constante de R$ 4.00
    return diasAtraso * MULTA_DIA;
}
```

## 🚀 Como Rodar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/Caradophp/biblioteca-java-rober.get
    cd biblioteca-java-rober
    ```
2.  **Compile as classes Java:**
    ```bash
    # Dependendo da sua estrutura de pacotes, pode ser necessário um comando mais complexo
    javac -d bin src/Main.java
    ```
3.  **Execute a classe principal:**
    ```bash
    java -cp bin sua.classe.principal.Main
    ```
    *(Assumindo que você tenha uma classe principal para iniciar o sistema.)*

## 🤝 Contribuição

Sinta-se à vontade para sugerir melhorias, como a implementação de um banco de dados real (ex: SQLite) ou a adição de novas funcionalidades.

1.  Faça um Fork do projeto.
2.  Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`).
3.  Commit suas mudanças (`git commit -m 'feat: Adiciona nova funcionalidade X'`).
4.  Faça o Push para a branch (`git push origin feature/nova-funcionalidade`).
5.  Abra um Pull Request.
