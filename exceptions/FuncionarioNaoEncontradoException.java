package exceptions;

public class FuncionarioNaoEncontradoException extends FuncionarioException {
    public FuncionarioNaoEncontradoException(int id) {
        super("Funcionário com ID " + id + " não encontrado.");
    }

    public FuncionarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    // Opcional: Construtor para busca por e-mail
    public FuncionarioNaoEncontradoException(String campo, String valor) {
        super("Funcionário com " + campo + " '" + valor + "' não encontrado.");
    }
}