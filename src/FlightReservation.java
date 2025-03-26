import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FlightReservation implements DisplayClass {

    private final Flight flight = new Flight();
    private int flightIndexInFlightList;

    /**
     * Books tickets for a flight. Updates the available seats and manages customer registration.
     *
     * @param flightNo FlightID of the flight to be booked
     * @param numOfTickets Number of tickets to be booked
     * @param userID UserID of the customer booking the flight
     */
    public void bookFlight(String flightNo, int numOfTickets, String userID) {
        Flight selectedFlight = findFlightByNumber(flightNo);
        if (selectedFlight == null) {
            System.out.println("Invalid Flight Number...! No flight with the ID \"" + flightNo + "\" was found...");
            return;
        }

        Customer customer = findCustomerById(userID);
        if (customer == null) {
            System.out.println("Invalid User ID...! No customer found with the ID \"" + userID + "\".");
            return;
        }

        selectedFlight.setNoOfSeatsInTheFlight(selectedFlight.getNoOfSeats() - numOfTickets);
        if (!selectedFlight.isCustomerAlreadyAdded(selectedFlight.getListOfRegisteredCustomersInAFlight(), customer)) {
            selectedFlight.addNewCustomerToFlight(customer);
        }

        if (isFlightAlreadyAddedToCustomerList(customer.getCustomerFlightInfo().getFlightsRegisteredByUser(), selectedFlight)) {
            addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets);
        } else {
            customer.getCustomerFlightInfo().addFlight(selectedFlight, numOfTickets);
        }

        System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flightNo.toUpperCase());
    }

    /**
     * Cancels a flight booking for a customer and returns the tickets to availability.
     *
     * @param userID ID of the customer canceling the flight
     */
    public void cancelFlight(String userID) {
        Customer customer = findCustomerById(userID);
        if (customer == null) {
            System.out.println("Invalid User ID. No customer found.");
            return;
        }

        if (customer.getCustomerFlightInfo().getFlightsRegisteredByUser().isEmpty()) {
            System.out.println("No registered flights found for user ID: " + userID);
            return;
        }

        displayFlightsRegisteredByOneUser(userID);
        Scanner read = new Scanner(System.in);
        System.out.print("Enter the Flight Number of the Flight you want to cancel: ");
        String flightNum = read.nextLine();

        Flight selectedFlight = findFlightByNumber(flightNum);
        if (selectedFlight == null) {
            System.out.println("ERROR! Flight with ID \"" + flightNum + "\" not found.");
            return;
        }

        cancelCustomerFlight(customer, selectedFlight, read);
    }

    private void cancelCustomerFlight(Customer customer, Flight flight, Scanner read) {
        int index = customer.getCustomerFlightInfo().getFlightsRegisteredByUser().indexOf(flight);
        if (index == -1) {
            System.out.println("ERROR! Flight not registered under this customer.");
            return;
        }

        int numOfTicketsForFlight = customer.getCustomerFlightInfo().getNumOfTicketsBookedByUser().get(index);
        System.out.print("Enter the number of tickets to cancel: ");
        int numOfTickets = read.nextInt();

        while (numOfTickets > numOfTicketsForFlight) {
            System.out.print("ERROR! Number of tickets cannot exceed " + numOfTicketsForFlight + ". Please enter again: ");
            numOfTickets = read.nextInt();
        }

        int ticketsToBeReturned = flight.getNoOfSeats() + numOfTickets;
        customer.getCustomerFlightInfo().getNumOfTicketsBookedByUser().set(index, numOfTicketsForFlight - numOfTickets);

        if (numOfTicketsForFlight == numOfTickets) {
            customer.getCustomerFlightInfo().getFlightsRegisteredByUser().remove(index);
            customer.getCustomerFlightInfo().getNumOfTicketsBookedByUser().remove(index);
        }

        flight.setNoOfSeatsInTheFlight(ticketsToBeReturned);
    }

    /**
     * Adds more tickets for a flight that a customer has already booked.
     */
    private void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets) {
        int newNumOfTickets = customer.getCustomerFlightInfo().getNumOfTicketsBookedByUser().get(flightIndexInFlightList) + numOfTickets;
        customer.getCustomerFlightInfo().getNumOfTicketsBookedByUser().set(flightIndexInFlightList, newNumOfTickets);
    }

    /**
     * Checks if a customer has already registered for a given flight.
     */
    private boolean isFlightAlreadyAddedToCustomerList(List<Flight> flightList, Flight flight) {
        for (Flight registeredFlight : flightList) {
            if (registeredFlight.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                this.flightIndexInFlightList = flightList.indexOf(registeredFlight);
                return true;
            }
        }
        return false;
    }

    private Flight findFlightByNumber(String flightNo) {
        return flight.getFlightList().stream()
                .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNo))
                .findFirst()
                .orElse(null);
    }

    private Customer findCustomerById(String userID) {
        return User.getCustomersCollection().stream()
                .filter(c -> c.getUserID().equals(userID))
                .findFirst()
                .orElse(null);
    }

    private String flightStatus(Flight flight) {
        return flight.getFlightList().contains(flight) ? "As Per Schedule" : "Cancelled";
    }

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO |  Booked Tickets  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |  FLIGHT STATUS  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");

        User.getCustomersCollection().stream()
                .filter(customer -> customer.getUserID().equals(userID))
                .forEach(customer -> {
                    List<Flight> flights = customer.getCustomerFlightInfo().getFlightsRegisteredByUser();
                    for (int i = 0; i < flights.size(); i++) {
                        System.out.println(toString((i + 1), flights.get(i), customer));
                    }
                });

        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
    }

    private String toString(int serialNum, Flight flight, Customer customer) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9d | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  | %-10s |",
                serialNum, flight.getFlightSchedule(), flight.getFlightNumber(),
                customer.getCustomerFlightInfo().getNumOfTicketsBookedByUser().get(serialNum - 1),
                flight.getFromWhichCity(), flight.getToWhichCity(), flight.fetchArrivalTime(),
                flight.getFlightTime(), flight.getGate(), flightStatus(flight));
    }
}
