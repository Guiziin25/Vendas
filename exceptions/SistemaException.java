package exceptions;

// Exceção base para o sistema
public class SistemaException extends Exception {
    public SistemaException(String mensagem) {
        super(mensagem);
    }
}
