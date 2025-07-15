package exceptions;

public class LimiteFuncionariosAtingidoException extends FuncionarioException {
    public LimiteFuncionariosAtingidoException() {
        super("Limite máximo de funcionários atingido.");
    }
}
