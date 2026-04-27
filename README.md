# Restaurant Order & Menu Simulation System

This project is a Java-based console application that simulates a restaurant ordering system using Object-Oriented Programming principles. The system generates structured order data, which is further used for analysis in Excel and visualization in Power BI.

---

## Tech Stack

- Java (Object-Oriented Programming)
- CSV File Handling
- Microsoft Excel
- Power BI
- Git and GitHub

---

## Key Features

- Menu-driven ordering system
- Add and remove items dynamically
- Automatic bill generation
- Discount and tax calculation
- Persistent Order ID using file storage
- CSV data generation for analysis
- Power BI dashboard integration
- Continuous ordering system (does not exit after one order)

---

## OOP Concepts Used

Encapsulation  
Private variables with getters and setters

Inheritance  
MenuItem as base class, extended by FoodItem and DrinkItem

Abstraction  
Abstract class MenuItem used to define common behavior

Polymorphism  
Method overriding for item description and billing logic

---

## How to Run

Compile the program:

javac *.java

Run the program:

java Main

---

## Data Flow

Java Application → CSV File → Excel → Power BI Dashboard

---

## Screenshots

### Bill Output
![Bill Output](screenshots/bill.png)

### Power BI Dashboard
![Dashboard](screenshots/dashboard.png)

### Data Table (CSV / Excel View)
![Data Table](screenshots/data.png)

---

## Power BI Insights

- Identifies top-selling items
- Shows revenue distribution
- Compares food and drink sales
- Displays total revenue
- Tracks discount impact

---

## Sample Output Features

- Real-time order summary
- Multi-item billing
- Discount and tax calculation
- Formatted receipt output
- Automatic CSV export

---

## Project Structure

RESTAURENT/
│── Customer.java  
│── MenuItem.java  
│── FoodItem.java  
│── DrinkItem.java  
│── Order.java  
│── Restaurant.java  
│── Main.java  
│── screenshots/  
│   ├── bill.png  
│   ├── dashboard.png  
│   ├── data.png  
│── README.md  
│── .gitignore  

---

## Notes

- Compiled files are excluded using .gitignore  
- Generated files such as CSV are not tracked  
- Order ID persists using file storage  

---

## Future Enhancements

- GUI-based system using JavaFX or Swing  
- Database integration  
- Online ordering system  
- Advanced analytics  

---

## Author

Santhosh R

---

## Final Outcome

This project demonstrates how Object-Oriented Programming concepts can be applied to build a structured system and how generated data can be analyzed and visualized using modern tools.