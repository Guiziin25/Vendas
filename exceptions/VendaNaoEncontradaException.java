package exceptions;

public class VendaNaoEncontradaException extends VendaException {
    public VendaNaoEncontradaException(int id) {
        super("Venda com ID " + id + " não encontrada.");
    }
}
