package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.flight.ScheduledFlight;
import flight.reservation.Passenger;

import java.util.List;
import java.util.stream.Collectors;

public class FlightOrderBuilder {
    private FlightOrder order;

    public FlightOrderBuilder(List<ScheduledFlight> flights) {
        this.order = new FlightOrder(flights);
    }

    public FlightOrderBuilder setCustomer(Customer customer) {
        order.setCustomer(customer);
        return this;
    }

    public FlightOrderBuilder setPrice(double price) {
        order.setPrice(price);
        return this;
    }

    public FlightOrderBuilder setPassengers(List<String> passengerNames, List<ScheduledFlight> flights) {
        // Validate the order before setting passengers
        if (!isOrderValid(passengerNames, flights)) {
            throw new IllegalStateException("Order is not valid");
        }

        List<Passenger> passengers = passengerNames.stream()
                .map(Passenger::new)
                .collect(Collectors.toList());
        order.setPassengers(passengers);

        // Add passengers to scheduled flights
        flights.forEach(scheduledFlight -> scheduledFlight.addPassengers(passengers));
        return this;
    }

    private boolean isOrderValid(List<String> passengerNames, List<ScheduledFlight> flights) {
        boolean valid = true;

        // Check if customer or passengers are on the no-fly list
        valid = valid && !FlightOrder.getNoFlyList().contains(order.getCustomer().getName());
        valid = valid && passengerNames.stream()
                .noneMatch(passenger -> FlightOrder.getNoFlyList().contains(passenger));

        // Check flight capacity
        valid = valid && flights.stream().allMatch(scheduledFlight -> {
            try {
                return scheduledFlight.getAvailableCapacity() >= passengerNames.size();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                return false;
            }
        });

        return valid;
    }

    public FlightOrder build() {
        return order;
    }
}