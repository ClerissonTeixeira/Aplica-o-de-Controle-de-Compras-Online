package main;

import entidades.*;
import enums.FormaPagamento;
import enums.TipoProduto;
import repositorios.RepositorioProdutosImpl;
import servicos.ServicoPagamentoImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Criando cliente
        Cliente cliente = new Cliente("Clerisson", "clerisson@email.com");

        // Criando repositório de produtos
        RepositorioProdutosImpl repositorio = new RepositorioProdutosImpl();
        repositorio.adicionarProduto(new Produto("Cadeira", 150.00, TipoProduto.FISICO));
        repositorio.adicionarProduto(new Produto("E-book", 50.00, TipoProduto.DIGITAL));
        repositorio.adicionarProduto(new Produto("Mesa", 200.00, TipoProduto.FISICO));

        // Criando pedido e serviço de pagamento
        Pedido pedido = new Pedido(cliente);
        ServicoPagamentoImpl servicoPagamento = new ServicoPagamentoImpl();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- Menu de Compras ---");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Remover Produto");
            System.out.println("3. Listar Produtos do Carrinho");
            System.out.println("4. Escolher Forma de Pagamento");
            System.out.println("5. Finalizar Compra");
            System.out.println("6. Cancelar Compra");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    System.out.println("\nProdutos disponíveis:");
                    for (Produto produto : repositorio.listarProdutos()) {
                        System.out.println("- " + produto.getNome() + " (R$" + produto.getPreco() + ")");
                    }
                    System.out.print("Digite o nome do produto para adicionar ao carrinho: ");
                    String nomeProduto = scanner.nextLine();
                    Produto produto = repositorio.buscarProdutoPorNome(nomeProduto);
                    if (produto != null) {
                        pedido.adicionarProduto(produto);
                        System.out.println(produto.getNome() + " adicionado ao carrinho.");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 2:
                    System.out.println("\nProdutos no carrinho:");
                    for (Produto p : pedido.getProdutos()) {
                        System.out.println("- " + p.getNome() + " (R$" + p.getPreco() + ")");
                    }
                    System.out.print("Digite o nome do produto para remover do carrinho: ");
                    String nomeRemover = scanner.nextLine();
                    Produto produtoRemover = repositorio.buscarProdutoPorNome(nomeRemover);
                    if (produtoRemover != null) {
                        pedido.removerProduto(produtoRemover);
                        System.out.println(produtoRemover.getNome() + " removido do carrinho.");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 3:
                    System.out.println("\nProdutos no carrinho:");
                    for (Produto p : pedido.getProdutos()) {
                        System.out.println("- " + p.getNome() + " (R$" + p.getPreco() + ")");
                    }
                    break;

                case 4:
                    System.out.println("\nEscolha a forma de pagamento:");
                    System.out.println("1. À Vista");
                    System.out.println("2. Cartão de Crédito");
                    System.out.println("3. Cartão de Débito");
                    int pagamento = scanner.nextInt();
                    switch (pagamento) {
                        case 1:
                            pedido.definirFormaPagamento(FormaPagamento.AVISTA);
                            break;
                        case 2:
                            pedido.definirFormaPagamento(FormaPagamento.CREDITO);
                            break;
                        case 3:
                            pedido.definirFormaPagamento(FormaPagamento.DEBITO);
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            continue;
                    }
                    System.out.println("Forma de pagamento definida: " + pedido.getFormaPagamento());
                    break;

                case 5:
                    System.out.println("\nFinalizando compra...");
                    double total = pedido.calcularTotal();
                    if (total > 0) {
                        servicoPagamento.processarPagamento(total);
                        System.out.println("Compra finalizada com sucesso!");
                        System.out.println(pedido);
                    } else {
                        System.out.println("O carrinho está vazio.");
                    }
                    continuar = false;
                    break;

                case 6:
                    System.out.println("\nCompra cancelada.");
                    continuar = false;
                    break;

                case 7:
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }
}
