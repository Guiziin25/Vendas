package com.application.controller;

import com.application.dto.CarrinhoDto;
import com.application.dto.ItemCarrinhoDto;
import com.application.dto.TotalCarrinhoDto;
import com.application.dto.PagamentoDto;
import com.application.dto.VendaDto;
import com.application.service.ICarrinhoService;

public class CarrinhoController {

    private final ICarrinhoService carrinhoService;

    public CarrinhoController(ICarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    public CarrinhoDto adicionarItem(ItemCarrinhoDto dto) {
        carrinhoService.adicionarItem(dto.getClienteId(), dto.getProdutoId(), dto.getQuantidade());
        throw new UnsupportedOperationException("Implementação não pronta");
    }

    public void removerItem(int id) {
        carrinhoService.removerItem(id);
    }

    public ItemCarrinhoDto atualizarQuantidade(int id, int quantidade) {
        carrinhoService.atualizarQuantidade(id, quantidade);
        throw new UnsupportedOperationException("Implementação não pronta");
    }

    public TotalCarrinhoDto calcularTotal(String cupom) {
        throw new UnsupportedOperationException("Implementação não pronta");
    }

    public VendaDto finalizarCompra(PagamentoDto pagamentoDto) {
        throw new UnsupportedOperationException("Implementação não pronta");
    }
}