// ============================================================
//  DrinkItem.java
//  Represents a beverage on the menu.
//  OOP Concepts: INHERITANCE (extends MenuItem), POLYMORPHISM
// ============================================================

public class DrinkItem extends MenuItem {

    // ---- Private field specific to DrinkItem ----
    private boolean isCold;

    // ---- Constructor ----
    public DrinkItem(String name, double price, boolean isCold) {
        super(name, price, "Drink");   // Call parent (MenuItem) constructor
        this.isCold = isCold;
    }

    // ---- Getter ----
    public boolean isCold() { return isCold; }

    // ---- Polymorphism: Override abstract method from MenuItem ----
    @Override
    public String getDescription() {
        String tag = isCold ? "[COLD]   " : "[HOT]    ";
        return tag + " " + getName();
    }
}
