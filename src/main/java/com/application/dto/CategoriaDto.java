package com.application.dto;

public class CategoriaDto {
    private Integer id; // ID da categoria
    private String nome; // Nome da categoria
    private String descricao; // Descrição da categoria

    public CategoriaDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}