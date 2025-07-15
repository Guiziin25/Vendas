package controller;

import model.Fatura;
import model.Pagamento;
import repository.RepPagamento;
import repository.RepFatura;
import exceptions.PagamentoException;
import exceptions.MetodoPagamentoNaoSuportadoException;
import exceptions.PagamentoInvalidoException;
import exceptions.CampoObrigatorioException;

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

    public boolean processarPagamento(Pagamento pagamento, Fatura fatura)
            throws PagamentoException, CampoObrigatorioException {  // Adicionado CampoObrigatorioException
        // Validações iniciais
        if (pagamento == null) {
            throw new CampoObrigatorioException("Pagamento");
        }
        if (fatura == null) {
            throw new CampoObrigatorioException("Fatura");
        }
        if (fatura.getValorTotal() <= 0) {
            throw new PagamentoInvalidoException(fatura.getValorTotal());
        }

        // Processamento do pagamento
        boolean sucesso = pagamento.processarPagamento(fatura.getValorTotal());
        if (sucesso) {
            fatura.registrarPagamento(fatura.getValorTotal(), pagamento.getStatus());
            repFatura.atualizar(fatura);
            repPagamento.adicionar(pagamento);
        } else {
            throw new PagamentoException("Falha ao processar pagamento");
        }

        return sucesso;
    }
}