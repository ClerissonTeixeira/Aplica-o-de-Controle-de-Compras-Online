package servicos;

import interfaces.ServicoPagamento;
import java.util.ArrayList;
import java.util.List;

// Implementação CRUD do serviço de pagamento
public class ServicoPagamentoImpl implements ServicoPagamento {
    private List<Double> pagamentos;

    // Construtor da classe ServicoPagamentoImpl
    public ServicoPagamentoImpl() {
        this.pagamentos = new ArrayList<>();
    }

    // Create - Processa um novo pagamento e o adiciona ao histórico
    @Override
    public boolean processarPagamento(double valor) {
        pagamentos.add(valor);
        System.out.println("Pagamento de R$" + valor + " processado com sucesso!");
        return true;
    }

    // Read - Lista todos os pagamentos processados
    @Override
    public List<Double> listarPagamentos() {
        return pagamentos;
    }

    // Read - Busca um pagamento pelo valor
    @Override
    public boolean buscarPagamentoPorValor(double valor) {
        return pagamentos.contains(valor);
    }

    // Delete - Cancela um pagamento (remove do histórico)
    @Override
    public boolean cancelarPagamento(double valor) {
        if (pagamentos.contains(valor)) {
            pagamentos.remove(valor);
            System.out.println("Pagamento de R$" + valor + " cancelado com sucesso!");
            return true;
        }
        return false;
    }
}
