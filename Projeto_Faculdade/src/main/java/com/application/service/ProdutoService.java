package com.application.service;

import com.application.dao.IProdutoDAO;
import com.application.model.Produto;
import com.application.model.ImagemProduto;

import java.util.List;

public class ProdutoService implements IProdutoService {

    private IProdutoDAO produtoDAO;

    // Construtor que aceita IProdutoDAO
    public ProdutoService(IProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    @Override
    public void cadastrarProduto(Produto produto, List<ImagemProduto> imagens) {
        // Lógica para associar imagens ao produto, se necessário
        produtoDAO.cadastrar(produto);
    }

    @Override
    public void editarProduto(Produto produto) {
        produtoDAO.editar(produto);
    }

    @Override
    public void removerProduto(Integer id) {
        produtoDAO.excluir(id);
    }

    @Override
    public Produto buscarProduto(Integer id) {
        return produtoDAO.buscarPorId(id);
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoDAO.listar();
    }

    @Override
    public List<Produto> listarPorCategoria(Integer categoriaId) {
        return produtoDAO.buscarPorCategoria(categoriaId);
    }

    @Override
    public void adicionarImagem(Integer produtoId, ImagemProduto imagem) {
        // Implementar lógica para adicionar imagem ao produto
    }

    @Override
    public void removerImagem(Integer imagemId) {
        // Implementar lógica para remover imagem do produto
    }
}