package repositorios;

import entidades.Produto;
import enums.TipoProduto;
import interfaces.RepositorioProdutos;
import utils.ConexaoMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioProdutosImpl implements RepositorioProdutos {

	@Override
	public void adicionarProduto(Produto produto) {
	    String sqlBusca = "SELECT COUNT(*) FROM produtos WHERE nome = ? AND preco = ? AND tipo = ?";
	    String sqlInsercao = "INSERT INTO produtos (nome, preco, tipo) VALUES (?, ?, ?)";

	    try (Connection conn = ConexaoMySQL.getConnection();
	         PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {

	        // Verifica se o produto já existe
	        stmtBusca.setString(1, produto.getNome());
	        stmtBusca.setDouble(2, produto.getPreco());
	        stmtBusca.setString(3, produto.getTipo().name());
	        ResultSet rs = stmtBusca.executeQuery();

	        if (rs.next() && rs.getInt(1) == 0) {
	            // Insere apenas se o produto não existir
	            try (PreparedStatement stmtInsercao = conn.prepareStatement(sqlInsercao)) {
	                stmtInsercao.setString(1, produto.getNome());
	                stmtInsercao.setDouble(2, produto.getPreco());
	                stmtInsercao.setString(3, produto.getTipo().name());
	                stmtInsercao.executeUpdate();
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Produto> listarProdutos() {
	    List<Produto> produtos = new ArrayList<>();
	    String sql = "SELECT DISTINCT nome, preco, tipo FROM produtos";

	    try (Connection conn = ConexaoMySQL.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            Produto produto = new Produto(
	                rs.getString("nome"),
	                rs.getDouble("preco"),
	                TipoProduto.valueOf(rs.getString("tipo").toUpperCase())
	            );
	            produtos.add(produto);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return produtos;
	}

	@Override
	public Produto buscarProdutoPorNome(String nome) {
	    Produto produto = null;
	    String sql = "SELECT id, nome, preco, tipo FROM produtos WHERE nome = ?";

	    try (Connection conn = ConexaoMySQL.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, nome);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            produto = new Produto(
	                rs.getString("nome"),
	                rs.getDouble("preco"),
	                TipoProduto.valueOf(rs.getString("tipo").toUpperCase())
	            );
	            produto.setId(rs.getInt("id")); // Configura o ID do produto
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return produto;
	}

    @Override
    public void atualizarProduto(Produto produtoAtualizado) {
        String sql = "UPDATE produtos SET preco = ?, tipo = ? WHERE nome = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, produtoAtualizado.getPreco());
            stmt.setString(2, produtoAtualizado.getTipo().name());
            stmt.setString(3, produtoAtualizado.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removerProdutoPorNome(String nome) {
        String sql = "DELETE FROM produtos WHERE nome = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}