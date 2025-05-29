package com.application.service;

import com.application.dao.ICarrinhoDAO;
import com.application.model.Carrinho;
import com.application.model.ItemCarrinho;
import com.application.model.Produto;
import com.application.model.Venda;
import com.application.model.Pagamento;

import javax.persistence.EntityManager;

public class CarrinhoService implements ICarrinhoService {

    private ICarrinhoDAO carrinhoDAO;
    private EntityManager entityManager;

    public CarrinhoService(ICarrinhoDAO carrinhoDAO, EntityManager entityManager) {
        this.carrinhoDAO = carrinhoDAO;
        this.entityManager = entityManager;
    }

    @Override
    public void adicionarItem(Integer clienteId, Integer produtoId, int quantidade) {
        Carrinho carrinho = carrinhoDAO.buscarPorCliente(clienteId);
        if (carrinho == null) {
            carrinho = new Carrinho();
            // Inicializar carrinho, possivelmente setar cliente
        }
        Produto produto = entityManager.find(Produto.class, produtoId);
        ItemCarrinho item = new ItemCarrinho();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setCarrinho(carrinho);
        carrinhoDAO.adicionarItem(item);
    }

    @Override
    public void removerItem(Integer itemId) {
        carrinhoDAO.removerItem(itemId);
    }

    @Override
    public void atualizarQuantidade(Integer itemId, int quantidade) {
        // É necessário um DAO específico para ItemCarrinho para atualizar
        // exemplo genérico:
        throw new UnsupportedOperationException("Atualizar quantidade não implementado.");
    }

    @Override
    public double calcularTotal(Integer carrinhoId, String cupom) {
        // Implementação da lógica cálculo total, aplicando desconto se houver
        throw new UnsupportedOperationException("Calcular total não implementado.");
    }

    @Override
    public Venda finalizarCompra(Integer carrinhoId, Object dadosPagamento) {
        // Implementar finalização de compra, criação de venda e pagamento
        throw new UnsupportedOperationException("Finalizar compra não implementado.");
    }
}