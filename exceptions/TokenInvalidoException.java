package exceptions;

public class TokenInvalidoException extends AutenticacaoException {
    public TokenInvalidoException() {
        super("Token de recuperação inválido ou expirado.");
    }
}
