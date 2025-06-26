package repository;

import model.Fatura;
import repository.Interfaces.IRepFatura;

public class RepFatura implements IRepFatura {
    private static RepFatura instancia;
    private Fatura[] faturas;
    private int tamanho;
    private static final int CAPACIDADE_INICIAL = 100;

    private RepFatura() {
        faturas = new Fatura[CAPACIDADE_INICIAL];
        tamanho = 0;
    }

    public static synchronized RepFatura getInstancia() {
        if (instancia == null) {
            instancia = new RepFatura();
        }
        return instancia;
    }

    @Override
    public void adicionar(Fatura fatura) {
        if (tamanho == faturas.length) {
            aumentarCapacidade();
        }
        faturas[tamanho] = fatura;
        tamanho++;
    }

    @Override
    public Fatura buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].getId() == id) {
                return faturas[i];
            }
        }
        return null;
    }

    @Override
    public Fatura[] listarTodas() {
        Fatura[] todasFaturas = new Fatura[tamanho];
        for (int i = 0; i < tamanho; i++) {
            todasFaturas[i] = faturas[i];
        }
        return todasFaturas;
    }

    @Override
    public Fatura[] buscarPorCliente(int idCliente) {
        int contador = 0;
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].getIdCliente() == idCliente) {
                contador++;
            }
        }

        Fatura[] faturasCliente = new Fatura[contador];
        int index = 0;
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].getIdCliente() == idCliente) {
                faturasCliente[index] = faturas[i];
                index++;
            }
        }
        return faturasCliente;
    }

    @Override
    public Fatura[] buscarPorStatus(String status) {
        int contador = 0;
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].getStatus().equalsIgnoreCase(status)) {
                contador++;
            }
        }

        Fatura[] faturasStatus = new Fatura[contador];
        int index = 0;
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].getStatus().equalsIgnoreCase(status)) {
                faturasStatus[index] = faturas[i];
                index++;
            }
        }
        return faturasStatus;
    }

    @Override
    public boolean atualizar(Fatura fatura) {
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].getId() == fatura.getId()) {
                faturas[i] = fatura;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remover(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].getId() == id) {
                for (int j = i; j < tamanho - 1; j++) {
                    faturas[j] = faturas[j + 1];
                }
                faturas[tamanho - 1] = null;
                tamanho--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int getQuantidade() {
        return tamanho;
    }

    private void aumentarCapacidade() {
        int novaCapacidade = faturas.length * 2;
        Fatura[] novoArray = new Fatura[novaCapacidade];
        for (int i = 0; i < faturas.length; i++) {
            novoArray[i] = faturas[i];
        }
        faturas = novoArray;
    }

    // Método adicional para buscar faturas vencidas
    public Fatura[] buscarFaturasVencidas() {
        int contador = 0;
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].estaVencida()) {
                contador++;
            }
        }

        Fatura[] vencidas = new Fatura[contador];
        int index = 0;
        for (int i = 0; i < tamanho; i++) {
            if (faturas[i].estaVencida()) {
                vencidas[index] = faturas[i];
                index++;
            }
        }
        return vencidas;
    }
}