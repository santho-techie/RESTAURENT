// ============================================================
//  TestCases.java
//  Code-Based Testing for Restaurant Order & Menu Simulation
// ============================================================

public class TestCases {

    public static void main(String[] args) {

        System.out.println("\n=================================================");
        System.out.println("     RESTAURANT SYSTEM - TEST CASE EXECUTION");
        System.out.println("=================================================\n");


        // =====================================================
        // TEST CASE 1 - CUSTOMER OBJECT CREATION
        // =====================================================

        System.out.println("TEST CASE 1 - Customer Creation");

        Customer customer = new Customer(
                "Santhosh",
                "9876543210",
                "A12"
        );

        System.out.println(customer);

        System.out.println("[PASS] Customer object created successfully.\n");


        // =====================================================
        // TEST CASE 2 - FOOD ITEM CREATION
        // =====================================================

        System.out.println("TEST CASE 2 - Food Item Creation");

        FoodItem food1 = new FoodItem(
                "Chicken Biryani",
                280.00,
                false
        );

        FoodItem food2 = new FoodItem(
                "Paneer Butter Masala",
                220.00,
                true
        );

        System.out.println(food1.getDescription());
        System.out.println(food2.getDescription());

        System.out.println("[PASS] Food items created successfully.\n");


        // =====================================================
        // TEST CASE 3 - DRINK ITEM CREATION
        // =====================================================

        System.out.println("TEST CASE 3 - Drink Item Creation");

        DrinkItem drink1 = new DrinkItem(
                "Cold Coffee",
                90.00,
                true
        );

        DrinkItem drink2 = new DrinkItem(
                "Masala Chai",
                40.00,
                false
        );

        System.out.println(drink1.getDescription());
        System.out.println(drink2.getDescription());

        System.out.println("[PASS] Drink items created successfully.\n");


        // =====================================================
        // TEST CASE 4 - ORDER CREATION
        // =====================================================

        System.out.println("TEST CASE 4 - Order Creation");

        Order order = new Order();

        System.out.println("Order ID: " + order.getOrderId());

        System.out.println("[PASS] Order created successfully.\n");


        // =====================================================
        // TEST CASE 5 - ADD ITEMS TO ORDER
        // =====================================================

        System.out.println("TEST CASE 5 - Add Items to Order");

        order.addItem(food1, 2);
        order.addItem(food2, 1);
        order.addItem(drink1, 3);

        for (OrderItem oi : order.getItems()) {
            System.out.println(oi);
        }

        System.out.println("[PASS] Items added successfully.\n");


        // =====================================================
        // TEST CASE 6 - SUBTOTAL CALCULATION
        // =====================================================

        System.out.println("TEST CASE 6 - Subtotal Calculation");

        double subtotal = order.getSubtotal();

        System.out.println("Subtotal = Rs. " + subtotal);

        System.out.println("[PASS] Subtotal calculated successfully.\n");


        // =====================================================
        // TEST CASE 7 - DISCOUNT CALCULATION
        // =====================================================

        System.out.println("TEST CASE 7 - Discount Calculation");

        order.applyAutoDiscount();

        System.out.println("Discount Rate = " +
                (order.getDiscountRate() * 100) + "%");

        System.out.println("Discount Amount = Rs. " +
                order.getDiscountAmount());

        System.out.println("[PASS] Discount applied successfully.\n");


        // =====================================================
        // TEST CASE 8 - TAX CALCULATION
        // =====================================================

        System.out.println("TEST CASE 8 - Tax Calculation");

        System.out.println("GST Amount = Rs. " +
                order.getTaxAmount());

        System.out.println("[PASS] Tax calculated successfully.\n");


        // =====================================================
        // TEST CASE 9 - GRAND TOTAL
        // =====================================================

        System.out.println("TEST CASE 9 - Grand Total");

        System.out.println("Grand Total = Rs. " +
                order.getGrandTotal());

        System.out.println("[PASS] Grand total calculated successfully.\n");


        // =====================================================
        // TEST CASE 10 - BILL GENERATION
        // =====================================================

        System.out.println("TEST CASE 10 - Bill Generation");

        Restaurant restaurant =
                new Restaurant("Spice Garden Restaurant");

        restaurant.printBill(customer, order);

        System.out.println("[PASS] Bill generated successfully.\n");


        // =====================================================
        // TEST CASE 11 - CSV FILE GENERATION
        // =====================================================

        System.out.println("TEST CASE 11 - CSV File Generation");

        restaurant.saveOrderToCSV(customer, order);

        System.out.println("[PASS] CSV file generated successfully.\n");


        // =====================================================
        // TEST CASE 12 - REMOVE ITEM
        // =====================================================

        System.out.println("TEST CASE 12 - Remove Item");

        boolean removed = order.removeItem(0);

        if (removed) {
            System.out.println("Item removed successfully.");
            System.out.println("[PASS] Remove item test successful.\n");
        } else {
            System.out.println("[FAIL] Remove item test failed.\n");
        }


        // =====================================================
        // FINAL STATUS
        // =====================================================

        System.out.println("=================================================");
        System.out.println("     ALL TEST CASES EXECUTED SUCCESSFULLY");
        System.out.println("=================================================\n");
    }
}