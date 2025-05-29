package com.application.controller;

import com.application.dto.ClienteDto;
import com.application.dto.LoginDto;
import com.application.dto.TokenDto;
import com.application.model.Cliente;
import com.application.service.IClienteService;

public class ClienteController {

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ClienteDto cadastrar(ClienteDto dto) {
        var cliente = toEntity(dto);
        clienteService.cadastrarCliente(cliente);
        return toDto(cliente);
    }

    public ClienteDto editar(int id, ClienteDto dto) {
        var cliente = clienteService.buscarCliente(id);
        if (cliente == null) return null;
        updateEntity(cliente, dto);
        clienteService.editarCliente(cliente);
        return toDto(cliente);
    }

    public ClienteDto buscar(int id) {
        var cliente = clienteService.buscarCliente(id);
        return cliente != null ? toDto(cliente) : null;
    }

    public TokenDto autenticar(LoginDto dto) {
        var cliente = clienteService.autenticar(dto.getEmail(), dto.getSenha());
        if (cliente == null) return null;
        var token = new TokenDto();
        token.setToken("token-simulado-para-" + cliente.getEmail());
        return token;
    }

    public void recuperarSenha(String email) {
        clienteService.recuperarSenha(email);
    }

    private Cliente toEntity(ClienteDto dto) {
        var cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(dto.getSenha());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataCadastro(dto.getDataCadastro());
        return cliente;
    }

    private void updateEntity(Cliente cliente, ClienteDto dto) {
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setSenha(dto.getSenha());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataCadastro(dto.getDataCadastro());
    }

    private ClienteDto toDto(Cliente cliente) {
        var dto = new ClienteDto();
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        dto.setSenha(cliente.getSenha());
        dto.setTelefone(cliente.getTelefone());
        dto.setDataCadastro(cliente.getDataCadastro());
        return dto;
    }
}