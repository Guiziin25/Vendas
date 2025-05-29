package com.application.service;

import com.application.model.Venda;

public interface ICarrinhoService {

    void adicionarItem(Integer clienteId, Integer produtoId, int quantidade);

    void removerItem(Integer itemId);

    void atualizarQuantidade(Integer itemId, int quantidade);

    double calcularTotal(Integer carrinhoId, String cupom);

    Venda finalizarCompra(Integer carrinhoId, Object dadosPagamento);
}
