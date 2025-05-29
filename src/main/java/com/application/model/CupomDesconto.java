package com.application.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cupons_desconto")
public class CupomDesconto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codigo;

    private double valorDesconto;

    @Enumerated(EnumType.STRING)
    private TipoDesconto tipo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataValidade;

    private boolean ativo;

    public CupomDesconto() {}

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public TipoDesconto getTipo() {
        return tipo;
    }

    public void setTipo(TipoDesconto tipo) {
        this.tipo = tipo;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}