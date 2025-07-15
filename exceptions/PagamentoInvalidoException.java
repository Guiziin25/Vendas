package exceptions;

public class PagamentoInvalidoException extends PagamentoException {
    public PagamentoInvalidoException(double valor) {
        super("Valor de pagamento inválido: " + valor);
    }
}
