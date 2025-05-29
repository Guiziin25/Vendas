package com.application.dao;

import com.application.model.Cliente;
import java.util.List;

public interface IClienteDAO {
    void cadastrar(Cliente cliente);
    void editar(Cliente cliente);
    Cliente buscarPorId(Integer id);
    Cliente buscarPorEmail(String email);
    List<Cliente> listar();
}