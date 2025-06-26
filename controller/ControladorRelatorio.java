package controller;

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

    public void gerarRelatorioVendas(Date inicio, Date fim) {
        System.out.println("Relatório de vendas gerado para o período: "
                + inicio + " até " + fim);
    }
}