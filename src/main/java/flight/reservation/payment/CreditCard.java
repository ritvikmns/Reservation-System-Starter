package flight.reservation.payment;

import java.util.Date;

/**
 * Dummy credit card class.
 */
//Strategy
public class CreditCard  implements PaymentStrategy {
    private double amount;
    private String number;
    private Date date;
    private String cvv;
    private boolean valid;

    public CreditCard(String number, Date date, String cvv) {
        this.amount = 100000;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
        this.setValid();
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isValid() {
        return valid;
    }
//    strategy
    @Override
    public boolean pay(double amount) {
        if (isValid() && this.amount >= amount) {
            System.out.println("Paying " + amount + " using Credit Card.");
            this.amount -= amount;
            return true;
        } else {
            System.out.println("Payment failed: Insufficient funds or invalid card.");
            return false;
        }
    }
    public void setValid() {
        // Dummy validation
        this.valid = number.length() > 0 && date.getTime() > System.currentTimeMillis() && !cvv.equals("000");
    }
}