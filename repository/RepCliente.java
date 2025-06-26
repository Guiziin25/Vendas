package repository;

import model.Cliente;
import repository.Interfaces.IRepCliente;

public class RepCliente implements IRepCliente {
    private static RepCliente instancia;
    private Cliente[] clientes;
    private int tamanhoAtual;
    private static final int TAMANHO_MAX = 100; // Capacidade máxima

    // Construtor privado (Singleton)
    private RepCliente() {
        clientes = new Cliente[TAMANHO_MAX];
        tamanhoAtual = 0;
    }

    // Método Singleton (thread-safe)
    public static synchronized RepCliente getInstancia() {
        if (instancia == null) {
            instancia = new RepCliente();
        }
        return instancia;
    }

    @Override
    public void adicionar(Cliente cliente) {
        if (tamanhoAtual < TAMANHO_MAX) {
            clientes[tamanhoAtual] = cliente;
            tamanhoAtual++;
        } else {
            System.out.println("ERRO: Limite máximo de clientes atingido!");
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (clientes[i].getId() == id) {
                return clientes[i];
            }
        }
        return null;
    }

    @Override
    public Cliente[] listarTodos() {
        Cliente[] clientesAtivos = new Cliente[tamanhoAtual];
        System.arraycopy(clientes, 0, clientesAtivos, 0, tamanhoAtual);
        return clientesAtivos;
    }

    @Override
    public boolean atualizar(Cliente cliente) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (clientes[i].getId() == cliente.getId()) {
                clientes[i] = cliente;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remover(int id) {
        for (int i = 0; i < tamanhoAtual; i++) {
            if (clientes[i].getId() == id) {
                // Substitui pelo último elemento e decrementa o tamanho
                clientes[i] = clientes[tamanhoAtual - 1];
                clientes[tamanhoAtual - 1] = null;
                tamanhoAtual--;
                return true;
            }
        }
        return false;
    }

    @Override
    public Cliente[] buscarPorNome(String nome) {
        int count = 0;
        // Conta quantos clientes têm o nome buscado
        for (int i = 0; i < tamanhoAtual; i++) {
            if (clientes[i].getNome().equalsIgnoreCase(nome)) {
                count++;
            }
        }

        if (count == 0) {
            return new Cliente[0]; // Retorna array vazio se não encontrar
        }

        Cliente[] resultados = new Cliente[count];
        int index = 0;
        for (int i = 0; i < tamanhoAtual; i++) {
            if (clientes[i].getNome().equalsIgnoreCase(nome)) {
                resultados[index++] = clientes[i];
            }
        }
        return resultados;
    }
}