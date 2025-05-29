package com.application.service;

import com.application.model.Produto;
import com.application.model.ImagemProduto;
import java.util.List;

public interface IProdutoService {

    void cadastrarProduto(Produto produto, List<ImagemProduto> imagens);

    void editarProduto(Produto produto);

    void removerProduto(Integer id);

    Produto buscarProduto(Integer id);

    List<Produto> listarProdutos();

    List<Produto> listarPorCategoria(Integer categoriaId);

    void adicionarImagem(Integer produtoId, ImagemProduto imagem);

    void removerImagem(Integer imagemId);
}