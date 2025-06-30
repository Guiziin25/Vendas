package controller;

import model.Fatura;
import model.Pagamento;
import repository.RepPagamento;
import repository.RepFatura;

public class ControladorPagamento {
    private static ControladorPagamento instancia;
    private RepFatura repFatura;
    private RepPagamento repPagamento;

    private ControladorPagamento() {
        this.repFatura = RepFatura.getInstancia();
        this.repPagamento = RepPagamento.getInstancia();
    }

    public static synchronized ControladorPagamento getInstancia() {
        if (instancia == null) {
            instancia = new ControladorPagamento();
        }
        return instancia;
    }

    public boolean processarPagamento(Pagamento pagamento, Fatura fatura) {
        boolean sucesso = pagamento.processarPagamento(fatura.getValorTotal());
        if (sucesso) {
            fatura.registrarPagamento(fatura.getValorTotal(), pagamento.getStatus());
            repFatura.atualizar(fatura);
            repPagamento.adicionar(pagamento);
        }
        return sucesso;
    }
}