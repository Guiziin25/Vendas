package com.application.service;

import com.application.model.Funcionario;

public interface IFuncionarioService {

    void cadastrarFuncionario(Funcionario funcionario);

    void editarFuncionario(Funcionario funcionario);

    Funcionario buscarFuncionario(Integer id);

    Funcionario autenticar(String email, String senha);
}