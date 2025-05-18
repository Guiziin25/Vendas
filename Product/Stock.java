package Product;

public class Stock {
    private int quantity;
    private int minimumQuantity;

    public Stock(int quantity, int minimumQuantity) {
        this.quantity = quantity;
        this.minimumQuantity = minimumQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }
}
