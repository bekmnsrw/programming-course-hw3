package com.example.hw3.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionProvider {
    public static Connection getConnection() {
        try {
            Class.forName(PostgresDBConnectionDataHolder.DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find driver class");
        }

        try {
            return DriverManager.getConnection(
                    PostgresDBConnectionDataHolder.URL,
                    PostgresDBConnectionDataHolder.USERNAME,
                    PostgresDBConnectionDataHolder.PASSWORD
            );
        } catch (SQLException e) {
            System.out.println("Unable to receive connection");
            throw new RuntimeException(e);
        }
    }
}
