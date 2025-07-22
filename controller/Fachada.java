package controller;

import exceptions.*;
import model.*;
import java.util.Date;
import java.util.List;

/**
 * Classe Fachada que fornece uma interface simplificada para a camada GUI interagir com o sistema.
 * Centraliza todas as operações do sistema em métodos mais simples e organizados.
 */
public class Fachada {
    // Instâncias dos controladores
    private final ControladorAcesso controladorAcesso;
    private final ControladorCliente controladorCliente;
    private final ControladorFuncionario controladorFuncionario;
    private final ControladorProduto controladorProduto;
    private final ControladorCarrinho controladorCarrinho;
    private final ControladorVenda controladorVenda;
    private final ControladorPromocoes controladorPromocoes;
    private final ControladorFatura controladorFatura;
    private final ControladorPagamento controladorPagamento;

    // Singleton da Fachada
    private static Fachada instancia;

    private Fachada() {
        this.controladorAcesso = ControladorAcesso.getInstancia();
        this.controladorCliente = ControladorCliente.getInstancia();
        this.controladorFuncionario = ControladorFuncionario.getInstancia();
        this.controladorProduto = ControladorProduto.getInstancia();
        this.controladorCarrinho = ControladorCarrinho.getInstancia();
        this.controladorVenda = ControladorVenda.getInstancia();
        this.controladorPromocoes = ControladorPromocoes.getInstancia();
        this.controladorFatura = ControladorFatura.getInstancia();
        this.controladorPagamento = ControladorPagamento.getInstancia();
    }

    public static synchronized Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    // Métodos de Autenticação
    public Funcionario autenticarFuncionario(String login, String senha) throws LoginInvalidoException, SistemaException {
        return controladorAcesso.autenticar(login, senha);
    }

    public Cliente autenticarCliente(int id, String senha) throws ClienteNaoEncontradoException, DadosInvalidosException, SistemaException {
        return controladorCliente.autenticarCliente(id, senha);
    }

    public boolean isAdmin(Funcionario funcionario) throws AcessoNegadoException, SistemaException {
        return controladorAcesso.isAdmin(funcionario);
    }

    // Métodos de Cliente
    public void cadastrarCliente(Cliente cliente) throws ClienteJaCadastradoException, DadosInvalidosException, SistemaException {
        controladorCliente.cadastrarCliente(cliente);
    }

    public Cliente buscarCliente(int id) throws ClienteNaoEncontradoException, SistemaException {
        return controladorCliente.buscarCliente(id);
    }

    public List<Cliente> listarClientes() throws SistemaException {
        return List.of(controladorCliente.listarClientes());
    }

    // Métodos de Produto
    public void cadastrarProduto(Produto produto) throws ProdutoException, SistemaException {
        controladorProduto.cadastrarProduto(produto);
    }

    public Produto buscarProduto(int id) throws ProdutoNaoEncontradoException, SistemaException {
        return controladorProduto.buscarProduto(id);
    }

    public List<Produto> listarProdutos() throws SistemaException {
        return List.of(controladorProduto.listarTodosProdutos());
    }

    public List<Produto> buscarProdutosPorCategoria(String categoria) throws DadosInvalidosException, SistemaException {
        return List.of(controladorProduto.buscarProdutosPorCategoria(categoria));
    }

    // Métodos de Carrinho
    public void adicionarAoCarrinho(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, EstoqueInsuficienteException, SistemaException {
        controladorCarrinho.adicionarItem(idProduto, quantidade);
    }

    public void removerDoCarrinho(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, SistemaException {
        controladorCarrinho.removerItem(idProduto, quantidade);
    }

    public List<Produto> listarCarrinho() throws CarrinhoVazioException, SistemaException {
        return List.of(controladorCarrinho.listarItens());
    }

    public double calcularTotalCarrinho() throws CarrinhoVazioException {
        return controladorCarrinho.calcularTotal();
    }

    public double calcularFrete() throws CarrinhoVazioException {
        return controladorCarrinho.calcularFrete();
    }

    // Métodos de Venda
    public void finalizarVenda(int idCliente, double valorTotal) throws VendaException, SistemaException {
        Venda venda = new Venda(controladorVenda.listarVendas().length + 1,
                idCliente, new Date(), valorTotal, "Concluída");
        controladorVenda.registrarVenda(venda);
    }

    public List<Venda> listarVendasPorCliente(int idCliente) throws ClienteNaoEncontradoException, SistemaException {
        return List.of(controladorVenda.listarVendasPorCliente(idCliente));
    }

    // Métodos de Promoções
    public void aplicarCupom(String codigoCupom, double valorOriginal) throws CupomInvalidoException, SistemaException {
        controladorPromocoes.aplicarCupom(valorOriginal, codigoCupom);
    }

    public double verificarDescontoProduto(int idProduto) throws PromocaoException {
        return controladorPromocoes.verificarDescontoProduto(idProduto);
    }

    // Métodos de Funcionários
    public void contratarFuncionario(Funcionario funcionario) throws FuncionarioException, SistemaException {
        controladorFuncionario.contratarFuncionario(funcionario);
    }

    public Funcionario buscarFuncionario(int id) throws FuncionarioNaoEncontradoException, SistemaException {
        return controladorFuncionario.buscarFuncionario(id);
    }

    // Métodos de Pagamento
    public boolean processarPagamento(String metodo, double valor, Fatura fatura)
            throws FaturaJaPagaException, FaturaVencidaException, PagamentoInvalidoException {
        Pagamento pagamento = new Pagamento(metodo, fatura.getIdCliente(), valor, metodo, new Date());
        return controladorPagamento.processarPagamento(pagamento, fatura);
    }

    // Métodos auxiliares para GUI
    public String[] listarCategorias() throws SistemaException {
        return controladorProduto.listarTodasCategorias();
    }

    public String[] listarMetodosPagamento() {
        return new String[]{"Cartão de Crédito", "Cartão de Débito", "PIX", "Boleto"};
    }

    public void limparCarrinho() {
        controladorCarrinho.limparCarrinho();
    }
}