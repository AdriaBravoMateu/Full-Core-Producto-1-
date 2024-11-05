package grupoFullCoreControlador;

import grupoFullCore.modelo.*;
import grupoFullCore.modelo.DAO.InscripcionDAO;
import grupoFullCore.modelo.DAO.SocioDAO;
import grupoFullCore.modelo.DAO.ExcursionDAO;
import grupoFullCore.modelo.DAO.factory.DAOFactory;
import grupoFullCoreVista.VistaInscripcion;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ControladorInscripcion {
    private InscripcionDAO inscripcionDAO;
    private SocioDAO socioDAO;
    private ExcursionDAO excursionDAO;
    private VistaInscripcion vista;
    private ControladorSocio controladorSocio;
    private ControladorExcursion controladorExcursion;

    public ControladorInscripcion(VistaInscripcion vista, ControladorSocio controladorSocio, ControladorExcursion controladorExcursion) {
        this.inscripcionDAO = DAOFactory.getInscripcionDAO(); // Acceso a InscripcionDAO
        this.socioDAO = DAOFactory.getSocioDAO(); // Acceso a SocioDAO
        this.excursionDAO = DAOFactory.getExcursionDAO(); // Acceso a ExcursionDAO
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
        controladorSocio.mostrarTodosLosSocios();
        int numeroSocio = vista.leerNumeroSocio();
        Socio socio = socioDAO.buscarSocioPorNumero(numeroSocio);

        if (socio == null) {
            vista.mostrarResultado("Socio no encontrado. Se procederá a añadir un nuevo socio.");
            socio = controladorSocio.agregarSocio();
            if (socio == null) {
                vista.mostrarResultado("Se ha cancelado el proceso de inscripción.");
                return;
            }
        }

        controladorExcursion.mostrarExcursiones(excursionDAO.mostrarExcursiones());
        String codigoExcursion = vista.leerCodigoExcursion();
        Excursion excursion = excursionDAO.buscarExcursionPorCodigo(codigoExcursion);

        if (excursion != null) {
            List<Inscripcion> inscripciones = inscripcionDAO.mostrarInscripcionesPorFecha(LocalDate.MIN, LocalDate.MAX);
            mostrarListaInscripciones(inscripciones);
            int numeroInscripcion;
            do {
                numeroInscripcion = vista.leerNumeroInscripcion();
                if (inscripcionDAO.buscarInscripcionPorNumero(numeroInscripcion) != null) {
                    vista.mostrarResultado("El número de inscripción ya existe. Introduzca otro número.");
                }
            } while (inscripcionDAO.buscarInscripcionPorNumero(numeroInscripcion) != null);

            Inscripcion inscripcion = new Inscripcion(numeroInscripcion, LocalDate.now(), socio, excursion);
            inscripcionDAO.agregarInscripcion(inscripcion);
            vista.mostrarResultado("Inscripción añadida correctamente.");
        } else {
            vista.mostrarResultado("Excursión no encontrada.");
        }
    }

    private void eliminarInscripcion() {
        controladorExcursion.mostrarExcursiones(excursionDAO.mostrarExcursiones());
        String codigoExcursion = vista.leerCodigoExcursion();
        Excursion excursion = excursionDAO.buscarExcursionPorCodigo(codigoExcursion);

        if (excursion == null) {
            vista.mostrarResultado("Excursión no encontrada.");
            return;
        }

        List<Inscripcion> inscripciones = inscripcionDAO.mostrarInscripcionesPorExcursion(excursion.getCodigo());
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
            inscripcionDAO.eliminarInscripcion(inscripcion.getNumeroInscripcion());
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
                inscripciones = inscripcionDAO.mostrarInscripciones();
                break;
            case 2:
                controladorSocio.mostrarTodosLosSocios();
                int numeroSocio = vista.leerNumeroSocio();
                inscripciones = inscripcionDAO.mostrarInscripcionesPorSocio(numeroSocio);
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
            }
            vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");
        } else {
            vista.mostrarResultado("No se encontraron inscripciones.");
        }
    }

    private List<Inscripcion> mostrarInscripcionesPorFechas() {
        vista.mostrarResultado("Introduce las fechas para filtrar las inscripciones.");
        LocalDate fechaInicio = vista.leerFechaInsc();
        LocalDate fechaFin = vista.leerFechaInsc();

        if (fechaInicio.isAfter(fechaFin)) {
            vista.mostrarResultado("La fecha de inicio no puede ser posterior a la fecha de fin.");
            return List.of(); // Lista vacía en caso de error
        }

        return inscripcionDAO.mostrarInscripcionesPorFecha(fechaInicio, fechaFin);
    }

    private void mostrarInscripcionesDeExcursion(Excursion excursion) {
        List<Inscripcion> inscripciones = inscripcionDAO.mostrarInscripcionesPorExcursion(excursion.getCodigo());

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
        }
        vista.mostrarResultado("+-----------------+----------------------+-----------------+");
    }
}
