package flight.reservation.plane;

public class PlaneFactory {
    public static IPlane createPlane(String type, String model) {
        switch (type) {
            case "Helicopter":
                return new Helicopter(model);
            case "PassengerDrone":
                return new PassengerDrone(model);
            case "PassengerPlane":
                return new PassengerPlane(model);
            default:
                throw new IllegalArgumentException(String.format("Model type '%s' is not recognized", type));
        }
    }
}
