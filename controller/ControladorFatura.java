package controller;

import model.Fatura;
import repository.Interfaces.IRepFatura;
import repository.RepFatura;

public class ControladorFatura {
    private static ControladorFatura instancia;
    private IRepFatura repFatura;

    private ControladorFatura() {
        this.repFatura = RepFatura.getInstancia();
    }

    public static synchronized ControladorFatura getInstancia() {
        if (instancia == null) {
            instancia = new ControladorFatura();
        }
        return instancia;
    }

    public void emitirFatura(Fatura fatura) {
        if (fatura.getValorTotal() > 0) {
            repFatura.adicionar(fatura);
        }
    }
}