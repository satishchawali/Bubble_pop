package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://192.168.1.100:3306/bubblepop?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "user1";
    private static final String PASSWORD = "password";

    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load MySQL JDBC Driver (optional for modern JDBC)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish Connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC Driver not found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Database connection failed!");
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }
}
