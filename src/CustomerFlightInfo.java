import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Extract Class: Moved flight-related information out of Customer
public class CustomerFlightInfo {
    private final List<Flight> flightsRegisteredByUser = new ArrayList<>();
    private final List<Integer> numOfTicketsBookedByUser = new ArrayList<>();

    public void addFlight(Flight flight, int numOfTickets) {
        flightsRegisteredByUser.add(flight);
        numOfTicketsBookedByUser.add(numOfTickets);
    }

    // Encapsulate Collection: Return unmodifiable lists to prevent external modifications
    public List<Flight> getFlightsRegisteredByUser() {
        return Collections.unmodifiableList(flightsRegisteredByUser);
    }

    public List<Integer> getNumOfTicketsBookedByUser() {
        return Collections.unmodifiableList(numOfTicketsBookedByUser);
    }
}