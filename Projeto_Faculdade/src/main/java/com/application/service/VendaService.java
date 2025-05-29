package com.application.service;

import com.application.dao.IVendaDAO;
import com.application.model.Venda;
import com.application.model.ItemVenda;
import com.application.model.Pagamento;

import java.util.Date;
import java.util.List;

public class VendaService implements IVendaService {

    private IVendaDAO vendaDAO;

    public VendaService(IVendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    @Override
    public void realizarVenda(Venda venda, List<ItemVenda> itens, Pagamento pagamento) {
        // Aqui você pode adicionar lógica para calcular valores, atualizar estoque, etc.
        venda.setItens(itens);
        venda.setPagamento(pagamento);
        vendaDAO.cadastrar(venda);
    }

    @Override
    public void cancelarVenda(Integer vendaId) {
        Venda venda = vendaDAO.buscarPorId(vendaId);
        if (venda != null) {
            venda.setStatus(com.application.model.StatusVenda.CANCELADO);
            vendaDAO.editar(venda);
        }
    }

    @Override
    public Venda buscarVenda(Integer id) {
        return vendaDAO.buscarPorId(id);
    }

    @Override
    public List<Venda> listarVendas() {
        return vendaDAO.listar();
    }

    @Override
    public Object gerarRelatorioVendas(Date inicio, Date fim) {
        // Implementação de relatórios pode retornar DTOs, mapas, etc.
        return vendaDAO.listarPorPeriodo(inicio, fim);
    }

    @Override
    public Object gerarNotaFiscal(Integer vendaId) {
        // Implementação da geração de nota fiscal (pode retornar DTO com dados)
        throw new UnsupportedOperationException("Gerar nota fiscal não implementado.");
    }
}