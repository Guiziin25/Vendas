package com.application.dao;

import com.application.model.Estoque;
import java.util.List;

public interface IEstoqueDAO {
    void atualizar(Estoque estoque);
    Estoque buscarPorProduto(Integer produtoId);
    List<Estoque> listarComBaixoEstoque();
}