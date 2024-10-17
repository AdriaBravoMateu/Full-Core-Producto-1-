package Controlador;

//IMPORT
import grupofc.modelo.*;
import Vista.VistaCentroExcursionista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ControladorCentroExcursionista {
    private CentroExcursionista centro;
    private VistaCentroExcursionista vista;

    //CONSTRUCTOR
    public ControladorCentroExcursionista(CentroExcursionista centro, VistaCentroExcursionista vista) {
        this.centro = centro;
        this.vista = vista;
    }

    // Iniciamos la aplicación con el menú principal
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
    //MÉTODOS DE GESTIÓN DE LAS CLASES


    /* -----------------------------------------------------------------------------------------------------------------
    ----------------------------- GESTIÓN DE EXCURSIONES----------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */

    //Menú EXCURSIONES
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

    // Métodos de controlador de EXCURSIONES

    // AGREGAR EXCURSIONES
    private void agregarExcursion() {
        String codigo = vista.leerCodigoExcursion();
        String descripcion = vista.leerDescripcionExcursion();
        String fechaStr = vista.leerFechaExcursion();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaLocal = LocalDate.parse(fechaStr, formatter);
        LocalDate fecha = java.sql.Date.valueOf(fechaLocal).toLocalDate();
        int dias = vista.leerNumeroDiasExcursion();
        double precio = vista.leerPrecioInscripcion();
        Excursion excursion = new Excursion(codigo, descripcion, fecha, dias, precio);
        centro.añadirExcursion(excursion);
        vista.mostrarResultado("Excursión añadida correctamente.");
    }

    //MOSTRAR EXCURSIONES
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



    /* -----------------------------------------------------------------------------------------------------------------
----------------------------- GESTIÓN DE SOCIOS ------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
 */
    // Menú SOCIOS
    private void gestionarSocios() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMenuSocios();
            int opcion = vista.leerOpcion();
            switch (opcion) {
                case 1:
                    vista.mostrarTipoSocios();
                    int tipoSocio = vista.leerOpcion();
                    switch (tipoSocio){
                        case 1:
                            agregarSocioEstandar();
                            break;
                        case 2:
                            agregarSocioFederado();
                            break;
                        case 3:
                            agregarSocioInfantil();
                            break;
                        default:
                            vista.mostrarResultado("Opción de tipo de socio no válida");
                    }
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

    // Métodos de controlador de SOCIOS

    //AGREGAR SOCIO ESTÁNDAR
    private void agregarSocioEstandar() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();
        String nif = vista.leerNif();

        //Mostramos el menu para seleccionar el tipo
        vista.seleccionarTipoSeguro();
        int opcionSeguro = vista.leerOpcion();

        TipoSeguro seguroEnum;
        double precio;
        switch(opcionSeguro){
            case 1:
                seguroEnum = TipoSeguro.BASICO;
                precio = 50;
                break;
            case 2:
                seguroEnum = TipoSeguro.COMPLETO;
                precio = 100;
                break;
            default:
                vista.mostrarResultado("Opción de seguro no válida. Se asignará el seguro Básico por defecto");
                seguroEnum = TipoSeguro.BASICO;
                precio = 50;
                break;
        }
        // Crear el seguro con el precio adecuado
        Seguro seguro = new Seguro(seguroEnum, precio);

        //Crear el socio estándar
        SocioEstandar socio = new SocioEstandar(numeroSocio,nombre,nif,seguro);

        //Añadir el socio al centro
        centro.añadirSocioEstandar(socio);

        //Mostrar el resultado
        vista.mostrarResultado(("Socio Estándar añadido correctamente."));
    }


    // MODIFICAR SEGURO SOCIO ESTÁNDAR
    private void modificarSeguroSocioEstandar() {
        // Mostrar la lista de socios estándar
        mostrarSociosEstandar();

        int numeroSocio = vista.leerNumeroSocio();

        //Mostramos mensaje aclarativo
        vista.mostrarResultado("Seleccione el tipo de seguro al que quiere cambiar:");

        // Mostramos el menú para seleccionar el tipo de seguro (igual que en agregarSocioEstandar)
        vista.seleccionarTipoSeguro();
        int opcionSeguro = vista.leerOpcion();

        TipoSeguro seguroEnum;
        double precio;
        switch (opcionSeguro) {
            case 1:
                seguroEnum = TipoSeguro.BASICO;
                precio = 50;
                break;
            case 2:
                seguroEnum = TipoSeguro.COMPLETO;
                precio = 100;
                break;
            default:
                vista.mostrarResultado("Opción de seguro no válida. Se asignará el seguro Básico por defecto.");
                seguroEnum = TipoSeguro.BASICO;
                precio = 50;
                break;
        }


        // Crear el nuevo seguro con el tipo y precio seleccionados
        Seguro nuevoSeguro = new Seguro(seguroEnum, precio);

        // Modificar el seguro del socio en el centro
        centro.modificarSeguroSocioEstandar(numeroSocio, nuevoSeguro);

        // Mostrar el resultado
        vista.mostrarResultado("Seguro modificado correctamente.");
    }


    // AGREGAR SOCIO FEDERADO
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


    //AGREGAR SOCIO INFANTIL
    private void agregarSocioInfantil() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = vista.leerNumeroSocio();

        // Mostrar la lista de socios estándar y federados
        mostrarSociosEstandarYFederados();

        // Pedir al usuario que seleccione el número de socio del progenitor
        int numeroSocioProgenitor = vista.leerNumeroSocioProgenitor();

        // Buscar el progenitor en la lista de socios
        Socio progenitor = centro.mostrarSocios().stream()
                .filter(s -> (s instanceof SocioEstandar || s instanceof SocioFederado) && s.getNumeroSocio() == numeroSocioProgenitor)
                .findFirst().orElse(null);

        if (progenitor != null) {
            SocioInfantil socioInfantil = new SocioInfantil(numeroSocio, nombre, progenitor);
            centro.añadirSocioInfantil(socioInfantil);
            vista.mostrarResultado("Socio Infantil añadido correctamente. Progenitor: " + progenitor.getNombre());
        } else {
            vista.mostrarResultado("Progenitor no encontrado. No se puede añadir el socio infantil.");
        }
    }


    // ELIMINAR SOCIO
    private void eliminarSocio() {
        int numeroSocio = vista.leerNumeroSocio();
        try {
            centro.eliminarSocio(numeroSocio);
            vista.mostrarResultado("Socio eliminado correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado(e.getMessage());
        }
    }


    //MOSTRAR SOCIOS
    private void mostrarSocios() {
        List<Socio> socios = centro.mostrarSocios();
        StringBuilder resultado = new StringBuilder("Lista de socios:\n");
        for (Socio socio : socios) {
            resultado.append(socio.toString()).append("\n");
        }
        vista.mostrarResultado(resultado.toString());
    }


    // MOSTRAR FACTURA MENSUAL
    private void mostrarFacturaMensualPorSocio() {
        int numeroSocio = vista.leerNumeroSocio();
        double factura = centro.calcularFacturaMensualPorSocio(numeroSocio);
        vista.mostrarResultado("Factura mensual: " + factura);
    }

    //Métodos Auxiliares SOCIOS

    // MOSTRAR SOCIOS ESTÁNDAR Y FEDERADOS
    private void mostrarSociosEstandarYFederados() {
        List<Socio> socios = centro.mostrarSocios();  // Obtener la lista de todos los socios

        // Cabecera de la tabla
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
        vista.mostrarResultado(String.format("| Número Socio | Nombre               | Tipo       |"));
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));

        // Mostrar cada socio estándar o federado con línea de separación
        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar || socio instanceof SocioFederado) {
                String tipoSocio = socio instanceof SocioEstandar ? "Estándar" : "Federado";
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
                // Añadir una línea de separación entre cada fila de socio
                vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
            }
        }
    }


    // MOSTRAR SOCIOS ESTANDAR
    private void mostrarSociosEstandar() {
        List<Socio> socios = centro.mostrarSocios();  // Obtener la lista de todos los socios

        // Cabecera de la tabla
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
        vista.mostrarResultado(String.format("| Número Socio | Nombre               | Tipo       |"));
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));

        // Mostrar cada socio estándar con línea de separación
        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar) {  // Filtrar solo los socios estándar
                String tipoSocio = "Estándar";
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
                // Añadir una línea de separación entre cada fila de socio
                vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
            }
        }
    }

    /* -----------------------------------------------------------------------------------------------------------------
----------------------------- GESTIÓN DE INSCRIPCIONES -----------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
*/
    // Menú INSCRIPCIONES
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

    // Métodos de controlador de INSCRIPCIONES

    // AGREGAR INSCRIPCIÓN
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


    // ELIMINAR INSCRIPCIÓN
    private void eliminarInscripcion() {
        int numeroInscripcion = vista.leerNumeroInscripcion();
        try {
            centro.eliminarInscripcion(numeroInscripcion);
            vista.mostrarResultado("Inscripción eliminada correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado(e.getMessage());
        }
    }


    // MOSTRAR INSCRIPCIONES
    private void mostrarInscripciones() {
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorFechas(LocalDate.MIN, LocalDate.MAX);
        StringBuilder resultado = new StringBuilder("Lista de inscripciones:\n");
        for (Inscripcion inscripcion : inscripciones) {
            resultado.append(inscripcion.toString()).append("\n");
        }
        vista.mostrarResultado(resultado.toString());
    }
}