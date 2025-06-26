package repository;

import model.Promocao;
import repository.Interfaces.IRepPromocao;
import java.util.Date;

public class RepPromocao implements IRepPromocao {
    private static RepPromocao instancia;
    private Promocao[] promocoes;
    private int tamanho;
    private static final int CAPACIDADE_INICIAL = 100;

    private RepPromocao() {
        promocoes = new Promocao[CAPACIDADE_INICIAL];
        tamanho = 0;
    }

    public static synchronized RepPromocao getInstancia() {
        if (instancia == null) {
            instancia = new RepPromocao();
        }
        return instancia;
    }

    @Override
    public boolean adicionar(Promocao promocao) {
        if (tamanho >= promocoes.length) {
            aumentarCapacidade();
        }

        // Verifica se já existe promoção/cupom com mesmo código
        if (promocao.isCupom() && buscarPorCodigo(promocao.getCodigo()) != null) {
            return false;
        }

        promocoes[tamanho] = promocao;
        tamanho++;
        return true;
    }

    @Override
    public Promocao buscarPorId(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].getId() == id) {
                return promocoes[i];
            }
        }
        return null;
    }

    @Override
    public Promocao buscarPorCodigo(String codigo) {
        if (codigo == null) return null;

        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].isCupom() && codigo.equals(promocoes[i].getCodigo())) {
                return promocoes[i];
            }
        }
        return null;
    }

    @Override
    public Promocao[] buscarPorProduto(int idProduto) {
        int count = 0;
        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].getIdProduto() == idProduto && !promocoes[i].isCupom()) {
                count++;
            }
        }

        Promocao[] resultado = new Promocao[count];
        int index = 0;
        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].getIdProduto() == idProduto && !promocoes[i].isCupom()) {
                resultado[index++] = promocoes[i];
            }
        }
        return resultado;
    }

    @Override
    public Promocao[] buscarAtivas() {
        Date hoje = new Date();
        int count = 0;

        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].estaAtiva()) {
                count++;
            }
        }

        Promocao[] ativas = new Promocao[count];
        int index = 0;
        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].estaAtiva()) {
                ativas[index++] = promocoes[i];
            }
        }
        return ativas;
    }

    @Override
    public Promocao[] buscarExpiradas() {
        Date hoje = new Date();
        int count = 0;

        for (int i = 0; i < tamanho; i++) {
            if (!promocoes[i].estaAtiva()) {
                count++;
            }
        }

        Promocao[] expiradas = new Promocao[count];
        int index = 0;
        for (int i = 0; i < tamanho; i++) {
            if (!promocoes[i].estaAtiva()) {
                expiradas[index++] = promocoes[i];
            }
        }
        return expiradas;
    }

    @Override
    public Promocao[] buscarCuponsAtivos() {
        Date hoje = new Date();
        int count = 0;

        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].isCupom() && promocoes[i].estaAtiva()) {
                count++;
            }
        }

        Promocao[] cupons = new Promocao[count];
        int index = 0;
        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].isCupom() && promocoes[i].estaAtiva()) {
                cupons[index++] = promocoes[i];
            }
        }
        return cupons;
    }

    @Override
    public boolean atualizar(Promocao promocao) {
        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].getId() == promocao.getId()) {
                promocoes[i] = promocao;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remover(int id) {
        for (int i = 0; i < tamanho; i++) {
            if (promocoes[i].getId() == id) {
                // Move o último elemento para a posição removida
                promocoes[i] = promocoes[tamanho - 1];
                promocoes[tamanho - 1] = null;
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

    @Override
    public Promocao[] listarTodos() {
        Promocao[] todas = new Promocao[tamanho];
        System.arraycopy(promocoes, 0, todas, 0, tamanho);
        return todas;
    }

    private void aumentarCapacidade() {
        int novaCapacidade = promocoes.length * 2;
        Promocao[] novoArray = new Promocao[novaCapacidade];
        System.arraycopy(promocoes, 0, novoArray, 0, promocoes.length);
        promocoes = novoArray;
    }
}