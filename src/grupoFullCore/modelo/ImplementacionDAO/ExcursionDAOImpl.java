package grupoFullCore.modelo.ImplementacionDAO;

import grupoFullCore.modelo.Excursion;
import grupoFullCore.modelo.DatabaseConnection;
import grupoFullCore.modelo.DAO.ExcursionDAO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExcursionDAOImpl implements ExcursionDAO {

    @Override
    public void agregarExcursion(Excursion excursion) {
        String query = "INSERT INTO Excursion (descripcion, fecha, numeroDias, precioInscripcion) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, excursion.getDescripcion());
            statement.setDate(2, Date.valueOf(excursion.getFecha()));
            statement.setInt(3, excursion.getNumeroDias());
            statement.setDouble(4, excursion.getPrecioInscripcion());

            statement.executeUpdate();

            // Obtener número de Inscripcion generado Automáticamente para que aparezca en la tabla de SQL

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                excursion.setCodigo(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Error al agregar la excursión: " + e.getMessage());
        }
    }

    @Override
    public Excursion buscarExcursionPorCodigo(int codigo) {
        String query = "SELECT * FROM Excursion WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, codigo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String descripcion = resultSet.getString("descripcion");
                LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
                int numeroDias = resultSet.getInt("numeroDias");
                double precioInscripcion = resultSet.getDouble("precioInscripcion");

                return new Excursion(codigo, descripcion, fecha, numeroDias, precioInscripcion);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la excursión: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Excursion> mostrarExcursiones() {
        List<Excursion> excursiones = new ArrayList<>();
        String query = "SELECT * FROM Excursion";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String descripcion = resultSet.getString("descripcion");
                LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
                int numeroDias = resultSet.getInt("numeroDias");
                double precioInscripcion = resultSet.getDouble("precioInscripcion");

                excursiones.add(new Excursion(codigo, descripcion, fecha, numeroDias, precioInscripcion));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar las excursiones: " + e.getMessage());
        }
        return excursiones;
    }

    @Override
    public List<Excursion> mostrarExcursionesConFiltro(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Excursion> excursiones = new ArrayList<>();
        String query = "SELECT * FROM Excursion WHERE fecha BETWEEN ? AND ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(fechaInicio));
            statement.setDate(2, Date.valueOf(fechaFin));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int codigo = resultSet.getInt("codigo");
                String descripcion = resultSet.getString("descripcion");
                LocalDate fecha = resultSet.getDate("fecha").toLocalDate();
                int numeroDias = resultSet.getInt("numeroDias");
                double precioInscripcion = resultSet.getDouble("precioInscripcion");

                excursiones.add(new Excursion(codigo, descripcion, fecha, numeroDias, precioInscripcion));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar excursiones con filtro: " + e.getMessage());
        }
        return excursiones;
    }

    @Override
    public void eliminarExcursion(int codigo) throws Exception {
        String query = "DELETE FROM Excursion WHERE codigo = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar la excursión: " + e.getMessage());
        }
    }
}
