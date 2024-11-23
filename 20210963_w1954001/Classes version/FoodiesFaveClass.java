import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// JavaFX
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * foodClassVer
 */
public class FoodiesFaveClass extends Application {

    // An array to hold three FoodQueue objects representing three queues.
    static FoodQueue[] queues = new FoodQueue[3];
    // The initial total number of burgers in stock.
    static int totalBurgers = 50;
    static final int BURGER_PRICE = 650;

    public static void main(String[] args) {
        // Initialize the three queues with their respective capacities.
        queues[0] = new FoodQueue(2);
        queues[1] = new FoodQueue(3);
        queues[2] = new FoodQueue(5);

        // Infinite loop to keep the program running until the user chooses to exit.
        while (true) {
            // Display the menu options and get the user's choice.
            displayMenu();
            Scanner scanner = new Scanner(System.in);
            String inputKey = scanner.nextLine().toUpperCase();

            // Handle the user's choice using if-else
            switch (inputKey) {
                case "100":
                case "VFQ":
                    // Display all the queues.
                    viewAllQueues();
                    break;

                case "101":
                case "VEQ":
                    // View all empty queues.
                    viewAllEmptyQueues();
                    break;

                case "102":
                case "ACQ":
                    // Add a customer to the shortest queue.
                    addCustomer();
                    break;

                case "103":
                case "RCQ":
                    // Remove a customer from a specific location in a queue.
                    removeCustomer();
                    break;

                case "104":
                case "PCQ":
                    // Remove a served customer from a specific location in a queue.
                    removeServedCustomer();
                    break;

                case "105":
                case "VCS":
                    // View all customers sorted in alphabetical order.
                    sortCustomerList();
                    break;

                case "106":
                case "SPD":
                    // Store program data into a file.
                    saveDataToTextFile("classData.txt", ";");
                    break;

                case "107":
                case "LPD":
                    // Load program data from a file.
                    loadDataFromTextFile("classData.txt", ";");
                    break;

                case "108":
                case "STK":
                    // View the remaining burger stock.
                    viewBurgerStock();
                    break;

                case "109":
                case "AFS":
                    // Add burgers to the stock.
                    addBurgerStock();
                    break;

                case "110":
                case "IFQ":
                    // Print income of each queue.
                    printIncomeOfEachQueue();
                    break;

                case "112":
                case "GUI":
                    // Launch the graphical user interface (GUI).
                    launch(args);
                    break;

                case "999":
                case "EXT":
                    // Exit the program.
                    System.out.println("Good bye! Come again!");
                    System.exit(0);
                    break;

                default:
                    // Invalid input
                    System.out.println("Invalid Input");
                    break;
            }
        }

    }

    static void printIncomeOfEachQueue() {
        System.out.println("Income of each queue:");
        for (int i = 0; i < queues.length; i++) {
            int income = queues[i].getIncome();
            System.out.println("Queue " + (i + 1) + ": " + income + " X " + "650 = " + (income * 650) + " (Rs)");
        }
    }

    /**
     * Loads program data from a text file with a specified separator.
     *
     * @param fileName  The name of the file to load the data from.
     * @param separator The separator used for separating data elements in the file.
     */
    private static void loadDataFromTextFile(String fileName, String separator) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String queueName = parts[0].trim();
                    String[] customersData = parts[1].trim().split(separator);

