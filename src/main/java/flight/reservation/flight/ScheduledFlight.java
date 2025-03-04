package flight.reservation.flight;

import flight.reservation.Airport;
import flight.reservation.Passenger;
import flight.reservation.plane.Helicopter;
import flight.reservation.plane.IPlane;
import flight.reservation.plane.PassengerDrone;
import flight.reservation.plane.PassengerPlane;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduledFlight extends Flight {

    private final List<Passenger> passengers;
    private final Date departureTime;
    private double currentPrice = 100;

    public ScheduledFlight(int number, Airport departure, Airport arrival, Object aircraft, Date departureTime) {
        super(number, departure, arrival, aircraft);
        this.departureTime = departureTime;
        this.passengers = new ArrayList<>();
    }

    public ScheduledFlight(int number, Airport departure, Airport arrival, Object aircraft, Date departureTime, double currentPrice) {
        super(number, departure, arrival, aircraft);
        this.departureTime = departureTime;
        this.passengers = new ArrayList<>();
        this.currentPrice = currentPrice;
    }

    // Need to change this method -----------------------------------

    public int getCrewMemberCapacity() throws NoSuchFieldException {
        if (!(this.aircraft instanceof IPlane)){
            throw new NoSuchFieldException("this aircraft has no information about its crew capacity");
        } else{
            return ((IPlane) this.aircraft).getCrewCapacity();
        }
    }

    // ---------------------------------------------------
    public void addPassengers(List<Passenger> passengers) {
        this.passengers.addAll(passengers);
    }

    public void removePassengers(List<Passenger> passengers) {
        this.passengers.removeAll(passengers);
    }

    // Need to change this method -----------------------------------
    public int getCapacity() throws NoSuchFieldException {
        if (!(this.aircraft instanceof IPlane)) {
            throw new NoSuchFieldException("this aircraft has no information about its capacity");
        } else {
            return ((IPlane) this.aircraft).getPassengerCapacity();
        }
    }
    // ---------------------------------------------------

    public int getAvailableCapacity() throws NoSuchFieldException {
        return this.getCapacity() - this.passengers.size();
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
