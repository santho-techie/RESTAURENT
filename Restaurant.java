// ============================================================
//  Restaurant.java
//  Manages the predefined menu, prints bills, and saves CSV.
//  OOP Concepts: ENCAPSULATION, POLYMORPHISM (via MenuItem list)
// ============================================================

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Restaurant {

    // ---- Private fields ----
    private String             restaurantName;
    private ArrayList<MenuItem> menu;           // holds FoodItem & DrinkItem objects
    private static final String CSV_FILE        = "order_history.csv";
    private static final String BORDER          = "=".repeat(65);
    private static final String DIVIDER         = "-".repeat(65);

    // ---- Constructor ----
    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
        this.menu           = new ArrayList<>();
        loadMenu();
    }

    // ----------------------------------------------------------
    //  loadMenu – populates the predefined menu
    // ----------------------------------------------------------
    private void loadMenu() {
        // ---- Vegetarian Food ----
        menu.add(new FoodItem("Paneer Butter Masala",   220.00, true));
        menu.add(new FoodItem("Dal Tadka",              150.00, true));
        menu.add(new FoodItem("Veg Biryani",            180.00, true));
        menu.add(new FoodItem("Aloo Paratha (2 pcs)",   100.00, true));
        menu.add(new FoodItem("Masala Dosa",            120.00, true));
        menu.add(new FoodItem("Palak Paneer",           200.00, true));
        menu.add(new FoodItem("Chole Bhature",          130.00, true));

        // ---- Non-Vegetarian Food ----
        menu.add(new FoodItem("Chicken Biryani",        280.00, false));
        menu.add(new FoodItem("Butter Chicken",         300.00, false));
        menu.add(new FoodItem("Fish Curry",             260.00, false));
        menu.add(new FoodItem("Mutton Rogan Josh",      350.00, false));
        menu.add(new FoodItem("Prawn Masala",           320.00, false));

        // ---- Cold Drinks ----
        menu.add(new DrinkItem("Mango Lassi",            80.00, true));
        menu.add(new DrinkItem("Cold Coffee",            90.00, true));
        menu.add(new DrinkItem("Fresh Lime Soda",        60.00, true));
        menu.add(new DrinkItem("Watermelon Juice",       70.00, true));

        // ---- Hot Drinks ----
        menu.add(new DrinkItem("Masala Chai",            40.00, false));
        menu.add(new DrinkItem("Filter Coffee",          50.00, false));
        menu.add(new DrinkItem("Hot Chocolate",          80.00, false));
    }

    // ----------------------------------------------------------
    //  displayMenu – prints the full menu to console
    // ----------------------------------------------------------
    public void displayMenu() {
        System.out.println("\n" + BORDER);
        System.out.printf("   %-63s%n", centerText(restaurantName + " — Menu", 63));
        System.out.println(BORDER);
        System.out.printf("  %-4s %-40s %s%n", "No.", "Item", "Price");
        System.out.println(DIVIDER);

        String lastCategory = "";
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);

            // Print category heading when it changes
            if (!item.getCategory().equals(lastCategory)) {
                System.out.printf("%n  [ %s ]%n", item.getCategory().toUpperCase());
                lastCategory = item.getCategory();
            }

            // getDescription() triggers POLYMORPHISM (FoodItem vs DrinkItem)
            System.out.printf("  %-4d %-40s Rs. %6.2f%n",
                    (i + 1), item.getDescription(), item.getPrice());
        }

        System.out.println("\n" + DIVIDER);
        System.out.println("  * Orders above Rs. 1000 receive a 10% discount!");
        System.out.println(BORDER);
    }

    // ----------------------------------------------------------
    //  displayCurrentOrder – shows a running order summary
    // ----------------------------------------------------------
    public void displayCurrentOrder(Order order) {
        if (order.isEmpty()) {
            System.out.println("\n  (Your order is currently empty)");
            return;
        }
        System.out.println("\n" + DIVIDER);
        System.out.printf("  Current Order  [Order #%d]%n", order.getOrderId());
        System.out.println(DIVIDER);
        int i = 1;
        for (OrderItem oi : order.getItems()) {
            System.out.printf("  [%2d] %s%n", i++, oi.toString());
        }
        System.out.println(DIVIDER);
        order.applyAutoDiscount();
        System.out.printf("  Subtotal            :  Rs. %8.2f%n", order.getSubtotal());
        if (order.getDiscountRate() > 0) {
            System.out.printf("  Discount (%.0f%%)       :  Rs. %8.2f%n",
                    order.getDiscountRate() * 100, order.getDiscountAmount());
        }
        System.out.printf("  Est. GST (5%%)       :  Rs. %8.2f%n", order.getTaxAmount());
        System.out.printf("  Est. Grand Total    :  Rs. %8.2f%n", order.getGrandTotal());
        System.out.println(DIVIDER);
    }

    // ----------------------------------------------------------
    //  printBill – final formatted receipt
    // ----------------------------------------------------------
    public void printBill(Customer customer, Order order) {
        String ts = getCurrentDateTime();
        order.setTimestamp(ts);
        order.applyAutoDiscount();

        System.out.println("\n" + BORDER);
        System.out.println(centerText("*** RECEIPT / BILL ***", 65));
        System.out.println(BORDER);
        System.out.printf("  Restaurant   :  %s%n", restaurantName);
        System.out.printf("  %s%n", customer.toString());
        System.out.printf("  Order ID     :  #%d%n", order.getOrderId());
        System.out.printf("  Date & Time  :  %s%n", ts);
        System.out.println(DIVIDER);
        System.out.printf("  %-28s %5s    %10s%n", "Item", "Qty", "Amount");
        System.out.println(DIVIDER);

        for (OrderItem oi : order.getItems()) {
            System.out.printf("  %-28s x%-4d  Rs. %8.2f%n",
                    oi.getMenuItem().getName(),
                    oi.getQuantity(),
                    oi.getSubtotal());
        }

        System.out.println(DIVIDER);
        System.out.printf("  %-35s Rs. %8.2f%n", "Subtotal:",            order.getSubtotal());

        if (order.getDiscountRate() > 0) {
            System.out.printf("  %-35s Rs. %8.2f%n",
                    String.format("Loyalty Discount (%.0f%%):", order.getDiscountRate() * 100),
                    order.getDiscountAmount());
            System.out.printf("  %-35s Rs. %8.2f%n", "After Discount:",  order.getAfterDiscount());
        }

        System.out.printf("  %-35s Rs. %8.2f%n", "GST (5%):",            order.getTaxAmount());
        System.out.println(BORDER);
        System.out.printf("  %-35s Rs. %8.2f%n", "GRAND TOTAL:",         order.getGrandTotal());
        System.out.println(BORDER);
        System.out.println(centerText("Thank you for dining at " + restaurantName + "!", 65));
        System.out.println(centerText("We hope to see you again soon!", 65));
        System.out.println(BORDER);
    }

    // ----------------------------------------------------------
    //  saveOrderToCSV – appends order rows to CSV (File Handling)
    //  Phone stored as text ("phone") to prevent Excel scientific notation
    // ----------------------------------------------------------
    public void saveOrderToCSV(Customer customer, Order order) {
        File csvFile = new File(CSV_FILE);
        boolean isNew = !csvFile.exists();

        try (FileWriter fw = new FileWriter(csvFile, true)) { // append = true

            // Write header only when creating a new file
            if (isNew) {
                fw.write("OrderID,DateTime,CustomerName,PhoneNumber,TableNumber,"
                       + "ItemName,Category,Quantity,UnitPrice,Subtotal,"
                       + "DiscountRate,Tax,GrandTotal\n");
            }

            String ts         = order.getTimestamp().isEmpty() ? getCurrentDateTime() : order.getTimestamp();
            double tax        = order.getTaxAmount();
            double grandTotal = order.getGrandTotal();
            double discRate   = order.getDiscountRate();

            for (OrderItem oi : order.getItems()) {
                fw.write(String.format(
                    "%d,%s,%s,\"%s\",%s,%s,%s,%d,%.2f,%.2f,%.0f%%,%.2f,%.2f%n",
                    order.getOrderId(),
                    ts,
                    escapeCsv(customer.getName()),
                    customer.getPhone(),          // quoted → Excel treats as text
                    escapeCsv(customer.getTableNumber()),
                    escapeCsv(oi.getMenuItem().getName()),
                    escapeCsv(oi.getMenuItem().getCategory()),
                    oi.getQuantity(),
                    oi.getMenuItem().getPrice(),
                    oi.getSubtotal(),
                    discRate * 100,
                    tax,
                    grandTotal
                ));
            }

            System.out.println("\n  [✓] Order saved to '" + CSV_FILE + "' successfully.");

        } catch (IOException e) {
            System.out.println("\n  [!] Could not save order: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------
    //  Helpers
    // ----------------------------------------------------------

    /** Returns a menu item by its 1-based display number. */
    public MenuItem getMenuItem(int number) {
        if (number >= 1 && number <= menu.size()) return menu.get(number - 1);
        return null;
    }

    public int    getMenuSize()        { return menu.size(); }
    public String getRestaurantName()  { return restaurantName; }

    /** Wraps a value in quotes if it contains a comma. */
    private String escapeCsv(String value) {
        if (value.contains(",")) return "\"" + value + "\"";
        return value;
    }

    /** Returns the current date/time as yyyy-MM-dd HH:mm:ss */
    private String getCurrentDateTime() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /** Centers a string within a given width. */
    private String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text;
    }
}
