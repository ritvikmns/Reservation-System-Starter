package flight.reservation.payment;

import java.util.HashMap;
import java.util.Map;

public class Paypal implements PaymentStrategy {
    public static final Map<String, String> DATA_BASE = new HashMap<>();
    private String email;
    private String password;
    static {
        DATA_BASE.put("amanda1985", "amanda@ya.com");
        DATA_BASE.put("qwerty", "john@amazon.eu");
    }
    public Paypal(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean pay(double amount) {
        if (DATA_BASE.containsKey(password) && DATA_BASE.get(password).equals(email)) {
            System.out.println("Paying " + amount + " using PayPal.");
            return true;
        } else {
            System.out.println("Payment failed: Invalid PayPal credentials.");
            return false;
        }
    }

}
