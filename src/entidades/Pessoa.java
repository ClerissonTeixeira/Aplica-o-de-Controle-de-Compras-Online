package entidades;

// Classe que representa uma pessoa genérica
public class Pessoa {
    private String nome;
    private String email;

    // Construtor da classe Pessoa
    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters
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

    @Override
    public String toString() {
        return "Nome: " + nome + ", Email: " + email;
    }
}
