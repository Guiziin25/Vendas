package com.application.dto;

public class PagamentoDto {
    private String metodo;
    private double valor;

    public PagamentoDto() {}

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}