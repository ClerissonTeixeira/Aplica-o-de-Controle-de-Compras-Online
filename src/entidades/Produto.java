package entidades;

import enums.TipoProduto;

// Classe Produto com o tipo definido por um Enum
public class Produto {
    private String nome;
    private double preco;
    private TipoProduto tipo;

    // Construtor da classe Produto
    public Produto(String nome, double preco, TipoProduto tipo) {
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Produto: " + nome + ", Preço: " + preco + ", Tipo: " + tipo;
    }
}
