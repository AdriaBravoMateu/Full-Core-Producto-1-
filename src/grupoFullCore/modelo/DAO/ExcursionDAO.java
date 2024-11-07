package grupoFullCore.modelo.DAO;

import grupoFullCore.modelo.Excursion;
import java.time.LocalDate;
import java.util.List;

public interface ExcursionDAO {
    void agregarExcursion(Excursion excursion);
    Excursion buscarExcursionPorCodigo(int codigo);
    List<Excursion> mostrarExcursiones();
    List<Excursion> mostrarExcursionesConFiltro(LocalDate fechaInicio, LocalDate fechaFin);
    void eliminarExcursion(int codigo) throws Exception;
}
