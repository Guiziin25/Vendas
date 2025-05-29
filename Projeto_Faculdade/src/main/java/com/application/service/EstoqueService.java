package com.application.service;

import com.application.dao.IEstoqueDAO;
import com.application.model.Estoque;

import java.util.List;

public class EstoqueService implements IEstoqueService {

    private IEstoqueDAO estoqueDAO;

    public EstoqueService(IEstoqueDAO estoqueDAO) {
        this.estoqueDAO = estoqueDAO;
    }

    @Override
    public void atualizarEstoque(Integer produtoId, int quantidade) {
        Estoque estoque = estoqueDAO.buscarPorProduto(produtoId);
        if (estoque != null) {
            estoque.setQuantidade(quantidade);
            estoqueDAO.atualizar(estoque);
        }
    }

    @Override
    public boolean verificarDisponibilidade(Integer produtoId, int quantidade) {
        Estoque estoque = estoqueDAO.buscarPorProduto(produtoId);
        return estoque != null && estoque.getQuantidade() >= quantidade;
    }

    @Override
    public List<Estoque> listarProdutosComBaixoEstoque() {
        return estoqueDAO.listarComBaixoEstoque();
    }

    @Override
    public void definirLimiteMinimo(Integer produtoId, int limite) {
        Estoque estoque = estoqueDAO.buscarPorProduto(produtoId);
        if (estoque != null) {
            estoque.setLimiteMinimo(limite);
            estoqueDAO.atualizar(estoque);
        }
    }
}