                    if (queueName.startsWith("Queue")) {
                        int queueIndex = Integer.parseInt(queueName.substring(5)) - 1;
                        FoodQueue queue = queues[queueIndex];
                        queue.getCustomers().clear();

                        for (int i = 0; i < customersData.length; i += 3) {
                            String firstName = customersData[i].trim();
                            String secondName = customersData[i + 1].trim();
                            int burgersRequired = Integer.parseInt(customersData[i + 2].trim());
                            queue.addCustomer(new Customer(firstName, secondName, burgersRequired));
                        }
                    } else if (queueName.equals("Total Burgers")) {
                        totalBurgers = Integer.parseInt(customersData[0].trim());
                    }
                }
            }
            System.out.println("Successfully loaded!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores program data into a text file with a specified separator.
     *
     * @param fileName  The name of the file to save the data to.
     * @param separator The separator to use for separating data elements in the
     *                  file.
     */
    private static void saveDataToTextFile(String fileName, String separator) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Save data for each queue to the file.
            for (int i = 0; i < queues.length; i++) {
                writer.write("Queue" + (i + 1) + ":");

                // Get the list of customers in the queue.
                List<Customer> customers = queues[i].getCustomers();

                // Write each customer's information in the queue to the file.
                for (Customer customer : customers) {
                    writer.write(separator + customer.getFirstName() + separator + customer.getSecondName()
                            + separator + customer.getBurgersRequired());
                }
                writer.newLine();
            }

            // Save the total number of burgers in stock to the file.
            writer.write("Total Burgers:" + separator + totalBurgers);
            writer.newLine();

            System.out.println("Successfully stored!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays all the queues with occupied and unoccupied positions.
     */
    static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("* Cashiers *");
        System.out.println("*****************");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                char symbol = 'X';
                if (j < queues.length && i < queues[j].getLength()) {
                    symbol = 'O';
                }
                System.out.print(symbol + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Adds burgers to the stock.
     */
    static void addBurgerStock() {
        System.out.println("Add burgers to the stock");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of burgers to add");
        int burgersToAdd = scanner.nextInt();

        if (burgersToAdd <= 0) {
            System.out.println("Invalid number of burgers. Please enter a positive value.");
            return;
        }

        totalBurgers += burgersToAdd;
        System.out.println(burgersToAdd + " burgers were added. Current total is " + totalBurgers);

    }

    /**
     * Displays the remaining burger stock.
     */
    static void viewBurgerStock() {
        System.out.println("The remaining burger stock is " + totalBurgers);
    }

    /**
     * Displays all empty queues.
     */
    static void viewAllEmptyQueues() {
        System.out.println("Empty Queues");
        checkEmptyQueues();
    }

    private static void checkEmptyQueues() {
        for (int i = 0; i < queues.length; i++) {
            FoodQueue queue = queues[i];
            if (queue.getLength() == 0) {
                System.out.println("Queue " + (i + 1) + " is empty.");
            }
        }
    }

    static void sortCustomerList() {
        System.out.println("The al customers A-Z");
        List<Customer> allCustomers = new ArrayList<>();

        for (FoodQueue queue : queues) {
            allCustomers.addAll(queue.getCustomers());
        }

        bubbleSort(allCustomers);

        int i = 1;
        for (Customer customer : allCustomers) {
            System.out.println(i + ") " + customer.getFirstName());
            i += 1;
        }
    }

    private static void bubbleSort(List<Customer> customers) {
        int n = customers.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (compareCustomers(customers.get(j), customers.get(j + 1)) > 0) {
                    // Swap customers[j] and customers[j + 1]
                    Customer temp = customers.get(j);
                    customers.set(j, customers.get(j + 1));
                    customers.set(j + 1, temp);
                    swapped = true;
                }
            }

            // If no two customers were swapped in the inner loop, the list is already
            // sorted
            if (!swapped) {
                break;
            }
        }
    }

    private static int compareCustomers(Customer c1, Customer c2) {
        int result = c1.getFirstName().compareTo(c2.getFirstName());
        if (result == 0) {
            result = c1.getSecondName().compareTo(c2.getSecondName());
        }
        return result;
    }

    private static void displayMenu() {
        System.out.println("===== Foodies Fave Food Center Menu =====");
        System.out.println("100 or VFQ: View all Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burgers Stock");
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("110 or IFQ: Print income of each queue.");
        System.out.println("112 or GUI: GUI of the application");
        System.out.println("999 or EXT: Exit the Program");
        System.out.println("========================================");
        System.out.println("Enter your choice: \n");
    }

    static void addCustomer() {
        System.out.println("Add a customer");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first name of customer");
        String firstName = scanner.nextLine();
        System.out.println("Enter the second name of customer");
        String secondName = scanner.nextLine();
        System.out.println("Enter the number of burgers required");
        int burgersRequired = scanner.nextInt();

        int shortestQueueIndex = findShortestQueueIndex();
        if (shortestQueueIndex != -1) {
            queues[shortestQueueIndex].addCustomer(new Customer(firstName, secondName, burgersRequired));
            totalBurgers -= burgersRequired;
            if (totalBurgers <= 10) {
                System.out.println("Warning: only 10 burgers left in stock");
            }
        }
    }

    static void removeCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the queue number");
        int queueNumber = scanner.nextInt() - 1;
        System.out.println("Enter index(position) of the queue");
        int index = scanner.nextInt();

        queues[queueNumber].removeCustomer(index - 1);

    }

    static void removeServedCustomer() {
        System.out.println("Remove a served customer");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the queue number (1, 2, or 3)");
        int queueNumber = scanner.nextInt();

        if (queueNumber < 1 || queueNumber > 3) {
            System.out.println("Invalid queue number");
            return;
        }

        FoodQueue queue = queues[queueNumber - 1];

        System.out.println("Enter the index of the served customer in queue " + queueNumber);
        int index = scanner.nextInt() - 1;

        if (index < 0 || index >= queue.getLength()) {
            System.out.println("Invalid customer index");
            return;
        }

        Customer removedCustomer = queue.removeServedCustomer(index);
        if (removedCustomer != null) {
            System.out.println("Served customer removed successfully.");
        } else {
            System.out.println("Failed to remove served customer.");
        }

    }

    private static int findShortestQueueIndex() {
        int shortestIndex = -1;
        int shortestLength = Integer.MAX_VALUE;

        for (int i = 0; i < queues.length; i++) {
            int length = queues[i].getLength();
            if (length < queues[i].getCapacity() && length < shortestLength) {
                shortestLength = length;
                shortestIndex = i;
            }
        }

        return shortestIndex;
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the root node (VBox) to hold the labels for each queue's customer
        // count.
        VBox root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Label labelTitle = new Label("Number of customers in Each Queue");
        labelTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        root.getChildren().add(labelTitle);
        // Create and add labels for each queue's customer count to the VBox.
        for (int queueNumber = 0; queueNumber < 3; queueNumber++) {
            Label label = new Label(
                    "Number of customers in Queue " + (queueNumber + 1) + ": " + queues[queueNumber].getLength());
            label.setStyle("-fx-font-size: 18px;");
            root.getChildren().add(label);
        }

        // Create the scene with the root node and set its dimensions
        Scene scene = new Scene(root, 400, 300);

        // Set the scene to the primary stage (window)
        primaryStage.setScene(scene);

        // Set the title of the window
        primaryStage.setTitle("Customer Queue Status");

        // Show the window
        primaryStage.show();
    }

}

/**
 * FoodQueue
 */
class FoodQueue {

    private List<Customer> customers;
    private int capacity;

    public FoodQueue(int capacity) {
        this.customers = new ArrayList<>();
        this.capacity = capacity;
    }

    public void addCustomer(Customer customer) {
        if (customers.size() < capacity) {
            customers.add(customer);
        } else {
            System.out.println("Queue is full. Cannot add more customers.");
        }
    }

    public void removeCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            customers.remove(index);
        } else {
            System.out.println("Invalid index to remove.");
        }
    }

    public Customer removeServedCustomer(int index) {
        if (index >= 0 && index < customers.size()) {
            Customer removedCustomer = customers.get(index);
            customers.remove(index);
            return removedCustomer;
        } else {
            System.out.println("Invalid customer index.");
            return null;
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public int getLength() {
        return customers.size();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getIncome() {
        int income = 0;
        for (Customer customer : customers) {
            income += customer.getBurgersRequired();
        }
        return income;
    }

}

/**
 * Customer
 */
class Customer {
    private String firstName;
    private String secondName;
    private int burgersRequired;

    public Customer(String firstName, String secondName, int burgersRequired) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.burgersRequired = burgersRequired;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getBurgersRequired() {
        return burgersRequired;
    }

}
