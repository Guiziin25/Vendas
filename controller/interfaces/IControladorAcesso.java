package controller.interfaces;

import exceptions.AcessoNegadoException;
import exceptions.LoginInvalidoException;
import exceptions.SistemaException;
import model.Funcionario;

public interface IControladorAcesso {
    Funcionario autenticar(String login, String senha) throws LoginInvalidoException, SistemaException;
    boolean isAdmin(Funcionario funcionario) throws AcessoNegadoException, SistemaException;
    boolean temPermissao(Funcionario funcionario, String permissaoDesejada) throws AcessoNegadoException, SistemaException;
}