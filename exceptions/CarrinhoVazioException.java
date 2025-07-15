package exceptions;

public class CarrinhoVazioException extends VendaException {
    public CarrinhoVazioException() {
        super("Não é possível finalizar a venda com o carrinho vazio.");
    }
}
