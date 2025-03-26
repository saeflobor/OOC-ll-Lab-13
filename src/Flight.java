import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Flight class extending FlightDistance
public class Flight extends FlightDistance {
    // Replace Magic Literal: Constants for clarity
    private static final double AVERAGE_GROUND_SPEED = 450;
    private static final int MINUTES_INTERVAL = 15;

    private final String flightSchedule;
    private final String flightNumber;
    private final String fromWhichCity;
    private final String toWhichCity;
    private final String gate;
    private double distanceInMiles;
    private double distanceInKm;
    private String flightTime;
    private int numOfSeats;

    // Encapsulate Collection: Static list of flights (use safe getter)
    private static final List<Flight> flightList = new ArrayList<>();

    public Flight(String flightSchedule, String flightNumber, int numOfSeats, String fromCity, String toCity, double miles, double km, String gate) {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.numOfSeats = numOfSeats;
        this.fromWhichCity = fromCity;
        this.toWhichCity = toCity;
        this.distanceInMiles = miles;
        this.distanceInKm = km;
        this.gate = gate;
        this.flightTime = calculateFlightTime(miles);
    }

    // Replace Magic Literal: Use constants for ground speed and minutes interval
    private String calculateFlightTime(double distance) {
        double time = distance / AVERAGE_GROUND_SPEED;
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);
        minutes = roundToNearestInterval(minutes, MINUTES_INTERVAL);
        return formatTime(hours, minutes);
    }

    private int roundToNearestInterval(int minutes, int interval) {
        int remainder = minutes % interval;
        return (remainder < interval / 2) ? minutes - remainder : minutes + (interval - remainder);
    }

    private String formatTime(int hours, int minutes) {
        return String.format("%02d:%02d", hours, minutes);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    // Encapsulate Collection: Return unmodifiable list of flights
    public static List<Flight> getFlightList() {
        return Collections.unmodifiableList(flightList);
    }

    public static void addFlight(Flight flight) {
        flightList.add(flight);
    }
}
