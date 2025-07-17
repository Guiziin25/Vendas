package controller;

import exceptions.AcessoNegadoException;
import exceptions.LoginInvalidoException;
import exceptions.SistemaException;
import model.Funcionario;
import repository.Interfaces.IRepFuncionario;
import repository.RepFuncionario;
import controller.interfaces.IControladorAcesso;

public class ControladorAcesso implements IControladorAcesso {

    // 1. Parte do Singleton - a gente guarda a única instância aqui
    private static ControladorAcesso instanciaUnica;

    // 2. O repositório que vamos usar (pela interface)
    private IRepFuncionario repositorioFuncionario;

    // 3. Construtor privado - só a própria classe pode criar uma instância
    private ControladorAcesso() {
        // Aqui a gente pega a instância do repositório de funcionários
        this.repositorioFuncionario = RepFuncionario.getInstancia();
    }

    // 4. Método para pegar a instância (Singleton)
    public static ControladorAcesso getInstancia() {
        // Se não existir ainda, cria uma nova
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorAcesso();
        }
        return instanciaUnica;
    }

    /**
     * Método para autenticar um funcionário
     * @param login Login do funcionário
     * @param senha Senha do funcionário
     * @return Funcionário autenticado
     * @throws LoginInvalidoException Se o login ou senha forem inválidos
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Funcionario autenticar(String login, String senha) throws LoginInvalidoException, SistemaException {
        // Primeiro verifica se não são nulos ou vazios
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginInvalidoException("Login e senha são obrigatórios");
        }

        try {
            // Pega todos os funcionários do repositório
            Funcionario[] todosFuncionarios = repositorioFuncionario.listarTodos();

            // Verifica se existem funcionários cadastrados
            if (todosFuncionarios == null || todosFuncionarios.length == 0) {
                throw new LoginInvalidoException("Nenhum funcionário cadastrado no sistema");
            }

            // Vai um por um pra ver se acha o login e senha
            for (Funcionario func : todosFuncionarios) {
                // Verifica se o login e senha batem
                if (func.getLogin() != null && func.getLogin().equals(login) &&
                        func.getSenha() != null && func.getSenha().equals(senha)) {
                    return func; // Retorna o funcionário autenticado
                }
            }

            // Se chegou aqui, não encontrou
            throw new LoginInvalidoException("Login ou senha incorretos");

        } catch (LoginInvalidoException e) {
            throw e; // Re-lança exceções específicas
        } catch (Exception e) {
            throw new SistemaException("Erro ao autenticar: " + e.getMessage());
        }
    }

    /**
     * Verifica se um funcionário tem permissão de administrador
     * @param funcionario Funcionário a ser verificado
     * @return true se for admin, false caso contrário
     * @throws AcessoNegadoException Se o funcionário não tiver permissões
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean isAdmin(Funcionario funcionario) throws AcessoNegadoException, SistemaException {
        if (funcionario == null) {
            throw new SistemaException("Funcionário não pode ser nulo");
        }

        try {
            String[] permissoes = funcionario.getPermissoes();
            if (permissoes == null || permissoes.length == 0) {
                throw new AcessoNegadoException("Funcionário não possui permissões");
            }

            for (String permissao : permissoes) {
                if ("ADMIN".equalsIgnoreCase(permissao)) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            if (e instanceof AcessoNegadoException) {
                throw e; // Re-lança exceções específicas
            }
            throw new SistemaException("Erro ao verificar permissões: " + e.getMessage());
        }
    }

    /**
     * Verifica se um funcionário tem uma permissão específica
     * @param funcionario Funcionário a ser verificado
     * @param permissaoDesejada Permissão que se deseja verificar
     * @return true se tiver a permissão, false caso contrário
     * @throws AcessoNegadoException Se o funcionário não tiver a permissão
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean temPermissao(Funcionario funcionario, String permissaoDesejada)
            throws AcessoNegadoException, SistemaException {
        if (funcionario == null) {
            throw new SistemaException("Funcionário não pode ser nulo");
        }
        if (permissaoDesejada == null || permissaoDesejada.trim().isEmpty()) {
            throw new SistemaException("Permissão desejada não pode ser vazia");
        }

        try {
            String[] permissoes = funcionario.getPermissoes();
            if (permissoes == null || permissoes.length == 0) {
                throw new AcessoNegadoException("Funcionário não possui permissões");
            }

            for (String permissao : permissoes) {
                if (permissaoDesejada.equalsIgnoreCase(permissao)) {
                    return true;
                }
            }

            throw new AcessoNegadoException("Funcionário não possui a permissão: " + permissaoDesejada);

        } catch (Exception e) {
            if (e instanceof AcessoNegadoException) {
                throw e; // Re-lança exceções específicas
            }
            throw new SistemaException("Erro ao verificar permissões: " + e.getMessage());
        }
    }
}