package controller.interfaces;

import exceptions.CarrinhoVazioException;
import exceptions.EstoqueInsuficienteException;
import exceptions.ProdutoNaoEncontradoException;
import exceptions.SistemaException;
import model.Produto;

public interface IControladorCarrinho {
    void adicionarItem(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, EstoqueInsuficienteException, SistemaException;
    double calcularTotal() throws CarrinhoVazioException;
    double calcularFrete() throws CarrinhoVazioException;
    void removerItem(int idProduto, int quantidade) throws ProdutoNaoEncontradoException, CarrinhoVazioException, SistemaException;
    void limparCarrinho();
    Produto[] listarItens() throws CarrinhoVazioException;
    int getQuantidadePorProduto(int idProduto) throws CarrinhoVazioException;
}