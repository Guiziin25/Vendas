package repository.Interfaces;

import model.Venda;

import java.util.Date;

public interface IRepVenda {
    void adicionar(Venda venda);
    Venda buscarPorId(int id);
    Venda[] listarTodos();
    Venda[] buscarPorCliente(int idCliente);
    Venda[] buscarPorData(Date dataInicio, Date dataFim);
    double calcularTotalVendas();
    int getQuantidade();
}