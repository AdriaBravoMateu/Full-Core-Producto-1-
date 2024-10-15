import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Clase gestora que maneja la gestión de Excursiones, Socios e Inscripciones
public class CentroExcursionista {

    private ArrayList<Socio> socios;              // Lista de socios
    private ArrayList<Excursion> excursiones;     // Lista de excursiones
    private ArrayList<Inscripcion> inscripciones; // Lista de inscripciones

    // Constructor que inicializa las listas
    public CentroExcursionista() {
        socios = new ArrayList<>();
        excursiones = new ArrayList<>();
        inscripciones = new ArrayList<>();
    }

    // ==================== Gestión de Excursiones ====================

    // Añadir una excursión al sistema
    public void añadirExcursion(Excursion excursion) {
        excursiones.add(excursion);
        System.out.println("Excursión añadida: " + excursion.getDescripcion());
    }

    // Mostrar excursiones dentro de un rango de fechas
    public List<Excursion> mostrarExcursionesConFiltro(LocalDate fechaInicio, LocalDate fechaFin) {
        return excursiones.stream()
                .filter(excursion -> {
                    LocalDate fechaExcursion = excursion.getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    return (fechaExcursion.isEqual(fechaInicio) || fechaExcursion.isAfter(fechaInicio)) &&
                            (fechaExcursion.isEqual(fechaFin) || fechaExcursion.isBefore(fechaFin));
                })
                .collect(Collectors.toList());
    }

    // ==================== Gestión de Socios ====================

    // Añadir un nuevo socio estándar
    public void añadirSocioEstandar(SocioEstandar socio) {
        socios.add(socio);
        System.out.println("Socio estándar añadido: " + socio.getNombre());
    }

    // Modificar el seguro de un socio estándar
    public void modificarSeguroSocioEstandar(int numeroSocio, Seguro nuevoSeguro) {
        SocioEstandar socio = (SocioEstandar) buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            socio.modificarSeguro(nuevoSeguro);
            System.out.println("Seguro del socio estándar " + socio.getNombre() + " modificado.");
        } else {
            System.out.println("Socio no encontrado.");
        }
    }

    // Añadir un nuevo socio federado
    public void añadirSocioFederado(SocioFederado socio) {
        socios.add(socio);
        System.out.println("Socio federado añadido: " + socio.getNombre());
    }

    // Añadir un nuevo socio infantil
    public void añadirSocioInfantil(SocioInfantil socio) {
        socios.add(socio);
        System.out.println("Socio infantil añadido: " + socio.getNombre());
    }

    // Eliminar un socio si no tiene inscripciones
    public void eliminarSocio(int numeroSocio) throws Exception {
        Socio socio = buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            boolean tieneInscripciones = inscripciones.stream().anyMatch(i -> i.getSocio().equals(socio));
            if (tieneInscripciones) {
                throw new Exception("No se puede eliminar un socio con inscripciones activas.");
            } else {
                socios.remove(socio);
                System.out.println("Socio eliminado: " + socio.getNombre());
            }
        } else {
            System.out.println("Socio no encontrado.");
        }
    }

    // ==================== Mostrar información de socios ====================

    // Mostrar todos los socios
    public List<Socio> mostrarSocios() {
        return socios;
    }

    // Mostrar socios por tipo (estándar, federado, infantil)
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

    // Mostrar la factura mensual de un socio específico
    public void mostrarFacturaMensualPorSocio(int numeroSocio) {
        Socio socio = buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            double factura = socio.calcularFacturaMensual();
            System.out.println("Factura mensual del socio " + socio.getNombre() + ": " + factura + "€");
        } else {
            System.out.println("Socio no encontrado.");
        }
    }

    // ==================== Gestión de Inscripciones ====================

    // Añadir una nueva inscripción
    public void añadirInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
        System.out.println("Inscripción añadida para el socio: " + inscripcion.getSocio().getNombre());
    }

    // Eliminar una inscripción si la fecha de la excursión es posterior a la fecha actual
    public void eliminarInscripcion(int numeroInscripcion) throws Exception {
        Inscripcion inscripcion = inscripciones.stream()
                .filter(i -> i.getNumeroInscripcion() == numeroInscripcion)
                .findFirst()
                .orElse(null);
        if (inscripcion != null) {
            LocalDate fechaExcursion = inscripcion.getExcursion().getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            if (fechaExcursion.isAfter(LocalDate.now())) {
                inscripciones.remove(inscripcion);
                System.out.println("Inscripción eliminada: " + inscripcion.getSocio().getNombre());
            } else {
                throw new Exception("No se puede eliminar una inscripción de una excursión pasada.");
            }
        } else {
            System.out.println("Inscripción no encontrada.");
        }
    }

    // Mostrar inscripciones filtradas por socio
    public List<Inscripcion> mostrarInscripcionesPorSocio(int numeroSocio) {
        return inscripciones.stream()
                .filter(i -> i.getSocio().getNumeroSocio() == numeroSocio)
                .collect(Collectors.toList());
    }

    // Mostrar inscripciones filtradas por fechas
    public List<Inscripcion> mostrarInscripcionesPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return inscripciones.stream()
                .filter(i -> {
                    LocalDate fechaExcursion = i.getExcursion().getFecha().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    return (fechaExcursion.isEqual(fechaInicio) || fechaExcursion.isAfter(fechaInicio)) &&
                            (fechaExcursion.isEqual(fechaFin) || fechaExcursion.isBefore(fechaFin));
                })
                .collect(Collectors.toList());
    }

    // ==================== Métodos auxiliares ====================

    // Buscar un socio por número de socio
    private Socio buscarSocioPorNumero(int numeroSocio) {
        return socios.stream()
                .filter(s -> s.getNumeroSocio() == numeroSocio)
                .findFirst()
                .orElse(null);
    }
}
