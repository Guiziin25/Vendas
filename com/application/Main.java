package com.application;

import model.*;
import controller.*;
import exceptions.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== INÍCIO DOS TESTES ===");

            // Teste de todos os controladores
            testarControladorAcesso();
            testarControladorCliente();
            testarControladorProduto();
            testarControladorPromocoes();
            testarControladorFuncionario();
            testarControladorVendaECarrinho();
            testarControladorFaturaEPagamento();
            testarRecuperacaoSenha();

            System.out.println("\n=== TODOS OS TESTES FORAM CONCLUÍDOS COM SUCESSO! ===");
        } catch (Exception e) {
            System.err.println("\n=== ERRO DURANTE A EXECUÇÃO DOS TESTES ===");
            System.err.println("Tipo: " + e.getClass().getSimpleName());
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testarControladorAcesso() {
        System.out.println("\n=== TESTE CONTROLADOR DE ACESSO ===");
        ControladorAcesso controladorAcesso = ControladorAcesso.getInstancia();

        try {
            // Teste de autenticação inválida
            System.out.println("\nTeste 1: Tentar autenticar com login inválido");
            controladorAcesso.autenticar("login_inexistente", "senha_errada");
            System.out.println("ERRO: Autenticação deveria ter falhado");
        } catch (AutenticacaoException e) {
            System.out.println("SUCESSO: Autenticação inválida tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Criar funcionários para teste
        Funcionario funcAdmin = new FuncionarioAssalariado(
                1, "Admin", "111.111.111-11", "admin@empresa.com",
                "(81) 1111-1111", new Date(), "Gerente", "TI",
                "admin", "admin123", new String[]{"ADMIN"},
                5000, 1000
        );

        Funcionario funcNormal = new FuncionarioHorista(
                2, "Funcionário Normal", "222.222.222-22", "normal@empresa.com",
                "(81) 2222-2222", new Date(), "Vendedor", "Vendas",
                "normal", "normal123", new String[]{"vendas"},
                50, 160, false
        );

        try {
            System.out.println("\nTeste 2: Verificar permissão de administrador");
            boolean ehAdmin = controladorAcesso.isAdmin(funcAdmin);
            System.out.println("SUCESSO: Funcionário admin é admin? " + ehAdmin);

            System.out.println("\nTeste 3: Verificar permissão de funcionário normal");
            controladorAcesso.isAdmin(funcNormal);
            System.out.println("ERRO: Funcionário normal não deveria ter permissão de admin");
        } catch (AcessoNegadoException e) {
            System.out.println("SUCESSO: Acesso negado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorCliente() {
        System.out.println("\n=== TESTE CONTROLADOR DE CLIENTE ===");
        ControladorCliente controladorCliente = ControladorCliente.getInstancia();

        // Teste 1: Cadastrar cliente com dados inválidos
        try {
            System.out.println("\nTeste 1: Cadastrar cliente com dados inválidos");
            Cliente clienteInvalido = new Cliente(0, "", "", "", "", "");
            controladorCliente.cadastrarCliente(clienteInvalido);
            System.out.println("ERRO: Cadastro de cliente inválido deveria falhar");
        } catch (ClienteException | CampoObrigatorioException e) {
            System.out.println("SUCESSO: Cliente inválido tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Preparação dos clientes para testes
        Cliente cliente1 = new Cliente(
                1, "João Silva", "joao@email.com", "senha123",
                "Rua A, 123", "(11) 9999-9999");

        Cliente cliente2 = new Cliente(
                2, "Maria Souza", "maria@email.com", "abc456",
                "Av. B, 456", "(11) 8888-8888");

        // Teste 2: Cadastrar clientes válidos
        try {
            System.out.println("\nTeste 2: Cadastrar clientes válidos");
            controladorCliente.cadastrarCliente(cliente1);
            controladorCliente.cadastrarCliente(cliente2);
            System.out.println("SUCESSO: Clientes cadastrados com sucesso");
        } catch (ClienteException | CampoObrigatorioException e) {
            System.out.println("ERRO: Falha ao cadastrar clientes válidos");
            System.out.println("Mensagem: " + e.getMessage());
            return; // Se falhar aqui, não faz sentido continuar os testes
        }

        // Teste 3: Buscar cliente existente
        try {
            System.out.println("\nTeste 3: Buscar cliente existente");
            Cliente cliente = controladorCliente.buscarCliente(1);
            System.out.println("SUCESSO: Cliente encontrado: " + cliente.getNome());
        } catch (ClienteException e) {
            System.out.println("ERRO: Falha ao buscar cliente existente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Buscar cliente inexistente
        try {
            System.out.println("\nTeste 4: Buscar cliente inexistente");
            controladorCliente.buscarCliente(999);
            System.out.println("ERRO: Busca por cliente inexistente deveria falhar");
        } catch (ClienteException e) {
            System.out.println("SUCESSO: Cliente não encontrado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 5: Autenticar cliente com senha correta
        try {
            System.out.println("\nTeste 5: Autenticar cliente");
            boolean autenticado = controladorCliente.autenticarCliente(1, "senha123");
            System.out.println("SUCESSO: Cliente autenticado? " + autenticado);
        } catch (ClienteException | CampoObrigatorioException e) {
            System.out.println("ERRO: Falha na autenticação válida");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 6: Autenticar com senha inválida
        try {
            System.out.println("\nTeste 6: Autenticar com senha inválida");
            controladorCliente.autenticarCliente(1, "senhaerrada");
            System.out.println("ERRO: Autenticação com senha errada deveria falhar");
        } catch (ClienteException | CampoObrigatorioException e) {
            System.out.println("SUCESSO: Autenticação inválida tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 7: Autenticar com senha vazia (deve lançar CampoObrigatorioException)
        try {
            System.out.println("\nTeste 7: Autenticar com senha vazia");
            controladorCliente.autenticarCliente(1, "");
            System.out.println("ERRO: Autenticação com senha vazia deveria falhar");
        } catch (ClienteException | CampoObrigatorioException e) {
            System.out.println("SUCESSO: Senha vazia tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorProduto() {
        System.out.println("\n=== TESTE CONTROLADOR DE PRODUTO ===");
        ControladorProduto controladorProduto = ControladorProduto.getInstancia();

        Produto produto1 = new Produto(
                1, "Notebook", "Notebook i7 16GB", 4500.00,
                "Eletrônicos", 10, 2, "notebook.jpg");

        Produto produto2 = new Produto(
                2, "Mouse", "Mouse sem fio", 120.00,
                "Acessórios", 50, 10, "mouse.jpg");

        try {
            System.out.println("\nTeste 1: Cadastrar produtos válidos");
            controladorProduto.cadastrarProduto(produto1);
            controladorProduto.cadastrarProduto(produto2);
            System.out.println("SUCESSO: Produtos cadastrados com sucesso");

            System.out.println("\nTeste 2: Buscar produto existente");
            Produto produto = controladorProduto.buscarProduto(1);
            System.out.println("SUCESSO: Produto encontrado: " + produto.getNome());

            System.out.println("\nTeste 3: Buscar produto inexistente");
            controladorProduto.buscarProduto(999);
            System.out.println("ERRO: Busca por produto inexistente deveria falhar");
        } catch (ProdutoException e) {
            System.out.println("SUCESSO: Produto não encontrado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        try {
            System.out.println("\nTeste 4: Registrar venda com estoque suficiente");
            controladorProduto.registrarVenda(1, 2);
            System.out.println("SUCESSO: Venda registrada com sucesso");

            System.out.println("\nTeste 5: Registrar venda com estoque insuficiente");
            controladorProduto.registrarVenda(1, 20);
            System.out.println("ERRO: Venda com estoque insuficiente deveria falhar");
        } catch (ProdutoException e) {
            System.out.println("SUCESSO: Erro de produto tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        try {
            System.out.println("\nTeste 6: Listar produtos por categoria existente");
            Produto[] produtos = controladorProduto.buscarProdutosPorCategoria("Acessórios");
            System.out.println("SUCESSO: " + produtos.length + " produtos encontrados");

            System.out.println("\nTeste 7: Listar produtos por categoria inexistente");
            controladorProduto.buscarProdutosPorCategoria("Inexistente");
            System.out.println("ERRO: Categoria inexistente deveria falhar");
        } catch (ProdutoException e) {
            System.out.println("SUCESSO: Categoria não encontrada tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorPromocoes() {
        System.out.println("\n=== TESTE CONTROLADOR DE PROMOÇÕES ===");
        ControladorPromocoes controladorPromocoes = ControladorPromocoes.getInstancia();
        Date hoje = new Date();
        Date amanha = new Date(hoje.getTime() + 86400000); // +1 dia
        Date semanaQueVem = new Date(hoje.getTime() + 86400000 * 7);

        try {
            System.out.println("\nTeste 1: Criar promoção de produto válida");
            controladorPromocoes.criarPromocaoProduto(
                    1, 10.0, true, hoje, semanaQueVem, "Promoção de notebook");
            System.out.println("SUCESSO: Promoção criada com sucesso");

            System.out.println("\nTeste 2: Criar cupom de desconto válido");
            controladorPromocoes.criarCupom(
                    "CUPOM10", 10.0, true, hoje, semanaQueVem, "Cupom 10%", 100);
            System.out.println("SUCESSO: Cupom criado com sucesso");

            System.out.println("\nTeste 3: Aplicar cupom válido");
            double valorComDesconto = controladorPromocoes.aplicarCupom(1000.0, "CUPOM10");
            System.out.println("SUCESSO: Valor com desconto: " + valorComDesconto);

            System.out.println("\nTeste 4: Aplicar cupom inválido");
            controladorPromocoes.aplicarCupom(1000.0, "CUPOM_INVALIDO");
            System.out.println("ERRO: Cupom inválido deveria falhar");
        } catch (PromocaoException e) {
            System.out.println("SUCESSO: Erro de promoção tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorFuncionario() {
        System.out.println("\n=== TESTE CONTROLADOR DE FUNCIONÁRIO ===");
        ControladorFuncionario controladorFuncionario = ControladorFuncionario.getInstancia();

        // Preparação dos funcionários para testes
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

        // Teste 1: Contratar funcionários válidos
        try {
            System.out.println("\nTeste 1: Contratar funcionários válidos");
            controladorFuncionario.contratarFuncionario(assalariado);
            controladorFuncionario.contratarFuncionario(horista);
            System.out.println("SUCESSO: Funcionários contratados com sucesso");
        } catch (FuncionarioException | CampoObrigatorioException e) {
            System.out.println("ERRO: Falha ao contratar funcionários válidos");
            System.out.println("Mensagem: " + e.getMessage());
            return; // Se falhar aqui, não faz sentido continuar os testes
        }

        // Teste 2: Buscar funcionário existente
        try {
            System.out.println("\nTeste 2: Buscar funcionário existente");
            Funcionario funcionario = controladorFuncionario.buscarFuncionario(3);
            System.out.println("SUCESSO: Funcionário encontrado: " + funcionario.getNome());
        } catch (FuncionarioException e) {
            System.out.println("ERRO: Falha ao buscar funcionário existente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 3: Buscar funcionário inexistente
        try {
            System.out.println("\nTeste 3: Buscar funcionário inexistente");
            controladorFuncionario.buscarFuncionario(999);
            System.out.println("ERRO: Busca por funcionário inexistente deveria falhar");
        } catch (FuncionarioException e) {
            System.out.println("SUCESSO: Funcionário não encontrado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Calcular salário de funcionário
        try {
            System.out.println("\nTeste 4: Calcular salário de funcionário");
            double salario = controladorFuncionario.calcularSalarioFuncionario(3);
            System.out.println("SUCESSO: Salário calculado: " + salario);
        } catch (FuncionarioException e) {
            System.out.println("ERRO: Falha ao calcular salário");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 5: Tentar contratar funcionário inválido (sem nome)
        try {
            System.out.println("\nTeste 5: Tentar contratar funcionário inválido");
            FuncionarioComissionado invalido = new FuncionarioComissionado(
                    5, "", "555.555.555-55", "invalido@empresa.com",
                    "(55) 5555-5555", new Date(), "Vendedor", "Vendas",
                    "invalido", "invalido123", new String[]{"vendas"},
                    50000, 0.1);
            controladorFuncionario.contratarFuncionario(invalido);
            System.out.println("ERRO: Cadastro de funcionário inválido deveria falhar");
        } catch (FuncionarioException | CampoObrigatorioException e) {
            System.out.println("SUCESSO: Funcionário inválido tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorFaturaEPagamento() {
        System.out.println("\n=== TESTE CONTROLADOR DE FATURA E PAGAMENTO ===");
        ControladorFatura controladorFatura = ControladorFatura.getInstancia();
        ControladorPagamento controladorPagamento = ControladorPagamento.getInstancia();

        try {
            System.out.println("\nTeste 1: Emitir fatura válida");
            Fatura fatura = new Fatura(
                    1, 1, 1, 1000.0, new Date(),
                    new Date(System.currentTimeMillis() + 86400000 * 7),
                    "Fatura teste");
            controladorFatura.emitirFatura(fatura);
            System.out.println("SUCESSO: Fatura emitida: " + fatura);

            System.out.println("\nTeste 2: Processar pagamento válido");
            Pagamento pagamento = new Pagamento("credito", 1, 1000.0, "Cartão");
            boolean sucesso = controladorPagamento.processarPagamento(pagamento, fatura);
            System.out.println("SUCESSO: Pagamento processado? " + sucesso);
            System.out.println(pagamento.gerarComprovante());
            System.out.println("Status da fatura: " + fatura.getStatus());
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
    private static void testarControladorVendaECarrinho() {
        System.out.println("\n=== TESTE CONTROLADOR DE VENDA E CARRINHO ===");
        ControladorCarrinho controladorCarrinho = ControladorCarrinho.getInstancia();
        ControladorVenda controladorVenda = ControladorVenda.getInstancia();
        ControladorProduto controladorProduto = ControladorProduto.getInstancia();

        try {
            // Primeiro precisamos cadastrar alguns produtos para testar
            Produto produto1 = new Produto(1, "Notebook", "Notebook i7", 4500.00, "Eletrônicos", 10, 2, "notebook.jpg");
            Produto produto2 = new Produto(2, "Mouse", "Mouse sem fio", 120.00, "Acessórios", 50, 10, "mouse.jpg");

            controladorProduto.cadastrarProduto(produto1);
            controladorProduto.cadastrarProduto(produto2);

            System.out.println("\nTeste 1: Adicionar itens ao carrinho");
            controladorCarrinho.adicionarItem(1); // Notebook
            controladorCarrinho.adicionarItem(2); // Mouse
            System.out.println("SUCESSO: Itens adicionados ao carrinho");

            System.out.println("\nTeste 2: Calcular total do carrinho");
            double total = controladorCarrinho.calcularTotal();
            System.out.println("SUCESSO: Total do carrinho: " + total);

            System.out.println("\nTeste 3: Calcular frete");
            double frete = controladorCarrinho.calcularFrete();
            System.out.println("SUCESSO: Frete calculado: " + frete);

            System.out.println("\nTeste 4: Registrar venda");
            Venda venda = new Venda(1, 1, new Date(), total + frete, "Concluída");
            controladorVenda.registrarVenda(venda);
            System.out.println("SUCESSO: Venda registrada com sucesso");

            System.out.println("\nTeste 5: Gerar relatório de vendas");
            Date hoje = new Date();
            Date ontem = new Date(hoje.getTime() - 86400000); // 1 dia antes
            double totalVendas = controladorVenda.gerarRelatorioVendas(ontem, hoje);
            System.out.println("SUCESSO: Total de vendas no período: " + totalVendas);

            System.out.println("\nTeste 6: Tentar registrar venda inválida (valor zero)");
            Venda vendaInvalida = new Venda(2, 1, new Date(), 0, "Concluída");
            controladorVenda.registrarVenda(vendaInvalida);
            System.out.println("ERRO: Venda com valor zero deveria falhar");
        } catch (Exception e) {
            System.out.println("SUCESSO: Venda inválida tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarRecuperacaoSenha() {
        System.out.println("\n=== TESTE RECUPERAÇÃO DE SENHA ===");
        ControladorFuncionario controladorFuncionario = ControladorFuncionario.getInstancia();
        ControladorCliente controladorCliente = ControladorCliente.getInstancia();

        try {
            System.out.println("\nTeste 1: Recuperação de senha para funcionário");
            FuncionarioAssalariado funcTeste = new FuncionarioAssalariado(
                    100, "Func Teste", "100.100.100-10", "func_teste@empresa.com",
                    "(81) 1000-1000", new Date(), "Testador", "TI",
                    "func_teste", "senha123", new String[]{"testes"},
                    3000, 500);
            controladorFuncionario.contratarFuncionario(funcTeste);

            Funcionario func = controladorFuncionario.buscarFuncionario(100);
            func.setTokenRecuperacao("TOKEN_TESTE");
            System.out.println("SUCESSO: Token definido para funcionário: " + func.getTokenRecuperacao());

            System.out.println("\nTeste 2: Recuperação de senha para cliente");
            Cliente clienteTeste = new Cliente(
                    100, "Cliente Teste", "cliente_teste@email.com", "senha123",
                    "Rua Teste, 100", "(81) 1000-1000");
            controladorCliente.cadastrarCliente(clienteTeste);

            Cliente cliente = controladorCliente.buscarCliente(100);
            cliente.setTokenRecuperacao("TOKEN_CLIENTE");
            System.out.println("SUCESSO: Token gerado para cliente: " + cliente.getTokenRecuperacao());
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}