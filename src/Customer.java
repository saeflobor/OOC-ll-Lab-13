import java.util.*;

public class Customer {

    // ************************************************************ Fields
    // ************************************************************
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "root";
    private static final int MAX_ADMINS = 10;


    // ************************************************************
    // Behaviours/Methods
    // ************************************************************

    private static String[][] adminCredentials = new String[MAX_ADMINS][2];
    private static List<Customer> customersCollection = new ArrayList<>();

    public static void main(String[] args) {
        initializeDefaultAdmin();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\t\t\t\t+++++++++++++ Welcome to BAV Airlines +++++++++++++\n");
        System.out.println("\n***** Default Username & Password is root-root *****\n");

        int option;
        do {
            displayMainMenu();
            option = getValidOption(scanner, 0, 5);
            handleMainMenuOption(option, scanner);
        } while (option != 0);

        System.out.println("Thank you for using BAV Airlines Ticketing System!");
        scanner.close();
    }


    /**
     * Registers new customer to the program. Obj of RandomGenerator(Composition) is
     * being used to assign unique userID to the newly created customer.
     *
     * @param name     name of the customer
     * @param email    customer's email
     * @param password customer's account password
     * @param phone    customer's phone-number
     * @param address  customer's address
     * @param age      customer's age
     */

    private static void initializeDefaultAdmin() {
        adminCredentials[0][0] = DEFAULT_USERNAME;
        adminCredentials[0][1] = DEFAULT_PASSWORD;
    }


    private static void displayMainMenu() {
        System.out.println("\n(a) Press 0 to Exit.");
        System.out.println("(b) Press 1 to Login as Admin.");
        System.out.println("(c) Press 2 to Register as Admin.");
        System.out.println("(d) Press 3 to Login as Passenger.");
        System.out.println("(e) Press 4 to Register as Passenger.");
        System.out.println("(f) Press 5 to Display the User Manual.");
        System.out.print("Enter the desired option: ");
    }


    private static int getValidOption(Scanner scanner, int min, int max) {
        int option;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                scanner.next();
            }
            option = scanner.nextInt();
            if (option < min || option > max) {
                System.out.printf("ERROR!! Please enter a value between %d and %d: ", min, max);
            }
        } while (option < min || option > max);
        return option;
    }


    private static void handleMainMenuOption(int option, Scanner scanner) {
        switch (option) {
            case 1:
                loginAsAdmin(scanner);
                break;
            case 2:
                registerAdmin(scanner);
                break;
            case 3:
                loginAsPassenger(scanner);
                break;
            case 4:
                registerPassenger(scanner);
                break;
            case 5:
                displayUserManual(scanner);
                break;
            default:
                break;
        }
    }


    private static void loginAsAdmin(Scanner scanner) {
        System.out.print("\nEnter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();

        if (!isValidAdmin(username, password)) {
            System.out.println("ERROR! Invalid credentials.");
            return;
        }

        System.out.printf("Logged in Successfully as '%s'.\n", username);
        adminMenu(scanner);
    }


    private static boolean isValidAdmin(String username, String password) {
        for (String[] credentials : adminCredentials) {
            if (credentials[0] != null && credentials[0].equals(username) && credentials[1].equals(password)) {
                return true;
            }
        }
        return false;
    }


    private static void adminMenu(Scanner scanner) {
        int option;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Passenger");
            System.out.println("2. Search Passenger");
            System.out.println("3. Update Passenger Data");
            System.out.println("4. Delete Passenger");
            System.out.println("5. Display All Passengers");
            System.out.println("0. Logout");
            System.out.print("Enter option: ");
            option = getValidOption(scanner, 0, 5);
            handleAdminOption(option, scanner);
        } while (option != 0);
    }


    private static void handleAdminOption(int option, Scanner scanner) {
        Customer customer = new Customer();
        switch (option) {
            case 1:
                customer.addNewCustomer();
                break;
            case 2:
                customer.searchUser();
                break;
            case 3:
                customer.editUserInfo();
                break;
            case 4:
                customer.deleteUser();
                break;
            case 5:
                customer.displayCustomersData();
                break;
            default:
                break;
        }
    }


    private static void registerPassenger(Scanner scanner) {
        System.out.println("Passenger registration logic here.");
    }


    private static void displayUserManual(Scanner scanner) {
        System.out.println("Displaying user manual...");
    }
