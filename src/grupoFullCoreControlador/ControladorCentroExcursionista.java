package grupoFullCoreControlador;

import grupoFullCore.modelo.*;
import grupoFullCoreVista.VistaCentroExcursionista;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ControladorCentroExcursionista {
    private CentroExcursionista centro;
    private VistaCentroExcursionista vista;

    public ControladorCentroExcursionista(CentroExcursionista centro, VistaCentroExcursionista vista) {
        this.centro = centro;
        this.vista = vista;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            vista.mostrarMenuPrincipal();
            int opcion = vista.leerOpcion();
            switch (opcion) {
                case 1:
                    gestionarExcursiones();
                    break;
                case 2:
                    gestionarSocios();
                    break;
                case 3:
                    gestionarInscripciones();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    // ----------------------------- GESTIÓN DE EXCURSIONES -----------------------------

    private void gestionarExcursiones() {
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
                case 3:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    private void agregarExcursion() {
        String codigo = vista.leerCodigoExcursion();
        String descripcion = vista.leerDescripcionExcursion();
        String fechaStr = vista.leerFecha();
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
        String fechaInicioStr = vista.leerFecha();
        String fechaFinStr = vista.leerFecha();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
        List<Excursion> excursiones = centro.mostrarExcursionesConFiltro(fechaInicio, fechaFin);
        StringBuilder resultado = new StringBuilder("Excursiones encontradas:\n");
        for (Excursion excursion : excursiones) {
            resultado.append(excursion.toString()).append("\n");
        }
        vista.mostrarResultado(resultado.toString());
    }

    // ----------------------------- GESTIÓN DE SOCIOS -----------------------------

    private void gestionarSocios() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMenuSocios();
            int opcion = vista.leerOpcion();
            switch (opcion) {
                case 1:
                    agregarSocio();
                    break;
                case 2:
                    modificarSeguroSocioEstandar();
                    break;
                case 3:
                    eliminarSocio();
                    break;
                case 4:
                    mostrarSocios();
                    break;
                case 5:
                    mostrarFacturaMensualPorSocio();
                    break;
                case 6:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    private Socio agregarSocio() {
        vista.mostrarTipoSocios();
        int tipoSocio = vista.leerOpcion();
        Socio socio = null;
        switch (tipoSocio) {
            case 1:
                socio = agregarSocioEstandar();
                break;
            case 2:
                socio = agregarSocioFederado();
                break;
            case 3:
                socio = agregarSocioInfantil();
                break;
            default:
                vista.mostrarResultado("Opción de tipo de socio no válida");
                break;
        }
        return socio;
    }

    private Socio agregarSocioEstandar() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();
        String nif = vista.leerNif();

        vista.seleccionarTipoSeguro();
        int opcionSeguro = vista.leerOpcion();

        TipoSeguro seguroEnum = (opcionSeguro == 1) ? TipoSeguro.BASICO : TipoSeguro.COMPLETO;
        double precio = (seguroEnum == TipoSeguro.BASICO) ? 50 : 100;

        Seguro seguro = new Seguro(seguroEnum, precio);
        SocioEstandar socio = new SocioEstandar(numeroSocio, nombre, nif, seguro);
        centro.añadirSocioEstandar(socio);

        vista.mostrarResultado(("Socio Estándar añadido correctamente."));
        return socio;
    }

    private Socio agregarSocioFederado() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();
        String nif = vista.leerNif();

        List<Federacion> federaciones = centro.getFederaciones();
        int opcionFederacion = vista.mostrarFederaciones(federaciones);

        if (opcionFederacion >= 1 && opcionFederacion <= federaciones.size()) {
            Federacion federacionSeleccionada = federaciones.get(opcionFederacion - 1);

            SocioFederado socio = new SocioFederado(numeroSocio, nombre, nif, federacionSeleccionada);
            centro.añadirSocioFederado(socio);
            vista.mostrarResultado("Socio Federado añadido correctamente.");
            return socio;
        } else {
            vista.mostrarResultado("Opción de federación no válida.");
            return null;
        }
    }

    private Socio agregarSocioInfantil() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();

        mostrarSociosEstandarYFederados();
        int numeroSocioProgenitor = vista.leerNumeroSocioProgenitor();

        Socio progenitor = centro.mostrarSocios().stream()
                .filter(s -> (s instanceof SocioEstandar || s instanceof SocioFederado) && s.getNumeroSocio() == numeroSocioProgenitor)
                .findFirst().orElse(null);

        if (progenitor != null) {
            SocioInfantil socioInfantil = new SocioInfantil(numeroSocio, nombre, progenitor);
            centro.añadirSocioInfantil(socioInfantil);
            vista.mostrarResultado("Socio Infantil añadido correctamente.");
            return socioInfantil;
        } else {
            vista.mostrarResultado("Progenitor no encontrado.");
            return null;
        }
    }

    private void modificarSeguroSocioEstandar() {
        mostrarSociosEstandar();
        int numeroSocio = vista.leerNumeroSocio();
        SocioEstandar socio = (SocioEstandar) centro.buscarSocioPorNumero(numeroSocio);

        if (socio == null) {
            vista.mostrarResultado("Error: El socio con número " + numeroSocio + " no existe.");
            return;
        }

        vista.mostrarResultado("Seleccione el tipo de seguro al que quiere cambiar:");
        vista.seleccionarTipoSeguro();
        int opcionSeguro = vista.leerOpcion();

        TipoSeguro nuevoTipoSeguro = (opcionSeguro == 1) ? TipoSeguro.BASICO : TipoSeguro.COMPLETO;
        Seguro nuevoSeguro = new Seguro(nuevoTipoSeguro, (nuevoTipoSeguro == TipoSeguro.BASICO) ? 50 : 100);
        centro.modificarSeguroSocioEstandar(numeroSocio, nuevoSeguro);
        vista.mostrarResultado("Seguro modificado correctamente.");
    }

    private void eliminarSocio() {
        mostrarTodosLosSocios();
        int numeroSocio = vista.leerNumeroSocio();
        try {
            centro.eliminarSocio(numeroSocio);
            vista.mostrarResultado("Socio eliminado correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado(e.getMessage());
        }
    }

    private void mostrarFacturaMensualPorSocio() {
        mostrarTodosLosSocios();
        int numeroSocio = vista.leerNumeroSocio();
        double factura = centro.calcularFacturaMensualPorSocio(numeroSocio);
        vista.mostrarResultado("Factura mensual: " + factura);
    }

    private void mostrarSocios() {
        vista.mostrarOpcionMostrarSocios();
        int opcion = vista.leerOpcion();

        switch (opcion) {
            case 1:
                mostrarTodosLosSocios();
                break;
            case 2:
                vista.mostrarOpcionesFiltrarSocios();
                int tipoSocio = vista.leerOpcion();
                switch (tipoSocio) {
                    case 1:
                        mostrarSociosEstandar();
                        break;
                    case 2:
                        mostrarSociosFederados();
                        break;
                    case 3:
                        mostrarSociosInfantiles();
                        break;
                    default:
                        vista.mostrarResultado("Opción no válida.");
                }
                break;
            default:
                vista.mostrarResultado("Opción no válida.");
        }
    }

    private void mostrarSociosEstandar() {
        List<Socio> socios = centro.mostrarSocios();
        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar) {
                vista.mostrarResultado(socio.toString());
            }
        }
    }

    private void mostrarSociosFederados() {
        List<Socio> socios = centro.mostrarSocios();
        for (Socio socio : socios) {
            if (socio instanceof SocioFederado) {
                vista.mostrarResultado(socio.toString());
            }
        }
    }

    private void mostrarSociosInfantiles() {
        List<Socio> socios = centro.mostrarSocios();
        for (Socio socio : socios) {
            if (socio instanceof SocioInfantil) {
                vista.mostrarResultado(socio.toString());
            }
        }
    }

    private void mostrarSociosEstandarYFederados() {
        List<Socio> socios = centro.mostrarSocios();
        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar || socio instanceof SocioFederado) {
                vista.mostrarResultado(socio.toString());
            }
        }
    }

    private void mostrarTodosLosSocios() {
        List<Socio> socios = centro.mostrarSocios();
        for (Socio socio : socios) {
            vista.mostrarResultado(socio.toString());
        }
    }

    // ----------------------------- GESTIÓN DE INSCRIPCIONES -----------------------------

    private void gestionarInscripciones() {
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
                case 4:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    private void agregarInscripcion() {
        mostrarSocios();
        int numeroSocio = vista.leerNumeroSocio();
        Socio socio = validarExistenciaSocio(numeroSocio);

        if (socio == null) return;

        mostrarExcursiones();
        String codigoExcursion = vista.leerCodigoExcursion();
        Excursion excursion = validarExistenciaExcursion(codigoExcursion);

        if (excursion == null) return;

        int numeroInscripcion = vista.leerNumeroInscripcion();
        Date fecha = new Date();
        Inscripcion inscripcion = new Inscripcion(numeroInscripcion, fecha, socio, excursion);
        centro.añadirInscripcion(inscripcion);
        vista.mostrarResultado("Inscripción añadida correctamente.");
    }

    private void eliminarInscripcion() {
        mostrarExcursiones();
        String codigoExcursion = vista.leerCodigoExcursion();
        Excursion excursion = validarExistenciaExcursion(codigoExcursion);

        if (excursion == null) return;

        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorExcursion(excursion.getCodigo());
        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No hay inscripciones para esta excursión.");
            return;
        }

        vista.mostrarResultado("Inscripciones de la excursión: " + excursion.getDescripcion());
        for (Inscripcion inscripcion : inscripciones) {
            vista.mostrarResultado(inscripcion.toString());
        }

        int numeroInscripcion = vista.leerNumeroInscripcion();
        try {
            centro.eliminarInscripcion(numeroInscripcion);
            vista.mostrarResultado("Inscripción eliminada correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado("Error al eliminar la inscripción: " + e.getMessage());
        }
    }

    private void mostrarInscripciones() {
        vista.mostrarResultado("Introduce las fechas para filtrar las inscripciones.");
        String fechaInicioStr = vista.leerFecha();
        String fechaFinStr = vista.leerFecha();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorFechas(fechaInicio, fechaFin);

        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No se encontraron inscripciones.");
        } else {
            for (Inscripcion inscripcion : inscripciones) {
                vista.mostrarResultado(inscripcion.toString());
            }
        }
    }

    // ----------------------------- MÉTODOS AUXILIARES -----------------------------

    private Socio validarExistenciaSocio(int numeroSocio) {
        Socio socio = centro.buscarSocioPorNumero(numeroSocio);
        if (socio == null) {
            vista.mostrarResultado("Error: El socio con número " + numeroSocio + " no existe.");
        }
        return socio;
    }

    private Excursion validarExistenciaExcursion(String codigoExcursion) {
        List<Excursion> excursiones = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX);
        Excursion excursion = excursiones.stream()
                .filter(e -> e.getCodigo().equals(codigoExcursion))
                .findFirst().orElse(null);
        if (excursion == null) {
            vista.mostrarResultado("Error: La excursión con código " + codigoExcursion + " no existe.");
        }
        return excursion;
    }

    private void mostrarExcursiones() {
        List<Excursion> excursiones = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX);
        for (Excursion excursion : excursiones) {
            vista.mostrarResultado(excursion.toString());
        }
    }
}
