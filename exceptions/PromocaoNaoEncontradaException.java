package exceptions;

public class PromocaoNaoEncontradaException extends PromocaoException {
    // Construtor existente que recebe ID para procurar os produtos
    public PromocaoNaoEncontradaException(int id) {
        super("Promoção não encontrada para o produto ID " + id);
    }

    // Novo construtor que recebe mensagem completa
    public PromocaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}