package com.application.dto;

public class TotalCarrinhoDto {
    private double total;
    private double desconto;

    public TotalCarrinhoDto() {}

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}