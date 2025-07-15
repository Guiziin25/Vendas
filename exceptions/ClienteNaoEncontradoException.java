package exceptions;

public class ClienteNaoEncontradoException extends ClienteException {
    // Construtor com ID
    public ClienteNaoEncontradoException(int id) {
        super("Cliente com ID " + id + " não encontrado.");
    }

    // Novo construtor com mensagem personalizada
    public ClienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}