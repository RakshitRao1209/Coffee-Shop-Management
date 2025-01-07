package com.cba.midweek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public void saveOrder(Order order) throws SQLException {
        String orderSql = "INSERT INTO Orders (customerID, totalAmount) VALUES (?, ?)";
        String orderItemSql = "INSERT INTO OrderItems (orderID, itemID) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement orderItemStmt = conn.prepareStatement(orderItemSql)) {
            conn.setAutoCommit(false);
            orderStmt.setInt(1, order.getCustomer().getCustomerID());
            orderStmt.setDouble(2, order.getTotalAmount());
            orderStmt.executeUpdate();
            try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderID(generatedKeys.getInt(1));
                }
            }
            for (MenuItem item : order.getItems()) {
                orderItemStmt.setInt(1, order.getOrderID());
                orderItemStmt.setInt(2, item.getItemID());
                orderItemStmt.addBatch();
            }
            orderItemStmt.executeBatch();
            conn.commit();
            Logger.log("Order placed: " + order);
        } catch (SQLException e) {
            Logger.log("Failed to save order: " + e.getMessage());
            throw new SQLException("Failed to save order", e);
        }
    }
}
