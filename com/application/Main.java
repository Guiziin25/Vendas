package com.application;

import model.*;
import controller.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Teste do Controlador de Acesso
        testarControladorAcesso();

        // Teste do Controlador de Cliente
        testarControladorCliente();

        // Teste do Controlador de Produto
        testarControladorProduto();

        // Teste do Controlador de Promoções
        testarControladorPromocoes();

        // Teste do Controlador de Funcionários
        testarControladorFuncionario();

        // Teste do Controlador de Vendas e Carrinho
        testarControladorVendaECarrinho();

        // Teste do Controlador de Fatura e Pagamento
        testarControladorFaturaEPagamento();
    }

    private static void testarControladorAcesso() {
        System.out.println("\n=== TESTE CONTROLADOR DE ACESSO ===");

        // Padrão Singleton
        ControladorAcesso controladorAcesso = ControladorAcesso.getInstancia();

        // Criar alguns funcionários para o teste
        Funcionario func1 = new FuncionarioAssalariado(
                1, "Admin", "111.111.111-11", "admin@empresa.com",
                "(81) 1111-1111", new Date(), "Gerente", "TI",
                "admin", "admin123", new String[]{"admin", "gerente"},
                5000, 1000
        );

        Funcionario func2 = new FuncionarioHorista(
                2, "Vendedor", "222.222.222-22", "vendedor@empresa.com",
                "(81) 2222-2222", new Date(), "Vendedor", "Vendas",
                "vendedor", "venda123", new String[]{"vendas"},
                50, 160, false
        );

        //testar a contratação dos funcionários
        ControladorFuncionario.getInstancia().contratarFuncionario(func1);
        ControladorFuncionario.getInstancia().contratarFuncionario(func2);

        // Testar autenticação
        System.out.println("Autenticação admin (deve ser true): " +
                controladorAcesso.autenticar("admin", "admin123"));
        System.out.println("Autenticação inválida (deve ser false): " +
                controladorAcesso.autenticar("admin", "senhaerrada"));

        // Testar permissões
        System.out.println("Permissão admin (deve ser true): " +
                controladorAcesso.temPermissao(1, "admin"));
        System.out.println("Permissão inexistente (deve ser false): " +
                controladorAcesso.temPermissao(2, "admin"));
    }

    private static void testarControladorCliente() {
        System.out.println("\n=== TESTE CONTROLADOR DE CLIENTE ===");

        // Singleton para o controlador de clientes
        ControladorCliente controladorCliente = ControladorCliente.getInstancia();

        // Cadastrar clientes
        Cliente cliente1 = new Cliente(
                1, "João Silva", "joao@email.com", "senha123",
                "Rua A, 123", "(11) 9999-9999");
        Cliente cliente2 = new Cliente(
                2, "Maria Souza", "maria@email.com", "abc456",
                "Av. B, 456", "(11) 8888-8888");

        controladorCliente.cadastrarCliente(cliente1);
        controladorCliente.cadastrarCliente(cliente2);

        // Testar autenticação dos clientes
        System.out.println("Autenticação cliente 1 (deve ser true): " +
                controladorCliente.autenticarCliente(1, "senha123"));
        System.out.println("Autenticação inválida (deve ser false): " +
                controladorCliente.autenticarCliente(1, "senhaerrada"));

        // Buscar de clientes
        System.out.println("Buscar cliente 1: " +
                controladorCliente.buscarCliente(1));
        System.out.println("Buscar cliente inexistente: " +
                controladorCliente.buscarCliente(99));
    }

    private static void testarControladorProduto() {
        System.out.println("\n=== TESTE CONTROLADOR DE PRODUTO ===");

        ControladorProduto controladorProduto = ControladorProduto.getInstancia();

        // Cadastrar produtos
        Produto produto1 = new Produto(
                1, "Notebook", "Notebook i7 16GB", 4500.00,
                "Eletrônicos", 10, 2, "notebook.jpg");
        Produto produto2 = new Produto(
                2, "Mouse", "Mouse sem fio", 120.00,
                "Acessórios", 50, 10, "mouse.jpg");
        Produto produto3 = new Produto(
                3, "Teclado", "Teclado mecânico", 350.00,
                "Acessórios", 2, 5, "teclado.jpg");

        controladorProduto.cadastrarProduto(produto1);
        controladorProduto.cadastrarProduto(produto2);
        controladorProduto.cadastrarProduto(produto3);

        // Listar produtos
        System.out.println("\nTodos os produtos:");
        for (Produto p : controladorProduto.listarTodosProdutos()) {
            System.out.println(p);
        }

        // Buscar por categoria
        System.out.println("\nProdutos da categoria Acessórios:");
        for (Produto p : controladorProduto.buscarProdutosPorCategoria("Acessórios")) {
            System.out.println(p.getNome() + " - R$" + p.getPreco());
        }

        // Testar estoque
        System.out.println("\nVerificar estoque produto 1:");
        System.out.println(controladorProduto.verificarEstoque(1));

        // Registrar venda
        controladorProduto.registrarVenda(1, 2);
        System.out.println("\nEstoque após venda de 2 notebooks:");
        System.out.println(controladorProduto.verificarEstoque(1));

        // Emitir alertas de estoque
        System.out.println("\nAlertas de estoque:");
        controladorProduto.emitirAlertasEstoque();
    }

    private static void testarControladorPromocoes() {
        System.out.println("\n=== TESTE CONTROLADOR DE PROMOÇÕES ===");

        ControladorPromocoes controladorPromocoes = ControladorPromocoes.getInstancia();
        Date hoje = new Date();
        Date amanha = new Date(hoje.getTime() + 86400000); // +1 dia
        Date semanaQueVem = new Date(hoje.getTime() + 86400000 * 7);

        // Criar promoção para produto
        controladorPromocoes.criarPromocaoProduto(
                1, 10.0, true, hoje, semanaQueVem, "Promoção de notebook");

        // Criar cupom de desconto
        controladorPromocoes.criarCupom(
                "CUPOM10", 10.0, true, hoje, semanaQueVem, "Cupom 10%", 100);

        // Listar promoções ativas
        System.out.println("Promoções ativas para produto 1:");
        for (Promocao p : controladorPromocoes.buscarPromocoesPorProduto(1)) {
            System.out.println(p.getDescricao() + " - " + p.getValorDesconto() + "%");
        }

        // Testar cupom
        double valorOriginal = 1000.0;
        double valorComDesconto = controladorPromocoes.aplicarCupom(valorOriginal, "CUPOM10");
        System.out.println("\nAplicar cupom:");
        System.out.println("Original: R$" + valorOriginal);
        System.out.println("Com desconto: R$" + valorComDesconto);

        // Validar cupom
        System.out.println("\nCupom CUPOM10 é válido? " +
                controladorPromocoes.validarCupom("CUPOM10"));
    }

    private static void testarControladorFuncionario() {
        System.out.println("\n=== TESTE CONTROLADOR DE FUNCIONÁRIO ===");

        ControladorFuncionario controladorFuncionario = ControladorFuncionario.getInstancia();

        // Criar funcionários de diferentes tipos
        FuncionarioAssalariado assalariado = new FuncionarioAssalariado(
                3, "Carlos", "333.333.333-33", "carlos@empresa.com",
                "(33) 3333-3333", new Date(), "Gerente", "Vendas",
                "carlos", "carlos123", new String[]{"vendas", "gerente"},
                6000, 500);

        FuncionarioHorista horista = new FuncionarioHorista(
                4, "Ana", "444.444.444-44", "ana@empresa.com",
                "(44) 4444-4444", new Date(), "Atendente", "RH",
                "ana", "ana123", new String[]{"rh"},
                40, 180, true);

        FuncionarioComissionado comissionado = new FuncionarioComissionado(
                5, "Pedro", "555.555.555-55", "pedro@empresa.com",
                "(55) 5555-5555", new Date(), "Vendedor", "Vendas",
                "pedro", "pedro123", new String[]{"vendas"},
                50000, 0.1);

        controladorFuncionario.contratarFuncionario(assalariado);
        controladorFuncionario.contratarFuncionario(horista);
        controladorFuncionario.contratarFuncionario(comissionado);

        // Listar todos os funcionários
        System.out.println("Todos os funcionários:");
        controladorFuncionario.listarTodosFuncionarios();

        // Listar por departamento
        System.out.println("\nFuncionários do departamento Vendas:");
        controladorFuncionario.listarPorDepartamento("Vendas");

        // Calcular salários
        System.out.println("\nCálculo de salários:");
        System.out.println("Salário assalariado: R$" +
                controladorFuncionario.calcularSalarioFuncionario(3));
        System.out.println("Salário horista: R$" +
                controladorFuncionario.calcularSalarioFuncionario(4));
        System.out.println("Salário comissionado: R$" +
                controladorFuncionario.calcularSalarioFuncionario(5));
    }

    private static void testarControladorVendaECarrinho() {
        System.out.println("\n=== TESTE CONTROLADOR DE VENDA E CARRINHO ===");

        ControladorCarrinho controladorCarrinho = ControladorCarrinho.getInstancia();
        ControladorVenda controladorVenda = ControladorVenda.getInstancia();

        // Adicionar itens ao carrinho
        controladorCarrinho.adicionarItem(1); // Notebook
        controladorCarrinho.adicionarItem(2); // Mouse
        controladorCarrinho.adicionarItem(3); // Teclado

        // Calcular total
        double total = controladorCarrinho.calcularTotal();
        System.out.println("Total do carrinho: R$" + total);

        // Aplicar cupom
        double totalComDesconto = ControladorPromocoes.getInstancia()
                .aplicarCupom(total, "CUPOM10");
        System.out.println("Total com desconto: R$" + totalComDesconto);

        // Registrar venda
        Venda venda = new Venda(
                1, 1, new Date(), totalComDesconto, "Concluída");
        controladorVenda.registrarVenda(venda);

        // Gerar relatório
        Date hoje = new Date();
        Date ontem = new Date(hoje.getTime() - 86400000);
        double totalVendas = controladorVenda.gerarRelatorioVendas(ontem, hoje);
        System.out.println("Total de vendas no período: R$" + totalVendas);
    }

    private static void testarControladorFaturaEPagamento() {
        System.out.println("\n=== TESTE CONTROLADOR DE FATURA E PAGAMENTO ===");

        ControladorFatura controladorFatura = ControladorFatura.getInstancia();
        ControladorPagamento controladorPagamento = ControladorPagamento.getInstancia();

        // Criar fatura
        Fatura fatura = new Fatura(
                1, 1, 1, 1000.0, new Date(),
                new Date(System.currentTimeMillis() + 86400000 * 7),
                "Fatura teste");

        // Emitir fatura
        controladorFatura.emitirFatura(fatura);
        System.out.println("Fatura emitida: " + fatura);

        // Processar pagamento de fatura
        Pagamento pagamento = new Pagamento("credito", 1, 1000.0, "Cartão");

        if (pagamento.pagarFatura(fatura)) {
            System.out.println("Pagamento realizado e fatura atualizada!");
            System.out.println(pagamento.gerarComprovante());
            System.out.println("Status da fatura: " + fatura.getStatus());
        } else {
            System.out.println("Falha ao pagar fatura: " + pagamento.getStatus());
        }
    }
}