package exceptions;

public class EstoqueInsuficienteException extends ProdutoException {
    public EstoqueInsuficienteException(int id, int quantidade) {
        super("Estoque insuficiente para o produto ID " + id + ". Quantidade disponível: " + quantidade);
    }
}
