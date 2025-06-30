package repository.interfaces;

import model.Pagamento;

public interface IRepPagamento {
    boolean adicionar(Pagamento pagamento);
    Pagamento buscarPorId(int id);
    Pagamento[] listarTodos();
    boolean remover(int id);
    int getQuantidade();
}