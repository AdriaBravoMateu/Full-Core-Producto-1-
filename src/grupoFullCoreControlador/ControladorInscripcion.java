package grupoFullCoreControlador;


import grupoFullCore.modelo.*;
import grupoFullCoreVista.VistaExcursion;
import grupoFullCoreVista.VistaInscripcion;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ControladorInscripcion {
    private CentroExcursionista centro;
    private VistaInscripcion vista;
    private ControladorSocio controladorSocio;
    private ControladorExcursion controladorExcursion;

    public ControladorInscripcion(CentroExcursionista centro, VistaInscripcion vista, ControladorSocio controladorSocio, ControladorExcursion controladorExcursion ) {
        this.centro = centro;
        this.vista = vista;
        this.controladorSocio = controladorSocio;
        this.controladorExcursion = controladorExcursion;
    }

    public void setControladorSocio(ControladorSocio controladorSocio) {
        this.controladorSocio = controladorSocio;
    }
    public void setControladorExcursion(ControladorExcursion controladorExcursion) {
        this.controladorExcursion = controladorExcursion;
    }



    public void gestionarInscripciones() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMenuInscripciones();
            int opcion = vista.leerOpcion();
            switch (opcion) {
                case 1:
                    agregarInscripcion();
                    break;
                case 2:
                    eliminarInscripcion();
                    break;
                case 3:
                    mostrarInscripciones();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    private void agregarInscripcion() {
        // Mostrar todos los socios usando el controlador de socios
        controladorSocio.mostrarTodosLosSocios();

        int numeroSocio = vista.leerNumeroSocio();
        Socio socio = centro.mostrarSocios().stream()
                .filter(s -> s.getNumeroSocio() == numeroSocio)
                .findFirst().orElse(null);

        if (socio == null) {
            vista.mostrarResultado("Socio no encontrado. Se procederá a añadir un nuevo socio.");
            socio = controladorSocio.agregarSocio();
            if (socio == null) {
                vista.mostrarResultado("Se ha cancelado el proceso de inscripción.");
                return;
            }
        }

        // Mostrar excursiones usando el controlador de excursiones
        controladorExcursion.mostrarExcursiones(centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX));

        String codigoExcursion = vista.leerCodigoExcursion();
        Excursion excursion = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX).stream()
                .filter(e -> e.getCodigo().equals(codigoExcursion))
                .findFirst().orElse(null);

        if (excursion != null) {
            List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorFechas(LocalDate.MIN, LocalDate.MAX);
            mostrarListaInscripciones(inscripciones);
            int numeroInscripcion;
            do {
                numeroInscripcion = vista.leerNumeroInscripcion();
                if (centro.buscarInscripcionPorNumero(numeroInscripcion) != null) {
                    vista.mostrarResultado("El número de inscripción ya existe. Introduzca otro número.");
                }
            } while (centro.buscarInscripcionPorNumero(numeroInscripcion) != null);

            Inscripcion inscripcion = new Inscripcion(numeroInscripcion, LocalDate.now(), socio, excursion);
            centro.añadirInscripcion(inscripcion);
            vista.mostrarResultado("Inscripción añadida correctamente.");
        } else {
            vista.mostrarResultado("Excursión no encontrada.");
        }
    }

    private void eliminarInscripcion() {
        List<Excursion> excursiones = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX);
        if (excursiones.isEmpty()) {
            vista.mostrarResultado("No se han encontrado excursiones programadas.");
            return;
        }

    // Llamar al método de instancia 'mostrarExcursiones' con la lista de excursiones
        controladorExcursion.mostrarExcursiones(centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX));
        String codigoExcursion = vista.leerCodigoExcursion();
        Excursion excursion = excursiones.stream()
                .filter(e -> e.getCodigo().equals(codigoExcursion))
                .findFirst().orElse(null);

        if (excursion == null) {
            vista.mostrarResultado("Excursión no encontrada.");
            return;
        }

        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorExcursion(excursion.getCodigo());
        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No hay inscripciones para esta excursión.");
            return;
        }

        mostrarInscripcionesDeExcursion(excursion);
        int numeroInscripcion = vista.leerNumeroInscripcion();
        Inscripcion inscripcion = inscripciones.stream()
                .filter(i -> i.getNumeroInscripcion() == numeroInscripcion)
                .findFirst().orElse(null);

        if (inscripcion == null) {
            vista.mostrarResultado("No existe ninguna inscripción con ese código.");
            return;
        }

        LocalDateTime fechaExcursion = excursion.getFecha().atStartOfDay();
        if (Duration.between(LocalDateTime.now(), fechaExcursion).toHours() < 24) {
            vista.mostrarResultado("No se pudo eliminar la inscripción con tan poca antelación.");
            return;
        }

        try {
            centro.eliminarInscripcion(inscripcion.getNumeroInscripcion());
            vista.mostrarResultado("Inscripción eliminada correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado("Error al eliminar la inscripción: " + e.getMessage());
        }
    }

    private void mostrarInscripciones() {
        int opcion = vista.mostrarFiltroInscripciones();
        List<Inscripcion> inscripciones = null;

        switch (opcion) {
            case 1:
                inscripciones = centro.mostrarInscripcionesPorFechas(LocalDate.MIN, LocalDate.MAX);
                break;
            case 2:
                controladorSocio.mostrarTodosLosSocios();
                int numeroSocio = vista.leerNumeroSocio();
                inscripciones = centro.mostrarInscripcionesPorSocio(numeroSocio);
                break;
            case 3:
                inscripciones = mostrarInscripcionesPorFechas();
                break;
            case 0:
                return;
            default:
                vista.mostrarResultado("Opción no válida.");
                return;
        }
        mostrarListaInscripciones(inscripciones);
    }

    private void mostrarListaInscripciones(List<Inscripcion> inscripciones) {
        if (inscripciones != null && !inscripciones.isEmpty()) {
            String formato = "| %-15s | %-20s | %-15s | %20s |\n";
            vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");
            vista.mostrarResultado("| Nº Inscripción  | Nombre del Socio     | Excursión       | Fecha Inscripción    |");
            vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");

            for (Inscripcion inscripcion : inscripciones) {
                vista.mostrarResultado(String.format(formato,
                        inscripcion.getNumeroInscripcion(),
                        inscripcion.getSocio().getNombre(),
                        inscripcion.getExcursion().getDescripcion(),
                        inscripcion.getFechaInscripcion()));
                vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");
            }
        } else {
            vista.mostrarResultado("No se encontraron inscripciones.");
        }
    }

    private List<Inscripcion> mostrarInscripcionesPorFechas() {
        vista.mostrarResultado("Introduce las fechas para filtrar las inscripciones.");

        LocalDate fechaInicio = vista.leerFechaInsc();
        LocalDate fechaFin = vista.leerFechaInsc();

        // Validar que la fecha de inicio no sea posterior a la fecha de fin
        if (fechaInicio.isAfter(fechaFin)) {
            vista.mostrarResultado("La fecha de inicio no puede ser posterior a la fecha de fin.");
            return List.of(); // Retorna una lista vacía en caso de error
        }

        return centro.mostrarInscripcionesPorFechas(fechaInicio, fechaFin);
    }


    private void mostrarInscripcionesDeExcursion(Excursion excursion) {
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorExcursion(excursion.getCodigo());

        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No hay inscripciones para esta excursión.");
            return;
        }

        String formato = "| %-15s | %-20s | %-15s |\n";
        vista.mostrarResultado("+-----------------+----------------------+-----------------+");
        vista.mostrarResultado("| Nº Inscripción  | Nombre del Socio     | Fecha Inscripción|");
        vista.mostrarResultado("+-----------------+----------------------+-----------------+");

        for (Inscripcion inscripcion : inscripciones) {
            vista.mostrarResultado(String.format(formato,
                    inscripcion.getNumeroInscripcion(),
                    inscripcion.getSocio().getNombre(),
                    inscripcion.getFechaInscripcion().toString()));
            vista.mostrarResultado("+-----------------+----------------------+-----------------+");
        }
    }
}
