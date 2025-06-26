package repository;

import model.Venda;
import repository.Interfaces.IRepVenda;
import java.util.Date;

public class RepVenda implements IRepVenda {
    private static RepVenda instancia;
    private Venda[] vendas;
    private int tamanhoAtual;
    private static final int TAMANHO_MAX = 1000;

    private RepVenda() {
        vendas = new Venda[TAMANHO_MAX];
        tamanhoAtual = 0;
    }

    public static synchronized RepVenda getInstancia() {
        if (instancia == null) {
            instancia = new RepVenda();
        }
        return instancia;
    }

    @Override
    public void adicionar(Venda venda) {
        if (tamanhoAtual < TAMANHO_MAX) {
            vendas[tamanhoAtual] = venda;
            tamanhoAtual++;
        } else {
            System.out.println("ERRO: Limite máximo de vendas atingido!");
        }
    }

    @Override
    public Venda buscarPorId(int id) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (vendas[i].getId() == id) {
                return vendas[i];
            }
        }
        return null;
    }

    @Override
    public Venda[] listarTodos() {
        Venda[] todasVendas = new Venda[tamanhoAtual];
        System.arraycopy(vendas, 0, todasVendas, 0, tamanhoAtual);
        return todasVendas;
    }

    @Override
    public Venda[] buscarPorCliente(int idCliente) {
        int count = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (vendas[i].getIdCliente() == idCliente) {
                count++;
            }
        }

        Venda[] vendasCliente = new Venda[count];
        int index = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (vendas[i].getIdCliente() == idCliente) {
                vendasCliente[index++] = vendas[i];
            }
        }
        return vendasCliente;
    }

    @Override
    public Venda[] buscarPorData(Date dataInicio, Date dataFim) {
        int count = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            Date dataVenda = vendas[i].getDataVenda();
            if (!dataVenda.before(dataInicio) && !dataVenda.after(dataFim)) {
                count++;
            }
        }

        Venda[] vendasPeriodo = new Venda[count];
        int index = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            Date dataVenda = vendas[i].getDataVenda();
            if (!dataVenda.before(dataInicio) && !dataVenda.after(dataFim)) {
                vendasPeriodo[index++] = vendas[i];
            }
        }
        return vendasPeriodo;
    }

    @Override
    public double calcularTotalVendas() {
        double total = 0.0;
        for (int i = 0; i < tamanhoAtual; i++) {
            total += vendas[i].getValorTotal();
        }
        return total;
    }

    @Override
    public int getQuantidade() {
        return tamanhoAtual;
    }
}