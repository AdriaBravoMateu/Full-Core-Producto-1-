package grupoFullCore.modelo.ImplementacionDAO;

import grupoFullCore.modelo.*;
import grupoFullCore.modelo.DAO.SeguroDAO;
import grupoFullCore.modelo.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguroDAOImpl implements SeguroDAO {

    // Método para agregar un nuevo seguro
    @Override
    public void agregarSeguro(Seguro seguro) {
        String query = "INSERT INTO seguro (tipo, precio) VALUES (?, ?) ON DUPLICATE KEY UPDATE precio = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Asignación de parámetros
            statement.setString(1, seguro.getTipo().name()); // Convertimos TipoSeguro a String
            statement.setDouble(2, seguro.getPrecio());
            statement.setDouble(3, seguro.getPrecio()); // Para actualizar el precio si el tipo ya existe

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar el seguro: " + e.getMessage());
        }
    }

    // Método para buscar un seguro por tipo
    @Override
    public Seguro buscarSeguroPorTipo(TipoSeguro tipo) {
        String query = "SELECT * FROM seguro WHERE tipo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, tipo.name());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double precio = resultSet.getDouble("precio"); // Recupera el precio desde la BD
                Seguro seguro = new Seguro(tipo);
                seguro.setPrecio(precio); // Asegura que el precio se setea correctamente
                return seguro;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el seguro por tipo: " + e.getMessage());
        }
        return null;
    }


    // Método para mostrar todos los seguros
    @Override
    public List<Seguro> mostrarSeguros() {
        List<Seguro> seguros = new ArrayList<>();
        String query = "SELECT * FROM seguro";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String tipoSeguro = resultSet.getString("tipo");
                TipoSeguro tipoSeguroEnum = TipoSeguro.fromString(tipoSeguro); // Convierte el tipo a enum
                // Solo necesitamos pasar el tipo; el constructor asignará el precio
                seguros.add(new Seguro(tipoSeguroEnum));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar los seguros: " + e.getMessage());
        }
        return seguros;
    }
}
