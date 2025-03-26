import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerFlightInfo {
    private final List<Flight> flightsRegisteredByUser = new ArrayList<>();
    private final List<Integer> numOfTicketsBookedByUser = new ArrayList<>();

    public void addFlight(Flight flight, int numOfTickets) {
        flightsRegisteredByUser.add(flight);
        numOfTicketsBookedByUser.add(numOfTickets);
    }

    // Encapsulate Collection: Prevent direct modification of lists
    public List<Flight> getFlightsRegisteredByUser() {
        return Collections.unmodifiableList(flightsRegisteredByUser);
    }

    public List<Integer> getNumOfTicketsBookedByUser() {
        return Collections.unmodifiableList(numOfTicketsBookedByUser);
    }
}
