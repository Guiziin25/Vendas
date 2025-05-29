package com.application.service;

import com.application.dao.ICategoriaDAO;
import com.application.dto.CategoriaDto;

import java.util.List;

public class CategoriaService implements ICategoriaService {
    private ICategoriaDAO categoriaDAO;

    public CategoriaService(ICategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    @Override
    public void cadastrarCategoria(CategoriaDto categoria) {
        categoriaDAO.cadastrar(categoria);
    }

    @Override
    public void editarCategoria(CategoriaDto categoria) {
        categoriaDAO.editar(categoria);
    }

    @Override
    public void excluirCategoria(Integer id) {
        categoriaDAO.excluir(id);
    }

    @Override
    public CategoriaDto buscarCategoria(Integer id) {
        return categoriaDAO.buscarPorId(id);
    }

    @Override
    public List<CategoriaDto> listarCategorias() {
        return categoriaDAO.listar();
    }
}