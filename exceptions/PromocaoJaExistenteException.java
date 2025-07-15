package exceptions;

public class PromocaoJaExistenteException extends PromocaoException {
    public PromocaoJaExistenteException(String codigo) {
        super("Já existe uma promoção/cupom com o código '" + codigo + "'.");
    }
}
