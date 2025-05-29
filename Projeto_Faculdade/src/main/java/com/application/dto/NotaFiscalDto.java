package com.application.dto;

public class NotaFiscalDto {
    private int vendaId;
    private String detalhes;

    public NotaFiscalDto() {}

    public int getVendaId() {
        return vendaId;
    }

    public void setVendaId(int vendaId) {
        this.vendaId = vendaId;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}