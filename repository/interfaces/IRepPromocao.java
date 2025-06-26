package repository.Interfaces;

import model.Promocao;
import java.util.Date;

public interface IRepPromocao {

    boolean adicionar(Promocao promocao);
    Promocao buscarPorId(int id);
    Promocao buscarPorCodigo(String codigo);
    Promocao[] buscarPorProduto(int idProduto);
    Promocao[] buscarAtivas();
    Promocao[] buscarExpiradas();
    Promocao[] buscarCuponsAtivos();
    boolean atualizar(Promocao promocao);
    boolean remover(int id);
    int getQuantidade();
    Promocao[] listarTodos();
}