package com.application.dao;

import com.application.dto.CategoriaDto;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCategoriaDAO implements ICategoriaDAO {
    private List<CategoriaDto> categorias = new ArrayList<>();

    @Override
    public void cadastrar(CategoriaDto categoria) {
        categorias.add(categoria);
    }

    @Override
    public void editar(CategoriaDto categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(categoria.getId())) {
                categorias.set(i, categoria);
                break;
            }
        }
    }

    @Override
    public void excluir(Integer id) {
        categorias.removeIf(categoria -> categoria.getId().equals(id));
    }

    @Override
    public CategoriaDto buscarPorId(Integer id) {
        return categorias.stream()
                .filter(categoria -> categoria.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CategoriaDto> listar() {
        return new ArrayList<>(categorias);
    }
}