package controller;

import model.Cliente;
import repository.Interfaces.IRepCliente;
import repository.RepCliente;
import exceptions.ClienteException;
import exceptions.ClienteJaCadastradoException;
import exceptions.ClienteNaoEncontradoException;
import exceptions.CampoObrigatorioException;

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

    public void cadastrarCliente(Cliente cliente) throws ClienteException, CampoObrigatorioException {
        if (cliente == null) {
            throw new CampoObrigatorioException("Cliente");
        }

        validarCamposObrigatorios(cliente);

        if (buscarClientePorEmail(cliente.getEmail()) != null) {
            throw new ClienteJaCadastradoException(cliente.getEmail());
        }

        repCliente.adicionar(cliente);
    }

    public boolean autenticarCliente(int id, String senha) throws ClienteException, CampoObrigatorioException {
        if (senha == null || senha.isEmpty()) {
            throw new CampoObrigatorioException("Senha");
        }

        Cliente cliente = buscarCliente(id);
        return cliente.getSenha().equals(senha);
    }

    public Cliente buscarCliente(int id) throws ClienteNaoEncontradoException {
        Cliente cliente = repCliente.buscarPorId(id);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException(id);
        }
        return cliente;
    }

    public String buscarClienteString(int id) throws ClienteNaoEncontradoException {
        Cliente cliente = buscarCliente(id);
        return cliente.toString();
    }

    public Cliente buscarClientePorEmail(String email) {
        Cliente[] clientes = repCliente.listarTodos();
        for (Cliente cliente : clientes) {
            if (cliente != null && email.equalsIgnoreCase(cliente.getEmail())) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente buscarClientePorToken(String token) {
        Cliente[] clientes = repCliente.listarTodos();
        for (Cliente cliente : clientes) {
            if (cliente != null && token.equals(cliente.getTokenRecuperacao())) {
                return cliente;
            }
        }
        return null;
    }

    private String gerarTokenRecuperacao() {
        return java.util.UUID.randomUUID().toString();
    }

    public boolean solicitarRecuperacaoSenha(String email)
            throws ClienteException, CampoObrigatorioException {

        // Validação mais robusta
        if (email == null) {
            throw new CampoObrigatorioException("Email não pode ser nulo");
        }
        if (email.trim().isEmpty()) {
            throw new CampoObrigatorioException("Email não pode estar vazio");
        }

        Cliente cliente = buscarClientePorEmail(email);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente com email " + email + " não encontrado");
        }

        String token = gerarTokenRecuperacao();
        cliente.setTokenRecuperacao(token);
        enviarEmailRecuperacao(email, token);
        return true;
    }

    public boolean redefinirSenha(String token, String novaSenha)
            throws ClienteException, CampoObrigatorioException {

        // Validações mais robustas
        if (token == null) {
            throw new CampoObrigatorioException("Token não pode ser nulo");
        }
        if (token.isEmpty()) {
            throw new CampoObrigatorioException("Token não pode estar vazio");
        }
        if (novaSenha == null) {
            throw new CampoObrigatorioException("Nova senha não pode ser nula");
        }
        if (novaSenha.isEmpty()) {
            throw new CampoObrigatorioException("Nova senha não pode estar vazia");
        }
        if (novaSenha.length() < 8) {
            throw new ClienteException("A senha deve ter pelo menos 8 caracteres");
        }

        Cliente cliente = buscarClientePorToken(token);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Token inválido ou expirado");
        }

        cliente.setSenha(novaSenha);
        cliente.setTokenRecuperacao(null);
        return true;
    }

    private void enviarEmailRecuperacao(String email, String token) {
        System.out.println("E-mail enviado para " + email + " com o token: " + token);
    }

    private void validarCamposObrigatorios(Cliente cliente) throws CampoObrigatorioException {
        if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
            throw new CampoObrigatorioException("Nome");
        }
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new CampoObrigatorioException("Email");
        }
        if (cliente.getSenha() == null || cliente.getSenha().isEmpty()) {
            throw new CampoObrigatorioException("Senha");
        }
    }
}