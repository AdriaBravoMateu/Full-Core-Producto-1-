package grupoFullCore.modelo.DAO.factory;

import grupoFullCore.modelo.DAO.*;
import grupoFullCore.modelo.ImplementacionDAO.*;

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

    public static SeguroDAO getSeguroDAO() { return new SeguroDAOImpl();
    }
}
