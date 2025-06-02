package Sales;

import java.math.BigDecimal;
import java.util.Date;

public class Sale {
    private Long id;
    private Date date;
    private BigDecimal total;

    public Sale(Long id, Date date, BigDecimal total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
