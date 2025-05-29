package com.application.service;

import com.application.model.Cliente;

public interface IClienteService {

    void cadastrarCliente(Cliente cliente);

    void editarCliente(Cliente cliente);

    Cliente buscarCliente(Integer id);

    Cliente autenticar(String email, String senha);

    void recuperarSenha(String email);
}