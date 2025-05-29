package com.application.service;

import com.application.model.Venda;
import com.application.model.ItemVenda;
import com.application.model.Pagamento;

import java.util.Date;
import java.util.List;

public interface IVendaService {

    void realizarVenda(Venda venda, List<ItemVenda> itens, Pagamento pagamento);

    void cancelarVenda(Integer vendaId);

    Venda buscarVenda(Integer id);

    List<Venda> listarVendas();

    Object gerarRelatorioVendas(Date inicio, Date fim);

    Object gerarNotaFiscal(Integer vendaId);
}