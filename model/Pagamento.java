package model;

public class Pagamento implements IPagamento {
    private String tipo;
    private String status;

    public Pagamento(String tipo,int id, double valorTotal, String metodo) {
        this.tipo = tipo.toLowerCase();
        this.status = "Pendente";
    }

    @Override
    public boolean processarPagamento(double valor) {
        if (valor <= 0) {
            status = "Valor inválido";
            return false;
        }
        switch (tipo) {
            case "credito":
                status = "Pago com cartão de crédito";
                break;
            case "debito":
                status = "Pago com cartão de débito";
                break;
            case "pix":
                status = "Pago com Pix";
                break;
            case "paypal":
                status = "Pago via PayPal";
                break;
            default:
                status = "Método não suportado";
                return false;
        }
        return true;
    }

    // Pagar uma fatura
    public boolean pagarFatura(Fatura fatura) {
        if (!"pendente".equals(fatura.getStatus())) {
            status = "Fatura já paga ou cancelada";
            return false;
        }
        boolean sucesso = processarPagamento(fatura.getValorTotal());
        if (sucesso) {
            fatura.registrarPagamento(fatura.getValorTotal(), tipo);
        }
        return sucesso;
    }

    @Override
    public String gerarComprovante() {
        return "Comprovante: " + status;
    }

    @Override
    public String getStatus() {
        return status;
    }
}