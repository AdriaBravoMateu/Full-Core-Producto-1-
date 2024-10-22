package grupoFullCoreControlador;

import grupoFullCore.modelo.*;
import grupoFullCoreVista.VistaExcursion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControladorExcursion {
    private CentroExcursionista centro;
    private VistaExcursion vista;

    public ControladorExcursion(CentroExcursionista centro, VistaExcursion vista) {
        this.centro = centro;
        this.vista = vista;
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
        String codigo;
        do {
            codigo = vista.leerCodigoExcursion();
            if (centro.buscarExcursionPorCodigo(codigo)) {
                vista.mostrarResultado("El código de excursión ya existe. Introduzca otro código.");
            }
        } while (centro.buscarExcursionPorCodigo(codigo));

        String descripcion = vista.leerDescripcionExcursion();
        String fechaStr = vista.leerFechaExcursion();  // Utiliza el método actualizado para leer la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaLocal = LocalDate.parse(fechaStr, formatter);
        LocalDate fecha = java.sql.Date.valueOf(fechaLocal).toLocalDate();
        int dias = vista.leerNumeroDiasExcursion();
        double precio = vista.leerPrecioInscripcion();
        Excursion excursion = new Excursion(codigo, descripcion, fecha, dias, precio);
        centro.añadirExcursion(excursion);
        vista.mostrarResultado("Excursión añadida correctamente.");
    }

    private void mostrarExcursionesConFiltro() {
        vista.mostrarResultado("Introduce las fechas para filtrar las excursiones.");
        String fechaInicioStr = vista.leerFechaExcursion();
        String fechaFinStr = vista.leerFechaExcursion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
        List<Excursion> excursionesFiltradas = centro.mostrarExcursionesConFiltro(fechaInicio, fechaFin);
        mostrarExcursiones(centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX));
    }

    public void mostrarExcursiones(List<Excursion> excursiones) {
        if (excursiones.isEmpty()) {
            vista.mostrarResultado("No se encontraron excursiones.");
            return;
        }
        String formato = "| %-10s | %-20s | %-10s | %-5d | %-10.2f |\n";
        vista.mostrarResultado("+------------+----------------------+-------------+-------+-----------+");
        vista.mostrarResultado("| Código     | Descripción           | Fecha      | Días  | Precio    |");
        vista.mostrarResultado("+------------+----------------------+-------------+-------+-----------+");
        for (Excursion excursion : excursiones) {
            vista.mostrarResultado(String.format(formato,
                    excursion.getCodigo(),
                    excursion.getDescripcion(),
                    excursion.getFecha(),
                    excursion.getNumeroDias(),
                    excursion.getPrecioInscripcion()
            ));
            vista.mostrarResultado("+------------+----------------------+------------+-------+------------+");
        }
    }
}
