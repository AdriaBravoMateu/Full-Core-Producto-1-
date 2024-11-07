package grupoFullCoreControlador;

import grupoFullCore.modelo.*;
import grupoFullCore.modelo.DAO.ExcursionDAO;
import grupoFullCore.modelo.DAO.factory.DAOFactory;
import grupoFullCoreVista.VistaExcursion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControladorExcursion {
    private ExcursionDAO excursionDAO;
    private VistaExcursion vista;
    private ControladorSocio controladorSocio;
    private ControladorInscripcion controladorInscripcion;

    public ControladorExcursion(VistaExcursion vista, ControladorSocio controladorSocio, ControladorInscripcion controladorInscripcion) {
        this.excursionDAO = DAOFactory.getExcursionDAO(); // Obtiene ExcursionDAO usando DAOFactory
        this.vista = vista;
        this.controladorSocio = controladorSocio;
        this.controladorInscripcion = controladorInscripcion;
    }

    public void setControladorSocio(ControladorSocio controladorSocio) {
        this.controladorSocio = controladorSocio;
    }

    public void setControladorInscripcion(ControladorInscripcion controladorInscripcion) {
        this.controladorInscripcion = controladorInscripcion;
    }

    public void gestionarExcursiones() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMenuExcursiones();
            int opcion = vista.leerOpcion();
            switch (opcion) {
                case 1:
                    agregarExcursion();
                    break;
                case 2:
                    mostrarExcursionesConFiltro();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    private void agregarExcursion() {
        int codigo;

        // Muestra excursiones existentes para evitar duplicados de código
        mostrarExcursiones(excursionDAO.mostrarExcursiones());

        do {
            codigo = vista.leerCodigoExcursion();
            if (excursionDAO.buscarExcursionPorCodigo(codigo) != null) {
                vista.mostrarResultado("El código de excursión ya existe. Introduzca otro código.");
            }
        } while (excursionDAO.buscarExcursionPorCodigo(codigo) != null);

        // Lee los demás datos de la excursión
        String descripcion = vista.leerDescripcionExcursion();
        String fechaStr = vista.leerFechaExcursion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(fechaStr, formatter);
        int dias = vista.leerNumeroDiasExcursion();
        double precio = vista.leerPrecioInscripcion();

        // Crea la nueva excursión y la agrega a la base de datos
        Excursion excursion = new Excursion(codigo, descripcion, fecha, dias, precio);
        excursionDAO.agregarExcursion(excursion);
        vista.mostrarResultado("Excursión añadida correctamente.");
    }

    private void mostrarExcursionesConFiltro() {
        vista.mostrarResultado("Introduce las fechas para filtrar las excursiones.");
        String fechaInicioStr = vista.leerFechaExcursion();
        String fechaFinStr = vista.leerFechaExcursion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);

        List<Excursion> excursionesFiltradas = excursionDAO.mostrarExcursionesConFiltro(fechaInicio, fechaFin);
        mostrarExcursiones(excursionesFiltradas);
    }

    public void mostrarExcursiones(List<Excursion> excursiones) {
        if (excursiones.isEmpty()) {
            vista.mostrarResultado("No se encontraron excursiones.");
            return;
        }

        String formato = "| %-10s | %-20s | %-10s | %-5d | %-10.2f |\n";
        vista.mostrarResultado("+------------+----------------------+-------------+-------+-----------+");
        vista.mostrarResultado("| Código     | Descripción          | Fecha       | Días  | Precio    |");
        vista.mostrarResultado("+------------+----------------------+-------------+-------+-----------+");

        for (Excursion excursion : excursiones) {
            vista.mostrarResultado(String.format(formato,
                    excursion.getCodigo(),
                    excursion.getDescripcion(),
                    excursion.getFecha(),
                    excursion.getNumeroDias(),
                    excursion.getPrecioInscripcion()
            ));
        }
        vista.mostrarResultado("+------------+----------------------+-------------+-------+-----------+");
    }
}
