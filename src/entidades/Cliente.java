package entidades;

// A classe Cliente herda de Pessoa
public class Cliente extends Pessoa {

    // Construtor da classe Cliente
    public Cliente(String nome, String email) {
        super(nome, email);
    }

    @Override
    public String toString() {
        return "Cliente: " + getNome() + ", Email: " + getEmail();
    }
}
