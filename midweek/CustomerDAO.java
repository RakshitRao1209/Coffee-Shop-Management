package com.cba.midweek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    public void registerCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getName());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setCustomerID(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Customer loginCustomer(int customerID) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE customerID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getString("name"), rs.getInt("customerID"));
                }
            }
        }
        return null;
    }
}
