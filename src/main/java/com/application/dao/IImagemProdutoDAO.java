package com.application.dao;

import com.application.model.ImagemProduto;
import java.util.List;

public interface IImagemProdutoDAO {
    void cadastrar(ImagemProduto imagemProduto);
    void editar(ImagemProduto imagemProduto);
    void excluir(Integer id);
    ImagemProduto buscarPorId(Integer id);
    List<ImagemProduto> listarPorProduto(Integer produtoId);
}