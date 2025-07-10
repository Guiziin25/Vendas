package controller;

import model.Produto;
import repository.Interfaces.IRepProduto;
import repository.RepProduto;

public class ControladorCarrinho {
    private static ControladorCarrinho instancia;
    private IRepProduto repProduto;
    private Produto[] itens;
    private int quantidadeItens;

    private ControladorCarrinho() {
        this.repProduto = RepProduto.getInstancia();
        this.itens = new Produto[100];
        this.quantidadeItens = 0;
    }

    public static synchronized ControladorCarrinho getInstancia() {
        if (instancia == null) {
            instancia = new ControladorCarrinho();
        }
        return instancia;
    }

    public void adicionarItem(int idProduto) {
        Produto produto = repProduto.buscarPorId(idProduto);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado");
        }
        itens[quantidadeItens++] = produto;
    }

    public double calcularTotal() {
        double total = 0;
        for (int i = 0; i < quantidadeItens; i++) {
            total += itens[i].getPreco();
        }
        return total;
    }

    public double calcularFrete() {
        return 10.0;
    }

}