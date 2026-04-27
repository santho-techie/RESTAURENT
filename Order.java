// ============================================================
//  Order.java
//  Contains two classes:
//    OrderItem – pairs a MenuItem with a quantity
//    Order     – the full order: list of items, totals, discount
//  OOP Concept: ENCAPSULATION
// ============================================================

import java.util.ArrayList;

// ------------------------------------------------------------
//  OrderItem  –  one line on the bill
// ------------------------------------------------------------
class OrderItem {

    private MenuItem menuItem;
    private int      quantity;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    // Getters
    public MenuItem getMenuItem() { return menuItem; }
    public int      getQuantity() { return quantity; }
    public void     setQuantity(int quantity) { this.quantity = quantity; }

    // Line total
    public double getSubtotal() {
        return menuItem.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-28s x%-3d  Rs. %8.2f",
                menuItem.getName(), quantity, getSubtotal());
    }
}


// ------------------------------------------------------------
//  Order  –  a complete customer order
// ------------------------------------------------------------
public class Order {

    // Static counter shared across all orders in the session
    private static int orderCounter = loadLastOrderId();

    // ---- Private fields ----
    private int               orderId;
    private ArrayList<OrderItem> items;
    private double            taxRate      = 0.05;  // 5% GST
    private double            discountRate = 0.00;  // applied if eligible
    private String            timestamp;            // set at bill-print time

    // ---- Constructor ----
    public Order() {
        this.orderId   = ++orderCounter;
        this.items     = new ArrayList<>();
        this.timestamp = "";
        saveLastOrderId();
    }

    // ---- Add an item (merge if already present) ----
    public void addItem(MenuItem menuItem, int qty) {
        for (OrderItem oi : items) {
            if (oi.getMenuItem().getName().equalsIgnoreCase(menuItem.getName())) {
                oi.setQuantity(oi.getQuantity() + qty);
                return;
            }
        }
        items.add(new OrderItem(menuItem, qty));
    }

    // ---- Remove item by zero-based index; returns true on success ----
    public boolean removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            return true;
        }
        return false;
    }

    // ---- Financial calculations ----
    public double getSubtotal() {
        double total = 0;
        for (OrderItem oi : items) total += oi.getSubtotal();
        return total;
    }

    public double getDiscountAmount() { return getSubtotal() * discountRate; }
    public double getAfterDiscount()  { return getSubtotal() - getDiscountAmount(); }
    public double getTaxAmount()      { return getAfterDiscount() * taxRate; }
    public double getGrandTotal()     { return getAfterDiscount() + getTaxAmount(); }

    // ---- Automatic discount: 10% if subtotal >= 1000 ----
    public void applyAutoDiscount() {
        discountRate = (getSubtotal() >= 1000.00) ? 0.10 : 0.00;
    }

    // ---- Getters ----
    public int                  getOrderId()      { return orderId; }
    public ArrayList<OrderItem> getItems()        { return items; }
    public boolean              isEmpty()         { return items.isEmpty(); }
    public double               getTaxRate()      { return taxRate; }
    public double               getDiscountRate() { return discountRate; }
    public String               getTimestamp()    { return timestamp; }
    public void                 setTimestamp(String ts) { this.timestamp = ts; }

    private static int loadLastOrderId() {
    try {
        java.io.File file = new java.io.File("last_order_id.txt");

        if (!file.exists()) return 1000;

        java.util.Scanner sc = new java.util.Scanner(file);
        int lastId = sc.nextInt();
        sc.close();

        return lastId;

    } catch (Exception e) {
        return 1000;
    }
}

private void saveLastOrderId() {
    try {
        java.io.FileWriter fw = new java.io.FileWriter("last_order_id.txt");
        fw.write(String.valueOf(orderId));
        fw.close();
    } catch (Exception e) {
        System.out.println("Error saving order ID");
    }
}
}
