package exceptions;

public class PagamentoInvalidoException extends PagamentoException {
    // Construtor para valor inválido (double)
    public PagamentoInvalidoException(double valor) {
        super("Valor de pagamento inválido: " + valor);
    }

    // Construtor para mensagem personalizada (String)
    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}