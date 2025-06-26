package controller;

import model.Fatura;
import model.IPagamento;
import repository.RepFatura;

public class ControladorPagamento {
    private static ControladorPagamento instancia;
    private RepFatura repFatura;

    private ControladorPagamento() {
        this.repFatura = RepFatura.getInstancia();
    }

    public static synchronized ControladorPagamento getInstancia() {
        if (instancia == null) {
            instancia = new ControladorPagamento();
        }
        return instancia;
    }

    public boolean processarPagamento(IPagamento pagavel, Fatura fatura, String metodo) {
        boolean sucesso = pagavel.processarPagamento(fatura.getValorTotal());
        if (sucesso) {
            fatura.registrarPagamento(fatura.getValorTotal(), metodo);
            repFatura.atualizar(fatura);
        }
        return sucesso;
    }
}