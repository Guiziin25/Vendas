package com.application.dao;

import com.application.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class InMemoryClienteDAO implements IClienteDAO {
    private List<Cliente> clientes = new ArrayList<>();

    @Override
    public void cadastrar(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public void editar(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(cliente.getId())) {
                clientes.set(i, cliente);
                break;
            }
        }
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        return clientes.stream()
                .filter(cliente -> cliente.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(clientes); // Retorna uma nova lista com os clientes
    }
}