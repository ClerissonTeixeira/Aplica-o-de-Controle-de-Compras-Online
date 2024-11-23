package main;

import entidades.Cliente;
import entidades.Pedido;
import entidades.Produto;
import enums.FormaPagamento;
import enums.TipoProduto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repositorios.RepositorioPedidosImpl;
import repositorios.RepositorioProdutosImpl;
import servicos.ServicoPagamentoImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Testando a conexão com o Hibernate
        try (SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            System.out.println("Conexão com o Hibernate configurada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao configurar o Hibernate. Verifique as configurações.");
            e.printStackTrace();
            return;
        }

        // Scanner para entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Criando cliente e pedido
        Cliente cliente = new Cliente("Clerisson", "clerisson@email.com");
        Pedido pedido = new Pedido(cliente);

        // Criando repositório de produtos
        RepositorioProdutosImpl repositorio = new RepositorioProdutosImpl();
        repositorio.adicionarProduto(new Produto("Cadeira", 150.00, TipoProduto.FISICO));
        repositorio.adicionarProduto(new Produto("E-book", 50.00, TipoProduto.DIGITAL));
        repositorio.adicionarProduto(new Produto("Mesa", 200.00, TipoProduto.FISICO));

        // Criando serviço de pagamento
        ServicoPagamentoImpl servicoPagamento = new ServicoPagamentoImpl();

        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("\n--- Menu de Compras ---");
                System.out.println("1. Adicionar Produto");
                System.out.println("2. Remover Produto");
                System.out.println("3. Listar Produtos do Carrinho");
                System.out.println("4. Escolher Forma de Pagamento");
                System.out.println("5. Finalizar Compra");
                System.out.println("6. Cancelar Compra");
                System.out.println("7. Sair");
                System.out.print("Escolha uma opção: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Por favor, insira um número válido.");
                    scanner.next();
                    continue;
                }

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.println("\nProdutos disponíveis:");
                        List<Produto> produtosDisponiveis = repositorio.listarProdutos();
                        produtosDisponiveis.forEach(p -> System.out.println("- " + p.getNome() + " (R$" + p.getPreco() + ")"));

                        System.out.print("Digite o nome do produto para adicionar ao carrinho: ");
                        String nomeProduto = scanner.nextLine();
                        Produto produtoEscolhido = repositorio.buscarProdutoPorNome(nomeProduto);

                        if (produtoEscolhido != null) {
                            pedido.adicionarProduto(produtoEscolhido);
                            System.out.println(produtoEscolhido.getNome() + " adicionado ao carrinho.");
                        } else {
                            System.out.println("Produto não encontrado.");
                        }
                        break;

                    case 2:
                        System.out.println("\nProdutos no carrinho:");
                        if (pedido.getProdutos().isEmpty()) {
                            System.out.println("O carrinho está vazio.");
                            break;
                        }

                        pedido.getProdutos().forEach(p -> System.out.println("- " + p.getNome() + " (R$" + p.getPreco() + ")"));
                        System.out.print("Digite o nome do produto para remover do carrinho: ");
                        String nomeRemover = scanner.nextLine();

                        Produto produtoRemover = pedido.getProdutos().stream()
                                .filter(p -> p.getNome().equalsIgnoreCase(nomeRemover))
                                .findFirst()
                                .orElse(null);

                        if (produtoRemover != null) {
                            pedido.removerProduto(produtoRemover);
                            System.out.println(produtoRemover.getNome() + " removido do carrinho.");
                        } else {
                            System.out.println("Produto não encontrado no carrinho.");
                        }
                        break;

                    case 3:
                        System.out.println("\nProdutos no carrinho:");
                        if (pedido.getProdutos().isEmpty()) {
                            System.out.println("O carrinho está vazio.");
                        } else {
                            pedido.getProdutos().forEach(p -> System.out.println("- " + p.getNome() + " (R$" + p.getPreco() + ")"));
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
                        if (pedido.getProdutos().isEmpty()) {
                            System.out.println("O carrinho está vazio. Adicione produtos antes de finalizar.");
                            break;
                        }

                        if (pedido.getFormaPagamento() == null) {
                            System.out.println("Forma de pagamento não definida. Escolha uma forma de pagamento antes de finalizar.");
                            continue;
                        }

                        RepositorioPedidosImpl repositorioPedidos = new RepositorioPedidosImpl();
                        repositorioPedidos.salvarPedido(pedido);
                        servicoPagamento.processarPagamento(pedido.calcularTotal());

                        System.out.println("Compra finalizada com sucesso!");
                        System.out.println(pedido);
                        pedido.getProdutos().clear();
                        continuar = perguntarSeContinua(scanner);
                        break;

                    case 6:
                        System.out.println("\nCompra cancelada. O carrinho foi esvaziado.");
                        pedido.getProdutos().clear();
                        continuar = perguntarSeContinua(scanner);
                        break;

                    case 7:
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static boolean perguntarSeContinua(Scanner scanner) {
        System.out.println("Deseja continuar comprando? (S/N)");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }
}

