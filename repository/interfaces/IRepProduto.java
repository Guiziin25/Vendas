package repository.Interfaces;

import model.Produto;

public interface IRepProduto {
    // ---- MÉTODOS BÁSICOS (CRUD) ---- //
    void adicionar(Produto produto);
    Produto buscarPorId(int id);
    Produto[] listarTodos();
    boolean atualizar(Produto produto);
    boolean remover(int id);

    // ---- MÉTODOS DE BUSCA ---- //
    Produto[] buscarPorNome(String nome);
    Produto[] buscarPorCategoria(String categoria);
    int getQuantidade();

    // ---- MÉTODOS DE IMAGENS ---- //
    boolean adicionarImagemAoProduto(int idProduto, String caminhoImagem);
    boolean removerImagemDoProduto(int idProduto, String caminhoImagem);
    boolean definirImagemPrincipal(int idProduto, String caminhoImagem);
    String[] listarImagensDoProduto(int idProduto);
    String getImagemPrincipalDoProduto(int idProduto);

    // ---- MÉTODOS DE ESTOQUE ---- //
    boolean atualizarEstoque(int idProduto, int quantidade);
    Produto[] listarProdutosComEstoqueBaixo();
}