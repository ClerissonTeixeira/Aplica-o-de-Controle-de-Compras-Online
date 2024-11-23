package servicos;

import interfaces.ServicoPagamento;
import utils.ConexaoMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoPagamentoImpl implements ServicoPagamento {

    @Override
    public boolean processarPagamento(double valor) {
        String sql = "INSERT INTO pagamentos (valor) VALUES (?)";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, valor);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Double> listarPagamentos() {
        List<Double> pagamentos = new ArrayList<>();
        String sql = "SELECT valor FROM pagamentos";

        try (Connection conn = ConexaoMySQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pagamentos.add(rs.getDouble("valor"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamentos;
    }

    @Override
    public boolean buscarPagamentoPorValor(double valor) {
        String sql = "SELECT 1 FROM pagamentos WHERE valor = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, valor);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean cancelarPagamento(double valor) {
        String sql = "DELETE FROM pagamentos WHERE valor = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, valor);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
