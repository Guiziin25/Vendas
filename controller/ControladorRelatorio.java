package controller;

import exceptions.*;
import model.Venda;
import repository.Interfaces.IRepVenda;
import repository.RepVenda;
import java.util.Date;

public class ControladorRelatorio {
    private static ControladorRelatorio instancia;
    private IRepVenda repVenda;

    private ControladorRelatorio() {
        this.repVenda = RepVenda.getInstancia();
    }

    public static synchronized ControladorRelatorio getInstancia() {
        if (instancia == null) {
            instancia = new ControladorRelatorio();
        }
        return instancia;
    }

    /**
     * Gera um relatório de vendas para o período especificado
     * @param inicio Data de início do período
     * @param fim Data de fim do período
     * @return String contendo o relatório formatado
     * @throws DadosInvalidosException Se as datas forem inválidas
     * @throws SistemaException Se ocorrer um erro ao gerar o relatório
     */
    public String gerarRelatorioVendas(Date inicio, Date fim) throws DadosInvalidosException, SistemaException {
        try {
            // Validação das datas
            if (inicio == null || fim == null) {
                throw new DadosInvalidosException("Datas não podem ser nulas");
            }

            if (inicio.after(fim)) {
                throw new DadosInvalidosException("Data de início deve ser anterior à data de fim");
            }

            // Obter vendas do período
            Venda[] vendas = repVenda.buscarPorData(inicio, fim);

            // Calcular totais
            double totalVendas = 0;
            int quantidadeVendas = vendas.length;

            for (Venda venda : vendas) {
                totalVendas += venda.getValorTotal();
            }

            // Gerar relatório formatado
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("=== RELATÓRIO DE VENDAS ===\n");
            relatorio.append("Período: ").append(inicio).append(" até ").append(fim).append("\n");
            relatorio.append("Total de vendas: ").append(quantidadeVendas).append("\n");
            relatorio.append("Valor total: R$ ").append(String.format("%.2f", totalVendas)).append("\n");
            relatorio.append("=== DETALHES DAS VENDAS ===\n");

            for (Venda venda : vendas) {
                relatorio.append("ID: ").append(venda.getId())
                        .append(" | Data: ").append(venda.getDataVenda())
                        .append(" | Valor: R$ ").append(String.format("%.2f", venda.getValorTotal()))
                        .append("\n");
            }

            return relatorio.toString();

        } catch (DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao gerar relatório de vendas: " + e.getMessage());
        }
    }

    /**
     * Gera um relatório de vendas por cliente
     * @param idCliente ID do cliente
     * @return String contendo o relatório formatado
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws SistemaException Se ocorrer um erro ao gerar o relatório
     */
    public String gerarRelatorioVendasPorCliente(int idCliente) throws ClienteNaoEncontradoException, SistemaException {
        try {
            Venda[] vendas = repVenda.buscarPorCliente(idCliente);

            if (vendas.length == 0) {
                throw new ClienteNaoEncontradoException("Nenhuma venda encontrada para o cliente ID: " + idCliente);
            }

            double totalVendas = 0;
            for (Venda venda : vendas) {
                totalVendas += venda.getValorTotal();
            }

            StringBuilder relatorio = new StringBuilder();
            relatorio.append("=== RELATÓRIO DE VENDAS POR CLIENTE ===\n");
            relatorio.append("Cliente ID: ").append(idCliente).append("\n");
            relatorio.append("Total de compras: ").append(vendas.length).append("\n");
            relatorio.append("Valor total: R$ ").append(String.format("%.2f", totalVendas)).append("\n");
            relatorio.append("=== HISTÓRICO DE COMPRAS ===\n");

            for (Venda venda : vendas) {
                relatorio.append("Data: ").append(venda.getDataVenda())
                        .append(" | Valor: R$ ").append(String.format("%.2f", venda.getValorTotal()))
                        .append("\n");
            }

            return relatorio.toString();

        } catch (ClienteNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao gerar relatório por cliente: " + e.getMessage());
        }
    }

    /**
     * Gera um relatório de vendas mensal
     * @param mes Mês (1-12)
     * @param ano Ano
     * @return String contendo o relatório formatado
     * @throws DadosInvalidosException Se o mês ou ano forem inválidos
     * @throws SistemaException Se ocorrer um erro ao gerar o relatório
     */
    public String gerarRelatorioMensal(int mes, int ano) throws DadosInvalidosException, SistemaException {
        try {
            if (mes < 1 || mes > 12) {
                throw new DadosInvalidosException("Mês inválido (deve ser entre 1 e 12)");
            }

            if (ano <= 0) {
                throw new DadosInvalidosException("Ano inválido");
            }

            // Criar intervalo de datas para o mês
            Date inicio = new Date(ano - 1900, mes - 1, 1);
            Date fim = new Date(ano - 1900, mes, 0); // Último dia do mês

            return gerarRelatorioVendas(inicio, fim);

        } catch (DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao gerar relatório mensal: " + e.getMessage());
        }
    }
}