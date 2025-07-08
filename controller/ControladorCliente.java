package controller;

import model.Cliente;
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

    // Cadastra um novo cliente
    public void cadastrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        repCliente.adicionar(cliente);
    }

    // Autentica cliente por ID e senha
    public boolean autenticarCliente(int id, String senha) {
        if (senha == null || senha.isEmpty()) {
            return false;
        }

        Cliente cliente = repCliente.buscarPorId(id);
        return cliente != null && cliente.getSenha().equals(senha);
    }

    // Busca cliente por ID e retorna como String
    public String buscarCliente(int id) {
        Cliente cliente = repCliente.buscarPorId(id);
        if (cliente == null) {
            return "Cliente não encontrado";
        }
        return cliente.toString(); // Usa o toString() da classe Cliente
    }

    //Buscar Cliente por email
    public Cliente buscarClientePorEmail(String email) {
        Cliente[] clientes = repCliente.listarTodos();
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return cliente;
            }
        }
        return null; // Retorna null se não encontrar
    }

    //Buscar Cliente por token
    public Cliente buscarClientePorToken(String token) {
        Cliente[] clientes = repCliente.listarTodos();
        for (Cliente cliente : clientes) {
            if (token.equals(cliente.getTokenRecuperacao())) {
                return cliente;
            }
        }
        return null; // Retorna null se não encontrar
    }

    //Método para solicitar recuperação de senha
    private String gerarTokenRecuperacao() {
        return java.util.UUID.randomUUID().toString();
    }

    public boolean solicitarRecuperacaoSenha(String email) {
        Cliente cliente = buscarClientePorEmail(email); // pode ser Cliente ou Funcionario
        if (cliente == null) {
            System.out.println("E-mail não encontrado!");
            return false;
        }
        String token = gerarTokenRecuperacao(); // gere um token seguro
        cliente.setTokenRecuperacao(token);
        enviarEmailRecuperacao(email, token); // envie o e-mail com o link
        return true;
    }

    // Exemplo de método para redefinir senha
    public boolean redefinirSenha(String token, String novaSenha) {
        Cliente cliente = buscarClientePorToken(token);
        if (cliente == null) {
            System.out.println("Token inválido ou expirado!");
            return false;
        }
        cliente.setSenha(novaSenha);
        cliente.setTokenRecuperacao(null); // invalida o token
        return true;
    }

    private void enviarEmailRecuperacao(String email, String token) {
        // Aqui você implementaria a lógica para enviar o e-mail com o link de recuperação
        System.out.println("E-mail enviado para " + email + " com o token: " + token);
    }

    // Método adicional útil
    public Cliente obterCliente(int id) {
        return repCliente.buscarPorId(id);
    }
}