package com.application.dao;

import com.application.model.Produto;
import java.util.List;

public interface IProdutoDAO {
    void cadastrar(Produto produto);
    void editar(Produto produto);
    void excluir(Integer id);
    Produto buscarPorId(Integer id);
    List<Produto> listar();
    List<Produto> buscarPorCategoria(Integer categoriaId);
    List<Produto> buscarPorNome(String nome);
}