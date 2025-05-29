package com.application.service;

import com.application.dao.IClienteDAO;
import com.application.model.Cliente;

public class ClienteService implements IClienteService {

    private IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void cadastrarCliente(Cliente cliente) {
        clienteDAO.cadastrar(cliente);
    }

    @Override
    public void editarCliente(Cliente cliente) {
        clienteDAO.editar(cliente);
    }

    @Override
    public Cliente buscarCliente(Integer id) {
        return clienteDAO.buscarPorId(id);
    }

    @Override
    public Cliente autenticar(String email, String senha) {
        Cliente cliente = clienteDAO.buscarPorEmail(email);
        if (cliente != null && cliente.getSenha().equals(senha)) {
            return cliente;
        }
        return null;
    }

    @Override
    public void recuperarSenha(String email) {
        // Implementar lógica para recuperar senha (ex: enviar email)
        throw new UnsupportedOperationException("Recuperar senha não implementado");
    }
}