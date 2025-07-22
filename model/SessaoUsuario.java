package model;

public class SessaoUsuario {
    private static SessaoUsuario instancia;
    private int idCliente;

    // Construtor privado para evitar instâncias externas
    private SessaoUsuario() {}

    // Método para obter a instância única
    public static synchronized SessaoUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SessaoUsuario();
        }
        return instancia;
    }

    // Método para definir o cliente logado
    public void login(int idCliente) {
        this.idCliente = idCliente;
    }

    // Método para obter o ID do cliente logado
    public int getIdClienteLogado() {
        return idCliente;
    }

    // Método para encerrar a sessão
    public void logout() {
        this.idCliente = 0; // Reseta o ID do cliente
    }
}