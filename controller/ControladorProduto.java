package controller;

import model.Produto;
import repository.Interfaces.IRepProduto;
import repository.RepProduto;
import exceptions.ProdutoException;
import exceptions.ProdutoNaoEncontradoException;
import exceptions.EstoqueInsuficienteException;
import exceptions.CampoObrigatorioException;
import exceptions.CategoriaNaoEncontradaException;

public class ControladorProduto {
    private static ControladorProduto instancia;
    private IRepProduto repProduto;

    private ControladorProduto() {
        this.repProduto = RepProduto.getInstancia();
    }

    public static synchronized ControladorProduto getInstancia() {
        if (instancia == null) {
            instancia = new ControladorProduto();
        }
        return instancia;
    }

    public boolean cadastrarProduto(Produto produto) throws ProdutoException {
        try {
            if (produto == null) {
                throw new CampoObrigatorioException("Produto");
            }
            validarCamposObrigatorios(produto);

            if (produto.getPreco() <= 0) {
                throw new ProdutoException("Preço deve ser maior que zero");
            }

            repProduto.adicionar(produto);
            return true;
        } catch (CampoObrigatorioException e) {
            throw new ProdutoException(e.getMessage()); // Encapsula em ProdutoException
        }
    }

    public Produto buscarProduto(int id) throws ProdutoNaoEncontradoException {
        Produto produto = repProduto.buscarPorId(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException(id);
        }
        return produto;
    }

    public Produto[] listarTodosProdutos() {
        return repProduto.listarTodos();
    }

    public boolean atualizarProduto(Produto produto) throws ProdutoException {
        try {
            // Validação de nulidade
            if (produto == null) {
                throw new CampoObrigatorioException("Produto");
            }

            // Validação de campos obrigatórios
            validarCamposObrigatorios(produto);

            // Tentativa de atualização no repositório
            if (!repProduto.atualizar(produto)) {
                throw new ProdutoException("Falha ao atualizar produto");
            }

            return true;
        } catch (CampoObrigatorioException e) {
            // Encapsula a exceção de validação em ProdutoException
            throw new ProdutoException("Erro de validação: " + e.getMessage());
        }
    }

    public boolean removerProduto(int id) throws ProdutoNaoEncontradoException {
        if (!repProduto.remover(id)) {
            throw new ProdutoNaoEncontradoException(id);
        }
        return true;
    }

    public Produto[] buscarProdutosPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new Produto[0];
        }
        return repProduto.buscarPorNome(nome);
    }

    public Produto[] buscarProdutosPorCategoria(String categoria) throws CategoriaNaoEncontradaException {
        try {
            // Validação de entrada
            if (categoria == null || categoria.trim().isEmpty()) {
                throw new CampoObrigatorioException("Categoria");
            }

            // Busca no repositório
            Produto[] produtos = repProduto.buscarPorCategoria(categoria);

            // Verifica se encontrou resultados
            if (produtos.length == 0) {
                throw new CategoriaNaoEncontradaException(categoria);
            }

            return produtos;
        } catch (CampoObrigatorioException e) {
            // Converte para CategoriaNaoEncontradaException mantendo a mensagem original
            throw new CategoriaNaoEncontradaException(e.getMessage());
        }
    }

    public boolean registrarVenda(int idProduto, int quantidade)
            throws ProdutoNaoEncontradoException, EstoqueInsuficienteException {
        try {
            if (quantidade <= 0) {
                // Usando o construtor que recebe ID
                throw new ProdutoNaoEncontradoException(idProduto);
            }

            Produto produto = buscarProduto(idProduto);
            if (produto.getQuantidadeEstoque() < quantidade) {
                throw new EstoqueInsuficienteException(idProduto, produto.getQuantidadeEstoque());
            }

            return repProduto.atualizarEstoque(idProduto, -quantidade);
        } catch (ProdutoException e) {
            // Agora usando o construtor correto
            throw new ProdutoNaoEncontradoException(idProduto);
        }
    }

    public String verificarEstoque(int idProduto) throws ProdutoNaoEncontradoException {
        Produto produto = buscarProduto(idProduto);
        return "Estoque: " + produto.getQuantidadeEstoque() +
                " | Mínimo: " + produto.getEstoqueMinimo() +
                (produto.precisaRepor() ? " (ESTOQUE BAIXO!)" : "");
    }

    private void validarCamposObrigatorios(Produto produto) throws CampoObrigatorioException {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new CampoObrigatorioException("Nome do produto");
        }
        if (produto.getCategoria() == null || produto.getCategoria().isEmpty()) {
            throw new CampoObrigatorioException("Categoria");
        }
    }
}