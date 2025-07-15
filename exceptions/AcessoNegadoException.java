package exceptions;

public class AcessoNegadoException extends AutenticacaoException {
    // Construtor padrão
    public AcessoNegadoException() {
        super("Acesso negado. Permissões insuficientes.");
    }

    // Construtor com mensagem personalizada
    public AcessoNegadoException(String mensagem) {
        super(mensagem);
    }
}