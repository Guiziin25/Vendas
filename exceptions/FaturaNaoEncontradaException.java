package exceptions;

public class FaturaNaoEncontradaException extends FaturaException {
    // Construtor para ID não encontrado (int)
    public FaturaNaoEncontradaException(int id) {
        super("Fatura com ID " + id + " não encontrada.");
    }

    // Construtor para mensagem personalizada (String)
    public FaturaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}