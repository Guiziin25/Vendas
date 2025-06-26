package model;

import java.util.Date;

public class Fatura {
    // Atributos
    private int id;
    private int idPedido;
    private int idCliente;
    private double valorTotal;
    private Date dataEmissao;
    private Date dataVencimento;
    private String status;
    private String metodoPagamento;
    private double valorPago;
    private Date dataPagamento;
    private String descricao;
    private boolean possuiDesconto;
    private double valorDesconto;

    // Construtor completo
    public Fatura(int id, int idPedido, int idCliente, double valorTotal,
                  Date dataEmissao, Date dataVencimento, String descricao) {
        this.id = id;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
        this.descricao = descricao;
        this.status = "pendente";
        this.metodoPagamento = null;
        this.valorPago = 0.0;
        this.dataPagamento = null;
        this.possuiDesconto = false;
        this.valorDesconto = 0.0;
    }

    // Construtor simplificado
    public Fatura(int id, int idPedido, int idCliente, double valorTotal) {
        this.id = id;
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
        this.dataEmissao = new Date();
        this.dataVencimento = new Date(System.currentTimeMillis() + 86400000 * 7);
        this.descricao = "Fatura do pedido " + idPedido;
        this.status = "pendente";
        this.metodoPagamento = null;
        this.valorPago = 0.0;
        this.dataPagamento = null;
        this.possuiDesconto = false;
        this.valorDesconto = 0.0;
    }

    // Método para aplicar desconto
    public void aplicarDesconto(double percentual) {
        if (percentual > 0 && percentual <= 100) {
            if (this.status.equals("pendente")) {
                this.valorDesconto = this.valorTotal * (percentual / 100);
                this.valorTotal = this.valorTotal - this.valorDesconto;
                this.possuiDesconto = true;
            }
        }
    }

    // Método para registrar pagamento
    public void registrarPagamento(double valor, String metodo) {
        if (valor > 0 && metodo != null) {
            if (metodo.isEmpty() == false) {
                this.valorPago = valor;
                this.metodoPagamento = metodo;
                this.dataPagamento = new Date();

                if (valor >= (this.valorTotal - this.valorDesconto)) {
                    this.status = "pago";
                } else {
                    this.status = "parcial";
                }
            }
        }
    }

    // Método para verificar se está vencida
    public boolean estaVencida() {
        Date hoje = new Date();
        if (hoje.after(this.dataVencimento)) {
            if (this.status.equals("pago") == false) {
                return true;
            }
        }
        return false;
    }

    // Getters e Setters COMPLETOS (sem atalhos)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getValorTotal() {
        return this.valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataEmissao() {
        return this.dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataVencimento() {
        return this.dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getStatus() {
        if (this.estaVencida()) {
            return "atrasado";
        } else {
            return this.status;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetodoPagamento() {
        return this.metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public double getValorPago() {
        return this.valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public Date getDataPagamento() {
        return this.dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPossuiDesconto() {
        return this.possuiDesconto;
    }

    public void setPossuiDesconto(boolean possuiDesconto) {
        this.possuiDesconto = possuiDesconto;
    }

    public double getValorDesconto() {
        return this.valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    // Método toString completo
    @Override
    public String toString() {
        String metodo = this.metodoPagamento;
        if (metodo == null) {
            metodo = "Nenhum";
        }

        return "Fatura #" + this.id +
                " - Cliente #" + this.idCliente +
                " | Pedido #" + this.idPedido + "\n" +
                "Valor: R$" + String.format("%.2f", this.valorTotal) +
                " | Desconto: R$" + String.format("%.2f", this.valorDesconto) + "\n" +
                "Status: " + this.getStatus() +
                " | Método: " + metodo + "\n" +
                "Emissão: " + this.dataEmissao.toString() +
                " | Vencimento: " + this.dataVencimento.toString();
    }
}