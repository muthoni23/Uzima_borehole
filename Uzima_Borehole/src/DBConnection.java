import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/uzima_borehole";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    
    // REMOVE the static Connection variable
    // private static Connection conn; // DELETE THIS LINE
    
    // Create a NEW connection every time
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✓ New database connection created!");
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found!");
            JOptionPane.showMessageDialog(null, "Driver not found: " + e.getMessage());
            e.printStackTrace();
            return null;
            
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            JOptionPane.showMessageDialog(null, 
                "Database connection failed:\n" + e.getMessage() + 
                "\n\nMake sure:\n" +
                "1. MySQL is running\n" +
                "2. Database 'uzima_borehole' exists\n" +
                "3. Password is correct (1234)",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}