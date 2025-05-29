package com.application;

import com.application.dao.InMemoryProdutoDAO;
import com.application.dao.InMemoryCategoriaDAO;
import com.application.dao.InMemoryClienteDAO;
import com.application.dao.InMemoryFuncionarioDAO;
import com.application.controller.*;
import com.application.dto.*;
import com.application.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static ProdutoController produtoController;
    private static CategoriaController categoriaController;
    private static ClienteController clienteController;
    private static FuncionarioController funcionarioController;

    // Simulações em memória
    private static InMemoryProdutoDAO produtoDAO = new InMemoryProdutoDAO();
    private static InMemoryCategoriaDAO categoriaDAO = new InMemoryCategoriaDAO();
    private static InMemoryClienteDAO clienteDAO = new InMemoryClienteDAO();
    private static InMemoryFuncionarioDAO funcionarioDAO = new InMemoryFuncionarioDAO();

    public static void main(String[] args) {
        // Instancia Services com listas em memória
        IProdutoService produtoService = new ProdutoService(produtoDAO);
        ICategoriaService categoriaService = new CategoriaService(categoriaDAO);
        IClienteService clienteService = new ClienteService(clienteDAO);
        IFuncionarioService funcionarioService = new FuncionarioService(funcionarioDAO);

        // Instancia Controllers
        produtoController = new ProdutoController(produtoService);
        categoriaController = new CategoriaController(categoriaService);
        clienteController = new ClienteController(clienteService);
        funcionarioController = new FuncionarioController(funcionarioService);

        try {
            int option;
            do {
                System.out.println("\nMenu de funcionalidades:");
                System.out.println("1 - Cadastrar Categoria");
                System.out.println("2 - Listar Categorias");
                System.out.println("3 - Cadastrar Produto");
                System.out.println("4 - Listar Produtos");
                System.out.println("5 - Cadastrar Cliente");
                System.out.println("6 - Autenticar Cliente");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                option = Integer.parseInt(scanner.nextLine());

                switch (option) {
                    case 1:
                        cadastrarCategoria();
                        break;
                    case 2:
                        listarCategorias();
                        break;
                    case 3:
                        cadastrarProduto();
                        break;
                    case 4:
                        listarProdutos();
                        break;
                    case 5:
                        cadastrarCliente();
                        break;
                    case 6:
                        autenticarCliente();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }

            } while (option != 0);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void cadastrarCategoria() {
        System.out.print("Nome da categoria: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição da categoria: ");
        String descricao = scanner.nextLine();

        CategoriaDto dto = new CategoriaDto();
        dto.setNome(nome);
        dto.setDescricao(descricao);

        categoriaController.cadastrar(dto);
        System.out.println("Categoria cadastrada com sucesso.");
    }

    private static void listarCategorias() {
        List<CategoriaDto> categorias = categoriaController.listar();
        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
        } else {
            System.out.println("Categorias:");
            for (CategoriaDto c : categorias) {
                System.out.println("- " + c.getNome() + ": " + c.getDescricao());
            }
        }
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do produto: ");
        String descricao = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("Preço promocional: ");
        double precoPromo = Double.parseDouble(scanner.nextLine());
        System.out.print("Em promoção (true/false): ");
        boolean emPromocao = Boolean.parseBoolean(scanner.nextLine());

        ProdutoDto dto = new ProdutoDto();
        dto.setNome(nome);
        dto.setDescricao(descricao);
        dto.setPreco(preco);
        dto.setPrecoPromocional(precoPromo);
        dto.setEmPromocao(emPromocao);
        dto.setDataCadastro(new java.util.Date());

        produtoController.cadastrar(dto, new ArrayList<>()); // Passa uma lista vazia de imagens
        System.out.println("Produto cadastrado com sucesso.");
    }

    private static void listarProdutos() {
        List<ProdutoDto> produtos = produtoController.listar();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("Produtos:");
            for (ProdutoDto p : produtos) {
                System.out.println("- " + p.getNome() + ": " + p.getDescricao() + " - Preço: " + p.getPreco());
            }
        }
    }

    private static void cadastrarCliente() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Email do cliente: ");
        String email = scanner.nextLine();
        System.out.print("Senha do cliente: ");
        String senha = scanner.nextLine();
        System.out.print("Telefone do cliente: ");
        String telefone = scanner.nextLine();

        ClienteDto dto = new ClienteDto();
        dto.setNome(nome);
        dto.setEmail(email);
        dto.setSenha(senha);
        dto.setTelefone(telefone);
        dto.setDataCadastro(new java.util.Date());

        clienteController.cadastrar(dto);
        System.out.println("Cliente cadastrado com sucesso.");
    }

    private static void autenticarCliente() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        LoginDto login = new LoginDto();
        login.setEmail(email);
        login.setSenha(senha);

        TokenDto token = clienteController.autenticar(login);
        if (token != null) {
            System.out.println("Autenticado! Token: " + token.getToken());
        } else {
            System.out.println("Falha na autenticação.");
        }
    }
}