package controller.interfaces;

import exceptions.*;
import model.Fatura;

public interface IControladorFatura {
    void emitirFatura(Fatura fatura) throws FaturaException, SistemaException;
    Fatura buscarFatura(int id) throws FaturaNaoEncontradaException, SistemaException;
    void registrarPagamento(int idFatura, double valor, String metodo) throws FaturaNaoEncontradaException, FaturaJaPagaException, FaturaVencidaException, PagamentoInvalidoException, SistemaException;
    Fatura[] listarFaturasPorCliente(int idCliente) throws SistemaException;
    Fatura[] listarFaturasPorStatus(String status) throws SistemaException;
    void aplicarDesconto(int idFatura, double percentual) throws FaturaException, SistemaException;
}
