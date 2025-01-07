package com.cba.midweek;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

   /* public void insertSampleData() throws SQLException {
        //String createSequenceSQL = "CREATE SEQUENCE IF NOT EXISTS item_id_seq START WITH 1 INCREMENT BY 1";
        //String clearTableSQL = "DELETE FROM MenuItems";
        //String clearOrderItemsSQL = "DELETE FROM OrderItems";
        String sql = "INSERT INTO MenuItems (itemID, name, price, discount) VALUES (NEXT VALUE FOR item_id_seq, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createSequenceSQL);
            stmt.execute(clearOrderItemsSQL);
            stmt.execute(clearTableSQL);
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Burger");
            stmt.setDouble(2, 5.99);
            stmt.setDouble(3, 0.0);
            stmt.executeUpdate();

            stmt.setString(1, "Pizza");
            stmt.setDouble(2, 8.99);
            stmt.setDouble(3, 0.0);
            stmt.executeUpdate();

            stmt.setString(1, "Pasta");
            stmt.setDouble(2, 7.99);
            stmt.setDouble(3, 10.0);
            stmt.executeUpdate();

            System.out.println("Sample data inserted into MenuItems table.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            throw e;
        }
    }*/
    public List<MenuItem> loadMenuItems() throws SQLException {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM MenuItems";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println("Database connection established.");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int itemID = rs.getInt("itemID");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    double discount = rs.getDouble("discount");

                    if (discount > 0) {
                        menuItems.add(new SpecialtyItem(name, price, itemID, discount));
                    } else {
                        menuItems.add(new MenuItem(name, price, itemID));
                    }
                }
                System.out.println("Query executed. Number of items loaded: " + menuItems.size());
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            throw e;
        }
        return menuItems;
    }
}