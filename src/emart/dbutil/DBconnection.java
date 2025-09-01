package emart.dbutil;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBconnection class to handle database connectivity.
 * 
 * @author LENOVO
 */
public class DBconnection
{
    // Declare static Connection object
    private static Connection conn;

    // Static block to initialize the connection
    static {
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Open the connection using DriverManager
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "grocery1", "grocery1");

            // Show success message
           // JOptionPane.showMessageDialog(null, "Connection opened successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (ClassNotFoundException e) {
            // Handle ClassNotFoundException if JDBC driver is not found
            JOptionPane.showMessageDialog(null, "Error in loading the driver");
            e.printStackTrace();
            System.exit(1);  // Exit program as the driver could not be loaded

        } catch (SQLException e) {
            // Handle SQLException if connection cannot be opened
            JOptionPane.showMessageDialog(null, "Error in opening the connection");
            e.printStackTrace();
            System.exit(1);  // Exit program as the connection could not be established
        }
    }

    // Getter for the connection
    public static Connection getConnection() {
        return conn;
    }

    // Method to close the connection
    public static void closeConnection() {
        try {
            // Ensure the connection is not null and open
            if (conn != null && !conn.isClosed()) {
                conn.close();  // Close the connection

                // Show success message
                JOptionPane.showMessageDialog(null, "Connection closed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            // Handle SQLException during connection close
            JOptionPane.showMessageDialog(null, "Error in closing the connection");
            e.printStackTrace();  // Print stack trace for debugging
        }
    }
}
