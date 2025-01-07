// CoffeeShopManagementSystem.java
package com.cba.midweek;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CoffeeShopManagementSystem {

    private static List<Customer> customers = new ArrayList<>();
    private static List<MenuItem> menuItems = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static ConcurrentOrderProcessor orderProcessor = new ConcurrentOrderProcessor(2);
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static MenuItemDAO menuItemDAO = new MenuItemDAO();
    private static OrderDAO orderDAO = new OrderDAO();
    private static OrderProducer orderProducer;
    private static OrderConsumer orderConsumer;

    public static void main(String[] args) {
        try {
            //menuItemDAO.insertSampleData();
            //initializeMenu();
            menuItems = menuItemDAO.loadMenuItems();
            System.out.println("Menu items loaded: " + menuItems.size());
            Logger.log("Menu items loaded: " + menuItems.size());
        } catch (SQLException e) {
            System.out.println("Failed to load menu items: " + e.getMessage());
            Logger.log("Failed to load menu items: " + e.getMessage());
            return;
        }
        orderConsumer = new OrderConsumer(orderProcessor);
        orderConsumer.start();

        while (true) {
            System.out.println("Welcome to the Coffee Shop Management System ");
            System.out.println("1. Register");
            System.out.println("2. Log in");
            System.out.println("3. Exit");
            System.out.println("Please Select The Options from Above");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    Customer customer = loginCustomer();
                    if (customer != null) {
                        try {
                            customerMenu(customer);
                        } catch (SQLException e) {
                            System.out.println("Customer Id Not Found  " + e.getMessage());
                            Logger.log("Customer Id Not Found " + e.getMessage());
                            return;
                        }
                    }
                    break;
                case 3:
                    //if (orderProducer != null) {
                    //    orderProducer.stopProducer();
                   // }
                    orderProducer.stopProducer();
                    //if (orderConsumer != null) {
                      //  orderConsumer.stopConsumer();
                    //}
                    orderConsumer.stopConsumer();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    Logger.log("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeMenu() throws SQLException {
        String sql = "INSERT INTO MenuItems (itemID, name, price, discount) VALUES (NEXT VALUE FOR item_id_seq, ?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        Statement stmt = conn.createStatement();

        try (Connection conn1 = DatabaseConnection.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(sql)) {
            stmt1.setString(1, "Burger");
            stmt1.setDouble(2, 5.99);
            stmt1.setDouble(3, 0.0);
            stmt1.executeUpdate();

            stmt1.setString(1, "Pizza");
            stmt1.setDouble(2, 8.99);
            stmt1.setDouble(3, 0.0);
            stmt1.executeUpdate();

            stmt1.setString(1, "Pasta");
            stmt1.setDouble(2, 7.99);
            stmt1.setDouble(3, 10.0);
            stmt1.executeUpdate();

            stmt1.setString(1, "Espresso");
            stmt1.setDouble(2, 2.50);
            stmt1.setDouble(3, 0.0);
            stmt1.executeUpdate();

            stmt1.setString(1, "Latte");
            stmt1.setDouble(2, 3.50);
            stmt1.setDouble(3, 0.0);
            stmt1.executeUpdate();

            stmt1.setString(1, "Cappuccino");
            stmt1.setDouble(2, 4.00);
            stmt1.setDouble(3, 0.0);
            stmt1.executeUpdate();

            System.out.println("Sample data inserted into MenuItems table.");
            Logger.log("Menu initialized with " + menuItems.size() + " items.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            throw e;
        }finally {
            stmt.close();
            conn.close();
        }
    }

    private static void registerCustomer() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        Customer customer = new Customer(name, customers.size() + 1);
        try {
            customerDAO.registerCustomer(customer);
            customers.add(customer);
            System.out.println("Registration successful. Your customer ID is " + customer.getCustomerID());
            Logger.log("Registration successful. Your customer ID is " + customer.getCustomerID());
        } catch (SQLException e) {
            System.out.println("Failed to register customer: " + e.getMessage());
            Logger.log("Failed to register customer: " + e.getMessage());
        }
    }

    private static Customer loginCustomer() {
        System.out.print("Enter your customer ID: ");
        int customerID = scanner.nextInt();
        scanner.nextLine(); // consume newline
        try {
            Customer customer = customerDAO.loginCustomer(customerID);
            if (customer != null) {
                System.out.println("Login successful. Welcome " + customer.getName());
                Logger.log("Login successful. Welcome " + customer.getName());
                return customer;
            } else {
                System.out.println("Customer ID not found. Please register first.");
                Logger.log("Customer ID not found. Please register first.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to login: " + e.getMessage());
            Logger.log("Failed to login: " + e.getMessage());
        }
        return null;
    }

    private static void customerMenu(Customer customer) throws SQLException {
        if (orderProducer != null) {
            orderProducer.stopProducer();
        }
        orderProducer = new OrderProducer(orderProcessor, customer);
        orderProducer.start();
        while (true) {
            System.out.println("1. View available menu items");
            System.out.println("2. Place an order");
            System.out.println("3. View order summary");
            System.out.println("4. View order summary by date range");
            System.out.println("5. View order summary by total amount range");
            System.out.println("6. Apply discounts to specialty items");
            System.out.println("7. Log out");
            System.out.println("Please Select The Options from Above");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewMenuItems();
                    break;
                case 2:
                    placeOrder(customer);
                    break;
                case 3:
                    viewOrderSummary(customer);
                    break;
                case 4:
                    System.out.println("Enter start date (yyyy-mm-dd): ");
                    String startDateStr = scanner.nextLine();
                    LocalDate startDate = LocalDate.parse(startDateStr);
                    System.out.println("Enter end date (yyyy-mm-dd): ");
                    String endDateStr = scanner.nextLine();
                    LocalDate endDate = LocalDate.parse(endDateStr);
                    displayOrderSummaryByDate(customer, startDate, endDate);
                    break;
                case 5:
                    System.out.println("Enter minimum total amount: ");
                    double minAmount = scanner.nextDouble();
                    System.out.println("Enter maximum total amount: ");
                    double maxAmount = scanner.nextDouble();
                    displayOrderSummaryByTotalAmount(customer, minAmount, maxAmount);
                    break;
                case 6:
                    applyDiscountsToSpecialtyItems();
                    break;
                case 7:
                    orderProducer.stopProducer();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewMenuItems() {
        System.out.println("Available menu items:");
        for (MenuItem item : menuItems) {
            System.out.println(item.getItemID() + ". " + item.getName() + ": $" + item.getPrice());
        }
    }

    private static void placeOrder(Customer customer) throws SQLException {
        Order order = new Order(orders.size() + 1, customer);
        while (true) {
            System.out.println("Enter item ID to add to order (0 to finish): ");
            Logger.log("Enter item ID to add to order (0 to finish): ");
            int itemID = scanner.nextInt();
            scanner.nextLine();// consume newline
            Logger.log("Item ID entered: " + itemID);
            if (itemID == 0) {
                Logger.log("Item Id selected is Incorrect");
                break;
            }
            MenuItem item = menuItems.stream().filter(i -> i.getItemID() == itemID).findFirst().orElse(null);
            if (item != null) {
                order.addItem(item);
                System.out.println(item.getName() + " added to order.");
                Logger.log("Item added to order: " + item.getName());
            } else {
                System.out.println("Item not found. Please try again.");
                Logger.log("Item not found: " + itemID);
            }
        }
        try {
            order.validateOrder();
            customer.addOrder(order);
            orders.add(order);
            orderDAO.saveOrder(order);

            // Payment process
            System.out.println("Enter payment method : ");
            System.out.println("Press 1 For Card Payment");
            System.out.println("Press 2 For Cash Payment");
            int paymentMethod = scanner.nextInt();
            if(paymentMethod == 1) {
                Payment payment = new CreditCardPayment(order.getTotalAmount());
                payment.processPayment(order.getTotalAmount());
                payment.generateReceipt(order, payment);
            } else if(paymentMethod == 2) {
                Payment payment = new CashPayment();
                payment.processPayment(order.getTotalAmount());
                payment.generateReceipt(order, payment);
            } else {
                System.out.println("Invalid payment method. Please try again.");
                Logger.log("Invalid payment method. Please try again.");
            }
            System.out.println("Order placed successfully. Your order ID is " + order.getOrderID());
            Logger.log("Order placed successfully. Your order ID is " + order.getOrderID());
        } catch (InvalidOrderException | SQLException | PaymentException e) {
            Logger.log("Failed to place order: " + e.getMessage());
            throw new SQLException("Failed to place order", e);
        }
    }


    private static void viewOrderSummary(Customer customer) {
        System.out.println("Order history for " + customer.getName() + ":");
        Logger.log("Order history for " + customer.getName() + ":");
        for (Order order : customer.getOrderHistory()) {
            System.out.println(order);
            Logger.log(order.toString());
        }
    }

    private static void displayOrderSummaryByDate(Customer customer, LocalDate startDate, LocalDate endDate) {
        List<Order> filteredOrders = OrderSummary.filterOrdersByDate(customer.getOrderHistory(), startDate, endDate);
        OrderSummary.displayOrderSummary(filteredOrders);
        System.out.println("Total orders: " + filteredOrders.size());
        Logger.log("Total orders: " + filteredOrders.size());

    }

    private static void displayOrderSummaryByTotalAmount(Customer customer, double minAmount, double maxAmount) {
        List<Order> filteredOrders = OrderSummary.filterOrdersByTotalAmount(customer.getOrderHistory(), minAmount, maxAmount);
        OrderSummary.displayOrderSummary(filteredOrders);
        System.out.println("Total orders: " + filteredOrders.size());

    }

    private static void searchMenuItems(String keyword) {
        List<MenuItem> results = MenuSearch.searchMenuItemsByKeyword(menuItems, keyword);
        results.forEach(System.out::println);
    }

    private static void filterMenuItemsByPriceRange(double minPrice, double maxPrice) {
        List<MenuItem> results = MenuSearch.filterMenuItemsByPriceRange(menuItems, minPrice, maxPrice);
        results.forEach(System.out::println);
    }

    private static void applyDiscountsToSpecialtyItems() {
        List<SpecialtyItem> specialtyItems = menuItems.stream()
                .filter(item -> item instanceof SpecialtyItem)
                .map(item -> (SpecialtyItem) item)
                .collect(Collectors.toList());
        System.out.println("Specialty Items before discount:");
        specialtyItems.forEach(item -> {
            System.out.println("Item: " + item.getName());
            System.out.println("Price: $" + item.getPrice());
            System.out.println();
        });
        List<SpecialtyItem> discountedItems = DiscountCalculator.applyDiscounts(specialtyItems);
        System.out.println("Specialty Items after discount:");
        discountedItems.forEach(item -> {
            System.out.println("Item: " + item.getName());
            System.out.println("Discounted Price: $" + item.getPrice());
            System.out.println();
        });
    }
}