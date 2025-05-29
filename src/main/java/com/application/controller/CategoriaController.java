package com.application.controller;

import com.application.dto.CategoriaDto;
import com.application.service.ICategoriaService;

import java.util.List;


public class CategoriaController {
    private ICategoriaService categoriaService;

    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public void cadastrar(CategoriaDto categoria) {
        categoriaService.cadastrarCategoria(categoria);
    }

    public void editar(CategoriaDto categoria) {
        categoriaService.editarCategoria(categoria);
    }

    public void excluir(Integer id) {
        categoriaService.excluirCategoria(id);
    }

    public CategoriaDto buscar(Integer id) {
        return categoriaService.buscarCategoria(id);
    }

    public List<CategoriaDto> listar() {
        return categoriaService.listarCategorias();
    }
}