// ============================================================
//  FoodItem.java
//  Represents a food item on the menu.
//  OOP Concepts: INHERITANCE (extends MenuItem), POLYMORPHISM
// ============================================================

public class FoodItem extends MenuItem {

    // ---- Private field specific to FoodItem ----
    private boolean isVegetarian;

    // ---- Constructor ----
    public FoodItem(String name, double price, boolean isVegetarian) {
        super(name, price, "Food");       // Call parent (MenuItem) constructor
        this.isVegetarian = isVegetarian;
    }

    // ---- Getter ----
    public boolean isVegetarian() { return isVegetarian; }

    // ---- Polymorphism: Override abstract method from MenuItem ----
    @Override
    public String getDescription() {
        String tag = isVegetarian ? "[VEG]    " : "[NON-VEG]";
        return tag + " " + getName();
    }
}
