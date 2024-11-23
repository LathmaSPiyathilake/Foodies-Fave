import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodiesFave {
    // Three queues to hold customer names
    static List<String> Q1 = new ArrayList<String>();
    static List<String> Q2 = new ArrayList<String>();
    static List<String> Q3 = new ArrayList<String>();

    // Total number of burgers available
    static int totalBurgers = 50;

    public static void main(String[] args) {
        // Infinite loop to keep the program running until the user chooses to exit.
        while (true) {
            // Display the menu options and get the user's choice.
            displayMenu();
            Scanner scanner = new Scanner(System.in);
            String inputKey = scanner.nextLine().toUpperCase();

            // Handle the user's choice using switch-case
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

    static void addCustomer() {
        System.out.println("Add a customer");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of customer");
        String customerName = scanner.nextLine();
        System.out.println("Enter the queue: 1/ 2/ 3");
        int queueNumber = scanner.nextInt();

        // Adding customers to the selected queue with limits
        if (queueNumber == 1) {
            if (Q1.size() != 2) {
                Q1.add(customerName);
                totalBurgers -= 5;
                System.out.println(totalBurgers);
                if (totalBurgers == 10) {
                    System.out.println("Warning: only 10 burgers in the stock");
                    return;
                }
            } else {
                System.out.println("Q1 is filled");
            }
        }
        if (queueNumber == 2) {
            if (Q2.size() != 3) {
                Q2.add(customerName);
                totalBurgers -= 5;
                System.out.println(totalBurgers);
                if (totalBurgers == 10) {
                    System.out.println("Warning: only 10 burgers in the stock");
                    return;
                }
            } else {
                System.out.println("Q2 is filled");
            }
        }
        if (queueNumber == 3) {
            if (Q3.size() != 5) {
                Q3.add(customerName);
                totalBurgers -= 5;
                System.out.println(totalBurgers);
                if (totalBurgers == 10) {
                    System.out.println("Warning: only 10 burgers in the stock");
                    return;
                }
            } else {
                System.out.println("Q3 is filled");
            }
        }

    }

    static void removeCustomer() {
        System.out.println("Remove a customer");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the queue: 1/ 2/ 3");
        int queueNumber = scanner.nextInt();

        System.out.println("Enter the index of queue " + queueNumber);
        int index = scanner.nextInt() - 1;

        // Removing a customer from the selected queue
        if (queueNumber == 1) {
            if (index >= 0 && index < Q1.size()) {
                Q1.remove(index);
                totalBurgers += 5;
            } else {
                System.out.println("Invalid index or the Queue is already empty.");
            }
        }
        if (queueNumber == 2) {
            if (index >= 0 && index < Q2.size()) {
                Q2.remove(index);
                totalBurgers += 5;
            } else {
                System.out.println("Invalid index or the Queue is already empty.");
            }
        }
        if (queueNumber == 3) {
            if (index >= 0 && index < Q3.size()) {
                Q3.remove(index);
                totalBurgers += 5;
            } else {
                System.out.println("Invalid index or the Queue is already empty.");
            }
        }
    }

    static void removeServedCustomer() {
        System.out.println("Remove a customer");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the queue: 1/ 2/ 3");
            int queueNumber = scanner.nextInt();
            if (queueNumber == 1) {
                System.out.println("Enter the index of queue 1: 1/ 2");
            }
            if (queueNumber == 2) {
                System.out.println("Enter the index of queue 2: 1/ 2/ 3");
            }
            if (queueNumber == 3) {
                System.out.println("Enter the index of queue 3: 1/ 2/ 3/ 4/ 5");
            }
            int index = scanner.nextInt() - 1;

            // Removing a served customer from the selected queue
            if (queueNumber == 1) {
                Q1.remove(index);
            }
            if (queueNumber == 2) {
                Q2.remove(index);
            }
            if (queueNumber == 3) {
                Q3.remove(index);
            }
        }
    }

    static void viewBurgerStock() {
        System.out.println("The remaining burger stock is " + totalBurgers);
    }

    static void addBurgerStock() {
        System.out.println("Remove a customer");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add the amount of burgers");
        int addBurger = scanner.nextInt();
        totalBurgers += addBurger;
        System.out.println(addBurger + " of burgers were added. Current total is " + totalBurgers);

    }

    static void sortCustomerList() {
        // Combining all queues and sorting customers alphabetically
        System.out.println("Customer list A - Z");
        List<String> combinedList = new ArrayList<>();
        combinedList.addAll(Q1);
        combinedList.addAll(Q2);
        combinedList.addAll(Q3);

        String[] combinedArray = combinedList.toArray(new String[0]);
        arraySorter(combinedArray);
        int i = 1;
        for (String string : combinedArray) {
            System.out.println(i + ") " + string);
            i += 1;
        }
    }

    private static void arraySorter(String[] array) {
        // Bubble sort implementation for sorting an array of strings
        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    // Swap array[j] and array[j + 1]
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were swapped in the inner loop, the array is already
            // sorted
            if (!swapped) {
                break;
            }
        }
    }

    static void viewAllQueues() {
        // Display all the queues with their customers

        System.out.println("*****************");
        System.out.println("* Cashiers *");
        System.out.println("*****************");

        int maxQueueSize = Math.max(Math.max(Q1.size(), Q2.size()), Q3.size());

        // Loop through the queues and print the customers in the specified format
        for (int i = 0; i < maxQueueSize; i++) {
            // Queue 1
            if (i < Q1.size()) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }

            // Queue 2
            if (i < Q2.size()) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }

            // Queue 3
            if (i < Q3.size()) {
                System.out.print("O");
            } else {
                System.out.print("X");
            }

            System.out.println(); // Move to the next line for the next row
        }

    }

    static void viewAllEmptyQueues() {
        // Display the queues that are currently empty
        System.out.println("Empty Queues");
        System.out.print((Q1.isEmpty() ? "Queue 1 \n" : ""));
        System.out.print((Q2.isEmpty() ? "Queue 2 \n" : ""));
        System.out.print((Q3.isEmpty() ? "Queue 3 \n" : ""));
    }

    private static void saveDataToTextFile(String fileName, String separator) {
        // Save data from queues to a text file with the specified separator
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Q1:");
            for (String element : Q1) {
                writer.write(separator + element);
            }
            writer.newLine();

            writer.write("Q2:");
            for (String element : Q2) {
                writer.write(separator + element);
            }
            writer.newLine();

            writer.write("Q3:");
            for (String element : Q3) {
                writer.write(separator + element);
            }
            writer.newLine();
            System.out.println("Successfully stored!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadDataFromTextFile(String fileName, String separator) {
        // Load data from a text file and populate the queues
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String listName = parts[0].trim();
                    String[] elements = parts[1].trim().split(separator);
                    List<String> list = getListByName(listName);
                    for (String element : elements) {
                        list.add(element);
                    }
                }
            }
            System.out.println("Successfully loaded!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getListByName(String name) {
        // Get the appropriate list based on the given name
        if ("Q1".equals(name)) {
            return Q1;
        } else if ("Q2".equals(name)) {
            return Q2;
        } else if ("Q3".equals(name)) {
            return Q3;
        } else {
            throw new IllegalArgumentException("Invalid list name: " + name);
        }
    }

    private static void displayMenu() {
        // Display the menu options
        System.out.println("===== Foodies Fave Food Center Menu =====");
        System.out.println("100 or VFQ: View all Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue");
        System.out.println("104 or PCQ: Remove a served customer");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burgers Stock");
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("999 or EXT: Exit the Program");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");
    }

}
