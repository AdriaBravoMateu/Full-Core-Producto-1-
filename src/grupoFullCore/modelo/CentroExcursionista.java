package grupoFullCore.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CentroExcursionista {

    private ArrayList<Socio> socios;              // Lista de socios
    private ArrayList<Excursion> excursiones;     // Lista de excursiones
    private ArrayList<Inscripcion> inscripciones; // Lista de inscripciones
    private List<Federacion> federaciones;        // Lista de federaciones disponibles

    // Constructor que inicializa las listas
    public CentroExcursionista() {
        socios = new ArrayList<>();
        excursiones = new ArrayList<>();
        inscripciones = new ArrayList<>();

        // Precargar federaciones disponibles
        this.federaciones = List.of(
                new Federacion("FED001", "Federación Andaluza"),
                new Federacion("FED002", "Federación Catalana"),
                new Federacion("FED003", "Federación Madrileña")
        );
    }

    // ====================== Gestión de Federaciones ======================
    public List<Federacion> getFederaciones() {
        return federaciones;
    }

    // ==================== Gestión de Excursiones ====================
    public void añadirExcursion(Excursion excursion) {
        excursiones.add(excursion);
    }

    public List<Excursion> mostrarExcursionesConFiltro(LocalDate fechaInicio, LocalDate fechaFin) {
        return excursiones.stream()
                .filter(excursion -> {
                    LocalDate fechaExcursion = excursion.getFecha();  // getFecha() debe devolver un LocalDate
                    return (fechaExcursion.isEqual(fechaInicio) || fechaExcursion.isAfter(fechaInicio)) &&
                            (fechaExcursion.isEqual(fechaFin) || fechaExcursion.isBefore(fechaFin));
                })
                .collect(Collectors.toList());
    }

    // ==================== Gestión de Socios ====================
    public void añadirSocioEstandar(SocioEstandar socio) {
        socios.add(socio);
    }

    public void modificarSeguroSocioEstandar(int numeroSocio, Seguro nuevoSeguro) {
        SocioEstandar socio = (SocioEstandar) buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            socio.modificarSeguro(nuevoSeguro);
        }
    }

    public void añadirSocioFederado(SocioFederado socio) {
        socios.add(socio);
    }

    public void añadirSocioInfantil(SocioInfantil socio) {
        socios.add(socio);
    }

    public void eliminarSocio(int numeroSocio) throws Exception {
        Socio socio = buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            boolean tieneInscripciones = inscripciones.stream().anyMatch(i -> i.getSocio().equals(socio));
            if (tieneInscripciones) {
                throw new Exception("No se puede eliminar un socio con inscripciones activas.");
            } else {
                socios.remove(socio);
            }
        }
    }

    public List<Socio> mostrarSocios() {
        return socios;
    }

    public List<Socio> mostrarSociosPorTipo(String tipo) {
        return socios.stream()
                .filter(socio -> {
                    if (tipo.equalsIgnoreCase("estándar") && socio instanceof SocioEstandar) return true;
                    if (tipo.equalsIgnoreCase("federado") && socio instanceof SocioFederado) return true;
                    if (tipo.equalsIgnoreCase("infantil") && socio instanceof SocioInfantil) return true;
                    return false;
                })
                .collect(Collectors.toList());
    }

    public double calcularFacturaMensualPorSocio(int numeroSocio) {
        Socio socio = buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            List<Inscripcion> inscripcionesDelMes = inscripciones.stream()
                    .filter(i -> i.getSocio().equals(socio) &&
                            i.getExcursion().getFecha().getMonthValue() == LocalDate.now().getMonthValue() &&
                            i.getExcursion().getFecha().getYear() == LocalDate.now().getYear())
                    .collect(Collectors.toList());
            return socio.calcularFacturaMensual(inscripcionesDelMes);
        }
        return 0.0;
    }

    // ==================== Gestión de Inscripciones ====================
    public void añadirInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    public void eliminarInscripcion(int numeroInscripcion) throws Exception {
        Inscripcion inscripcion = inscripciones.stream()
                .filter(i -> i.getNumeroInscripcion() == numeroInscripcion)
                .findFirst()
                .orElse(null);
        if (inscripcion != null) {
            LocalDate fechaExcursion = inscripcion.getExcursion().getFecha();
            if (fechaExcursion.isAfter(LocalDate.now())) {
                inscripciones.remove(inscripcion);
            } else {
                throw new Exception("No se puede eliminar una inscripción de una excursión pasada.");
            }
        }
    }

    public List<Inscripcion> mostrarInscripcionesPorSocio(int numeroSocio) {
        return inscripciones.stream()
                .filter(i -> i.getSocio().getNumeroSocio() == numeroSocio)
                .collect(Collectors.toList());
    }

    public List<Inscripcion> mostrarInscripcionesPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return inscripciones.stream()
                .filter(i -> {
                    LocalDate fechaExcursion = i.getExcursion().getFecha();
                    return (fechaExcursion.isEqual(fechaInicio) || fechaExcursion.isAfter(fechaInicio)) &&
                            (fechaExcursion.isEqual(fechaFin) || fechaExcursion.isBefore(fechaFin));
                })
                .collect(Collectors.toList());
    }

    public List<Inscripcion> mostrarInscripcionesPorExcursion(String codigoExcursion) {
        return inscripciones.stream()
                .filter(inscripcion -> inscripcion.getExcursion().getCodigo().equals(codigoExcursion))
                .collect(Collectors.toList());
    }

    // ==================== Métodos auxiliares ====================
    // Cambiar acceso a público para que sea accesible desde otras clases
    public Socio buscarSocioPorNumero(int numeroSocio) {
        return socios.stream()
                .filter(s -> s.getNumeroSocio() == numeroSocio)
                .findFirst()
                .orElse(null);
    }
}
