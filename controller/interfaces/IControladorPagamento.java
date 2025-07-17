package controller.interfaces;

import exceptions.FaturaJaPagaException;
import exceptions.FaturaVencidaException;
import exceptions.PagamentoInvalidoException;
import exceptions.SistemaException;
import model.Fatura;
import model.Pagamento;

public interface IControladorPagamento {
    boolean processarPagamento(Pagamento pagamento, Fatura fatura) throws FaturaJaPagaException, FaturaVencidaException, PagamentoInvalidoException;
    Pagamento [] listarPagamentos() throws SistemaException;
    Pagamento buscarPagamento(int id) throws PagamentoInvalidoException, SistemaException;
    Pagamento [] gerarRelatorioPagamentos(java.util.Date dataInicio, java.util.Date dataFim) throws SistemaException;
}