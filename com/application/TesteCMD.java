package com.application;

import model.*;
import controller.*;
import exceptions.*;
import java.util.Date;

public class TesteCMD {
    public static void main(String[] args) {
        try {
            System.out.println("=== INÍCIO DOS TESTES COMPLETOS DO SISTEMA ===");
            System.out.println("Este teste abrange todas as funcionalidades implementadas\n");

            // 1. Testes de Acesso
            testarControladorAcesso();

            // 2. Testes de Cliente
            testarControladorCliente();

            // 3. Testes de Produto
            testarControladorProduto();

            // 4. Testes de Promoções
            testarControladorPromocoes();

            // 5. Testes de Funcionários
            testarControladorFuncionario();

            // 6. Testes de Venda e Carrinho
            testarControladorVendaECarrinho();

            // 7. Testes de Fatura e Pagamento
            testarControladorFaturaEPagamento();

            // 8. Testes de Recuperação de Senha
            testarRecuperacaoSenha();

            System.out.println("\n=== TODOS OS TESTES FORAM CONCLUÍDOS COM SUCESSO! ===");
        } catch (Exception e) {
            System.err.println("\n=== ERRO DURANTE A EXECUÇÃO DOS TESTES ===");
            System.err.println("Tipo: " + e.getClass().getSimpleName());
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testarControladorAcesso() throws Exception {
        System.out.println("\n=== TESTE CONTROLADOR DE ACESSO ===");
        ControladorAcesso controladorAcesso = ControladorAcesso.getInstancia();

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

        // Teste 1: Autenticação inválida
        System.out.println("\nTeste 1: Tentar autenticar com login inválido");
        try {
            controladorAcesso.autenticar("login_inexistente", "senha_errada");
            System.out.println("ERRO: Autenticação deveria ter falhado");
        } catch (AutenticacaoException e) {
            System.out.println("SUCESSO: Autenticação inválida tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 2: Verificar permissão de administrador
        System.out.println("\nTeste 2: Verificar permissão de administrador");
        try {
            boolean ehAdmin = controladorAcesso.isAdmin(funcAdmin);
            System.out.println("SUCESSO: Funcionário admin é admin? " + ehAdmin);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        // Teste 3: Verificar permissão de funcionário normal
        System.out.println("\nTeste 3: Verificar permissão de funcionário normal");
        try {
            controladorAcesso.isAdmin(funcNormal);
            System.out.println("ERRO: Funcionário normal não deveria ter permissão de admin");
        } catch (AcessoNegadoException e) {
            System.out.println("SUCESSO: Acesso negado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Verificar permissão específica
        System.out.println("\nTeste 4: Verificar permissão específica");
        try {
            boolean temPermissao = controladorAcesso.temPermissao(funcNormal, "vendas");
            System.out.println("SUCESSO: Funcionário tem permissão de vendas? " + temPermissao);
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private static void testarControladorCliente() throws Exception {
        System.out.println("\n=== TESTE CONTROLADOR DE CLIENTE ===");

        ControladorCliente controladorCliente = ControladorCliente.getInstancia();

        // Teste 1: Cadastrar cliente com dados inválidos
        System.out.println("\nTeste 1: Cadastrar cliente com dados inválidos");
        try {
            // Cliente sem e-mail (inválido)
            Cliente clienteInvalido = new Cliente(0, "Nome Inválido", "", "senha", "Endereço", "12345678");
            controladorCliente.cadastrarCliente(clienteInvalido);
            System.out.println("ERRO: Cadastro de cliente inválido deveria falhar");
        } catch (DadosInvalidosException e) {
            System.out.println("SUCESSO: Cliente inválido tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Preparação dos clientes para testes (agora com e-mails válidos)
        Cliente cliente1 = new Cliente(
                1, "João Silva", "joao@email.com", "senha123",
                "Rua A, 123", "(11) 9999-9999");

        Cliente cliente2 = new Cliente(
                2, "Maria Souza", "maria@email.com", "abc456",
                "Av. B, 456", "(11) 8888-8888");

        // Teste 2: Cadastrar clientes válidos
        System.out.println("\nTeste 2: Cadastrar clientes válidos");
        try {
            controladorCliente.cadastrarCliente(cliente1);
            controladorCliente.cadastrarCliente(cliente2);
            System.out.println("SUCESSO: Clientes cadastrados com sucesso");
        } catch (ClienteException | CampoObrigatorioException e) {
            System.out.println("ERRO: Falha ao cadastrar clientes válidos");
            System.out.println("Mensagem: " + e.getMessage());
            return;
        }

        // Teste 3: Buscar cliente existente
        System.out.println("\nTeste 3: Buscar cliente existente");
        try {
            Cliente cliente = controladorCliente.buscarCliente(1);
            System.out.println("SUCESSO: Cliente encontrado: " + cliente.getNome());
        } catch (ClienteException e) {
            System.out.println("ERRO: Falha ao buscar cliente existente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Buscar cliente inexistente
        System.out.println("\nTeste 4: Buscar cliente inexistente");
        try {
            controladorCliente.buscarCliente(999);
            System.out.println("ERRO: Busca por cliente inexistente deveria falhar");
        } catch (ClienteException e) {
            System.out.println("SUCESSO: Cliente não encontrado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 5: Autenticar cliente com senha correta
        System.out.println("\nTeste 5: Autenticar cliente");
        try {
            Cliente clienteAutenticado = controladorCliente.autenticarCliente(1, "senha123");
            System.out.println("SUCESSO: Cliente autenticado: " + clienteAutenticado.getNome());
        } catch (ClienteException | CampoObrigatorioException e) {
            System.out.println("ERRO: Falha na autenticação válida");
            System.out.println("Mensagem: " + e.getMessage());
        }

// Teste 6: Autenticar com senha inválida
        System.out.println("\nTeste 6: Autenticar com senha inválida");
        try {
            controladorCliente.autenticarCliente(1, "senhaerrada");
            System.out.println("ERRO: Autenticação com senha errada deveria falhar");
        } catch (DadosInvalidosException e) {  // Alterado para DadosInvalidosException
            System.out.println("SUCESSO: Autenticação inválida tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

// Teste 7: Autenticar com senha vazia
        System.out.println("\nTeste 7: Autenticar com senha vazia");
        try {
            controladorCliente.autenticarCliente(1, "");
            System.out.println("ERRO: Autenticação com senha vazia deveria falhar");
        } catch (DadosInvalidosException e) {  // Alterado para DadosInvalidosException
            System.out.println("SUCESSO: Senha vazia tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 8: Cadastrar cliente com e-mail duplicado
        System.out.println("\nTeste 8: Cadastrar cliente com e-mail duplicado");
        try {
            Cliente clienteDuplicado = new Cliente(
                    3, "João Silva", "joao@email.com", "outrasenha",
                    "Outro Endereço", "(11) 7777-7777");

            controladorCliente.cadastrarCliente(clienteDuplicado);
            System.out.println("ERRO: Cadastro com e-mail duplicado deveria falhar");
        } catch (ClienteJaCadastradoException e) {
            System.out.println("SUCESSO: E-mail duplicado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

// Teste 9: Cadastrar cliente com e-mail inválido
        System.out.println("\nTeste 9: Cadastrar cliente com e-mail inválido");
        try {
            Cliente emailInvalido = new Cliente(
                    4, "Carlos", "emailinvalido", "senha",
                    "Endereço", "(11) 6666-6666");

            controladorCliente.cadastrarCliente(emailInvalido);
            System.out.println("ERRO: E-mail inválido deveria falhar");
        } catch (DadosInvalidosException e) {
            System.out.println("SUCESSO: E-mail inválido tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorProduto() throws Exception {
        System.out.println("\n=== TESTE CONTROLADOR DE PRODUTO ===");
        ControladorProduto controladorProduto = ControladorProduto.getInstancia();

        // Preparação dos produtos para testes
        Produto produto1 = new Produto(
                1, "Notebook", "Notebook i7 16GB", 4500.00,
                "Eletrônicos", 10, 2, "notebook.jpg");

        Produto produto2 = new Produto(
                2, "Mouse", "Mouse sem fio", 120.00,
                "Acessórios", 50, 10, "mouse.jpg");

        // Teste 1: Cadastrar produtos válidos
        System.out.println("\nTeste 1: Cadastrar produtos válidos");
        try {
            controladorProduto.cadastrarProduto(produto1);
            controladorProduto.cadastrarProduto(produto2);
            System.out.println("SUCESSO: Produtos cadastrados com sucesso");
        } catch (ProdutoException e) {
            System.out.println("ERRO: Falha ao cadastrar produtos");
            System.out.println("Mensagem: " + e.getMessage());
            return;
        }

        // Teste 2: Buscar produto existente
        System.out.println("\nTeste 2: Buscar produto existente");
        try {
            Produto produto = controladorProduto.buscarProduto(1);
            System.out.println("SUCESSO: Produto encontrado: " + produto.getNome());
        } catch (ProdutoException e) {
            System.out.println("ERRO: Falha ao buscar produto existente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 3: Buscar produto inexistente
        System.out.println("\nTeste 3: Buscar produto inexistente");
        try {
            controladorProduto.buscarProduto(999);
            System.out.println("ERRO: Busca por produto inexistente deveria falhar");
        } catch (ProdutoException e) {
            System.out.println("SUCESSO: Produto não encontrado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Registrar venda com estoque suficiente
        System.out.println("\nTeste 4: Registrar venda com estoque suficiente");
        try {
            controladorProduto.registrarVenda(1, 2);
            System.out.println("SUCESSO: Venda registrada com sucesso");

            // Verificar estoque após venda
            Produto p = controladorProduto.buscarProduto(1);
            System.out.println("Estoque atualizado: " + p.getQuantidadeEstoque());
        } catch (ProdutoException | DadosInvalidosException e) {
            System.out.println("ERRO: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // Teste 5: Registrar venda com estoque insuficiente
        System.out.println("\nTeste 5: Registrar venda com estoque insuficiente");
        try {
            controladorProduto.registrarVenda(1, 20);
            System.out.println("ERRO: Venda com estoque insuficiente deveria falhar");
        } catch (ProdutoException e) {
            System.out.println("SUCESSO: " + e.getClass().getSimpleName() + " tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        } catch (DadosInvalidosException e) {
            System.out.println("ERRO: Dados inválidos");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 6: Listar produtos por categoria existente
        System.out.println("\nTeste 6: Listar produtos por categoria existente");
        try {
            Produto[] produtos = controladorProduto.buscarProdutosPorCategoria("Acessórios");
            System.out.println("SUCESSO: " + produtos.length + " produtos encontrados");
            for (Produto p : produtos) {
                System.out.println("- " + p.getNome());
            }
        } catch (ProdutoException | DadosInvalidosException e) {
            System.out.println("ERRO: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // Teste 7: Listar produtos por categoria inexistente
        System.out.println("\nTeste 7: Listar produtos por categoria inexistente");
        try {
            controladorProduto.buscarProdutosPorCategoria("Inexistente");
            System.out.println("ERRO: Categoria inexistente deveria falhar");
        } catch (ProdutoException e) {
            System.out.println("SUCESSO: Categoria não encontrada tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        } catch (DadosInvalidosException e) {
            System.out.println("ERRO: Dados inválidos");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 8: Emitir alertas de estoque
        System.out.println("\nTeste 8: Emitir alertas de estoque");
        try {
            System.out.println("Produtos com estoque baixo:");
            controladorProduto.emitirAlertasEstoque();
        } catch (Exception e) {
            System.out.println("ERRO: Falha ao emitir alertas de estoque");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorPromocoes() throws Exception {
        System.out.println("\n=== TESTE CONTROLADOR DE PROMOÇÕES ===");
        ControladorPromocoes controladorPromocoes = ControladorPromocoes.getInstancia();
        Date hoje = new Date();
        Date amanha = new Date(hoje.getTime() + 86400000); // +1 dia
        Date semanaQueVem = new Date(hoje.getTime() + 86400000 * 7);

        // Teste 1: Criar promoção de produto válida
        System.out.println("\nTeste 1: Criar promoção de produto válida");
        try {
            controladorPromocoes.criarPromocaoProduto(
                    1, 10.0, true, hoje, semanaQueVem, "Promoção de notebook");
            System.out.println("SUCESSO: Promoção criada com sucesso");
        } catch (PromocaoException e) {
            System.out.println("ERRO: Falha ao criar promoção");
            System.out.println("Mensagem: " + e.getMessage());
            return;
        }

        // Teste 2: Criar cupom de desconto válido
        System.out.println("\nTeste 2: Criar cupom de desconto válido");
        try {
            controladorPromocoes.criarCupom(
                    "CUPOM10", 10.0, true, hoje, semanaQueVem, "Cupom 10%", 100);
            System.out.println("SUCESSO: Cupom criado com sucesso");
        } catch (PromocaoException e) {
            System.out.println("ERRO: Falha ao criar cupom");
            System.out.println("Mensagem: " + e.getMessage());
            return;
        }

        // Teste 3: Aplicar cupom válido
        System.out.println("\nTeste 3: Aplicar cupom válido");
        try {
            double valorComDesconto = controladorPromocoes.aplicarCupom(1000.0, "CUPOM10");
            System.out.println("SUCESSO: Valor com desconto: " + valorComDesconto);
        } catch (PromocaoException e) {
            System.out.println("ERRO: Falha ao aplicar cupom");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Aplicar cupom inválido
        System.out.println("\nTeste 4: Aplicar cupom inválido");
        try {
            controladorPromocoes.aplicarCupom(1000.0, "CUPOM_INVALIDO");
            System.out.println("ERRO: Cupom inválido deveria falhar");
        } catch (PromocaoException e) {
            System.out.println("SUCESSO: Cupom inválido tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 5: Verificar desconto de produto
        System.out.println("\nTeste 5: Verificar desconto de produto");
        try {
            double desconto = controladorPromocoes.verificarDescontoProduto(1);
            System.out.println("SUCESSO: Desconto do produto: " + desconto + "%");
        } catch (PromocaoException e) {
            System.out.println("ERRO: Falha ao verificar desconto do produto");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorFuncionario() throws Exception {
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
        System.out.println("\nTeste 1: Contratar funcionários válidos");
        try {
            controladorFuncionario.contratarFuncionario(assalariado);
            controladorFuncionario.contratarFuncionario(horista);
            System.out.println("SUCESSO: Funcionários contratados com sucesso");
        } catch (FuncionarioException | CampoObrigatorioException e) {
            System.out.println("ERRO: Falha ao contratar funcionários válidos");
            System.out.println("Mensagem: " + e.getMessage());
            return;
        }

        // Teste 2: Buscar funcionário existente
        System.out.println("\nTeste 2: Buscar funcionário existente");
        try {
            Funcionario funcionario = controladorFuncionario.buscarFuncionario(3);
            System.out.println("SUCESSO: Funcionário encontrado: " + funcionario.getNome());
        } catch (FuncionarioException e) {
            System.out.println("ERRO: Falha ao buscar funcionário existente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 3: Buscar funcionário inexistente
        System.out.println("\nTeste 3: Buscar funcionário inexistente");
        try {
            controladorFuncionario.buscarFuncionario(999);
            System.out.println("ERRO: Busca por funcionário inexistente deveria falhar");
        } catch (FuncionarioException e) {
            System.out.println("SUCESSO: Funcionário não encontrado tratado corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Calcular salário de funcionário
        System.out.println("\nTeste 4: Calcular salário de funcionário");
        try {
            double salario = controladorFuncionario.calcularSalarioFuncionario(3);
            System.out.println("SUCESSO: Salário calculado: " + salario);
        } catch (FuncionarioException e) {
            System.out.println("ERRO: Falha ao calcular salário");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 5: Tentar contratar funcionário inválido (sem nome)
        System.out.println("\nTeste 5: Tentar contratar funcionário inválido");
        try {
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

        // Teste 6: Listar funcionários por departamento
        System.out.println("\nTeste 6: Listar funcionários por departamento");
        try {
            Funcionario[] funcionariosVendas = controladorFuncionario.listarPorDepartamento("Vendas");
            System.out.println("SUCESSO: " + funcionariosVendas.length + " funcionários em Vendas");
        } catch (Exception e) {
            System.out.println("ERRO: Falha ao listar por departamento");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorFaturaEPagamento() throws Exception {
        System.out.println("\n=== TESTE CONTROLADOR DE FATURA E PAGAMENTO ===");
        ControladorFatura controladorFatura = ControladorFatura.getInstancia();
        ControladorPagamento controladorPagamento = ControladorPagamento.getInstancia();

        // Teste 1: Emitir fatura válida
        System.out.println("\nTeste 1: Emitir fatura válida");
        try {
            Fatura fatura = new Fatura(
                    1, 1, 1, 1000.0, new Date(),
                    new Date(System.currentTimeMillis() + 86400000 * 7),
                    "Fatura teste");
            controladorFatura.emitirFatura(fatura);
            System.out.println("SUCESSO: Fatura emitida: " + fatura);

            // Teste 2: Processar pagamento válido
            System.out.println("\nTeste 2: Processar pagamento válido");
            Pagamento pagamento = new Pagamento("credito", 1, 1000.0, "Cartão", new Date());
            boolean sucesso = controladorPagamento.processarPagamento(pagamento, fatura);

            if (sucesso) {
                System.out.println("SUCESSO: Pagamento processado com sucesso!");
                System.out.println("Comprovante: " + pagamento.gerarComprovante());
                System.out.println("Status da fatura: " + fatura.getStatus());
            } else {
                System.out.println("ERRO: Falha ao processar pagamento");
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        // Teste 3: Tentar pagar fatura já paga
        System.out.println("\nTeste 3: Tentar pagar fatura já paga");
        try {
            Fatura fatura = controladorFatura.buscarFatura(1);
            Pagamento pagamento = new Pagamento("debito", 2, 1000.0, "Cartão", new Date());
            boolean sucesso = controladorPagamento.processarPagamento(pagamento, fatura);

            if (sucesso) {
                System.out.println("ERRO: Pagamento de fatura já paga deveria falhar");
            }
        } catch (FaturaJaPagaException e) {
            System.out.println("SUCESSO: Fatura já paga tratada corretamente");
            System.out.println("Mensagem: " + e.getMessage());
        }

        // Teste 4: Listar faturas por cliente
        System.out.println("\nTeste 4: Listar faturas por cliente");
        try {
            Fatura[] faturas = controladorFatura.listarFaturasPorCliente(1);
            System.out.println("SUCESSO: " + faturas.length + " faturas encontradas");
        } catch (Exception e) {
            System.out.println("ERRO: Falha ao listar faturas");
            System.out.println("Mensagem: " + e.getMessage());
        }
    }

    private static void testarControladorVendaECarrinho() throws Exception {
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
            controladorCarrinho.adicionarItem(1, 1); // Notebook
            controladorCarrinho.adicionarItem(2, 2); // Mouse
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
            try {
                Venda vendaInvalida = new Venda(2, 1, new Date(), 0, "Concluída");
                controladorVenda.registrarVenda(vendaInvalida);
                System.out.println("ERRO: Venda com valor zero deveria falhar");
            } catch (Exception e) {
                System.out.println("SUCESSO: Venda inválida tratada corretamente");
                System.out.println("Mensagem: " + e.getMessage());
            }

            System.out.println("\nTeste 7: Listar vendas por cliente");
            try {
                Venda[] vendasCliente = controladorVenda.listarVendasPorCliente(1);
                System.out.println("SUCESSO: " + vendasCliente.length + " vendas encontradas");
            } catch (Exception e) {
                System.out.println("ERRO: Falha ao listar vendas por cliente");
                System.out.println("Mensagem: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private static void testarRecuperacaoSenha() throws Exception {
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