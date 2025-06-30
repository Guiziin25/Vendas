// repository/RepPagamento.java
package repository;

import model.Pagamento;

public class RepPagamento {
    private static RepPagamento instancia;
    private Pagamento[] pagamentos;
    private int tamanho;
    private static final int CAPACIDADE_INICIAL = 100;

    private RepPagamento() {
        pagamentos = new Pagamento[CAPACIDADE_INICIAL];
        tamanho = 0;
    }

    public static synchronized RepPagamento getInstancia() {
        if (instancia == null) {
            instancia = new RepPagamento();
        }
        return instancia;
    }

    public boolean adicionar(Pagamento pagamento) {
        if (tamanho >= pagamentos.length) {
            aumentarCapacidade();
        }
        pagamentos[tamanho] = pagamento;
        tamanho++;
        return true;
    }

    public Pagamento[] listarTodos() {
        Pagamento[] todos = new Pagamento[tamanho];
        System.arraycopy(pagamentos, 0, todos, 0, tamanho);
        return todos;
    }

    public int getQuantidade() {
        return tamanho;
    }

    private void aumentarCapacidade() {
        int novaCapacidade = pagamentos.length * 2;
        Pagamento[] novoArray = new Pagamento[novaCapacidade];
        System.arraycopy(pagamentos, 0, novoArray, 0, pagamentos.length);
        pagamentos = novoArray;
    }
}