import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private static final List<Customer> customersCollection = new ArrayList<>();

    public static void addCustomer(Customer customer) {
        customersCollection.add(customer);
    }

    public static List<Customer> getCustomersCollection() {
        return Collections.unmodifiableList(customersCollection);
    }

    // Extract Method: Moved print statement to a separate method
    private static void printCustomerRegistrationSuccess() {
        System.out.println("Customer Registered Successfully!");
    }

    public static void main(String[] args) {
        Customer c1 = Customer.collectCustomerData();
        addCustomer(c1);
        printCustomerRegistrationSuccess();
    }
}
