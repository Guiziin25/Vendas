package controller;

import model.Funcionario;
import model.FuncionarioAssalariado;
import model.FuncionarioComissionado;
import model.FuncionarioHorista;
import repository.Interfaces.IRepFuncionario;
import repository.RepFuncionario;
import exceptions.FuncionarioException;
import exceptions.FuncionarioNaoEncontradoException;
import exceptions.LimiteFuncionariosAtingidoException;
import exceptions.CampoObrigatorioException;

public class ControladorFuncionario {
    private static ControladorFuncionario instancia;
    private IRepFuncionario repFuncionario;

    private ControladorFuncionario() {
        this.repFuncionario = RepFuncionario.getInstancia();
    }

    public static synchronized ControladorFuncionario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorFuncionario();
        }
        return instancia;
    }

    public boolean contratarFuncionario(Funcionario funcionario)
            throws FuncionarioException, LimiteFuncionariosAtingidoException, CampoObrigatorioException {

        if (funcionario == null) {
            throw new CampoObrigatorioException("Funcionário");
        }

        validarCamposObrigatorios(funcionario);

        if (repFuncionario.getQuantidade() >= 100) {
            throw new LimiteFuncionariosAtingidoException();
        }

        if (!repFuncionario.adicionar(funcionario)) {
            throw new FuncionarioException("Falha ao contratar funcionário");
        }

        return true;
    }

    public Funcionario buscarFuncionario(int id) throws FuncionarioNaoEncontradoException {
        Funcionario funcionario = repFuncionario.buscarPorId(id);
        if (funcionario == null) {
            throw new FuncionarioNaoEncontradoException(id);
        }
        return funcionario;
    }

    public void listarTodosFuncionarios() {
        Funcionario[] funcionarios = repFuncionario.listarTodos();
        if (funcionarios.length == 0) {
            System.out.println("Nenhum funcionário cadastrado");
            return;
        }

        System.out.println("\n=== LISTA DE FUNCIONÁRIOS ===");
        for (Funcionario func : funcionarios) {
            System.out.println(func);
        }
    }

    public boolean atualizarFuncionario(Funcionario funcionario)
            throws FuncionarioException, CampoObrigatorioException {

        if (funcionario == null) {
            throw new CampoObrigatorioException("Funcionário");
        }

        validarCamposObrigatorios(funcionario);

        if (!repFuncionario.atualizar(funcionario)) {
            throw new FuncionarioNaoEncontradoException(funcionario.getId());
        }
        return true;
    }


    public boolean demitirFuncionario(int id) throws FuncionarioNaoEncontradoException {
        if (!repFuncionario.remover(id)) {
            throw new FuncionarioNaoEncontradoException(id);
        }
        return true;
    }

    public void listarPorDepartamento(String departamento) {
        Funcionario[] funcionarios = repFuncionario.buscarPorDepartamento(departamento);
        if (funcionarios.length == 0) {
            System.out.println("Nenhum funcionário no departamento " + departamento);
            return;
        }

        System.out.println("\n=== FUNCIONÁRIOS DO DEPARTAMENTO " + departamento.toUpperCase() + " ===");
        for (Funcionario func : funcionarios) {
            System.out.println(func);
        }
    }

    public double calcularSalarioFuncionario(int idFuncionario) throws FuncionarioNaoEncontradoException {
        Funcionario funcionario = buscarFuncionario(idFuncionario);
        return funcionario.calcularRemuneracao();
    }

    private void validarCamposObrigatorios(Funcionario funcionario) throws CampoObrigatorioException {
        if (funcionario.getNome() == null || funcionario.getNome().isEmpty()) {
            throw new CampoObrigatorioException("Nome");
        }
        if (funcionario.getCpf() == null || funcionario.getCpf().isEmpty()) {
            throw new CampoObrigatorioException("CPF");
        }
        if (funcionario.getLogin() == null || funcionario.getLogin().isEmpty()) {
            throw new CampoObrigatorioException("Login");
        }
        if (funcionario.getSenha() == null || funcionario.getSenha().isEmpty()) {
            throw new CampoObrigatorioException("Senha");
        }
    }
}