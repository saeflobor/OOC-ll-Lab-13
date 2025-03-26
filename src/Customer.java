import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Customer {
    private final String userID;
    private String email;
    private String name;
    private String phone;
    private final String password;
    private String address;
    private int age;
    private final CustomerFlightInfo customerFlightInfo;

    private static final List<Customer> customerCollection = new ArrayList<>();

    // Constructor
    public Customer(String name, String email, String password, String phone, String address, int age) {
        this.userID = RandomGenerator.generateRandomID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.customerFlightInfo = new CustomerFlightInfo();
    }

    // Extract Method: Separated user input logic
    public static Customer collectCustomerData() {
        Scanner read = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = read.nextLine();
        String email = getValidEmail(read);
        System.out.print("Enter your password: ");
        String password = read.nextLine();
        System.out.print("Enter your phone number: ");
        String phone = read.nextLine();
        System.out.print("Enter your address: ");
        String address = read.nextLine();
        System.out.print("Enter your age: ");
        int age = read.nextInt();
        return new Customer(name, email, password, phone, address, age);
    }

    // Decompose Conditional: Separated email validation
    private static String getValidEmail(Scanner read) {
        String email;
        do {
            System.out.print("Enter your email address: ");
            email = read.nextLine();
            if (isEmailAlreadyRegistered(email)) {
                System.out.println("ERROR! Email already exists. Try another.");
            }
        } while (isEmailAlreadyRegistered(email));
        return email;
    }

    public static void addCustomer(Customer customer) {
        customerCollection.add(customer);
    }

    public static boolean isEmailAlreadyRegistered(String emailID) {
        return customerCollection.stream().anyMatch(c -> c.getEmail().equals(emailID));
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public CustomerFlightInfo getCustomerFlightInfo() {
        return customerFlightInfo;
    }

    public static List<Customer> getCustomerCollection() {
        return Collections.unmodifiableList(customerCollection);
    }
}
