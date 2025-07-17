package controller.interfaces;

import exceptions.*;
import model.Funcionario;

public interface IControladorFuncionario {
    void contratarFuncionario(Funcionario funcionario) throws FuncionarioException, SistemaException;
    Funcionario buscarFuncionario(int id) throws FuncionarioNaoEncontradoException, SistemaException;
    Funcionario[] listarTodosFuncionarios() throws SistemaException;
    void atualizarFuncionario(Funcionario funcionario) throws FuncionarioException, SistemaException;
    void demitirFuncionario(int id) throws FuncionarioNaoEncontradoException, SistemaException;
    Funcionario [] listarPorDepartamento(String departamento) throws SistemaException;
    double calcularSalarioFuncionario(int idFuncionario) throws FuncionarioNaoEncontradoException, SistemaException;
    Funcionario buscarFuncionarioPorEmail(String email) throws FuncionarioNaoEncontradoException, SistemaException;
    Funcionario buscarFuncionarioPorToken(String token) throws TokenInvalidoException, SistemaException;
    void solicitarRecuperacaoSenha(String email) throws FuncionarioNaoEncontradoException, SistemaException;
    void redefinirSenha(String token, String novaSenha) throws TokenInvalidoException, DadosInvalidosException, SistemaException;
    String gerarTokenRecuperacao();
    void enviarEmailRecuperacao(String email, String token);
}
