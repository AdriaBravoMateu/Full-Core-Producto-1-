package grupoFullCore.modelo.ImplementacionDAO;

import grupoFullCore.modelo.Federacion;
import grupoFullCore.modelo.DatabaseConnection;
import grupoFullCore.modelo.DAO.FederacionDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FederacionDAOImpl implements FederacionDAO {

    @Override
    public void agregarFederacion(Federacion federacion) {
        String query = "INSERT INTO Federaciones (codigo, nombre) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, federacion.getCodigo());
            statement.setString(2, federacion.getNombre());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar la federación: " + e.getMessage());
        }
    }

    @Override
    public Federacion buscarFederacionPorCodigo(String codigo) {
        String query = "SELECT * FROM Federaciones WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, codigo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                return new Federacion(codigo, nombre);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la federación: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Federacion> mostrarFederaciones() {
        List<Federacion> federaciones = new ArrayList<>();
        String query = "SELECT * FROM Federaciones";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String codigo = resultSet.getString("codigo");
                String nombre = resultSet.getString("nombre");

                federaciones.add(new Federacion(codigo, nombre));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar las federaciones: " + e.getMessage());
        }
        return federaciones;
    }

    @Override
    public void eliminarFederacion(String codigo) throws Exception {
        String query = "DELETE FROM Federaciones WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar la federación: " + e.getMessage());
        }
    }
}
