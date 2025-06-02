package Payment;

public class Payments {
    private String method;
    private String status;

    //Cria o Pagamento com o status PENDENTE até sua validação
    public Payments(String method) {
        this.method = method;
        this.status = "PENDENTE";
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
