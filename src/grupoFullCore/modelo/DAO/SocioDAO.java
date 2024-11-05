package grupoFullCore.modelo.DAO;

import grupoFullCore.modelo.Socio;
import java.util.List;

public interface SocioDAO {
    void agregarSocio(Socio socio);
    Socio buscarSocioPorNumero(int numeroSocio);
    List<Socio> mostrarSocios();
    void eliminarSocio(int numeroSocio) throws Exception;
    void actualizarSocio(Socio socio);
}
