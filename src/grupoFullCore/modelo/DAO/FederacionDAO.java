package grupoFullCore.modelo.DAO;

import grupoFullCore.modelo.Federacion;
import java.util.List;

public interface FederacionDAO {
    void agregarFederacion(Federacion federacion);
    Federacion buscarFederacionPorCodigo(String codigo);
    List<Federacion> mostrarFederaciones();
    void eliminarFederacion(String codigo) throws Exception;
}