package com.application.controller;

import com.application.dto.ProdutoDto;
import com.application.model.ImagemProduto;
import com.application.model.Produto;
import com.application.service.IProdutoService;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoController {

    private final IProdutoService produtoService;

    public ProdutoController(IProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public ProdutoDto cadastrar(ProdutoDto dto, List<ImagemProduto> imagens) {
        var produto = toEntity(dto);
        produtoService.cadastrarProduto(produto, imagens);
        return toDto(produto);
    }

    public ProdutoDto editar(int id, ProdutoDto dto) {
        var produto = produtoService.buscarProduto(id);
        if (produto == null) return null;
        updateEntity(produto, dto);
        produtoService.editarProduto(produto);
        return toDto(produto);
    }

    public void remover(int id) {
        produtoService.removerProduto(id);
    }

    public ProdutoDto buscar(int id) {
        var produto = produtoService.buscarProduto(id);
        return produto != null ? toDto(produto) : null;
    }

    public List<ProdutoDto> listar() {
        return produtoService.listarProdutos()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<ProdutoDto> listarPorCategoria(int categoriaId) {
        return produtoService.listarPorCategoria(categoriaId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private Produto toEntity(ProdutoDto dto) {
        var p = new Produto();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setPreco(dto.getPreco());
        p.setPrecoPromocional(dto.getPrecoPromocional());
        p.setEmPromocao(dto.isEmPromocao());
        p.setDataCadastro(dto.getDataCadastro());
        return p;
    }

    private void updateEntity(Produto produto, ProdutoDto dto) {
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setPrecoPromocional(dto.getPrecoPromocional());
        produto.setEmPromocao(dto.isEmPromocao());
        produto.setDataCadastro(dto.getDataCadastro());
    }

    private ProdutoDto toDto(Produto produto) {
        var dto = new ProdutoDto();
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setPrecoPromocional(produto.getPrecoPromocional());
        dto.setEmPromocao(produto.isEmPromocao());
        dto.setDataCadastro(produto.getDataCadastro());
        return dto;
    }
}