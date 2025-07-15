package exceptions;

public class FaturaVencidaException extends FaturaException {
    public FaturaVencidaException(int id) {
        super("Fatura com ID " + id + " está vencida.");
    }
}
