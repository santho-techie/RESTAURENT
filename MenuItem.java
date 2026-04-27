// ============================================================
//  MenuItem.java
//  Abstract base class for all menu items.
//  OOP Concepts: ABSTRACTION, ENCAPSULATION
// ============================================================

public abstract class MenuItem {

    // ---- Private fields (Encapsulation) ----
    private String name;
    private double price;
    private String category;

    // ---- Constructor ----
    public MenuItem(String name, double price, String category) {
        this.name     = name;
        this.price    = price;
        this.category = category;
    }

    // ---- Getters & Setters ----
    public String getName()              { return name; }
    public void   setName(String name)   { this.name = name; }

    public double getPrice()             { return price; }
    public void   setPrice(double price) { this.price = price; }

    public String getCategory()               { return category; }
    public void   setCategory(String cat)     { this.category = cat; }

    // ---- Abstract method (Abstraction) ----
    // Every subclass MUST provide its own description.
    public abstract String getDescription();

    // ---- Standard display string ----
    @Override
    public String toString() {
        return String.format("%-28s %-10s Rs. %7.2f", name, category, price);
    }
}
