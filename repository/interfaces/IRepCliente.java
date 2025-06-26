package repository.Interfaces;

import model.Cliente;

public interface IRepCliente {
    void adicionar(Cliente cliente);
    Cliente buscarPorId(int id);
    Cliente[] listarTodos();
    boolean atualizar(Cliente cliente);
    boolean remover(int id);
    Cliente[] buscarPorNome(String nome);
}