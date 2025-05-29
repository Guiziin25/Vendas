package com.application.controller;

import com.application.dto.FuncionarioDto;
import com.application.dto.LoginDto;
import com.application.dto.TokenDto;
import com.application.model.CargoFuncionario;
import com.application.model.Funcionario;
import com.application.service.IFuncionarioService;

public class FuncionarioController {

    private final IFuncionarioService funcionarioService;

    public FuncionarioController(IFuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public FuncionarioDto cadastrar(FuncionarioDto dto) {
        var funcionario = toEntity(dto);
        funcionarioService.cadastrarFuncionario(funcionario);
        return toDto(funcionario);
    }

    public FuncionarioDto editar(int id, FuncionarioDto dto) {
        var funcionario = funcionarioService.buscarFuncionario(id);
        if (funcionario == null) return null;
        updateEntity(funcionario, dto);
        funcionarioService.editarFuncionario(funcionario);
        return toDto(funcionario);
    }

    public FuncionarioDto buscar(int id) {
        var funcionario = funcionarioService.buscarFuncionario(id);
        return funcionario != null ? toDto(funcionario) : null;
    }

    public TokenDto autenticar(LoginDto dto) {
        var funcionario = funcionarioService.autenticar(dto.getEmail(), dto.getSenha());
        if (funcionario == null) return null;
        var token = new TokenDto();
        token.setToken("token-simulado-para-" + funcionario.getEmail());
        return token;
    }

    private Funcionario toEntity(FuncionarioDto dto) {
        var funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        funcionario.setSenha(dto.getSenha());

        // Converte a String para CargoFuncionario
        funcionario.setCargo(CargoFuncionario.valueOf(dto.getCargo().toUpperCase())); // Converte a String para o enum

        funcionario.setDataCadastro(dto.getDataCadastro());
        return funcionario;
    }

    private void updateEntity(Funcionario funcionario, FuncionarioDto dto) {
        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        funcionario.setSenha(dto.getSenha());
        funcionario.setCargo(CargoFuncionario.valueOf(dto.getCargo().toUpperCase())); // Atualiza o cargo
        funcionario.setDataCadastro(dto.getDataCadastro());
    }

    private FuncionarioDto toDto(Funcionario funcionario) {
        var dto = new FuncionarioDto();
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setSenha(funcionario.getSenha());
        dto.setCargo(funcionario.getCargo().name()); // Converte o enum para String
        dto.setDataCadastro(funcionario.getDataCadastro());
        return dto;
    }
}