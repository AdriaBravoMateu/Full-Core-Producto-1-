package grupoFullCore.modelo.ImplementacionDAO;

import grupoFullCore.modelo.Inscripcion;
import grupoFullCore.modelo.Socio;
import grupoFullCore.modelo.Excursion;
import grupoFullCore.modelo.DatabaseConnection;
import grupoFullCore.modelo.DAO.InscripcionDAO;
import grupoFullCore.modelo.DAO.factory.DAOFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class InscripcionDAOImpl implements InscripcionDAO {
    private static final Logger logger = Logger.getLogger(InscripcionDAOImpl.class.getName());
    @Override
    public void agregarInscripcion(Inscripcion inscripcion) {
        String query = "INSERT INTO Inscripcion (numeroInscripcion, fechaInscripcion, numeroSocio, codigoExcursion) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, inscripcion.getNumeroInscripcion());
            statement.setDate(2, Date.valueOf(inscripcion.getFechaInscripcion()));
            statement.setInt(3, inscripcion.getSocio().getNumeroSocio());
            statement.setString(4, inscripcion.getExcursion().getCodigo());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar la inscripción: " + e.getMessage());
        }
    }

    @Override
    public Inscripcion buscarInscripcionPorNumero(int numeroInscripcion) {
        String query = "SELECT * FROM Inscripcion WHERE numeroInscripcion = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, numeroInscripcion);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();
                int numeroSocio = resultSet.getInt("numeroSocio");
                String codigoExcursion = resultSet.getString("codigoExcursion");

                Socio socio = new SocioDAOImpl().buscarSocioPorNumero(numeroSocio);
                Excursion excursion = new ExcursionDAOImpl().buscarExcursionPorCodigo(codigoExcursion);

                return new Inscripcion(numeroInscripcion, fechaInscripcion, socio, excursion);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar la inscripción: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Inscripcion> mostrarInscripciones() {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT * FROM Inscripcion";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int numeroInscripcion = resultSet.getInt("numeroInscripcion");
                LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();
                int numeroSocio = resultSet.getInt("numeroSocio");
                String codigoExcursion = resultSet.getString("codigoExcursion");

                Socio socio = new SocioDAOImpl().buscarSocioPorNumero(numeroSocio);
                Excursion excursion = new ExcursionDAOImpl().buscarExcursionPorCodigo(codigoExcursion);

                inscripciones.add(new Inscripcion(numeroInscripcion, fechaInscripcion, socio, excursion));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar las inscripciones: " + e.getMessage());
        }
        return inscripciones;
    }

    @Override
    public List<Inscripcion> mostrarInscripcionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT * FROM Inscripcion WHERE fechaInscripcion BETWEEN ? AND ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(fechaInicio));
            statement.setDate(2, Date.valueOf(fechaFin));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int numeroInscripcion = resultSet.getInt("numeroInscripcion");
                LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();
                int numeroSocio = resultSet.getInt("numeroSocio");
                String codigoExcursion = resultSet.getString("codigoExcursion");

                Socio socio = new SocioDAOImpl().buscarSocioPorNumero(numeroSocio);
                Excursion excursion = new ExcursionDAOImpl().buscarExcursionPorCodigo(codigoExcursion);

                inscripciones.add(new Inscripcion(numeroInscripcion, fechaInscripcion, socio, excursion));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar las inscripciones por fecha: " + e.getMessage());
        }
        return inscripciones;
    }

    @Override
    public List<Inscripcion> mostrarInscripcionesPorSocio(int numeroSocio) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT * FROM Inscripcion WHERE numeroSocio = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, numeroSocio);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int numeroInscripcion = resultSet.getInt("numeroInscripcion");
                LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();
                String codigoExcursion = resultSet.getString("codigoExcursion");

                Socio socio = new SocioDAOImpl().buscarSocioPorNumero(numeroSocio);
                Excursion excursion = new ExcursionDAOImpl().buscarExcursionPorCodigo(codigoExcursion);

                inscripciones.add(new Inscripcion(numeroInscripcion, fechaInscripcion, socio, excursion));
            }
        } catch (SQLException e) {
            System.err.println("Error al mostrar las inscripciones por socio: " + e.getMessage());
        }
        return inscripciones;
    }
    @Override
    public List<Inscripcion> mostrarInscripcionesPorExcursion(String codigoExcursion) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT * FROM Inscripcion WHERE codigoExcursion = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, codigoExcursion);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int numeroInscripcion = resultSet.getInt("numeroInscripcion");
                LocalDate fechaInscripcion = resultSet.getDate("fechaInscripcion").toLocalDate();
                int numeroSocio = resultSet.getInt("numeroSocio");

                Socio socio = DAOFactory.getSocioDAO().buscarSocioPorNumero(numeroSocio);
                Excursion excursion = DAOFactory.getExcursionDAO().buscarExcursionPorCodigo(codigoExcursion);

                Inscripcion inscripcion = new Inscripcion(numeroInscripcion, fechaInscripcion, socio, excursion);
                inscripciones.add(inscripcion);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while fetching inscriptions for excursion code: " + codigoExcursion, e);
        }
        return inscripciones;
    }
    @Override
    public void eliminarInscripcion(int numeroInscripcion) throws Exception {
        String query = "DELETE FROM Inscripcion WHERE numeroInscripcion = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, numeroInscripcion);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar la inscripción: " + e.getMessage());
        }
    }
}
