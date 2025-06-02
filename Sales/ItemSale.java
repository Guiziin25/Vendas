package Sales;

import java.math.BigDecimal;

public class ItemSale {
    private int quantity;
    private BigDecimal priceunit;

    public ItemSale(int quantity, BigDecimal priceunit) {
        this.quantity = quantity;
        this.priceunit = priceunit;
    }

    public BigDecimal getPriceunit() {
        return priceunit;
    }

    public void setPriceunit(BigDecimal priceunit) {
        this.priceunit = priceunit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
