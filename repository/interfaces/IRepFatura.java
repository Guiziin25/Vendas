package repository.Interfaces;

import model.Fatura;

public interface IRepFatura {
    void adicionar(Fatura fatura);
    Fatura buscarPorId(int id);
    Fatura[] listarTodas();
    Fatura[] buscarPorCliente(int idCliente);
    Fatura[] buscarPorStatus(String status);
    boolean atualizar(Fatura fatura);
    boolean remover(int id);
    int getQuantidade();
}