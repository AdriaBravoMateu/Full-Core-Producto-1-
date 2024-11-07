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
        String queryVerificarInscripciones = "SELECT COUNT(*) FROM inscripcion WHERE numeroSocio = ?";
        String queryObtenerTipo = "SELECT tipoSocio FROM socio WHERE numeroSocio = ?";
        String queryEliminarSocio = "DELETE FROM socio WHERE numeroSocio = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statementVerificarInscripciones = connection.prepareStatement(queryVerificarInscripciones);
             PreparedStatement statementObtenerTipo = connection.prepareStatement(queryObtenerTipo);
             PreparedStatement statementEliminarSocio = connection.prepareStatement(queryEliminarSocio)) {

            statementVerificarInscripciones.setInt(1, numeroSocio);
            ResultSet resultSetInscripciones = statementVerificarInscripciones.executeQuery();

            if (resultSetInscripciones.next() && resultSetInscripciones.getInt(1) > 0) {
                throw new Exception("No se puede eliminar un socio con inscripciones registradas.");
            }

            //Obtenemos tipo de socio
            statementObtenerTipo.setInt(1, numeroSocio);
            ResultSet resultSet = statementObtenerTipo.executeQuery();

            if (resultSet.next()) {
                String tipoSocio = resultSet.getString("tipoSocio");

                // Eliminamos el socio de la tabla específica de tipo
                if ("Estandar".equals(tipoSocio)) {
                    String queryEliminarEstandar = "DELETE FROM socio_estandar WHERE numeroSocio = ?";
                    try (PreparedStatement statementEliminarEstandar = connection.prepareStatement(queryEliminarEstandar)) {
                        statementEliminarEstandar.setInt(1, numeroSocio);
                        statementEliminarEstandar.executeUpdate();
                    }
                } else if ("Federado".equals(tipoSocio)) {
                    String queryEliminarFederado = "DELETE FROM socio_federado WHERE numeroSocio = ?";
                    try (PreparedStatement statementEliminarFederado = connection.prepareStatement(queryEliminarFederado)) {
                        statementEliminarFederado.setInt(1, numeroSocio);
                        statementEliminarFederado.executeUpdate();
                    }
                } else if ("Infantil".equals(tipoSocio)) {
                    String queryEliminarInfantil = "DELETE FROM socio_infantil WHERE numeroSocio = ?";
                    try (PreparedStatement statementEliminarInfantil = connection.prepareStatement(queryEliminarInfantil)) {
                        statementEliminarInfantil.setInt(1, numeroSocio);
                        statementEliminarInfantil.executeUpdate();
                    }
                }

                // Eliminamos el socio de la tabla socio
                statementEliminarSocio.setInt(1, numeroSocio);
                statementEliminarSocio.executeUpdate();
            } else {
                throw new Exception("No se encontró el socio con número " + numeroSocio);
            }

        } catch (SQLException e) {
            throw new Exception("Error al eliminar el socio: " + e.getMessage());
        }
    }

    public void actualizarTipoSeguro(SocioEstandar socio, TipoSeguro nuevoTipoSeguro) {
        String sql = "UPDATE socio_estandar SET tipoSeguro = ? WHERE numeroSocio = ?";

        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Asigna el tipo de seguro como cadena de texto, y el número de socio para identificar la fila
            stmt.setString(1, nuevoTipoSeguro.toString());
            stmt.setInt(2, socio.getNumeroSocio());

            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                // Crea un nuevo objeto Seguro y asigna este a socio
                Seguro nuevoSeguro = new Seguro(nuevoTipoSeguro);
                socio.setSeguro(nuevoSeguro); // Ahora pasamos un objeto Seguro
            } else {
                throw new RuntimeException("No se pudo actualizar el tipo de seguro; el socio no existe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el tipo de seguro", e);
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

                if (tipoSeguro != null) {
                    String queryEstandar = "UPDATE socio_estandar SET nif = ?, tipoSeguro = ? WHERE numeroSocio = ?";
                    try (PreparedStatement statementEstandar = connection.prepareStatement(queryEstandar)) {
                        statementEstandar.setString(1, socioEstandar.getNif());
                        statementEstandar.setString(2, tipoSeguro);
                        statementEstandar.setInt(3, socio.getNumeroSocio());
                        statementEstandar.executeUpdate();
                    }
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
