package com.application.dto;

public class ItemVendaDto {
    private int produtoId;
    private int quantidade;

    public ItemVendaDto() {}

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}