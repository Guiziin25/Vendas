package controller;

import exceptions.*;
import model.Venda;
import repository.Interfaces.IRepVenda;
import repository.RepVenda;
import java.util.Date;

public class ControladorVenda {
    private static ControladorVenda instancia;
    private IRepVenda repVenda;

    private ControladorVenda() {
        this.repVenda = RepVenda.getInstancia();
    }

    public static synchronized ControladorVenda getInstancia() {
        if (instancia == null) {
            instancia = new ControladorVenda();
        }
        return instancia;
    }

    /**
     * Registra uma nova venda no sistema
     * @param venda Objeto Venda contendo os dados da venda
     * @throws VendaException Se os dados da venda forem inválidos
     * @throws SistemaException Se ocorrer um erro ao registrar a venda
     */
    public void registrarVenda(Venda venda) throws VendaException, SistemaException {
        try {
            // Validações
            if (venda == null) {
                throw new VendaException("Venda não pode ser nula");
            }

            if (venda.getValorTotal() <= 0) {
                throw new VendaException("Valor da venda deve ser maior que zero");
            }

            if (venda.getIdCliente() <= 0) {
                throw new VendaException("Cliente inválido");
            }

            if (venda.getDataVenda() == null || venda.getDataVenda().after(new Date())) {
                throw new VendaException("Data da venda inválida");
            }

            repVenda.adicionar(venda);

        } catch (VendaException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao registrar venda: " + e.getMessage());
        }
    }

    /**
     * Gera um relatório de vendas com o total vendido no período especificado
     * @param inicio Data de início do período
     * @param fim Data de fim do período
     * @return Valor total das vendas no período
     * @throws DadosInvalidosException Se as datas forem inválidas
     * @throws SistemaException Se ocorrer um erro ao gerar o relatório
     */
    public double gerarRelatorioVendas(Date inicio, Date fim) throws DadosInvalidosException, SistemaException {
        try {
            // Validação das datas
            if (inicio == null || fim == null) {
                throw new DadosInvalidosException("Datas não podem ser nulas");
            }

            if (inicio.after(fim)) {
                throw new DadosInvalidosException("Data de início deve ser anterior à data de fim");
            }

            Venda[] vendas = repVenda.buscarPorData(inicio, fim);
            double total = 0;

            for (Venda v : vendas) {
                total += v.getValorTotal();
            }

            return total;

        } catch (DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao gerar relatório de vendas: " + e.getMessage());
        }
    }

    /**
     * Busca uma venda pelo ID
     * @param id ID da venda
     * @return Objeto Venda encontrado
     * @throws VendaNaoEncontradaException Se a venda não for encontrada
     * @throws SistemaException Se ocorrer um erro na busca
     */
    public Venda buscarVenda(int id) throws VendaNaoEncontradaException, SistemaException {
        try {
            Venda venda = repVenda.buscarPorId(id);
            if (venda == null) {
                throw new VendaNaoEncontradaException(id);
            }
            return venda;
        } catch (VendaNaoEncontradaException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar venda: " + e.getMessage());
        }
    }

    /**
     * Lista todas as vendas de um cliente
     * @param idCliente ID do cliente
     * @return Array de vendas do cliente
     * @throws ClienteNaoEncontradoException Se o cliente não tiver vendas registradas
     * @throws SistemaException Se ocorrer um erro na busca
     */
    public Venda[] listarVendasPorCliente(int idCliente) throws ClienteNaoEncontradoException, SistemaException {
        try {
            Venda[] vendas = repVenda.buscarPorCliente(idCliente);
            if (vendas.length == 0) {
                throw new ClienteNaoEncontradoException(idCliente);
            }
            return vendas;
        } catch (ClienteNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar vendas por cliente: " + e.getMessage());
        }
    }

    /**
     * Calcula o total de vendas realizadas
     * @return Valor total de todas as vendas
     * @throws SistemaException Se ocorrer um erro no cálculo
     */
    public double calcularTotalVendas() throws SistemaException {
        try {
            return repVenda.calcularTotalVendas();
        } catch (Exception e) {
            throw new SistemaException("Erro ao calcular total de vendas: " + e.getMessage());
        }
    }
}