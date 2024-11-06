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
        String querySocio = "INSERT INTO socio (numeroSocio, nombre, tipoSocio) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statementSocio = connection.prepareStatement(querySocio)) {

            statementSocio.setInt(1, socio.getNumeroSocio());
            statementSocio.setString(2, socio.getNombre());
            statementSocio.setString(3, socio.getTipo());
            statementSocio.executeUpdate();

            if (socio instanceof SocioEstandar) {
                // Inserción en la tabla `socio_estandar`
                SocioEstandar socioEstandar = (SocioEstandar) socio;
                String tipoSeguro = socioEstandar.getSeguro().getTipo().name();
                tipoSeguro = tipoSeguro.substring(0, 1).toUpperCase() + tipoSeguro.substring(1).toLowerCase();

                String queryEstandar = "INSERT INTO socio_estandar (numeroSocio, nif, tipoSeguro) VALUES (?, ?, ?)";
                try (PreparedStatement statementEstandar = connection.prepareStatement(queryEstandar)) {
                    statementEstandar.setInt(1, socio.getNumeroSocio());
                    statementEstandar.setString(2, socioEstandar.getNif());
                    statementEstandar.setString(3, tipoSeguro);
                    statementEstandar.executeUpdate();
                }
            } else if (socio instanceof SocioFederado) {
                // Inserción en la tabla `socio_federado`
                SocioFederado socioFederado = (SocioFederado) socio;
                String queryFederado = "INSERT INTO socio_federado (numeroSocio, nif, codigoFederacion) VALUES (?, ?, ?)";
                try (PreparedStatement statementFederado = connection.prepareStatement(queryFederado)) {
                    statementFederado.setInt(1, socio.getNumeroSocio());
                    statementFederado.setString(2, socioFederado.getNif());
                    statementFederado.setString(3, socioFederado.getFederacion().getCodigo());
                    statementFederado.executeUpdate();
                }
            } else if (socio instanceof SocioInfantil) {
                // Recupera el progenitor desde la base de datos antes de crear el `SocioInfantil`
                SocioInfantil socioInfantil = (SocioInfantil) socio;
                int numeroSocioProgenitor = socioInfantil.getNumeroSocioProgenitor();
                Socio progenitor = buscarSocioPorNumero(numeroSocioProgenitor);  // Busca el progenitor en la base de datos

                // Verificación para evitar `NullPointerException`
                if (progenitor == null) {
                    System.err.println("Error: El progenitor con número de socio " + numeroSocioProgenitor + " no existe.");
                    return;  // Salir del método si el progenitor no existe
                }

                // Reasigna el objeto `progenitor` al `socioInfantil`
                socioInfantil = new SocioInfantil(socioInfantil.getNumeroSocio(), socioInfantil.getNombre(), progenitor);

                String queryInfantil = "INSERT INTO socio_infantil (numeroSocio, numeroSocioProgenitor) VALUES (?, ?)";
                try (PreparedStatement statementInfantil = connection.prepareStatement(queryInfantil)) {
                    statementInfantil.setInt(1, socioInfantil.getNumeroSocio());
                    statementInfantil.setInt(2, socioInfantil.getProgenitor().getNumeroSocio());
                    statementInfantil.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al agregar el socio: " + e.getMessage());
        }
    }

    // Método para buscar un socio por su número de socio
    @Override
    public Socio buscarSocioPorNumero(int numeroSocio) {
        String query = "SELECT * FROM socio WHERE numeroSocio = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Asignar parámetro para el número de socio
            statement.setInt(1, numeroSocio);

            // Ejecutar consulta y procesar resultado
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");

                // Verificar en la tabla socio_estandar
                String queryEstandar = "SELECT * FROM socio_estandar WHERE numeroSocio = ?";
                try (PreparedStatement stmtEstandar = connection.prepareStatement(queryEstandar)) {
                    stmtEstandar.setInt(1, numeroSocio);
                    ResultSet rsEstandar = stmtEstandar.executeQuery();
                    if (rsEstandar.next()) {
                        String nif = rsEstandar.getString("nif");
                        String tipoSeguro = rsEstandar.getString("tipoSeguro");
                        TipoSeguro tipoSeguroEnum = TipoSeguro.fromString(tipoSeguro);
                        Seguro seguro = new Seguro(tipoSeguroEnum);
                        return new SocioEstandar(numeroSocio, nombre, nif, seguro);
                    }
                }

                // Verificar en la tabla socio_federado
                String queryFederado = "SELECT * FROM socio_federado WHERE numeroSocio = ?";
                try (PreparedStatement stmtFederado = connection.prepareStatement(queryFederado)) {
                    stmtFederado.setInt(1, numeroSocio);
                    ResultSet rsFederado = stmtFederado.executeQuery();
                    if (rsFederado.next()) {
                        String nif = rsFederado.getString("nif");
                        String codigoFederacion = rsFederado.getString("codigoFederacion");
                        Federacion federacion = new Federacion(codigoFederacion); // Asume que tienes un constructor adecuado
                        return new SocioFederado(numeroSocio, nombre, nif, federacion);
                    }
                }

                // Verificar en la tabla socio_infantil
                String queryInfantil = "SELECT * FROM socio_infantil WHERE numeroSocio = ?";
                try (PreparedStatement stmtInfantil = connection.prepareStatement(queryInfantil)) {
                    stmtInfantil.setInt(1, numeroSocio);
                    ResultSet rsInfantil = stmtInfantil.executeQuery();
                    if (rsInfantil.next()) {
                        int numeroSocioProgenitor = rsInfantil.getInt("numeroSocioProgenitor");
                        Socio progenitor = buscarSocioPorNumero(numeroSocioProgenitor); // Llama a buscarSocioPorNumero para obtener el progenitor
                        return new SocioInfantil(numeroSocio, nombre, progenitor);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el socio: " + e.getMessage());
        }
        return null; // Retorna null si no se encuentra el socio o si ocurre un error
    }

    // Método para listar todos los socios
    @Override
    public List<Socio> mostrarSocios() {
        List<Socio> socios = new ArrayList<>();
        String query = "SELECT * FROM socio";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int numeroSocio = resultSet.getInt("numeroSocio");
                Socio socio = buscarSocioPorNumero(numeroSocio); // Usa buscarSocioPorNumero para obtener el tipo correcto
                if (socio != null) {
                    socios.add(socio);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los socios: " + e.getMessage());
        }
        return socios;
    }

    // Método para eliminar un socio
    @Override
    public void eliminarSocio(int numeroSocio) throws Exception {
        String query = "DELETE FROM socio WHERE numeroSocio = ?";

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
        String querySocio = "UPDATE socio SET nombre = ?, tipoSocio = ? WHERE numeroSocio = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statementSocio = connection.prepareStatement(querySocio)) {

            // Actualizamos tabla socio
            statementSocio.setInt(1, socio.getNumeroSocio());
            statementSocio.setString(2, socio.getNombre());
            statementSocio.setString(3, socio.getTipo());
            statementSocio.executeUpdate();


            // Actualizamos la tabla específica según el tipo de socio
            if (socio instanceof SocioEstandar) {
                SocioEstandar socioEstandar = (SocioEstandar) socio;
                String tipoSeguro = socioEstandar.getSeguro().getTipo().name();
                tipoSeguro = tipoSeguro.substring(0, 1).toUpperCase() + tipoSeguro.substring(1).toLowerCase();

                if (tipoSeguro != null) {
                    String queryEstandar = "UPDATE socio_estandar SET nif = ?, tipoSeguro = ? WHERE numeroSocio = ?";
                    try (PreparedStatement statementEstandar = connection.prepareStatement(queryEstandar)) {
                        statementEstandar.setString(1, socioEstandar.getNif());
                        statementEstandar.setString(2, tipoSeguro);  // Configura el tipo de seguro
                        statementEstandar.setInt(3, socioEstandar.getNumeroSocio());
                        statementEstandar.executeUpdate();
                    }
                } else {
                    System.err.println("Error: El seguro del socio estándar es nulo.");
                }

            } else if (socio instanceof SocioFederado) {
                SocioFederado socioFederado = (SocioFederado) socio;
                String queryFederado = "UPDATE socio_federado SET nif = ?, federacion = ? WHERE numeroSocio = ?";
                try (PreparedStatement statementFederado = connection.prepareStatement(queryFederado)) {
                    statementFederado.setInt(1, socio.getNumeroSocio());
                    statementFederado.setString(2, socioFederado.getNif());
                    statementFederado.setString(3, socioFederado.getFederacion().getNombre());
                    statementFederado.executeUpdate();
                }
            } else if (socio instanceof SocioInfantil) {
                SocioInfantil socioInfantil = (SocioInfantil) socio;
                String queryInfantil = "UPDATE socio_infantil SET progenitor = ? WHERE numeroSocio = ?";
                try (PreparedStatement statementInfantil = connection.prepareStatement(queryInfantil)) {
                    statementInfantil.setInt(1, socio.getNumeroSocio());
                    statementInfantil.setInt(2, socioInfantil.getProgenitor().getNumeroSocio());
                    statementInfantil.executeUpdate();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar el socio: " + e.getMessage());
        }
    }
}
