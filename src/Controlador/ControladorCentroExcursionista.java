package Controlador;

import grupofc.modelo.*;
import Vista.VistaCentroExcursionista;

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

    // Iniciar la aplicación con el menú principal
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

    // Gestión de Excursiones
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
        String fechaStr = vista.leerFechaExcursion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaLocal = LocalDate.parse(fechaStr, formatter);
        Date fecha = java.sql.Date.valueOf(fechaLocal);
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
        List<Excursion> excursiones = centro.mostrarExcursionesConFiltro(fechaInicio, fechaFin);
        StringBuilder resultado = new StringBuilder("Excursiones encontradas:\n");
        for (Excursion excursion : excursiones) {
            resultado.append(excursion.toString()).append("\n");
        }
        vista.mostrarResultado(resultado.toString());
    }

    // Gestión de Socios
    private void gestionarSocios() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMenuSocios();
            int opcion = vista.leerOpcion();
            switch (opcion) {
                case 1:
                    agregarSocioEstandar();
                    break;
                case 2:
                    modificarSeguroSocioEstandar();
                    break;
                case 3:
                    agregarSocioFederado();
                    break;
                case 4:
                    agregarSocioInfantil();
                    break;
                case 5:
                    eliminarSocio();
                    break;
                case 6:
                    mostrarSocios();
                    break;
                case 7:
                    mostrarFacturaMensualPorSocio();
                    break;
                case 8:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    private void agregarSocioEstandar() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();
        String nif = vista.leerNif();
        String tipoSeguro = vista.leerTipoSeguro();
        TipoSeguro seguroEnum = TipoSeguro.valueOf(tipoSeguro.toUpperCase());
        Seguro seguro = new Seguro(seguroEnum, seguroEnum == TipoSeguro.Basico ? 50 : 100);
        SocioEstandar socio = new SocioEstandar(numeroSocio, nombre, nif, seguro);
        centro.añadirSocioEstandar(socio);
        vista.mostrarResultado("Socio Estándar añadido correctamente.");
    }

    private void modificarSeguroSocioEstandar() {
        int numeroSocio = vista.leerNumeroSocio();
        String tipoSeguro = vista.leerTipoSeguro();
        TipoSeguro seguroEnum = TipoSeguro.valueOf(tipoSeguro.toUpperCase());
        Seguro nuevoSeguro = new Seguro(seguroEnum, seguroEnum == TipoSeguro.Basico ? 50 : 100);
        centro.modificarSeguroSocioEstandar(numeroSocio, nuevoSeguro);
        vista.mostrarResultado("Seguro modificado correctamente.");
    }

    private void agregarSocioFederado() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();
        String nif = vista.leerNif();
        String nombreFederacion = vista.leerFederacion();
        Federacion federacion = new Federacion("FED001", nombreFederacion);
        SocioFederado socio = new SocioFederado(numeroSocio, nombre, nif, federacion);
        centro.añadirSocioFederado(socio);
        vista.mostrarResultado("Socio Federado añadido correctamente.");
    }

    private void agregarSocioInfantil() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();
        int numeroSocioProgenitor = vista.leerNumeroSocioProgenitor();
        Socio progenitor = centro.mostrarSocios().stream()
                .filter(s -> s.getNumeroSocio() == numeroSocioProgenitor)
                .findFirst().orElse(null);
        if (progenitor != null) {
            SocioInfantil socio = new SocioInfantil(numeroSocio, nombre, progenitor);
            centro.añadirSocioInfantil(socio);
            vista.mostrarResultado("Socio Infantil añadido correctamente.");
        } else {
            vista.mostrarResultado("Progenitor no encontrado.");
        }
    }

    private void eliminarSocio() {
        int numeroSocio = vista.leerNumeroSocio();
        try {
            centro.eliminarSocio(numeroSocio);
            vista.mostrarResultado("Socio eliminado correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado(e.getMessage());
        }
    }

    private void mostrarSocios() {
        List<Socio> socios = centro.mostrarSocios();
        StringBuilder resultado = new StringBuilder("Lista de socios:\n");
        for (Socio socio : socios) {
            resultado.append(socio.toString()).append("\n");
        }
        vista.mostrarResultado(resultado.toString());
    }

    private void mostrarFacturaMensualPorSocio() {
        int numeroSocio = vista.leerNumeroSocio();
        double factura = centro.calcularFacturaMensualPorSocio(numeroSocio);
        vista.mostrarResultado("Factura mensual: " + factura);
    }

    // Gestión de Inscripciones
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
        int numeroSocio = vista.leerNumeroSocio();
        String codigoExcursion = vista.leerCodigoExcursion();
        Socio socio = centro.mostrarSocios().stream()
                .filter(s -> s.getNumeroSocio() == numeroSocio)
                .findFirst().orElse(null);
        Excursion excursion = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX).stream()
                .filter(e -> e.getCodigo().equals(codigoExcursion))
                .findFirst().orElse(null);

        if (socio != null && excursion != null) {
            int numeroInscripcion = vista.leerNumeroInscripcion();
            Date fecha = java.sql.Date.valueOf(LocalDate.now());
            Inscripcion inscripcion = new Inscripcion(numeroInscripcion, fecha, socio, excursion);
            centro.añadirInscripcion(inscripcion);
            vista.mostrarResultado("Inscripción añadida correctamente.");
        } else {
            vista.mostrarResultado("Socio o excursión no encontrados.");
        }
    }

    private void eliminarInscripcion() {
        int numeroInscripcion = vista.leerNumeroInscripcion();
        try {
            centro.eliminarInscripcion(numeroInscripcion);
            vista.mostrarResultado("Inscripción eliminada correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado(e.getMessage());
        }
    }

    private void mostrarInscripciones() {
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorFechas(LocalDate.MIN, LocalDate.MAX);
        StringBuilder resultado = new StringBuilder("Lista de inscripciones:\n");
        for (Inscripcion inscripcion : inscripciones) {
            resultado.append(inscripcion.toString()).append("\n");
        }
        vista.mostrarResultado(resultado.toString());
    }
}