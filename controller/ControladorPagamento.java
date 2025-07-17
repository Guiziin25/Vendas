package controller;

import exceptions.*;
import model.Fatura;
import model.Pagamento;
import repository.RepFatura;
import repository.RepPagamento;
import controller.interfaces.IControladorPagamento;

public class ControladorPagamento implements IControladorPagamento {
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

    /**
     * Processa um pagamento para uma fatura
     * @param pagamento Pagamento a ser processado
     * @param fatura Fatura a ser paga
     * @return Comprovante de pagamento
     * @throws PagamentoInvalidoException Se o pagamento for inválido
     * @throws FaturaNaoEncontradaException Se a fatura não for encontrada
     * @throws FaturaJaPagaException Se a fatura já estiver paga
     * @throws FaturaVencidaException Se a fatura estiver vencida
     * @throws MetodoPagamentoNaoSuportadoException Se o método de pagamento não for suportado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean processarPagamento(Pagamento pagamento, Fatura fatura)
            throws FaturaJaPagaException, FaturaVencidaException, PagamentoInvalidoException {

        // Verifica se a fatura já está paga
        if (!fatura.getStatus().equalsIgnoreCase("pendente")) {
            throw new FaturaJaPagaException(fatura.getId());
        }

        // Verifica se a fatura está vencida
        if (fatura.estaVencida()) {
            throw new FaturaVencidaException(fatura.getId());
        }

        // Processa o pagamento
        boolean pagamentoSucesso = pagamento.processarPagamento(fatura.getValorTotal());

        if (pagamentoSucesso) {
            fatura.registrarPagamento(fatura.getValorTotal(), pagamento.getStatus());
        }

        return pagamentoSucesso;
    }

    /**
     * Lista todos os pagamentos registrados
     * @return Array de pagamentos
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Pagamento[] listarPagamentos() throws SistemaException {
        try {
            return repPagamento.listarTodos();
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar pagamentos: " + e.getMessage());
        }
    }

    /**
     * Busca um pagamento por ID
     * @param id ID do pagamento
     * @return Pagamento encontrado
     * @throws PagamentoInvalidoException Se o pagamento não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Pagamento buscarPagamento(int id) throws PagamentoInvalidoException, SistemaException {
        try {
            Pagamento[] pagamentos = repPagamento.listarTodos();
            for (Pagamento pagamento : pagamentos) {
                if (pagamento.getId() == id) {
                    return pagamento;
                }
            }
            throw new PagamentoInvalidoException(id);
        } catch (PagamentoInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar pagamento: " + e.getMessage());
        }
    }

    /**
     * Gera um relatório de pagamentos por período
     * @param dataInicio Data de início do período
     * @param dataFim Data de fim do período
     * @return Array de pagamentos no período
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Pagamento[] gerarRelatorioPagamentos(java.util.Date dataInicio, java.util.Date dataFim)
            throws SistemaException {
        try {
            if (dataInicio == null || dataFim == null) {
                throw new SistemaException("Datas não podem ser nulas");
            }
            if (dataInicio.after(dataFim)) {
                throw new SistemaException("Data de início deve ser anterior à data de fim");
            }

            Pagamento[] todosPagamentos = repPagamento.listarTodos();
            java.util.List<Pagamento> pagamentosPeriodo = new java.util.ArrayList<>();

            for (Pagamento pagamento : todosPagamentos) {
                // Supondo que Pagamento tenha um campo dataPagamento
                if (pagamento.getDataPagamento() != null &&
                        !pagamento.getDataPagamento().before(dataInicio) &&
                        !pagamento.getDataPagamento().after(dataFim)) {
                    pagamentosPeriodo.add(pagamento);
                }
            }

            return pagamentosPeriodo.toArray(new Pagamento[0]);
        } catch (Exception e) {
            throw new SistemaException("Erro ao gerar relatório de pagamentos: " + e.getMessage());
        }
    }
}