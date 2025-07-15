package exceptions;

public class MetodoPagamentoNaoSuportadoException extends PagamentoException {
    public MetodoPagamentoNaoSuportadoException(String metodo) {
        super("Método de pagamento '" + metodo + "' não é suportado.");
    }
}
