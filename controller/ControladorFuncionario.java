package controller;

import exceptions.*;
import model.Funcionario;
import model.FuncionarioAssalariado;
import model.FuncionarioComissionado;
import model.FuncionarioHorista;
import repository.Interfaces.IRepFuncionario;
import repository.RepFuncionario;
import controller.interfaces.IControladorFuncionario;
import repository.Interfaces.IRepCliente;
import repository.RepCliente;
import model.Cliente;

public class ControladorFuncionario implements IControladorFuncionario {
    private static ControladorFuncionario instancia;
    private IRepFuncionario repFuncionario;
    private IRepCliente repCliente;

    // Construtor privado para Singleton
    private ControladorFuncionario() {
        this.repFuncionario = RepFuncionario.getInstancia();
        this.repCliente = RepCliente.getInstancia();
    }

    // Método Singleton para obter a instância
    public static synchronized ControladorFuncionario getInstancia() {
        if (instancia == null) {
            instancia = new ControladorFuncionario();
        }
        return instancia;
    }

    /**
     * Contrata um novo funcionário
     * @param funcionario Funcionário a ser contratado
     * @throws FuncionarioException Se os dados do funcionário forem inválidos
     * @throws LimiteFuncionariosAtingidoException Se o limite de funcionários for atingido
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void contratarFuncionario(Funcionario funcionario)
            throws FuncionarioException, SistemaException {
        try {
            if (funcionario == null) {
                throw new FuncionarioException("Funcionário não pode ser nulo");
            }

            // Valida campos obrigatórios
            if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
                throw new FuncionarioException("Nome do funcionário é obrigatório");
            }

            if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
                throw new FuncionarioException("CPF do funcionário é obrigatório");
            }

            if (!repFuncionario.adicionar(funcionario)) {
                throw new LimiteFuncionariosAtingidoException();
            }
        } catch (FuncionarioException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao contratar funcionário: " + e.getMessage());
        }
    }

    /**
     * Busca um funcionário por ID
     * @param id ID do funcionário
     * @return Funcionário encontrado
     * @throws FuncionarioNaoEncontradoException Se o funcionário não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Funcionario buscarFuncionario(int id)
            throws FuncionarioNaoEncontradoException, SistemaException {
        try {
            Funcionario funcionario = repFuncionario.buscarPorId(id);
            if (funcionario == null) {
                throw new FuncionarioNaoEncontradoException(id);
            }
            return funcionario;
        } catch (FuncionarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar funcionário: " + e.getMessage());
        }
    }

    /**
     * Lista todos os funcionários
     * @return Array de funcionários
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Funcionario[] listarTodosFuncionarios() throws SistemaException {
        try {
            return repFuncionario.listarTodos();
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar funcionários: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um funcionário
     * @param funcionario Funcionário com dados atualizados
     * @throws FuncionarioException Se o funcionário não for encontrado ou dados forem inválidos
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void atualizarFuncionario(Funcionario funcionario)
            throws FuncionarioException, SistemaException {
        try {
            if (funcionario == null) {
                throw new FuncionarioException("Funcionário não pode ser nulo");
            }

            if (!repFuncionario.atualizar(funcionario)) {
                throw new FuncionarioNaoEncontradoException(funcionario.getId());
            }
        } catch (FuncionarioException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }
    /**
     * Demite um funcionário
     * @param id ID do funcionário a ser demitido
     * @throws FuncionarioNaoEncontradoException Se o funcionário não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void demitirFuncionario(int id)
            throws FuncionarioNaoEncontradoException, SistemaException {
        try {
            if (!repFuncionario.remover(id)) {
                throw new FuncionarioNaoEncontradoException(id);
            }
        } catch (FuncionarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao demitir funcionário: " + e.getMessage());
        }
    }

    /**
     * Lista funcionários por departamento
     * @param departamento Nome do departamento
     * @return Array de funcionários do departamento
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Funcionario[] listarPorDepartamento(String departamento)
            throws SistemaException {
        try {
            if (departamento == null || departamento.trim().isEmpty()) {
                throw new SistemaException("Departamento não pode ser vazio");
            }
            return repFuncionario.buscarPorDepartamento(departamento);
        } catch (Exception e) {
            throw new SistemaException("Erro ao listar funcionários por departamento: " + e.getMessage());
        }
    }

    /**
     * Calcula o salário de um funcionário
     * @param idFuncionario ID do funcionário
     * @return Valor do salário calculado
     * @throws FuncionarioNaoEncontradoException Se o funcionário não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public double calcularSalarioFuncionario(int idFuncionario)
            throws FuncionarioNaoEncontradoException, SistemaException {
        try {
            Funcionario funcionario = buscarFuncionario(idFuncionario);

            if (funcionario instanceof FuncionarioAssalariado) {
                FuncionarioAssalariado assalariado = (FuncionarioAssalariado) funcionario;
                return assalariado.getSalarioBase() + assalariado.getBeneficios();
            } else if (funcionario instanceof FuncionarioHorista) {
                FuncionarioHorista horista = (FuncionarioHorista) funcionario;
                double salario = horista.getValorHora() * horista.getHorasTrabalhadas();
                return horista.isRecebeAdicionalNoturno() ? salario * 1.2 : salario;
            } else if (funcionario instanceof FuncionarioComissionado) {
                FuncionarioComissionado comissionado = (FuncionarioComissionado) funcionario;
                return comissionado.getVendasMes() * comissionado.getTaxaComissao();
            }

            throw new SistemaException("Tipo de funcionário não reconhecido");
        } catch (FuncionarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao calcular salário: " + e.getMessage());
        }
    }

    /**
     * Busca funcionário por e-mail
     * @param email E-mail do funcionário
     * @return Funcionário encontrado
     * @throws FuncionarioNaoEncontradoException Se o funcionário não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Funcionario buscarFuncionarioPorEmail(String email)
            throws FuncionarioNaoEncontradoException, SistemaException {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new SistemaException("E-mail não pode ser vazio");
            }

            Funcionario[] funcionarios = repFuncionario.listarTodos();
            for (Funcionario funcionario : funcionarios) {
                if (email.equalsIgnoreCase(funcionario.getEmail())) {
                    return funcionario;
                }
            }
            throw new FuncionarioNaoEncontradoException("e-mail", email);
            // Ou alternativamente:
            // throw new FuncionarioNaoEncontradoException("Funcionário com e-mail " + email + " não encontrado");
        } catch (FuncionarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar funcionário por e-mail: " + e.getMessage());
        }
    }
    /**
     * Busca funcionário por token de recuperação
     * @param token Token de recuperação
     * @return Funcionário encontrado
     * @throws TokenInvalidoException Se o token for inválido
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Funcionario buscarFuncionarioPorToken(String token)
            throws TokenInvalidoException, SistemaException {
        try {
            if (token == null || token.trim().isEmpty()) {
                throw new TokenInvalidoException();
            }

            Funcionario[] funcionarios = repFuncionario.listarTodos();
            for (Funcionario funcionario : funcionarios) {
                if (token.equals(funcionario.getTokenRecuperacao())) {
                    return funcionario;
                }
            }
            throw new TokenInvalidoException();
        } catch (TokenInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar funcionário por token: " + e.getMessage());
        }
    }

    /**
     * Solicita recuperação de senha
     * @param email E-mail do funcionário
     * @throws FuncionarioNaoEncontradoException Se o funcionário não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void solicitarRecuperacaoSenha(String email)
            throws FuncionarioNaoEncontradoException, SistemaException {
        try {
            Funcionario funcionario = buscarFuncionarioPorEmail(email);
            String token = gerarTokenRecuperacao();
            funcionario.setTokenRecuperacao(token);
            enviarEmailRecuperacao(email, token);
        } catch (FuncionarioNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao solicitar recuperação de senha: " + e.getMessage());
        }
    }

    /**
     * Redefine a senha do funcionário
     * @param token Token de recuperação
     * @param novaSenha Nova senha
     * @throws TokenInvalidoException Se o token for inválido
     * @throws DadosInvalidosException Se a nova senha for inválida
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void redefinirSenha(String token, String novaSenha)
            throws TokenInvalidoException, DadosInvalidosException, SistemaException {
        try {
            if (novaSenha == null || novaSenha.trim().isEmpty()) {
                throw new DadosInvalidosException("Nova senha não pode ser vazia");
            }

            Funcionario funcionario = buscarFuncionarioPorToken(token);
            funcionario.setSenha(novaSenha);
            funcionario.setTokenRecuperacao(null); // Invalida o token
            atualizarFuncionario(funcionario);
        } catch (TokenInvalidoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao redefinir senha: " + e.getMessage());
        }
    }

    public String gerarTokenRecuperacao() {
        return java.util.UUID.randomUUID().toString();
    }

    public void enviarEmailRecuperacao(String email, String token) {
        // Implementação real enviaria um e-mail de fato
        System.out.println("E-mail de recuperação enviado para: " + email);
        System.out.println("Token: " + token);
    }

    public Funcionario autenticarFuncionario(int id, String senha)
            throws FuncionarioNaoEncontradoException, DadosInvalidosException, SistemaException {
        Funcionario funcionario = buscarFuncionario(id);
        if (funcionario == null) {
            throw new FuncionarioNaoEncontradoException(id);
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new DadosInvalidosException("Senha não pode ser vazia");
        }
        if (!senha.equals(funcionario.getSenha())) {
            throw new DadosInvalidosException("Senha incorreta");
        }
        return funcionario;
    }

    public boolean removerCliente(int id) throws SistemaException {
        try {
            return repCliente.remover(id);
        } catch (Exception e) {
            throw new SistemaException("Erro ao remover cliente: " + e.getMessage());
        }
    }

    public boolean atualizarCliente(Cliente cliente) throws SistemaException {
        try {
            return repCliente.atualizar(cliente);
        } catch (Exception e) {
            throw new SistemaException("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
}