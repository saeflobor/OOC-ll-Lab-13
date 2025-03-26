import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// User class with main method; uses encapsulation for customer collection
public class User {
    private static final List<Customer> customersCollection = new ArrayList<>();

    // Encapsulate Collection: Safe getter for customer collection
    public static List<Customer> getCustomersCollection() {
        return Collections.unmodifiableList(customersCollection);
    }

    public static void main(String[] args) {
        // Collect customer data and add to collection
        Customer c1 = Customer.collectCustomerData();
        Customer.addCustomer(c1);
        System.out.println("Customer Registered Successfully!");
    }
}
