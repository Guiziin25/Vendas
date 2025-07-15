package exceptions;

public class ClienteJaCadastradoException extends ClienteException {
    public ClienteJaCadastradoException(String email) {
        super("Cliente com e-mail " + email + " já está cadastrado.");
    }
}
