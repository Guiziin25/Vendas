package com.application.dto;

import java.util.Date;

public class FuncionarioDto {
    private String nome;
    private String email;
    private String senha;
    private String cargo; // Campo para receber o cargo como String
    private Date dataCadastro;

    public FuncionarioDto() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo; // Retorna o cargo como String
    }

    public void setCargo(String cargo) {
        this.cargo = cargo; // Define o cargo como String
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}