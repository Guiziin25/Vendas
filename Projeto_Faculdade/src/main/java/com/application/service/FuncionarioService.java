package com.application.service;

import com.application.dao.IFuncionarioDAO;
import com.application.model.Funcionario;

import java.util.List;

public class FuncionarioService implements IFuncionarioService {

    private IFuncionarioDAO funcionarioDAO;

    public FuncionarioService(IFuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    @Override
    public void cadastrarFuncionario(Funcionario funcionario) {
        funcionarioDAO.cadastrar(funcionario);
    }
  @Override

    public void editarFuncionario(Funcionario funcionario) {
        funcionarioDAO.editar(funcionario);
    }

    @Override
    public Funcionario buscarFuncionario(Integer id) {
        return funcionarioDAO.buscarPorId(id);
    }

    @Override
    public Funcionario autenticar(String email, String senha) {
        Funcionario funcionario = funcionarioDAO.buscarPorEmail(email);
        if (funcionario != null && funcionario.getSenha().equals(senha)) {
            return funcionario;
        }
        return null;
    }
}