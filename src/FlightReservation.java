import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FlightReservation implements DisplayClass {
    private static final String ERROR_FLIGHT_NOT_FOUND = "ERROR! Flight not found.";
    private static final String ERROR_INVALID_INPUT = "Invalid Flight Number or User ID!";
    private static final String SUCCESSFUL_BOOKING = "You've booked %d tickets for Flight \"%5s\"...";

    private final Flight flight = new Flight();
    private int flightIndexInFlightList;

    public void bookFlight(String flightNo, int numOfTickets, String userID) {
        Optional<Flight> selectedFlight = findFlightByNumber(flightNo);
        Optional<Customer> customer = findCustomerById(userID);

        if (selectedFlight.isEmpty() || customer.isEmpty()) {
            System.out.println(ERROR_INVALID_INPUT);
            return;
        }

        processFlightBooking(selectedFlight.get(), customer.get(), numOfTickets);
        System.out.printf("\n %50s " + SUCCESSFUL_BOOKING, "", numOfTickets, flightNo.toUpperCase());
    }

    private void processFlightBooking(Flight flight, Customer customer, int numOfTickets) {
        flight.setNoOfSeats(flight.getNoOfSeats() - numOfTickets);
        if (!flight.isCustomerAlreadyAdded(flight.getListOfRegisteredCustomersInAFlight(), customer)) {
            flight.addNewCustomerToFlight(customer);
        }

        if (isFlightAlreadyAddedToCustomerList(customer.getCustomerFlightInfo().getFlightsRegisteredByUser(), flight)) {
            addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets);
        } else {
            customer.getCustomerFlightInfo().addFlight(flight, numOfTickets);
        }
    }

    public void cancelFlight(String userID) {
        Optional<Customer> customer = findCustomerById(userID);
        if (customer.isEmpty()) {
            System.out.println("Invalid User ID. No customer found.");
            return;
        }

        Customer c1 = customer.get();
        if (c1.getCustomerFlightInfo().getFlightsRegisteredByUser().isEmpty()) {
            System.out.println("No registered flights found for user ID: " + userID);
            return;
        }

        displayFlightsRegisteredByOneUser(userID);
        processFlightCancellation(c1);
    }

    private void processFlightCancellation(Customer customer) {
        Scanner read = new Scanner(System.in);
        System.out.print("Enter the Flight Number of the Flight you want to cancel: ");
        String flightNum = read.nextLine();

        Optional<Flight> selectedFlight = findFlightByNumber(flightNum);
        if (selectedFlight.isEmpty()) {
            System.out.println(ERROR_FLIGHT_NOT_FOUND);
            return;
        }

        cancelCustomerFlight(customer, selectedFlight.get(), read);
    }

    private Optional<Flight> findFlightByNumber(String flightNo) {
        return flight.getFlightList().stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNo))
                .findFirst();
    }

    private Optional<Customer> findCustomerById(String userID) {
        return User.getCustomersCollection().stream()
                .filter(c -> c.getUserID().equals(userID))
                .findFirst();
    }

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        StringBuilder output = new StringBuilder();
        output.append("\nRegistered Flights for User ID: ").append(userID).append("\n");

        User.getCustomersCollection().stream()
                .filter(customer -> customer.getUserID().equals(userID))
                .forEach(customer -> customer.getCustomerFlightInfo().getFlightsRegisteredByUser()
                        .forEach(flight -> output.append(flight.toString()).append("\n")));

        System.out.println(output);
    }
}
