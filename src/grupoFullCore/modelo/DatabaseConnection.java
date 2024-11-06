package grupoFullCore.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/FullCoreDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Logger para la clase
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al conectar con la base de datos", e);
            throw e; // Re-lanzar la excepción después de registrarla
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error al cerrar la conexión a la base de datos", e);
        }
    }
}