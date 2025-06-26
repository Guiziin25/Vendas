package repository.Interfaces;

import model.Funcionario;
import model.FuncionarioAssalariado;
import model.FuncionarioComissionado;
import model.FuncionarioHorista;

public interface IRepFuncionario {
    boolean adicionar(Funcionario funcionario);  // Alterado para retornar boolean
    Funcionario buscarPorId(int id);
    Funcionario[] listarTodos();
    boolean atualizar(Funcionario funcionario);
    boolean remover(int id);
    Funcionario[] buscarPorNome(String nome);
    Funcionario[] buscarPorDepartamento(String departamento);
    FuncionarioAssalariado[] listarAssalariados();
    FuncionarioHorista[] listarHoristas();
    FuncionarioComissionado[] listarComissionados();
    int getQuantidade();
}