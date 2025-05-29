package com.application.dao;

import com.application.model.Funcionario;
import java.util.List;

public interface IFuncionarioDAO {
    void cadastrar(Funcionario funcionario);
    void editar(Funcionario funcionario);
    Funcionario buscarPorId(Integer id);
    Funcionario buscarPorEmail(String email);
    List<Funcionario> listar();
}