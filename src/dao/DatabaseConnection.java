package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configuración para Azure SQL Database
    private static final String URL = "jdbc:sqlserver://hamburgueseria-server.database.windows.net:1433;"
                                    + "database=hamburgueseria;"
                                    + "user=hamburgueseriaadmin;"
                                    + "password=Mibenjamin1;"
                                    + "encrypt=true;"
                                    + "trustServerCertificate=false;"
                                    + "hostNameInCertificate=*.database.windows.net;"
                                    + "loginTimeout=30;";
    
    // Alternativa con parámetros separados
    private static final String SERVER = "hamburgueseria-server.database.windows.net";
    private static final String DATABASE = "hamburgueseria";
    private static final String USERNAME = "hamburgueseriaadmin";
    private static final String PASSWORD = "Mibenjamin1";
    
    private static final String CONNECTION_URL = 
        "jdbc:sqlserver://" + SERVER + ":1433;" +
        "database=" + DATABASE + ";" +
        "user=" + USERNAME + ";" +
        "password=" + PASSWORD + ";" +
        "encrypt=true;" +
        "trustServerCertificate=false;" +
        "hostNameInCertificate=*.database.windows.net;" +
        "loginTimeout=30;";
    
    public static Connection getConnection() throws SQLException {
        try {
            // Driver para SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(CONNECTION_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC no encontrado", e);
        }
    }
    
    // Método para probar la conexión
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}