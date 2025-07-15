package exceptions;

public class DadosInvalidosException extends ValidacaoException {
    public DadosInvalidosException(String campo) {
        super("Dados inválidos para o campo: " + campo);
    }
}
