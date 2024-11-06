package grupoFullCore.modelo.DAO;

import grupoFullCore.modelo.Seguro;
import grupoFullCore.modelo.TipoSeguro;
import java.util.List;

public interface SeguroDAO {
    void agregarSeguro(Seguro seguro); // Agrega un seguro a la base de datos
    Seguro buscarSeguroPorTipo(TipoSeguro tipo); // Busca un seguro por tipo
    List<Seguro> mostrarSeguros(); // Muestra todos los seguros disponibles
}

