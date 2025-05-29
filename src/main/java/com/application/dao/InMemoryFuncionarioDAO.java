package com.application.dao;

import com.application.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFuncionarioDAO implements IFuncionarioDAO {
    private List<Funcionario> funcionarios = new ArrayList<>();

    @Override
    public void cadastrar(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    @Override
    public void editar(Funcionario funcionario) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId().equals(funcionario.getId())) {
                funcionarios.set(i, funcionario);
                break;
            }
        }
    }

    @Override
    public Funcionario buscarPorId(Integer id) {
        return funcionarios.stream()
                .filter(funcionario -> funcionario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Funcionario buscarPorEmail(String email) {
        return funcionarios.stream()
                .filter(funcionario -> funcionario.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Funcionario> listar() {
        return new ArrayList<>(funcionarios);
    }
}