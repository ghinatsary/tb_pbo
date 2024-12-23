import java.sql.*;

public class DatabaseConnection {
    public static Connection connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/tugas_jdbc";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}
