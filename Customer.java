// ============================================================
//  Customer.java
//  Stores customer details for an order.
//  OOP Concept: ENCAPSULATION
// ============================================================

public class Customer {

    // ---- Private fields ----
    private String name;
    private String phone;
    private String tableNumber;

    // ---- Constructor ----
    public Customer(String name, String phone, String tableNumber) {
        this.name        = name;
        this.phone       = phone;
        this.tableNumber = tableNumber;
    }

    // ---- Getters & Setters ----
    public String getName()                    { return name; }
    public void   setName(String name)         { this.name = name; }

    public String getPhone()                   { return phone; }
    public void   setPhone(String phone)       { this.phone = phone; }

    public String getTableNumber()             { return tableNumber; }
    public void   setTableNumber(String table) { this.tableNumber = table; }

    // ---- Summary line used in bill header ----
    @Override
    public String toString() {
        return String.format("Name : %-20s  Phone : %s  Table : %s",
                name, phone, tableNumber);
    }
}
