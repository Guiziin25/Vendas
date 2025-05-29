package com.application.dao;

import com.application.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class InMemoryProdutoDAO implements IProdutoDAO {
    private List<Produto> produtos = new ArrayList<>();

    @Override
    public void cadastrar(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public void editar(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId().equals(produto.getId())) {
                produtos.set(i, produto);
                break;
            }
        }
    }

    @Override
    public void excluir(Integer id) {
        produtos.removeIf(produto -> produto.getId().equals(id));
    }

    @Override
    public Produto buscarPorId(Integer id) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Produto> listar() {
        return new ArrayList<>(produtos);
    }

    @Override
    public List<Produto> buscarPorCategoria(Integer categoriaId) {
        // Implementar lógica para buscar produtos por categoria
        return null; // Retornar lista filtrada se necessário
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        // Implementar lógica para buscar produtos por nome
        return null; // Retornar lista filtrada se necessário
    }
}