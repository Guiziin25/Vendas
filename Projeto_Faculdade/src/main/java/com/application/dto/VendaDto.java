package com.application.dto;

import java.util.Date;
import java.util.List;

public class VendaDto {
    private int id;
    private List<ItemVendaDto> itens;
    private PagamentoDto pagamento;
    private Date dataVenda;

    public VendaDto() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemVendaDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaDto> itens) {
        this.itens = itens;
    }

    public PagamentoDto getPagamento() {
        return pagamento;
    }

    public void setPagamento(PagamentoDto pagamento) {
        this.pagamento = pagamento;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }
}