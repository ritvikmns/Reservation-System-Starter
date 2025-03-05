package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.flight.ScheduledFlight;
import flight.reservation.Passenger;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface Builder {
    public abstract void buildFlights(List<ScheduledFlight> flights);
    public abstract void buildCustomer(Customer customer);
    public abstract void buildPrice(double price);
    public abstract void buildPassengers(List<String> passengers);
}