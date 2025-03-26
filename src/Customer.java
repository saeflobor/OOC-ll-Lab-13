import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Main Customer class
public class Customer {
    private final String userID;
    private String email;
    private String name;
    private String phone;
    private final String password;
    private String address;
    private int age;
    private final CustomerFlightInfo customerFlightInfo; // Extract Class: Flight info moved here

    // Encapsulate Collection: Static list for customers (accessed via safe getters)
    private static final List<Customer> customerCollection = new ArrayList<>();

    // Default constructor
    public Customer() {
        this.userID = null;
        this.name = null;
        this.email = null;
        this.password = null;
        this.phone = null;
        this.address = null;
        this.age = 0;
        this.customerFlightInfo = new CustomerFlightInfo();
    }

    // Parameterized constructor (uses RandomGenerator for userID)
    public Customer(String name, String email, String password, String phone, String address, int age) {
        this.userID = RandomGenerator.generateRandomID(); // Extract Class: RandomGenerator
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.customerFlightInfo = new CustomerFlightInfo();
    }

    // Extract Method: Collect customer data from input
    public static Customer collectCustomerData() {
        Scanner read = new Scanner(System.in);
        System.out.print("Enter your name : ");
        String name = read.nextLine();
        System.out.print("Enter your email address : ");
        String email = read.nextLine();
        // Decompose Conditional: Check uniqueness of email
        while (isUniqueData(email)) {
            System.out.println("ERROR! Email already exists. Enter a new email: ");
            email = read.nextLine();
        }
        System.out.print("Enter your Password : ");
        String password = read.nextLine();
        System.out.print("Enter your Phone number : ");
        String phone = read.nextLine();
        System.out.print("Enter your address : ");
        String address = read.nextLine();
        System.out.print("Enter your age : ");
        int age = read.nextInt();
        return new Customer(name, email, password, phone, address, age);
    }

    // Encapsulate Collection: Add customer to collection
    public static void addCustomer(Customer customer) {
        customerCollection.add(customer);
    }

    // Replace Conditional with Polymorphism (via Stream API): Check uniqueness of email
    public static boolean isUniqueData(String emailID) {
        return customerCollection.stream().anyMatch(c -> c.getEmail().equals(emailID));
    }

    // Self-Encapsulate Field: Getter for userID
    public String getUserID() {
        return userID;
    }

    // Self-Encapsulate Field: Getter for email
    public String getEmail() {
        return email;
    }

    // Self-Encapsulate Field: Getter for flight information
    public CustomerFlightInfo getCustomerFlightInfo() {
        return customerFlightInfo;
    }

    // (Additional getters/setters could be added as needed without altering logic)
}
