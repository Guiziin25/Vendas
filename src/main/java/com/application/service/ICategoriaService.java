package com.application.service;

import com.application.dto.CategoriaDto;
import java.util.List;

public interface ICategoriaService {
    void cadastrarCategoria(CategoriaDto categoria);
    void editarCategoria(CategoriaDto categoria);
    void excluirCategoria(Integer id);
    CategoriaDto buscarCategoria(Integer id);
    List<CategoriaDto> listarCategorias();
}