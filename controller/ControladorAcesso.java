package controller;

import model.Funcionario;
import repository.Interfaces.IRepFuncionario;
import repository.RepFuncionario;
import exceptions.AutenticacaoException;
import exceptions.LoginInvalidoException;
import exceptions.AcessoNegadoException;

public class ControladorAcesso {
    private static ControladorAcesso instanciaUnica;
    private IRepFuncionario repositorioFuncionario;

    private ControladorAcesso() {
        this.repositorioFuncionario = RepFuncionario.getInstancia();
    }

    public static ControladorAcesso getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorAcesso();
        }
        return instanciaUnica;
    }

    public boolean autenticar(String login, String senha) throws AutenticacaoException {
        if (login == null || login.isEmpty() || senha == null || senha.isEmpty()) {
            throw new LoginInvalidoException("Login e senha são obrigatórios");
        }

        Funcionario[] todosFuncionarios = repositorioFuncionario.listarTodos();

        for (Funcionario func : todosFuncionarios) {
            if (func != null && login.equals(func.getLogin()) && senha.equals(func.getSenha())) {
                return true;
            }
        }

        throw new LoginInvalidoException("Credenciais inválidas");
    }

    public boolean isAdmin(Funcionario funcionario) throws AcessoNegadoException {
        // Verificação mais completa do parâmetro
        if (funcionario == null) {
            throw new AcessoNegadoException("O objeto Funcionário não pode ser nulo");
        }

        // Verificação mais completa das permissões
        String[] permissoes = funcionario.getPermissoes();
        if (permissoes == null || permissoes.length == 0) {
            throw new AcessoNegadoException("O funcionário não possui nenhuma permissão definida");
        }

        // Busca case-insensitive pela permissão ADMIN
        for (String permissao : permissoes) {
            if (permissao != null && "ADMIN".equalsIgnoreCase(permissao.trim())) {
                return true;
            }
        }

        throw new AcessoNegadoException("Acesso restrito - requer permissão de ADMIN");
    }
}