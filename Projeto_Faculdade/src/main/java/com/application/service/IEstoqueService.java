package com.application.service;

import com.application.model.Estoque;
import java.util.List;

public interface IEstoqueService {

    void atualizarEstoque(Integer produtoId, int quantidade);

    boolean verificarDisponibilidade(Integer produtoId, int quantidade);

    List<Estoque> listarProdutosComBaixoEstoque();

    void definirLimiteMinimo(Integer produtoId, int limite);
}