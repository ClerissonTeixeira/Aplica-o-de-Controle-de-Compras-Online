package repositorios;

import interfaces.RepositorioProdutos;
import entidades.Produto;
import java.util.ArrayList;
import java.util.List;

// Implementação CRUD do repositório de produtos
public class RepositorioProdutosImpl implements RepositorioProdutos {
    private List<Produto> produtos;

    // Construtor da classe RepositorioProdutosImpl
    public RepositorioProdutosImpl() {
        this.produtos = new ArrayList<>();
    }

    // Create - Adiciona um produto ao repositório
    @Override
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Read - Lista todos os produtos
    @Override
    public List<Produto> listarProdutos() {
        return produtos;
    }

    // Read - Busca um produto pelo nome
    @Override
    public Produto buscarProdutoPorNome(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return null; // Retorna null se o produto não for encontrado
    }

    // Update - Atualiza um produto existente
    @Override
    public void atualizarProduto(Produto produtoAtualizado) {
        Produto produto = buscarProdutoPorNome(produtoAtualizado.getNome());
        if (produto != null) {
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setTipo(produtoAtualizado.getTipo());
        }
    }

    // Delete - Remove um produto pelo nome
    @Override
    public boolean removerProdutoPorNome(String nome) {
        Produto produto = buscarProdutoPorNome(nome);
        if (produto != null) {
            produtos.remove(produto);
            return true;
        }
        return false;
    }
}
