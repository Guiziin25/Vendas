package exceptions;

public class CategoriaNaoEncontradaException extends ProdutoException {
    public CategoriaNaoEncontradaException(String categoria) {
        super("Categoria '" + categoria + "' não encontrada.");
    }
}
