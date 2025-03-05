package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.flight.ScheduledFlight;
import flight.reservation.Passenger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlightOrderBuilder implements Builder {
    private FlightOrder order;

    public FlightOrderBuilder() {
        order = new FlightOrder();
    }

    @Override
    public void buildFlights(List<ScheduledFlight> flights) {
        this.order.setFlights(flights);
        order.getScheduledFlights().forEach(scheduledFlight -> scheduledFlight.addPassengers(order.getPassengers()));
    }

    @Override
    public void buildCustomer(Customer customer) {
        this.order.setCustomer(customer);
    }

    @Override
    public void buildPrice(double price) {
        this.order.setPrice(price);
    }

    @Override
    public void buildPassengers(List<String> passengers) {
        List<Passenger> passengerList = passengers
                .stream()
                .map(Passenger::new)
                .collect(Collectors.toList());
        this.order.setPassengers(passengerList);
    }

    public FlightOrder getResult() {
        return this.order;
    }
}