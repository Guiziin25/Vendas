package exceptions;

public class LoginInvalidoException extends AutenticacaoException {
    // Construtor padrão
    public LoginInvalidoException() {
        super("Login ou senha inválidos.");
    }

    // Construtor com mensagem personalizada
    public LoginInvalidoException(String mensagem) {
        super(mensagem);
    }
}