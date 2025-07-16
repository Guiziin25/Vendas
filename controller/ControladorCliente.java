package controller;

import exceptions.ClienteJaCadastradoException;
import exceptions.ClienteNaoEncontradoException;
import exceptions.DadosInvalidosException;
import exceptions.SistemaException;
import exceptions.TokenInvalidoException;
import model.Cliente;
import repository.Interfaces.IRepCliente;
import repository.RepCliente;

public class ControladorCliente {
    private static ControladorCliente instancia;
    private IRepCliente repCliente;

    private ControladorCliente() {
        this.repCliente = RepCliente.getInstancia();
    }

    public static synchronized ControladorCliente getInstancia() {
        if (instancia == null) {
            instancia = new ControladorCliente();
        }
        return instancia;
    }

    /**
     * Cadastra um novo cliente
     * @param cliente Cliente a ser cadastrado
     * @throws ClienteJaCadastradoException Se o cliente já estiver cadastrado
     * @throws DadosInvalidosException Se os dados do cliente forem inválidos
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public void cadastrarCliente(Cliente cliente)
            throws ClienteJaCadastradoException, DadosInvalidosException, SistemaException {
        try {
            if (cliente == null) {
                throw new DadosInvalidosException("Cliente não pode ser nulo");
            }

            // Validações adicionais
            if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
                throw new DadosInvalidosException("Nome é obrigatório");
            }

            if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
                throw new DadosInvalidosException("E-mail é obrigatório");
            }

            if (!cliente.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new DadosInvalidosException("E-mail inválido");
            }

            if (cliente.getSenha() == null || cliente.getSenha().trim().isEmpty()) {
                throw new DadosInvalidosException("Senha é obrigatória");
            }

            // Verifica se cliente já existe
            try {
                Cliente existente = buscarClientePorEmail(cliente.getEmail());
                if (existente != null) {
                    throw new ClienteJaCadastradoException(cliente.getEmail());
                }
            } catch (ClienteNaoEncontradoException e) {
                // Cliente não existe, pode cadastrar
            }

            repCliente.adicionar(cliente);
        } catch (ClienteJaCadastradoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }
    /**
     * Autentica um cliente por ID e senha
     * @param id ID do cliente
     * @param senha Senha do cliente
     * @return Cliente autenticado
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws DadosInvalidosException Se a senha for inválida ou vazia
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Cliente autenticarCliente(int id, String senha)
            throws ClienteNaoEncontradoException, DadosInvalidosException, SistemaException {
        try {
            if (senha == null || senha.trim().isEmpty()) {
                throw new DadosInvalidosException("Senha não pode ser vazia");
            }

            Cliente cliente = repCliente.buscarPorId(id);
            if (cliente == null) {
                throw new ClienteNaoEncontradoException(id);
            }

            if (!cliente.getSenha().equals(senha)) {
                throw new DadosInvalidosException("Senha incorreta");
            }

            return cliente;
        } catch (ClienteNaoEncontradoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao autenticar cliente: " + e.getMessage());
        }
    }
    /**
     * Busca um cliente por ID
     * @param id ID do cliente
     * @return Cliente encontrado
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Cliente buscarCliente(int id) throws ClienteNaoEncontradoException, SistemaException {
        try {
            Cliente cliente = repCliente.buscarPorId(id);
            if (cliente == null) {
                throw new ClienteNaoEncontradoException(id);
            }
            return cliente;
        } catch (ClienteNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca cliente por e-mail
     * @param email E-mail do cliente
     * @return Cliente encontrado
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws DadosInvalidosException Se o e-mail for inválido
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Cliente buscarClientePorEmail(String email)
            throws ClienteNaoEncontradoException, DadosInvalidosException, SistemaException {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new DadosInvalidosException("E-mail não pode ser vazio");
            }

            Cliente[] clientes = repCliente.listarTodos();
            for (Cliente cliente : clientes) {
                if (email.equalsIgnoreCase(cliente.getEmail())) {
                    return cliente;
                }
            }
            throw new ClienteNaoEncontradoException("Cliente com e-mail " + email + " não encontrado");
        } catch (ClienteNaoEncontradoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar cliente por e-mail: " + e.getMessage());
        }
    }

    /**
     * Busca cliente por token de recuperação
     * @param token Token de recuperação
     * @return Cliente encontrado
     * @throws TokenInvalidoException Se o token for inválido
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Cliente buscarClientePorToken(String token) throws TokenInvalidoException, SistemaException {
        try {
            if (token == null || token.trim().isEmpty()) {
                throw new TokenInvalidoException();
            }

            Cliente[] clientes = repCliente.listarTodos();
            for (Cliente cliente : clientes) {
                if (token.equals(cliente.getTokenRecuperacao())) {
                    return cliente;
                }
            }
            throw new TokenInvalidoException();
        } catch (TokenInvalidoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao buscar cliente por token: " + e.getMessage());
        }
    }

    /**
     * Solicita recuperação de senha
     * @param email E-mail do cliente
     * @return true se a solicitação foi bem-sucedida
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean solicitarRecuperacaoSenha(String email)
            throws ClienteNaoEncontradoException, SistemaException {
        try {
            Cliente cliente = buscarClientePorEmail(email);
            String token = gerarTokenRecuperacao();
            cliente.setTokenRecuperacao(token);
            enviarEmailRecuperacao(email, token);
            return true;
        } catch (ClienteNaoEncontradoException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao solicitar recuperação de senha: " + e.getMessage());
        }
    }

    /**
     * Redefine a senha do cliente
     * @param token Token de recuperação
     * @param novaSenha Nova senha
     * @return true se a redefinição foi bem-sucedida
     * @throws TokenInvalidoException Se o token for inválido
     * @throws DadosInvalidosException Se a nova senha for inválida
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public boolean redefinirSenha(String token, String novaSenha)
            throws TokenInvalidoException, DadosInvalidosException, SistemaException {
        try {
            if (novaSenha == null || novaSenha.trim().isEmpty()) {
                throw new DadosInvalidosException("Nova senha não pode ser vazia");
            }

            Cliente cliente = buscarClientePorToken(token);
            cliente.setSenha(novaSenha);
            cliente.setTokenRecuperacao(null); // Invalida o token
            return true;
        } catch (TokenInvalidoException | DadosInvalidosException e) {
            throw e;
        } catch (Exception e) {
            throw new SistemaException("Erro ao redefinir senha: " + e.getMessage());
        }
    }

    private String gerarTokenRecuperacao() {
        return java.util.UUID.randomUUID().toString();
    }

    private void enviarEmailRecuperacao(String email, String token) {
        // Implementação real enviaria um e-mail de fato
        System.out.println("E-mail de recuperação enviado para: " + email);
        System.out.println("Token: " + token);
    }

    /**
     * Obtém um cliente por ID
     * @param id ID do cliente
     * @return Cliente encontrado
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado
     * @throws SistemaException Se ocorrer um erro no sistema
     */
    public Cliente obterCliente(int id) throws ClienteNaoEncontradoException, SistemaException {
        return buscarCliente(id);
    }
}