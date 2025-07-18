package com.application;

import java.util.Scanner;
import java.util.Date;

import exceptions.*;
import model.*;
import controller.*;


public class Testes {
    public static void main(String[] args) throws SistemaException, DadosInvalidosException {

        Scanner scanner = new Scanner(System.in);
        ControladorCliente controladorCliente = ControladorCliente.getInstancia();
        ControladorFuncionario controladorFuncionario = ControladorFuncionario.getInstancia();
        ControladorProduto controladorProduto = ControladorProduto.getInstancia();
        ControladorCarrinho controladorCarrinho = ControladorCarrinho.getInstancia();
        ControladorVenda controladorVenda = ControladorVenda.getInstancia();
        ControladorPagamento controladorPagamento = ControladorPagamento.getInstancia();
        ControladorAcesso controladorAcesso = ControladorAcesso.getInstancia();


        try {
            controladorCliente.cadastrarCliente(new Cliente(1, "João Silva", "joao@email.com", "senha", "Rua A, 123", "8187654321"));
            controladorCliente.cadastrarCliente(new Cliente(2, "Maria Souza", "maria@email.com", "senha", "Av. B, 456", "8112345678"));

            String[] permissoesAdmin = {"ADMIN"};
            controladorFuncionario.contratarFuncionario(
                    new FuncionarioAssalariado(1, "Gui Admin", "12345678900", "gui@email.com", "11911111111", new Date(), "Administrador", "TI", "admin", "admin", permissoesAdmin, 5000.0, 500.0)
            );
            String[] permissoesVendedor = {"VENDEDOR"};
            controladorFuncionario.contratarFuncionario(
                    new FuncionarioAssalariado(2, "Gonzaga Vendedor", "98765432100", "gonzaga@email.com", "11922222222", new Date(), "Vendedor", "Vendas", "gonzaga", "vendedor", permissoesVendedor, 3000.0, 300.0)
            );
        try {
                Produto produto1 = new Produto(1, "Notebook", "Notebook i7", 4500.00, "Eletrônicos", 10, 2, "notebook.jpg");
                Produto produto2 = new Produto(2, "Mouse", "Mouse sem fio", 120.00, "Acessórios", 50, 10, "mouse.jpg");

                controladorProduto.cadastrarProduto(produto1);
                controladorProduto.cadastrarProduto(produto2);
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar dados de teste: " + e.getMessage());
            }

            System.out.println("=== Bem-vindo à Loja Online ===");
            System.out.println("1 - Login como Cliente");
            System.out.println("2 - Login como Admin");
            System.out.println("3 - Login como Funcionário");
            System.out.print("Escolha o tipo de usuário: ");
            int tipoUsuario = scanner.nextInt();
            scanner.nextLine();

            if (tipoUsuario == 1) {
                // Login do cliente
                Cliente cliente = null;
                while (cliente == null) {
                    System.out.print("Digite seu ID de cliente: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = scanner.nextLine();
                    try {
                        cliente = controladorCliente.autenticarCliente(id, senha);
                        System.out.println("Login realizado com sucesso! Bem-vindo, " + cliente.getNome());
                    } catch (Exception e) {
                        System.out.println("Falha no login: " + e.getMessage());
                    }
                }

                // Menu do cliente
                boolean continuar = true;
                while (continuar) {
                    System.out.println("\nMenu Cliente:");
                    System.out.println("1 - Listar produtos");
                    System.out.println("2 - Adicionar produto ao carrinho");
                    System.out.println("3 - Ver carrinho");
                    System.out.println("4 - Finalizar compra");
                    System.out.println("0 - Sair");
                    System.out.print("Escolha uma opção: ");
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            Produto[] produtos = controladorProduto.listarTodosProdutos();
                            System.out.println("Produtos disponíveis:");
                            for (Produto p : produtos) {
                                System.out.println(p.getId() + " - " + p.getNome() + " - R$" + p.getPreco());
                            }
                            break;
                        case 2:
                            System.out.print("ID do produto: ");
                            int idProduto = scanner.nextInt();
                            System.out.print("Quantidade: ");
                            int qtd = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                controladorCarrinho.adicionarItem(idProduto, qtd);
                                System.out.println("Produto adicionado ao carrinho!");
                            } catch (Exception e) {
                                System.out.println("Erro: " + e.getMessage());
                            }
                            break;
                        case 3:
                            System.out.println("Itens no carrinho:");
                            try {
                                Produto[] itensCarrinho = controladorCarrinho.listarItens();
                                System.out.println("Itens no carrinho:");
                                for (Produto p : itensCarrinho) {
                                    System.out.println(p.getId() + " - " + p.getNome() + " - R$" + p.getPreco());
                                }
                            } catch (CarrinhoVazioException e) {
                                System.out.println("O carrinho está vazio.");
                            }
                            break;
                        case 4:
                            double total = controladorCarrinho.calcularTotal();
                            double frete = controladorCarrinho.calcularFrete();
                            System.out.println("Total: R$" + total);
                            System.out.println("Frete: R$" + frete);
                            System.out.println("Valor final: R$" + (total + frete));
                            System.out.print("Confirmar compra? (s/n): ");
                            String confirm = scanner.nextLine();
                            if (confirm.equalsIgnoreCase("s")) {
                                Venda venda = new Venda(0, cliente.getId(), new Date(), total + frete, "Concluída");
                                controladorVenda.registrarVenda(venda);
                                Pagamento pagamento = new Pagamento("credito", cliente.getId(), total + frete, "Cartão", new Date());
                                System.out.println("Compra realizada com sucesso!");
                                continuar = false;
                            }
                            break;
                        case 0:
                            continuar = false;
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                }
                System.out.println("Obrigado por comprar conosco!");
            } else if (tipoUsuario == 2) {
                // Login do admin
                Funcionario funcionario = null;
                while (funcionario == null) {
                    System.out.print("Digite seu ID de Admin: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = scanner.nextLine();
                    try {
                        funcionario = controladorFuncionario.autenticarFuncionario(id, senha);
                        System.out.println("Login realizado com sucesso! Bem-vindo, " + funcionario.getNome());
                    } catch (Exception e) {
                        System.out.println("Falha no login: " + e.getMessage());
                    }
                }

                try {
                    if (controladorAcesso.temPermissao(funcionario, "ADMIN")) {
                // Painel admin
                boolean continuar = true;
                while (continuar) {
                    System.out.println("\nPainel Admin:");
                    System.out.println("1 - Listar todos os clientes");
                    System.out.println("2 - Listar todas as vendas");
                    System.out.println("3 - Listar produtos");
                    System.out.println("4 - Adicionar produto");
                    System.out.println("5 - Remover produto");
                    System.out.println("0 - Sair");
                    System.out.print("Escolha uma opção: ");
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            Cliente[] clientes = controladorCliente.listarClientes();
                            System.out.println("Clientes cadastrados:");
                            for (Cliente c : clientes) {
                                System.out.println(c.getId() + " - " + c.getNome());
                            }
                            break;
                        case 2:
                            Venda[] vendas = controladorVenda.listarVendas();
                            System.out.println("Vendas realizadas:");
                            for (Venda v : vendas) {
                                System.out.println("Venda #" + v.getId() + " - Cliente: " + v.getIdCliente() + " - Valor: R$" + v.getValorTotal());
                            }
                            break;
                        case 3:
                            Produto[] produtos = controladorProduto.listarTodosProdutos();
                            System.out.println("Produtos disponíveis:");
                            for (Produto p : produtos) {
                                System.out.println(p.getId() + " - " + p.getNome() + " - R$" + p.getPreco());
                            }
                            break;
                        case 4:
                            System.out.print("Nome do produto: ");
                            String nome = scanner.nextLine();
                            System.out.print("Descrição: ");
                            String descricao = scanner.nextLine();
                            System.out.print("Preço: ");
                            double preco = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("Categoria: ");
                            String categoria = scanner.nextLine();
                            System.out.print("Quantidade em estoque: ");
                            int quantidadeEstoque = scanner.nextInt();
                            System.out.print("Estoque mínimo: ");
                            int estoqueMinimo = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Imagem principal: ");
                            String imagemPrincipal = scanner.nextLine();

                            Produto novoProduto = new Produto(0, nome, descricao, preco, categoria, quantidadeEstoque, estoqueMinimo, imagemPrincipal);
                            controladorProduto.cadastrarProduto(novoProduto);
                            System.out.println("Produto adicionado!");
                            break;
                        case 5:
                            System.out.print("ID do produto para remover: ");
                            int idRemover = scanner.nextInt();
                            scanner.nextLine();
                            controladorProduto.removerProduto(idRemover);
                            System.out.println("Produto removido!");
                            break;
                        case 0:
                            continuar = false;
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                }
                System.out.println("Sessão encerrada. Até logo!");
                    } else {
                        System.out.println("Acesso negado: você não possui permissão de administrador.");
                    }
                } catch (SistemaException e) {
                    System.out.println("Erro de permissão: " + e.getMessage());
                }
            } else if (tipoUsuario == 3) {
                // Login do Funcionário
                Funcionario funcionario = null;
                while (funcionario == null) {
                    System.out.print("Digite seu ID de Funcionário: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Digite sua senha: ");
                    String senha = scanner.nextLine();
                    try {
                        funcionario = controladorFuncionario.autenticarFuncionario(id, senha);
                        System.out.println("Login realizado com sucesso! Bem-vindo, " + funcionario.getNome());
                    } catch (Exception e) {
                        System.out.println("Falha no login: " + e.getMessage());
                    }
                }

                // Painel do Funcionário
                boolean continuar = true;
                while (continuar) {
                    System.out.println("\nPainel Funcionário:");
                    System.out.println("1 - Listar produtos");
                    System.out.println("2 - Adicionar produto");
                    System.out.println("3 - Remover produto");
                    System.out.println("0 - Sair");
                    System.out.print("Escolha uma opção: ");
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            Produto[] produtos = controladorProduto.listarTodosProdutos();
                            System.out.println("Produtos disponíveis:");
                            for (Produto p : produtos) {
                                System.out.println(p.getId() + " - " + p.getNome() + " - R$" + p.getPreco());
                            }
                            break;
                        case 2:
                            System.out.print("Nome do produto: ");
                            String nome = scanner.nextLine();
                            System.out.print("Descrição: ");
                            String descricao = scanner.nextLine();
                            System.out.print("Preço: ");
                            double preco = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("Categoria: ");
                            String categoria = scanner.nextLine();
                            System.out.print("Quantidade em estoque: ");
                            int quantidadeEstoque = scanner.nextInt();
                            System.out.print("Estoque mínimo: ");
                            int estoqueMinimo = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Imagem principal: ");
                            String imagemPrincipal = scanner.nextLine();

                            Produto novoProduto = new Produto(0, nome, descricao, preco, categoria, quantidadeEstoque, estoqueMinimo, imagemPrincipal);
                            controladorProduto.cadastrarProduto(novoProduto);
                            System.out.println("Produto adicionado!");
                            break;
                        case 3:
                            System.out.print("ID do produto para remover: ");
                            int idRemover = scanner.nextInt();
                            scanner.nextLine();
                            controladorProduto.removerProduto(idRemover);
                            System.out.println("Produto removido!");
                            break;
                        case 0:
                            continuar = false;
                            break;
                        default:
                            System.out.println("Opção inválida.");
                    }
                }
            } else {
                System.out.println("Tipo de usuário inválido.");
            }
            scanner.close();
        } catch (SistemaException | DadosInvalidosException e) {
            System.out.println("Erro no sistema: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }
}