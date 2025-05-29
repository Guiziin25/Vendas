package com.application.dao;

import com.application.dto.CategoriaDto;
import java.util.List;

public interface ICategoriaDAO {
    void cadastrar(CategoriaDto categoria);
    void editar(CategoriaDto categoria);
    void excluir(Integer id);
    CategoriaDto buscarPorId(Integer id); // Deve retornar CategoriaDto
    List<CategoriaDto> listar(); // Deve retornar List<CategoriaDto>
}