package controller;

import exceptions.FaturaException;
import exceptions.FaturaJaPagaException;
import exceptions.FaturaNaoEncontradaException;
import exceptions.FaturaVencidaException;
import exceptions.PagamentoInvalidoException;
import exceptions.SistemaException;
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

    /**
     * Emite uma nova fatura
     * @param fatura Fatura a ser emitida
     * @throws FaturaException Se a fatura for inválida
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void emitirFatura(Fatura fatura) throws FaturaException, SistemaException {
        try {
            if (fatura == null) {
                throw new FaturaException("Fatura não pode ser nula");
            }

            if (fatura.getValorTotal() <= 0) {
                throw new FaturaException("Valor da fatura deve ser maior que zero");
            }

            if (fatura.getIdCliente() <= 0) {
                throw new FaturaException("Cliente associado à fatura é inválido");
            }

            repFatura.adicionar(fatura);
        } catch (FaturaException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao emitir fatura: " + e.getMessage());
        }
    }

    /**
     * Busca uma fatura por ID
     * @param id ID da fatura
     * @return Fatura encontrada
     * @throws FaturaNaoEncontradaException Se a fatura não for encontrada
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Fatura buscarFatura(int id) throws FaturaNaoEncontradaException, SistemaException {
        try {
            Fatura fatura = repFatura.buscarPorId(id);
            if (fatura == null) {
                throw new FaturaNaoEncontradaException(id);
            }
            return fatura;
        } catch (FaturaNaoEncontradaException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar fatura: " + e.getMessage());
        }
    }

    /**
     * Registra um pagamento para uma fatura
     * @param idFatura ID da fatura
     * @param valor Valor do pagamento
     * @param metodo Método de pagamento
     * @throws FaturaNaoEncontradaException Se a fatura não for encontrada
     * @throws FaturaJaPagaException Se a fatura já estiver paga
     * @throws FaturaVencidaException Se a fatura estiver vencida
     * @throws PagamentoInvalidoException Se o pagamento for inválido
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void registrarPagamento(int idFatura, double valor, String metodo)
            throws FaturaNaoEncontradaException, FaturaJaPagaException,
            FaturaVencidaException, PagamentoInvalidoException, SistemaException {
        try {
            Fatura fatura = buscarFatura(idFatura);

            if (!fatura.getStatus().equalsIgnoreCase("pendente")) {
                throw new FaturaJaPagaException(idFatura);
            }

            if (fatura.estaVencida()) {
                throw new FaturaVencidaException(idFatura);
            }

            if (valor <= 0) {
                throw new PagamentoInvalidoException(valor);
            }

            if (metodo == null || metodo.trim().isEmpty()) {
                throw new PagamentoInvalidoException("Método de pagamento inválido");
            }

            fatura.registrarPagamento(valor, metodo);
            repFatura.atualizar(fatura);
        } catch (FaturaNaoEncontradaException | FaturaJaPagaException |
                 FaturaVencidaException | PagamentoInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    /**
     * Lista todas as faturas de um cliente
     * @param idCliente ID do cliente
     * @return Array de faturas do cliente
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Fatura[] listarFaturasPorCliente(int idCliente) throws SistemaException {
        try {
            if (idCliente <= 0) {
                throw new SistemaException("ID do cliente inválido");
            }
            return repFatura.buscarPorCliente(idCliente);
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar faturas do cliente: " + e.getMessage());
        }
    }

    /**
     * Lista faturas por status
     * @param status Status das faturas (pendente, pago, etc.)
     * @return Array de faturas com o status especificado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Fatura[] listarFaturasPorStatus(String status) throws SistemaException {
        try {
            if (status == null || status.trim().isEmpty()) {
                throw new SistemaException("Status não pode ser vazio");
            }
            return repFatura.buscarPorStatus(status);
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar faturas por status: " + e.getMessage());
        }
    }

    /**
     * Aplica desconto a uma fatura
     * @param idFatura ID da fatura
     * @param percentual Percentual de desconto (0-100)
     * @throws FaturaException Se a fatura não for encontrada, já estiver paga ou o percentual for inválido
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void aplicarDesconto(int idFatura, double percentual)
            throws FaturaException, SistemaException {
        try {
            Fatura fatura = buscarFatura(idFatura);

            if (!fatura.getStatus().equalsIgnoreCase("pendente")) {
                throw new FaturaJaPagaException(idFatura);
            }

            if (percentual <= 0 || percentual > 100) {
                throw new FaturaException("Percentual de desconto inválido (deve ser entre 0 e 100)");
            }

            fatura.aplicarDesconto(percentual);
            repFatura.atualizar(fatura);
        } catch (FaturaException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao aplicar desconto: " + e.getMessage());
        }
    }
}