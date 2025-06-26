package model;

public interface IPagamento {
    boolean processarPagamento(double valor);
    String gerarComprovante();
    String getStatus();
}