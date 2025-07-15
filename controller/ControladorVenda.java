package controller;

import exceptions.CampoObrigatorioException;
import model.Venda;
import repository.Interfaces.IRepVenda;
import repository.RepVenda;
import exceptions.VendaException;
import exceptions.CarrinhoVazioException;
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

    public void registrarVenda(Venda venda) throws VendaException {
        if (venda == null) {
            throw new VendaException("Venda não pode ser nula");
        }

        if (venda.getValorTotal() <= 0) {
            throw new VendaException("Valor da venda deve ser positivo");
        }

        repVenda.adicionar(venda);
    }

    public double gerarRelatorioVendas(Date inicio, Date fim) throws VendaException {
        try {
            // Validação de parâmetros
            if (inicio == null || fim == null) {
                throw new CampoObrigatorioException("Datas de início e fim");
            }

            if (fim.before(inicio)) {
                throw new VendaException("Data final deve ser após data inicial");
            }

            // Lógica principal
            Venda[] vendas = repVenda.buscarPorData(inicio, fim);
            double total = 0;
            for (Venda v : vendas) {
                total += v.getValorTotal();
            }
            return total;

        } catch (CampoObrigatorioException e) {
            // Encapsula a exceção de validação em VendaException
            throw new VendaException(e.getMessage());
        }
    }
}