package com.application.dto;

import java.util.List;

public class CarrinhoDto {
    private int clienteId;
    private List<ItemCarrinhoDto> itens;

    public CarrinhoDto() {}

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemCarrinhoDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinhoDto> itens) {
        this.itens = itens;
    }
}