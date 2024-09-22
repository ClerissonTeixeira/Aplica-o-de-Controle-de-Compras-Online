package interfaces;

import java.util.List;

// Interface CRUD para o serviço de pagamento
public interface ServicoPagamento {
    // Create - Processar um novo pagamento
    boolean processarPagamento(double valor);

    // Read - Listar todos os pagamentos (histórico)
    List<Double> listarPagamentos();

    // Read - Buscar um pagamento pelo valor
    boolean buscarPagamentoPorValor(double valor);

    // Delete - Cancelar um pagamento pelo valor
    boolean cancelarPagamento(double valor);
}
