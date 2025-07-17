package controller.interfaces;

import exceptions.*;
import model.Produto;

public interface IControladorProduto {
    boolean cadastrarProduto(Produto produto) throws ProdutoException, SistemaException;
    Produto buscarProduto(int id) throws ProdutoNaoEncontradoException, SistemaException;
    Produto[] listarTodosProdutos() throws SistemaException;
    boolean atualizarProduto(Produto produto) throws ProdutoNaoEncontradoException, ProdutoException, SistemaException;
    boolean removerProduto(int id) throws ProdutoNaoEncontradoException, SistemaException;
    Produto[] buscarProdutosPorNome(String nome) throws DadosInvalidosException, SistemaException;
    Produto[] buscarProdutosPorCategoria(String categoria) throws DadosInvalidosException, SistemaException;
    int getQuantidadeProdutos() throws SistemaException;
    boolean editarNomeCategoria(String nomeAntigo, String nomeNovo) throws CategoriaNaoEncontradaException, DadosInvalidosException, SistemaException;
    boolean podeRemoverCategoria(String nomeCategoria) throws DadosInvalidosException, SistemaException;
    String[] listarTodasCategorias() throws SistemaException;
    boolean adicionarImagem(int idProduto, String caminhoImagem) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException;
    boolean removerImagem(int idProduto, String caminhoImagem) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException;
    boolean definirImagemPrincipal(int idProduto, String caminhoImagem) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException;
    String[] listarImagens(int idProduto)  throws ProdutoNaoEncontradoException, SistemaException;
    String getImagemPrincipal(int idProduto) throws ProdutoNaoEncontradoException, SistemaException;
    boolean registrarVenda(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, EstoqueInsuficienteException, DadosInvalidosException, SistemaException;
    boolean reporEstoque(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, DadosInvalidosException, SistemaException;
    String verificarEstoque(int idProduto) throws ProdutoNaoEncontradoException, SistemaException;
    void emitirAlertasEstoque() throws SistemaException;
}
