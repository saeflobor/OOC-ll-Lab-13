import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Flight extends FlightDistance {
    private static final double AVERAGE_GROUND_SPEED = 450;
    private static final int MINUTES_INTERVAL = 15;

    private final String flightSchedule;
    private final String flightNumber;
    private final String fromCity;
    private final String toCity;
    private final String gate;
    private int numOfSeats;
    private String flightTime;

    private static final List<Flight> flightList = new ArrayList<>();

    public Flight(String flightSchedule, String flightNumber, int numOfSeats, String fromCity, String toCity, String gate) {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.numOfSeats = numOfSeats;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.gate = gate;
        this.flightTime = calculateFlightTime();
    }

    // Replace Magic Literal: Use constants instead of hardcoded values
    private String calculateFlightTime() {
        double time = distanceBetweenCities(fromCity, toCity) / AVERAGE_GROUND_SPEED;
        return formatFlightTime(time);
    }

    // Extract Method: Formatting logic extracted separately
    private String formatFlightTime(double time) {
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);
        minutes = roundToNearestInterval(minutes, MINUTES_INTERVAL);
        return String.format("%02d:%02d", hours, minutes);
    }

    private int roundToNearestInterval(int minutes, int interval) {
        int remainder = minutes % interval;
        return (remainder < interval / 2) ? minutes - remainder : minutes + (interval - remainder);
    }

    // Encapsulate Collection: Return unmodifiable list of flights
    public static List<Flight> getFlightList() {
        return Collections.unmodifiableList(flightList);
    }

    public static void addFlight(Flight flight) {
        flightList.add(flight);
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getNoOfSeats() {
        return numOfSeats;
    }

    public void setNoOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }
}
