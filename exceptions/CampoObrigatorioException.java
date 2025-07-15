package exceptions;

public class CampoObrigatorioException extends ValidacaoException {
    public CampoObrigatorioException(String campo) {
        super("O campo '" + campo + "' é obrigatório.");
    }
}
