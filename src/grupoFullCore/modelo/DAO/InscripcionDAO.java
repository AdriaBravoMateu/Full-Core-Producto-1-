package grupoFullCore.modelo.DAO;

import grupoFullCore.modelo.Inscripcion;
import java.time.LocalDate;
import java.util.List;

public interface InscripcionDAO {
    List<Inscripcion> mostrarInscripcionesPorSocioYExcursion(int numeroSocio, int codigoExcursion);
    void agregarInscripcion(Inscripcion inscripcion);
    Inscripcion buscarInscripcionPorNumero(int numeroInscripcion);
    List<Inscripcion> mostrarInscripciones();
    List<Inscripcion> mostrarInscripcionesPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
    List<Inscripcion> mostrarInscripcionesPorSocio(int numeroSocio);
    List<Inscripcion> mostrarInscripcionesPorExcursion(int codigoExcursion);
    void eliminarInscripcion(int numeroInscripcion) throws Exception;
}