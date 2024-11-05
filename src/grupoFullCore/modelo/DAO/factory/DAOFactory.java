package grupoFullCore.modelo.DAO.factory;

import grupoFullCore.modelo.DAO.SocioDAO;
import grupoFullCore.modelo.DAO.ExcursionDAO;
import grupoFullCore.modelo.DAO.InscripcionDAO;
import grupoFullCore.modelo.DAO.FederacionDAO;
import grupoFullCore.modelo.ImplementacionDAO.SocioDAOImpl;
import grupoFullCore.modelo.ImplementacionDAO.ExcursionDAOImpl;
import grupoFullCore.modelo.ImplementacionDAO.InscripcionDAOImpl;
import grupoFullCore.modelo.ImplementacionDAO.FederacionDAOImpl;

public class DAOFactory {

    // Método para obtener la instancia de SocioDAO
    public static SocioDAO getSocioDAO() {
        return new SocioDAOImpl();
    }

    // Método para obtener la instancia de ExcursionDAO
    public static ExcursionDAO getExcursionDAO() {
        return new ExcursionDAOImpl();
    }

    // Método para obtener la instancia de InscripcionDAO
    public static InscripcionDAO getInscripcionDAO() {
        return new InscripcionDAOImpl();
    }

    // Método para obtener la instancia de FederacionDAO
    public static FederacionDAO getFederacionDAO() {
        return new FederacionDAOImpl();
    }
}
