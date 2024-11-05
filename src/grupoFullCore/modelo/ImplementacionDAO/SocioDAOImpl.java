package grupoFullCore.modelo.ImplementacionDAO;

import grupoFullCore.modelo.*;
import grupoFullCore.modelo.DAO.SocioDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAOImpl implements SocioDAO {

    // Método para agregar un nuevo socio a la base de datos
    @Override
    public void agregarSocio(Socio socio) {
        // Definición de la consulta SQL con parámetros
        String query = "INSERT INTO Socios (numeroSocio, nombre, tipo, nif, seguro_tipo) VALUES (?, ?, ?, ?, ?)";

        // Usar try-with-resources para garantizar el cierre de recursos
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Asignación de parámetros
            statement.setInt(1, socio.getNumeroSocio());
            statement.setString(2, socio.getNombre());
            //statement.setString(3, socio.getClass().getSimpleName()); // Tipo de socio: Estándar, Federado, Infantil

            // Validación condicional si es un SocioEstandar
            if (socio instanceof SocioEstandar) {
                SocioEstandar socioEstandar = (SocioEstandar) socio;
                statement.setString(4, socioEstandar.getNif());
                statement.setString(5, socioEstandar.getSeguro().getTipo().name());
            }
            if (socio instanceof SocioFederado) {
                SocioFederado socioFederado = (SocioFederado) socio;
                statement.setString(4, socioFederado.getNif());
                statement.setString(5, socioFederado.getFederacion().getNombre());
            }
            if (socio instanceof SocioInfantil){
                SocioInfantil socioInfantil = (SocioInfantil) socio;
                statement.setString(4, socioInfantil.getProgenitor().getNombre());
            }
            /*else {
                statement.setString(4, null); // No aplica NIF ni seguro a otros tipos de socios
                statement.setString(5, null);
            }*/

            // Ejecutar la actualización
            statement.executeUpdate();
        } catch (SQLException e) {
            // Registro de errores
            System.err.println("Error al agregar el socio: " + e.getMessage());
        }
    }

    // Método para buscar un socio por su número de socio
    @Override
    public Socio buscarSocioPorNumero(int numeroSocio) {
        String query = "SELECT * FROM Socios WHERE numeroSocio = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Asignar parámetro para el número de socio
            statement.setInt(1, numeroSocio);

            // Ejecutar consulta y procesar resultado
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Extracción de datos del resultado
                String nombre = resultSet.getString("nombre");
                String tipo = resultSet.getString("tipo");

                if ("SocioEstandar".equals(tipo)) { // Si el tipo es SocioEstandar
                    String nif = resultSet.getString("nif");
                    String seguroTipo = resultSet.getString("seguro_tipo");

                    // Convertir seguroTipo a TipoSeguro, y luego crear un Seguro
                    TipoSeguro tipoSeguroEnum = TipoSeguro.valueOf(seguroTipo); // Convierte el String a TipoSeguro
                    Seguro seguro = new Seguro(tipoSeguroEnum); // Crea una instancia de Seguro con el TipoSeguro

                    // Crear y retornar el SocioEstandar con el seguro adecuado
                    return new SocioEstandar(numeroSocio, nombre, nif, seguro);
                }
                // Puedes agregar lógica adicional para otros tipos de socios aquí
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el socio: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Maneja casos en los que seguroTipo no coincide con ningún valor de TipoSeguro
            System.err.println("Error: Tipo de seguro no válido en la base de datos: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra el socio o si ocurre un error
    }

    // Método para listar todos los socios
    @Override
    public List<Socio> mostrarSocios() {
        List<Socio> socios = new ArrayList<>();
        String query = "SELECT * FROM Socios";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Procesamiento de resultados
            while (resultSet.next()) {
                int numeroSocio = resultSet.getInt("numeroSocio");
                String nombre = resultSet.getString("nombre");
                String tipo = resultSet.getString("tipo");

                // Verificar el tipo de socio
                if ("SocioEstandar".equals(tipo)) { // Si es un SocioEstandar
                    String nif = resultSet.getString("nif");
                    String seguroTipo = resultSet.getString("seguro_tipo");

                    // Convertir seguroTipo a TipoSeguro y luego crear un Seguro
                    TipoSeguro tipoSeguroEnum = TipoSeguro.valueOf(seguroTipo); // Convierte el String a TipoSeguro
                    Seguro seguro = new Seguro(tipoSeguroEnum); // Crea una instancia de Seguro con el TipoSeguro

                    // Agregar el SocioEstandar a la lista con el seguro adecuado
                    socios.add(new SocioEstandar(numeroSocio, nombre, nif, seguro));
                }
                // Aquí puedes agregar lógica para otros tipos de Socio (SocioFederado, SocioInfantil, etc.)
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los socios: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Manejo de valores no válidos para seguroTipo
            System.err.println("Error: Tipo de seguro no válido en la base de datos: " + e.getMessage());
        }
        return socios;
    }

    // Método para eliminar un socio
    @Override
    public void eliminarSocio(int numeroSocio) throws Exception {
        String query = "DELETE FROM Socios WHERE numeroSocio = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, numeroSocio);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar el socio: " + e.getMessage());
        }
    }

    @Override
    public void actualizarSocio(Socio socio) {
        String query = "UPDATE Socios SET nombre = ?, tipo = ?, nif = ?, seguro_tipo = ? WHERE numeroSocio = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, socio.getNombre());
            statement.setString(2, socio.getClass().getSimpleName());
            if (socio instanceof SocioEstandar) {
                SocioEstandar socioEstandar = (SocioEstandar) socio;
                statement.setString(3, socioEstandar.getNif());
                statement.setString(4, socioEstandar.getSeguro().getTipo().name());
            } else {
                statement.setNull(3, java.sql.Types.VARCHAR);
                statement.setNull(4, java.sql.Types.VARCHAR);
            }
            statement.setInt(5, socio.getNumeroSocio());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el socio: " + e.getMessage());
        }
    }
}