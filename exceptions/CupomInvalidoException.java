package exceptions;

public class CupomInvalidoException extends PromocaoException {
    public CupomInvalidoException(String codigo) {
        super("Cupom '" + codigo + "' é inválido ou expirado.");
    }
}
