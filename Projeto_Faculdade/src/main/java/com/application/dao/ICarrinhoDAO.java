package com.application.dao;

import com.application.model.Carrinho;
import com.application.model.ItemCarrinho;

public interface ICarrinhoDAO {
    void salvar(Carrinho carrinho);
    Carrinho buscarPorCliente(Integer clienteId);
    void adicionarItem(ItemCarrinho item);
    void removerItem(Integer itemId);
}