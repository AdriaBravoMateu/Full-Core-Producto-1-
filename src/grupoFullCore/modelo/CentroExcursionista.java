package grupoFullCore.modelo;

import grupoFullCore.modelo.DAO.SocioDAO;
import grupoFullCore.modelo.DAO.ExcursionDAO;
import grupoFullCore.modelo.DAO.InscripcionDAO;
import grupoFullCore.modelo.DAO.FederacionDAO;
import grupoFullCore.modelo.DAO.factory.DAOFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CentroExcursionista {
    private SocioDAO socioDAO;
    private ExcursionDAO excursionDAO;
    private InscripcionDAO inscripcionDAO;
    private FederacionDAO federacionDAO;

    // Constructor que inicializa los DAOs
    public CentroExcursionista() {
        this.socioDAO = DAOFactory.getSocioDAO();
        this.excursionDAO = DAOFactory.getExcursionDAO();
        this.inscripcionDAO = DAOFactory.getInscripcionDAO();
        this.federacionDAO = DAOFactory.getFederacionDAO();
    }

    // ====================== Gestión de Federaciones ======================

    public List<Federacion> getFederaciones() {
        return federacionDAO.mostrarFederaciones();
    }

    public void agregarFederacion(Federacion federacion) {
        federacionDAO.agregarFederacion(federacion);
    }

    // ==================== Gestión de Excursiones ====================

    public void añadirExcursion(Excursion excursion) {
        excursionDAO.agregarExcursion(excursion);
    }

    public List<Excursion> mostrarExcursionesConFiltro(LocalDate fechaInicio, LocalDate fechaFin) {
        return excursionDAO.mostrarExcursionesConFiltro(fechaInicio, fechaFin);
    }

    public boolean buscarExcursionPorCodigo(String codigoExcursion) {
        return excursionDAO.buscarExcursionPorCodigo(codigoExcursion) != null;
    }

    public void eliminarExcursion(String codigo) throws Exception {
        excursionDAO.eliminarExcursion(codigo);
    }

    // ==================== Gestión de Socios ====================

    public void añadirSocioEstandar(SocioEstandar socio) {
        socioDAO.agregarSocio(socio);
    }

    public void modificarSeguroSocioEstandar(int numeroSocio, Seguro nuevoSeguro) {
        SocioEstandar socio = (SocioEstandar) socioDAO.buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            socio.modificarSeguro(nuevoSeguro);
            socioDAO.actualizarSocio(socio); // Asumimos que este método existe en el DAO para actualizar datos
        }
    }

    public void añadirSocioFederado(SocioFederado socio) {
        socioDAO.agregarSocio(socio);
    }

    public void añadirSocioInfantil(SocioInfantil socio) {
        socioDAO.agregarSocio(socio);
    }

    public void eliminarSocio(int numeroSocio) throws Exception {
        Socio socio = socioDAO.buscarSocioPorNumero(numeroSocio);
        if (socio == null) {
            throw new Exception("Error: El socio con número " + numeroSocio + " no existe.");
        }

        // Verificar si el socio tiene inscripciones activas antes de eliminar
        if (!inscripcionDAO.mostrarInscripcionesPorSocio(numeroSocio).isEmpty()) {
            throw new Exception("No se puede eliminar un socio con inscripciones activas.");
        } else {
            socioDAO.eliminarSocio(numeroSocio);
        }
    }

    public List<Socio> mostrarSocios() {
        return socioDAO.mostrarSocios();
    }

    public List<Socio> mostrarSociosPorTipo(String tipo) {
        return socioDAO.mostrarSocios().stream()
                .filter(socio -> {
                    if (tipo.equalsIgnoreCase("estándar") && socio instanceof SocioEstandar) return true;
                    if (tipo.equalsIgnoreCase("federado") && socio instanceof SocioFederado) return true;
                    if (tipo.equalsIgnoreCase("infantil") && socio instanceof SocioInfantil) return true;
                    return false;
                })
                .collect(Collectors.toList());
    }

    public double calcularFacturaMensualPorSocio(int numeroSocio) {
        Socio socio = socioDAO.buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            List<Inscripcion> inscripcionesDelMes = inscripcionDAO.mostrarInscripcionesPorSocio(numeroSocio).stream()
                    .filter(i -> {
                        LocalDate fecha = i.getExcursion().getFecha();
                        return fecha.getMonthValue() == LocalDate.now().getMonthValue() &&
                                fecha.getYear() == LocalDate.now().getYear();
                    })
                    .collect(Collectors.toList());
            return socio.calcularFacturaMensual(inscripcionesDelMes);
        }
        return 0.0;
    }

    // ==================== Gestión de Inscripciones ====================

    public void añadirInscripcion(Inscripcion inscripcion) {
        inscripcionDAO.agregarInscripcion(inscripcion);
    }

    public void eliminarInscripcion(int numeroInscripcion) throws Exception {
        Inscripcion inscripcion = inscripcionDAO.buscarInscripcionPorNumero(numeroInscripcion);
        if (inscripcion != null && inscripcion.getExcursion().getFecha().isAfter(LocalDate.now())) {
            inscripcionDAO.eliminarInscripcion(numeroInscripcion);
        } else {
            throw new Exception("No se puede eliminar una inscripción de una excursión pasada.");
        }
    }

    public List<Inscripcion> mostrarInscripcionesPorSocio(int numeroSocio) {
        return inscripcionDAO.mostrarInscripcionesPorSocio(numeroSocio);
    }

    public List<Inscripcion> mostrarInscripcionesPorFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return inscripcionDAO.mostrarInscripcionesPorFecha(fechaInicio, fechaFin);
    }

    public List<Inscripcion> mostrarInscripcionesPorExcursion(String codigoExcursion) {
        return inscripcionDAO.mostrarInscripcionesPorExcursion(codigoExcursion);
    }
}
