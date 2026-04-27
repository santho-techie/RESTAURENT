// ============================================================
//  Main.java
//  Entry point. Runs a persistent main-menu loop so the
//  operator can process multiple orders in one session.
//
//  HOW TO COMPILE & RUN
//  ─────────────────────
//  1. Place all 7 .java files in one folder.
//  2. Open a terminal in that folder.
//  3. Compile:  javac *.java
//  4. Run:      java Main
// ============================================================

import java.util.Scanner;

public class Main {

    // One shared Scanner for the whole application
    private static final Scanner scanner = new Scanner(System.in);

    // ---- Shared restaurant instance (menu loaded once) ----
    private static final Restaurant restaurant =
            new Restaurant("Spice Garden Restaurant");

    // ===========================================================
    //  MAIN – persistent main-menu loop
    // ===========================================================
    public static void main(String[] args) {
        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("  Enter choice: ", 1, 2);

            switch (choice) {
                case 1:
                    runOrderSession();   // full order flow
                    break;
                case 2:
                    running = false;
                    break;
            }
        }

        System.out.println("\n  Thank you for using Spice Garden POS System. Goodbye!\n");
        scanner.close();
    }

    // ===========================================================
    //  printWelcomeBanner
    // ===========================================================
    private static void printWelcomeBanner() {
        System.out.println("\n" + "=".repeat(65));
        System.out.println("         SPICE GARDEN RESTAURANT");
        System.out.println("         Order & Menu Simulation System");
        System.out.println("         Console POS — v2.0");
        System.out.println("=".repeat(65));
    }

    // ===========================================================
    //  printMainMenu
    // ===========================================================
    private static void printMainMenu() {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("          MAIN MENU");
        System.out.println("-".repeat(40));
        System.out.println("  [1]  Create New Order");
        System.out.println("  [2]  Exit");
        System.out.println("-".repeat(40));
    }

    // ===========================================================
    //  runOrderSession – one complete order from start to bill
    // ===========================================================
    private static void runOrderSession() {

        // Step 1: Collect customer details
        Customer customer = collectCustomerDetails();

        // Step 2: Create a fresh order
        Order order = new Order();

        System.out.println("\n  [Order #" + order.getOrderId() + " started]");

        // Step 3: Ordering loop
        boolean ordering = true;
        while (ordering) {

            // Display full menu
            restaurant.displayMenu();

            // Show running order summary
            restaurant.displayCurrentOrder(order);

            // Show ordering options
            printOrderMenu(restaurant.getMenuSize());

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("D")) {
                // ---- Done: generate bill ----
                if (order.isEmpty()) {
                    System.out.println("\n  [!] Your order is empty. Please add at least one item.");
                } else {
                    ordering = false;       // exit ordering loop
                }

            } else if (input.equalsIgnoreCase("R")) {
                // ---- Remove an item ----
                handleRemoveItem(order);

            } else if (input.equalsIgnoreCase("C")) {
                // ---- Cancel entire order ----
                System.out.print("\n  Cancel this order? All items will be lost. (Y/N): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                    System.out.println("\n  [✓] Order cancelled. Returning to main menu.");
                    return;
                }

            } else {
                // ---- Try to parse as item number ----
                try {
                    int itemNo = Integer.parseInt(input);
                    MenuItem selected = restaurant.getMenuItem(itemNo);
                    if (selected == null) {
                        System.out.println("\n  [!] Invalid item number. Please choose 1–"
                                + restaurant.getMenuSize() + ".");
                    } else {
                        System.out.printf("  Adding: %s  (Rs. %.2f each)%n",
                                selected.getName(), selected.getPrice());
                        int qty = readInt("  Enter quantity: ", 1, 50);
                        order.addItem(selected, qty);
                        System.out.printf("  [✓] Added %dx %s to your order.%n",
                                qty, selected.getName());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n  [!] Unrecognised input. "
                            + "Enter an item number, D, R, or C.");
                }
            }
        }

        // Step 4: Print the final bill
        restaurant.printBill(customer, order);

        // Step 5: Ask to save CSV
        System.out.print("\n  Save this order to CSV file? (Y/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
            restaurant.saveOrderToCSV(customer, order);
        }

        System.out.println("\n  Returning to main menu...");
    }

    // ===========================================================
    //  printOrderMenu – inline ordering options
    // ===========================================================
    private static void printOrderMenu(int menuSize) {
        System.out.println("\n  OPTIONS:");
        System.out.printf("  Enter item number [1–%d] to add it to your order%n", menuSize);
        System.out.println("  [R] Remove an item from order");
        System.out.println("  [D] Done – Generate Bill");
        System.out.println("  [C] Cancel entire order");
        System.out.print("\n  Your choice: ");
    }

    // ===========================================================
    //  collectCustomerDetails – gathers name, phone, table
    // ===========================================================
    private static Customer collectCustomerDetails() {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("  CUSTOMER DETAILS");
        System.out.println("-".repeat(40));

        System.out.print("  Customer name   : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Walk-In Guest";

        System.out.print("  Phone number    : ");
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty()) phone = "N/A";

        System.out.print("  Table number    : ");
        String table = scanner.nextLine().trim();
        if (table.isEmpty()) table = "1";

        System.out.printf("%n  Welcome, %s! (Table %s)%n", name, table);
        return new Customer(name, phone, table);
    }

    // ===========================================================
    //  handleRemoveItem – lets user pick an item to delete
    // ===========================================================
    private static void handleRemoveItem(Order order) {
        if (order.isEmpty()) {
            System.out.println("\n  [!] Nothing to remove – order is empty.");
            return;
        }

        System.out.println("\n" + "-".repeat(40));
        System.out.println("  REMOVE ITEM");
        System.out.println("-".repeat(40));

        int i = 1;
        for (OrderItem oi : order.getItems()) {
            System.out.printf("  [%d] %s x%d%n",
                    i++, oi.getMenuItem().getName(), oi.getQuantity());
        }
        System.out.println("  [0] Cancel");

        int choice = readInt("  Enter number to remove: ", 0, order.getItems().size());

        if (choice == 0) {
            System.out.println("  Removal cancelled.");
            return;
        }

        String removedName = order.getItems().get(choice - 1).getMenuItem().getName();
        if (order.removeItem(choice - 1)) {
            System.out.println("  [✓] '" + removedName + "' removed from order.");
        } else {
            System.out.println("  [!] Could not remove that item.");
        }
    }

    // ===========================================================
    //  readInt – safe integer input within [min, max]
    // ===========================================================
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("  [!] Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Invalid input – numbers only.");
            }
        }
    }
}
