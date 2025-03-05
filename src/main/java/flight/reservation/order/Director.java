package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.flight.ScheduledFlight;

import java.util.List;

public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void constructOrder(List<String> passengers, List<ScheduledFlight> flights, double price, Customer customer) {
        builder.buildCustomer(customer);
        builder.buildPrice(price);
        builder.buildPassengers(passengers);
        builder.buildFlights(flights);
    }
}