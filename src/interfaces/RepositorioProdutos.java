package interfaces;

import entidades.Produto;
import java.util.List;

// Interface CRUD para o repositório de produtos
public interface RepositorioProdutos {
    // Create - Adicionar um novo produto
    void adicionarProduto(Produto produto);

    // Read - Listar todos os produtos
    List<Produto> listarProdutos();

    // Read - Buscar um produto pelo ID (nesse caso, pelo nome como exemplo)
    Produto buscarProdutoPorNome(String nome);

    // Update - Atualizar informações de um produto existente
    void atualizarProduto(Produto produto);

    // Delete - Remover um produto pelo nome
    boolean removerProdutoPorNome(String nome);
}
