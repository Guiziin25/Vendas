package Payment;

import java.util.Date;

public class DiscountCoupon {
    private String code;
    private Date experationDate;
    private double discountValue;

    public DiscountCoupon(String code, Date experationDate, double discountValue) {
        this.code = code;
        this.experationDate = experationDate;
        this.discountValue = discountValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExperationDate() {
        return experationDate;
    }

    public void setExperationDate(Date experationDate) {
        this.experationDate = experationDate;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public boolean isValid() {
        Date currentDate = new Date();
        return currentDate.before(experationDate);
    }

    public void applyDiscount(double originalPrice) {
        if (isValid()) {
            double discountedPrice = originalPrice - discountValue;
            System.out.println("Discount applied! New price: " + discountedPrice);
        } else {
            System.out.println("Coupon is expired.");
        }
    }
}
