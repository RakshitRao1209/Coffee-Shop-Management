package com.cba.midweek;
import java.sql.*;
import java.sql.Statement;
public class DatabaseConnection {
    private static final String URL = "jdbc:hsqldb:hsql://localhost/xdb";
    private static final String USER = "SA";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
