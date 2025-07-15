package exceptions;

public class FuncionarioNaoEncontradoException extends FuncionarioException {
    public FuncionarioNaoEncontradoException(int id) {
        super("Funcionário com ID " + id + " não encontrado.");
    }
}
