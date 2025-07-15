package exceptions;

public class ProdutoNaoEncontradoException extends ProdutoException {
    public ProdutoNaoEncontradoException(int id) {
        super("Produto com ID " + id + " não encontrado.");
    }
}
