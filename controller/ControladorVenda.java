package controller;

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

    public void registrarVenda(Venda venda) {
        // Validações complexas
        if (venda.getValorTotal() <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        repVenda.adicionar(venda);
    }

    public double gerarRelatorioVendas(Date inicio, Date fim) {
        Venda[] vendas = repVenda.buscarPorData(inicio, fim);
        double total = 0;
        for (Venda v : vendas) {
            total += v.getValorTotal();
        }
        return total;
    }
}