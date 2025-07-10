package controller;

import model.Funcionario;
import repository.Interfaces.IRepFuncionario;
import repository.RepFuncionario;

public class ControladorAcesso {

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

    // Método para verificar login e senha
    public boolean autenticar(String login, String senha) {
        // Primeiro verifica se não são nulos
        if (login == null || senha == null) {
            return false;
        }

        // Pega todos os funcionários do repositório
        Funcionario[] todosFuncionarios = repositorioFuncionario.listarTodos();

        // Vai um por um pra ver se acha o login e senha
        for (int i = 0; i < todosFuncionarios.length; i++) {
            Funcionario func = todosFuncionarios[i];

            // Verifica se o login e senha batem
            if (func.getLogin() != null && func.getLogin().equals(login) &&
                    func.getSenha() != null && func.getSenha().equals(senha)) {
                return true; // Achou!
            }
        }

        // Se chegou aqui, não encontrou
        return false;
    }

        public boolean isAdmin(Funcionario funcionario) {
            for (String permissao : funcionario.getPermissoes()) {
                if ("ADMIN".equals(permissao)) {
                    return true;
                }
            }
            return false;
        }

}