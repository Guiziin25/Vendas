package model;

import java.util.Date;

public class Promocao {
    private int id;
    private String codigo; // Código único (null para promoções de produto)
    private int idProduto; // -1 para cupons gerais
    private double valorDesconto;
    private boolean isPercentual;
    private Date dataInicio;
    private Date dataFim;
    private boolean ativo;
    private String descricao;
    private int usosMaximos;
    private int usosAtuais;

    // Construtor para promoção de produto
    public Promocao(int id, int idProduto, double valorDesconto, boolean isPercentual,
                    Date dataInicio, Date dataFim, String descricao) {
        this.id = id;
        this.idProduto = idProduto;
        this.valorDesconto = valorDesconto;
        this.isPercentual = isPercentual;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.ativo = true;
        this.codigo = null;
        this.usosMaximos = 0; // Ilimitado
        this.usosAtuais = 0;
    }

    // Construtor para cupom de desconto
    public Promocao(int id, String codigo, double valorDesconto, boolean isPercentual,
                    Date dataInicio, Date dataFim, String descricao, int usosMaximos) {
        this.id = id;
        this.codigo = codigo;
        this.idProduto = -1; // Cupom geral
        this.valorDesconto = valorDesconto;
        this.isPercentual = isPercentual;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.descricao = descricao;
        this.ativo = true;
        this.usosMaximos = usosMaximos;
        this.usosAtuais = 0;
    }

    // Aplica o desconto e incrementa o contador de usos
    public double aplicarDesconto(double valorOriginal) {
        if (!podeSerUsada()) {
            return valorOriginal;
        }

        usosAtuais++;
        if (isPercentual) {
            return valorOriginal * (1 - valorDesconto / 100);
        } else {
            double resultado = valorOriginal - valorDesconto;
            return resultado > 0 ? resultado : 0;
        }
    }

    // Verifica se a promoção/cupom pode ser usada
    public boolean podeSerUsada() {
        if (!estaAtiva()) {
            return false;
        }
        if (usosMaximos > 0 && usosAtuais >= usosMaximos) {
            return false;
        }
        return true;
    }

    // Verifica se está no período válido
    public boolean estaAtiva() {
        Date hoje = new Date();
        return ativo && !hoje.before(dataInicio) && !hoje.after(dataFim);
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public boolean isPercentual() {
        return isPercentual;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getUsosMaximos() {
        return usosMaximos;
    }

    public int getUsosAtuais() {
        return usosAtuais;
    }

    public boolean isCupom() {
        return codigo != null;
    }
}