package Payment;

public class Payments {
    private String method;
    private String status;

    public Payments(String method, String status) {
        this.method = method;
        this.status = status;
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
