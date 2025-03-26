import java.util.*;

public class Customer {
    private final String userID;
    private String email;
    private String name;
    private String phone;
    private final String password;
    private String address;
    private int age;
    private final CustomerFlightInfo customerFlightInfo;

    // Encapsulate Collection - Prevent direct access to static list
    private static final List<Customer> customerCollection = new ArrayList<>();
