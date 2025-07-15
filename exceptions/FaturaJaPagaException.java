package exceptions;

public class FaturaJaPagaException extends FaturaException {
    public FaturaJaPagaException(int id) {
        super("Fatura com ID " + id + " já está paga ou cancelada.");
    }
}
