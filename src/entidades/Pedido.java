package entidades;

import java.util.ArrayList;
import java.util.List;
import enums.FormaPagamento;

public class Pedido {
    private Cliente cliente; // Associação com o cliente
    private List<Produto> produtos; // Lista de produtos no pedido
    private FormaPagamento formaPagamento;

    // Construtor
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.produtos = new ArrayList<>(); // Inicializa a lista para evitar null
    }

    // Getter para o cliente
    public Cliente getCliente() {
        return cliente;
    }

    // Setter para o cliente (opcional, dependendo do uso no projeto)
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Getter para os produtos no pedido
    public List<Produto> getProdutos() {
        return produtos;
    }

    // Método para adicionar um produto ao pedido
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Método para remover um produto do pedido
    public void removerProduto(Produto produto) {
        produtos.removeIf(p -> p.getNome().equalsIgnoreCase(produto.getNome())); // Remove pelo nome
    }

    // Definir a forma de pagamento
    public void definirFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    // Obter a forma de pagamento
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    // Calcular o total do pedido
    public double calcularTotal() {
        return produtos.stream().mapToDouble(Produto::getPreco).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido do cliente: ").append(cliente.getNome()).append("\n");
        sb.append("Produtos:").append("\n");
        for (Produto produto : produtos) {
            sb.append("- ").append(produto.getNome()).append(" (R$").append(produto.getPreco()).append(")").append("\n");
        }
        sb.append("Forma de pagamento: ").append(formaPagamento).append("\n");
        sb.append("Total: R$").append(calcularTotal());
        return sb.toString();
    }
}