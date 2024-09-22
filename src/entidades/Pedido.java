package entidades;

import enums.FormaPagamento;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Cliente cliente;
    private List<Produto> produtos;
    private FormaPagamento formaPagamento; // Armazenar a forma de pagamento

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.produtos = new ArrayList<>();
    }

    // Adiciona um produto ao pedido
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto do pedido
    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    // Calcula o total do pedido
    public double calcularTotal() {
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }

    // Define a forma de pagamento
    public void definirFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    // Retorna a forma de pagamento
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    @Override
    public String toString() {
        return "Pedido do cliente " + cliente.getNome() + ", Total: R$" + calcularTotal() + ", Forma de Pagamento: " + formaPagamento;
    }

	public Produto[] getProdutos() {
		// TODO Auto-generated method stub
		return null;
	}
}
