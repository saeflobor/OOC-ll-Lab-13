import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FlightReservation implements DisplayClass {

    private final Flight flight = new Flight();
    private int flightIndexInFlightList;

    public void bookFlight(String flightNo, int numOfTickets, String userID) {
        Optional<Flight> selectedFlight = findFlightByNumber(flightNo);
        Optional<Customer> customer = findCustomerById(userID);

        if (selectedFlight.isEmpty() || customer.isEmpty()) {
            System.out.println("Invalid Flight Number or User ID!");
            return;
        }

        Flight f1 = selectedFlight.get();
        Customer c1 = customer.get();
        f1.setNoOfSeatsInTheFlight(f1.getNoOfSeats() - numOfTickets);

        if (!f1.isCustomerAlreadyAdded(f1.getListOfRegisteredCustomersInAFlight(), c1)) {
            f1.addNewCustomerToFlight(c1);
        }

        processCustomerFlightBooking(c1, f1, numOfTickets);
        System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flightNo.toUpperCase());
    }

    private void processCustomerFlightBooking(Customer customer, Flight flight, int numOfTickets) {
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
        Scanner read = new Scanner(System.in);
        System.out.print("Enter the Flight Number of the Flight you want to cancel: ");
        String flightNum = read.nextLine();

        Optional<Flight> selectedFlight = findFlightByNumber(flightNum);
        if (selectedFlight.isEmpty()) {
            System.out.println("ERROR! Flight not found.");
            return;
        }

        cancelCustomerFlight(c1, selectedFlight.get(), read);
    }
