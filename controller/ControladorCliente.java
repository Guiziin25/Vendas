package controller;

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

    // Método adicional útil
    public Cliente obterCliente(int id) {
        return repCliente.buscarPorId(id);
    }
}