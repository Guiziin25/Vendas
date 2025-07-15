package exceptions;

public class FaturaNaoEncontradaException extends FaturaException {
    public FaturaNaoEncontradaException(int id) {
        super("Fatura com ID " + id + " não encontrada.");
    }
}
