package repositorios;

import entidades.Pedido;
import entidades.Produto;
import utils.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RepositorioPedidosImpl {

	public void salvarPedido(Pedido pedido) {
	    String sqlPedido = "INSERT INTO pedidos (cliente, forma_pagamento, total) VALUES (?, ?, ?)";
	    String sqlItens = "INSERT INTO itens_pedido (pedido_id, produto_id, preco) VALUES (?, ?, ?)";

	    try (Connection conn = ConexaoMySQL.getConnection()) {
	        // Salvar o pedido
	        PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
	        stmtPedido.setString(1, pedido.getCliente().getNome()); // Nome do cliente
	        stmtPedido.setString(2, pedido.getFormaPagamento().toString()); // Forma de pagamento
	        stmtPedido.setDouble(3, pedido.calcularTotal()); // Total do pedido
	        stmtPedido.executeUpdate();

	        // Obter o ID do pedido gerado
	        ResultSet rs = stmtPedido.getGeneratedKeys();
	        int pedidoId = 0;
	        if (rs.next()) { // Verifica se o ResultSet tem dados
	            pedidoId = rs.getInt(1); // Obtém o ID gerado
	        } else {
	            throw new SQLException("Falha ao obter o ID do pedido.");
	        }

	        // Salvar os itens do pedido
	        PreparedStatement stmtItens = conn.prepareStatement(sqlItens);
	        for (Produto produto : pedido.getProdutos()) {
	            if (produto.getId() == 0) { // Verifica se o ID do produto é válido
	                throw new SQLException("Produto com ID inválido: " + produto.getNome());
	            }
	            stmtItens.setInt(1, pedidoId); // ID do pedido
	            stmtItens.setInt(2, produto.getId()); // ID do produto
	            stmtItens.setDouble(3, produto.getPreco()); // Preço do produto
	            stmtItens.addBatch();
	        }
	        stmtItens.executeBatch();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}