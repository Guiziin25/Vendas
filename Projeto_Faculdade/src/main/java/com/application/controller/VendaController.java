package com.application.controller;

import com.application.dto.RelatorioVendasDto;
import com.application.dto.VendaDto;
import com.application.dto.NotaFiscalDto;
import com.application.model.Venda;
import com.application.service.IVendaService;

import java.util.List;
import java.util.stream.Collectors;

public class VendaController {

    private final IVendaService vendaService;

    public VendaController(IVendaService vendaService) {
        this.vendaService = vendaService;
    }

    public VendaDto realizarVenda(VendaDto dto) {
        var venda = toEntity(dto);
        vendaService.realizarVenda(venda, venda.getItens(), venda.getPagamento());
        return toDto(venda);
    }

    public void cancelarVenda(int id) {
        vendaService.cancelarVenda(id);
    }

    public VendaDto buscar(int id) {
        var venda = vendaService.buscarVenda(id);
        return venda != null ? toDto(venda) : null;
    }

    public List<VendaDto> listar() {
        return vendaService.listarVendas()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public RelatorioVendasDto relatorioVendas(String inicio, String fim) {
        throw new UnsupportedOperationException("Implementação não pronta");
    }

    public NotaFiscalDto emitirNotaFiscal(int id) {
        throw new UnsupportedOperationException("Implementação não pronta");
    }

    private Venda toEntity(VendaDto dto) {
        return new Venda();
    }

    private VendaDto toDto(Venda venda) {
        return new VendaDto();
    }
}