package model;

import java.util.Date;

public class Venda {
    private int id;
    private int idCliente;
    private Date dataVenda;
    private double valorTotal;
    private String status;

    // Construtor
    public Venda(int id, int idCliente, Date dataVenda, double valorTotal, String status) {
        this.id = id;
        this.idCliente = idCliente;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}