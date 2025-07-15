package controller;

import model.Fatura;
import repository.Interfaces.IRepFatura;
import repository.RepFatura;
import exceptions.FaturaException;
import exceptions.FaturaJaPagaException;
import exceptions.FaturaNaoEncontradaException;
import exceptions.FaturaVencidaException;
import exceptions.CampoObrigatorioException;
import java.util.Date;

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

    public void emitirFatura(Fatura fatura) throws FaturaException, CampoObrigatorioException {
        // Validação de parâmetro nulo
        if (fatura == null) {
            throw new CampoObrigatorioException("Fatura não pode ser nula");
        }

        // Validação de valor
        if (fatura.getValorTotal() <= 0) {
            throw new FaturaException("Valor da fatura deve ser positivo (valor atual: " + fatura.getValorTotal() + ")");
        }

        // Validação adicional de datas
        if (fatura.getDataVencimento() != null &&
                fatura.getDataVencimento().before(new Date())) {
            throw new FaturaException("Data de vencimento não pode ser no passado");
        }

        repFatura.adicionar(fatura);
    }

    public Fatura buscarFatura(int id) throws FaturaNaoEncontradaException {
        Fatura fatura = repFatura.buscarPorId(id);
        if (fatura == null) {
            throw new FaturaNaoEncontradaException(id);
        }
        return fatura;
    }

    public void pagarFatura(int idFatura, double valor)
            throws FaturaNaoEncontradaException, FaturaJaPagaException, FaturaVencidaException {
        Fatura fatura = buscarFatura(idFatura);

        if (!fatura.getStatus().equalsIgnoreCase("pendente")) {
            throw new FaturaJaPagaException(idFatura);
        }

        if (fatura.estaVencida()) {
            throw new FaturaVencidaException(idFatura);
        }

        fatura.registrarPagamento(valor, "Pago");
        repFatura.atualizar(fatura);
    }
}