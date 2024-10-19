package grupoFullCoreControlador;

//IMPORT
import grupoFullCore.modelo.*;
import grupoFullCoreVista.VistaCentroExcursionista;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                case 0:
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
                case 0:
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
        String codigo;
        do {
            codigo = vista.leerCodigoExcursion();
            if (centro.buscarExcursionPorCodigo(codigo)) {
                vista.mostrarResultado("El código de excursión ya existe. Introduzca otro código.");
            }
        } while (centro.buscarExcursionPorCodigo(codigo));

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

    //MOSTRAR EXCURSIONES
    private void mostrarExcursionesConFiltro() {
        vista.mostrarResultado("Introduce las fechas para filtrar las excursiones.");
        String fechaInicioStr = vista.leerFecha();
        String fechaFinStr = vista.leerFecha();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
        List<Excursion> excursionesFiltradas = centro.mostrarExcursionesConFiltro(fechaInicio, fechaFin);
        mostrarExcursiones(excursionesFiltradas);

    }

    //SOBRECARGA MÉTODO
    private void mostrarExcursiones() {
        List<Excursion> excursiones = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX);
        mostrarExcursiones(excursiones); // Llamada a la nueva función que acepta una lista
    }

    // Nuevo método que acepta una lista de excursiones
    private void mostrarExcursiones(List<Excursion> excursiones) {
        if (excursiones.isEmpty()) {
            vista.mostrarResultado("No se encontraron excursiones.");
            return;
        }

        // Cabecera de la tabla con las nuevas columnas "Días" y "Precio"
        String formato = "| %-10s | %-20s | %-10s | %-5d | %-10.2f |\n";
        vista.mostrarResultado("+------------+----------------------+-------------+-------+-----------+");
        vista.mostrarResultado("| Código     | Descripción           | Fecha      | Días  | Precio    |");
        vista.mostrarResultado("+------------+----------------------+-------------+-------+-----------+");

        // Mostrar cada excursión con los nuevos datos
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
                case 0:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    // Métodos de controlador de SOCIOS

    //MENÚ AÑADIR NOMBRE
    private Socio agregarSocio() {
        Socio socio = null;
        boolean cancelar = false;
        while (!cancelar) {
            vista.mostrarTipoSocios();
            int tipoSocio = vista.leerOpcion();
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
                case 0:
                    cancelar = true;
                    break;
                default:
                    vista.mostrarResultado("Opción de tipo de socio no válida");
                    break;
            }
            if (socio != null){
                return socio;
            }
        }
        return null;
    }

    //AGREGAR SOCIO ESTÁNDAR
    private Socio agregarSocioEstandar() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = solicitarNumeroSocioValido();  // Comprueba que no exista el número

        String nif = vista.leerNif();
        TipoSeguro seguroEnum = seleccionarSeguro();
        Seguro seguro = new Seguro(seguroEnum);

        SocioEstandar socio = new SocioEstandar(numeroSocio, nombre, nif, seguro);
        centro.añadirSocioEstandar(socio);

        vista.mostrarResultado("Socio Estándar añadido correctamente.");
        return socio;
    }

    private TipoSeguro seleccionarSeguro() {
        TipoSeguro seguroEnum = null;
        boolean opcionValida = false;

        while (!opcionValida) {
            vista.seleccionarTipoSeguro();
            int opcionSeguro = vista.leerOpcion();

            switch (opcionSeguro) {
                case 1:
                    seguroEnum = TipoSeguro.BASICO;
                    opcionValida = true;
                    break;
                case 2:
                    seguroEnum = TipoSeguro.COMPLETO;
                    opcionValida = true;
                    break;
                default:
                    vista.mostrarResultado("Opción de seguro no válida. Por favor, elija una opción correcta.");
                    break;
            }
        }

        return seguroEnum;
    }


    // MODIFICAR SEGURO SOCIO ESTÁNDAR
    private void modificarSeguroSocioEstandar() {
        mostrarSociosEstandar();

        int numeroSocio = vista.leerNumeroSocio();

        vista.mostrarResultado("Selecciona el nuevo tipo de seguro:");

        TipoSeguro nuevoSeguroEnum = seleccionarSeguro();

        Seguro nuevoSeguro = new Seguro(nuevoSeguroEnum);

        centro.modificarSeguroSocioEstandar(numeroSocio, nuevoSeguro);

        vista.mostrarResultado("Seguro modificado correctamente.");
    }


    // AGREGAR SOCIO FEDERADO
    private Socio agregarSocioFederado() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = solicitarNumeroSocioValido();  /// Comprueba que no exista el número

        String nif = vista.leerNif();
        List<Federacion> federaciones = centro.getFederaciones();
        int opcionFederacion = vista.mostrarFederaciones(federaciones);

        SocioFederado socio = null;
        if (opcionFederacion >= 1 && opcionFederacion <= federaciones.size()) {
            Federacion federacionSeleccionada = federaciones.get(opcionFederacion - 1);
            socio = new SocioFederado(numeroSocio, nombre, nif, federacionSeleccionada);
            centro.añadirSocioFederado(socio);
            vista.mostrarResultado("Socio Federado añadido correctamente.");
        } else {
            vista.mostrarResultado("Opción de federación no válida.");
        }
        return socio;
    }


    //AGREGAR SOCIO INFANTIL
    private Socio agregarSocioInfantil() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = solicitarNumeroSocioValido();  // Comprueba que no exista el número

        mostrarSociosEstandarYFederados();
        int numeroSocioProgenitor = vista.leerNumeroSocioProgenitor();

        Socio progenitor = centro.mostrarSocios().stream()
                .filter(s -> (s instanceof SocioEstandar || s instanceof SocioFederado) && s.getNumeroSocio() == numeroSocioProgenitor)
                .findFirst().orElse(null);

        SocioInfantil socioInfantil = null;
        if (progenitor != null) {
            socioInfantil = new SocioInfantil(numeroSocio, nombre, progenitor);
            centro.añadirSocioInfantil(socioInfantil);
            vista.mostrarResultado("Socio Infantil añadido correctamente. Progenitor: " + progenitor.getNombre());
        } else {
            vista.mostrarResultado("Progenitor no encontrado. No se puede añadir el socio infantil.");
        }
        return socioInfantil;
    }

    //Método para asegurarse que el número de socio no exista
    private int solicitarNumeroSocioValido() {
        int numeroSocio;
        do {
            numeroSocio = vista.leerNumeroSocio();
            if (centro.buscarSocioPorNumero(numeroSocio) != null) {
                vista.mostrarResultado("El número de socio ya existe. Introduzca otro número.");
            }
        } while (centro.buscarSocioPorNumero(numeroSocio) != null);

        return numeroSocio;
    }



    // ELIMINAR SOCIO
    private void eliminarSocio() {
        mostrarTodosLosSocios();  // Mostrar todos los socios antes de eliminar
        int numeroSocio = vista.leerNumeroSocio();  // Leer el número del socio
        Socio socioAEliminar = centro.buscarSocioPorNumero(numeroSocio);  // Buscar socio

        if (socioAEliminar == null) {
            vista.mostrarResultado("Error: El socio con número " + numeroSocio + " no existe.");
        } else {
            try {
                centro.eliminarSocio(numeroSocio);  // Eliminar el socio
                vista.mostrarResultado("Socio eliminado correctamente.");
            } catch (Exception e) {
                vista.mostrarResultado("Error al eliminar el socio: " + e.getMessage());
            }
        }
    }



    //MOSTRAR SOCIOS
    private void mostrarSocios() {
        boolean cancelar = false;
        while (!cancelar) {
            vista.mostrarOpcionMostrarSocios();  // Muestra el menú "Todos los Socios o Filtrar por tipo"
            int opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    mostrarTodosLosSocios();  // Llama al metodo para mostrar todos los socios
                    break;
                case 2:
                    vista.mostrarOpcionesFiltrarSocios();  // Muestra el submenú para seleccionar el tipo de socio
                    int tipoSocio = vista.leerOpcion();
                    switch (tipoSocio) {
                        case 1:
                            mostrarSociosEstandar();  // Muestra solo los socios estándar
                            break;
                        case 2:
                            mostrarSociosFederados();  // Muestra solo los socios federados
                            break;
                        case 3:
                            mostrarSociosInfantiles();  // Muestra solo los socios infantiles
                            break;
                        case 0:
                            cancelar = true;
                            break;
                        default:
                            vista.mostrarResultado("Opción no válida. Ingrese un número válido.");
                            break;
                    }
                    break;
                case 0:
                    cancelar = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida. Ingrese un número válido.");
                    break;
            }
        }
    }

    // MOSTRAR FACTURA MENSUAL
    private void mostrarFacturaMensualPorSocio() {
        mostrarTodosLosSocios();
        int numeroSocio = vista.leerNumeroSocio();
        Socio socio = centro.buscarSocioPorNumero(numeroSocio);
        if (socio == null) {
            vista.mostrarResultado("El número de socio no existe.");
            return;
        }
        double factura = centro.calcularFacturaMensualPorSocio(numeroSocio);
        vista.mostrarResultado("Factura del mes actual: " + factura);
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

    private void mostrarSociosFederados() {
        List<Socio> socios = centro.mostrarSocios();  // Obtener la lista de todos los socios

        // Cabecera de la tabla
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
        vista.mostrarResultado(String.format("| Número Socio | Nombre               | Tipo       |"));
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));

        // Mostrar cada socio estándar con línea de separación
        for (Socio socio : socios) {
            if (socio instanceof SocioFederado) {  // Filtrar solo los socios federados
                String tipoSocio = "Federado";
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
                // Añadir una línea de separación entre cada fila de socio
                vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
            }
        }
    }

    private void mostrarSociosInfantiles() {
        List<Socio> socios = centro.mostrarSocios();  // Obtener la lista de todos los socios

        // Cabecera de la tabla
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
        vista.mostrarResultado(String.format("| Número Socio | Nombre               | Tipo       |"));
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));

        // Mostrar cada socio estándar con línea de separación
        for (Socio socio : socios) {
            if (socio instanceof SocioInfantil) {  // Filtrar solo los socios infantiles
                String tipoSocio = "Infantil";
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
                // Añadir una línea de separación entre cada fila de socio
                vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
            }
        }
    }

    private void mostrarTodosLosSocios() {
        List<Socio> socios = centro.mostrarSocios();  // Obtener la lista de todos los socios

        // Cabecera de la tabla
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
        vista.mostrarResultado(String.format("| Número Socio | Nombre               | Tipo       |"));
        vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));

        // Mostrar cada socio con línea de separación
        for (Socio socio : socios) {
            String tipoSocio = socio instanceof SocioEstandar ? "Estándar" :
                    socio instanceof SocioFederado ? "Federado" : "Infantil";
            vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
            vista.mostrarResultado(String.format("+--------------+----------------------+------------+"));
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
                case 0:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    // Métodos de controlador de INSCRIPCIONES

    // AGREGAR INSCRIPCIÓN
// AGREGAR INSCRIPCIÓN
    private void agregarInscripcion() {
        // Mostrar la tabla de socios antes de leer el número de socio
        mostrarTodosLosSocios();  // Mostrar la tabla de todos los socios

        int numeroSocio = vista.leerNumeroSocio();

        // Buscar el socio en la lista de socios
        Socio socio = centro.mostrarSocios().stream()
                .filter(s -> s.getNumeroSocio() == numeroSocio)
                .findFirst().orElse(null);

        // Si el socio no existe, permitir agregarlo
        if (socio == null) {
            vista.mostrarResultado("Socio no encontrado. Se procederá a añadir un nuevo socio.\n");
            socio = agregarSocio();
            if (socio == null){
                vista.mostrarResultado("Se ha cancelado el proceso de inscripción.\n");
                return;
            }
        }

        // Mostrar la tabla de excursiones antes de pedir el código de excursión
        mostrarExcursiones();  // Mostrar la tabla de excursiones disponibles

        String codigoExcursion = vista.leerCodigoExcursion();
        // Buscar la excursión
        Excursion excursion = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX).stream()
                .filter(e -> e.getCodigo().equals(codigoExcursion))
                .findFirst().orElse(null);

        // Continuar con la inscripción si la excursión existe
        if (excursion != null) {
            //Mostrar inscripciones
            mostrarInscripcionesPorFechas();
           //Comprobar que el número de inscripción no exista
            int numeroInscripcion;
            do {
                numeroInscripcion = vista.leerNumeroInscripcion();
                if (centro.buscarInscripcionPorNumero(numeroInscripcion) != null) {
                    vista.mostrarResultado("El número de inscripción ya existe. Introduzca otro número.");
                }
            } while (centro.buscarInscripcionPorNumero(numeroInscripcion) != null);

            Date fecha = java.sql.Date.valueOf(LocalDate.now());
            Inscripcion inscripcion = new Inscripcion(numeroInscripcion, fecha.toLocalDate(), socio, excursion);
            centro.añadirInscripcion(inscripcion);
            vista.mostrarResultado("Inscripción añadida correctamente.");
        } else {
            vista.mostrarResultado("Excursión no encontrada.");
        }
    }


    // ELIMINAR INSCRIPCIÓN
    private void eliminarInscripcion() {
        // Obtener la lista de excursiones primero
        List<Excursion> excursiones = centro.mostrarExcursionesConFiltro(LocalDate.MIN, LocalDate.MAX);

        // Verificar si hay excursiones disponibles
        if (excursiones.isEmpty()) {
            vista.mostrarResultado("No se han encontrado excursiones programadas.");
            return;
        }

        // Mostrar la tabla de todas las excursiones
        mostrarExcursiones();  // Función que muestra las excursiones en una tabla

        // Pedir al usuario que seleccione el código de la excursión
        String codigoExcursion = vista.leerCodigoExcursion();

        // Buscar la excursión seleccionada
        Excursion excursion = excursiones.stream()
                .filter(e -> e.getCodigo().equals(codigoExcursion))
                .findFirst().orElse(null);

        if (excursion == null) {
            vista.mostrarResultado("Excursión no encontrada.");
            return;
        }

        // Obtener las inscripciones de la excursión
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorExcursion(excursion.getCodigo());

        // Verificar si hay inscripciones para la excursión
        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No hay inscripciones para esta excursión.");
            return;
        }

        // Mostrar las inscripciones para la excursión seleccionada
        mostrarInscripcionesDeExcursion(excursion);

        // Pedir al usuario que seleccione la inscripción a eliminar
        int numeroInscripcion = vista.leerNumeroInscripcion();

        // Buscar la inscripción seleccionada
        Inscripcion inscripcion = inscripciones.stream()
                .filter(i -> i.getNumeroInscripcion() == numeroInscripcion)
                .findFirst().orElse(null);

        // Verificar si la inscripción existe
        if (inscripcion == null) {
            vista.mostrarResultado("No existe ninguna inscripción con ese código.");
            return;
        }

        // Verificar si quedan menos de 24 horas para la excursión
        LocalDateTime fechaExcursion = excursion.getFecha().atStartOfDay();
        LocalDateTime ahora = LocalDateTime.now();

        if (Duration.between(ahora, fechaExcursion).toHours() < 24) {
            vista.mostrarResultado("No se pudo eliminar la inscripción con tan poca antelación.");
            return;
        }

        // Si quedan más de 24 horas, proceder a eliminar la inscripción
        try {
            centro.eliminarInscripcion(inscripcion.getNumeroInscripcion());
            vista.mostrarResultado("Inscripción eliminada correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado("Error al eliminar la inscripción: " + e.getMessage());
        }
    }


    // MOSTRAR INSCRIPCIONES
    private void mostrarInscripciones() {
        boolean cancelar = false;

        while (!cancelar) {
            // Mostrar el menú
            int opcion = vista.mostrarFiltroInscripciones();
            List<Inscripcion> inscripciones = null;

            switch (opcion) {
                case 1:
                    inscripciones = mostrarInscripcionesPorFechas();
                    break;
                case 2:
                    inscripciones = mostrarInscripcionesPorSocio();
                    break;
                case 3:
                    inscripciones = mostrarInscripcionesPorFechaConcreta();
                    break;
                case 0:
                    cancelar = true;
                    vista.mostrarResultado("Regresando al menú anterior...");
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
                    break;
            }

            // Verificar que las inscripciones no sean null antes de intentar mostrarlas
            if (inscripciones != null) {
                if (inscripciones.isEmpty()) {
                    vista.mostrarResultado("No se encontraron inscripciones.");
                }
            }
        }
    }

    private List<Inscripcion> mostrarInscripcionesPorFechas() {
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorFechas(LocalDate.MIN, LocalDate.MAX);

        // Cabecera de la tabla
        if (!inscripciones.isEmpty()) {
            String formato = "| %-15s | %-20s | %-15s | %20s |\n";
            vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");
            vista.mostrarResultado("| Nº Inscripción  | Nombre del Socio     | Excursión       | Fecha Inscripción    |");
            vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");

            // Mostrar cada inscripción
            for (Inscripcion inscripcion : inscripciones) {
                vista.mostrarResultado(String.format(formato,
                        inscripcion.getNumeroInscripcion(),
                        inscripcion.getSocio().getNombre(),
                        inscripcion.getExcursion().getDescripcion(),
                        inscripcion.getFechaInscripcion().toString()));
                        vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");
            }
        } else {
            vista.mostrarResultado("No se encontraron inscripciones.");
        }
        return inscripciones;
    }

    private List<Inscripcion> mostrarInscripcionesPorSocio() {
        int numeroSocio = vista.leerNumeroSocio();
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorSocio(numeroSocio);

        // Comprobar si hay inscripciones
        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No hay inscripciones para este socio.");
            return inscripciones; // Retorna la lista vacía
        }

        // Cabecera de la tabla
        String formato = "| %-15s | %-20s | %-15s |\n";
        vista.mostrarResultado("+-----------------+----------------------+-----------------+");
        vista.mostrarResultado("| Nº Inscripción  | Nombre del Socio     | Excursión       |");
        vista.mostrarResultado("+-----------------+----------------------+-----------------+");

        // Mostrar cada inscripción
        for (Inscripcion inscripcion : inscripciones) {
            vista.mostrarResultado(String.format(formato,
                    inscripcion.getNumeroInscripcion(),
                    inscripcion.getSocio().getNombre(),
                    inscripcion.getExcursion().getDescripcion()));
                    vista.mostrarResultado("+-----------------+----------------------+-----------------+");
        }
        return inscripciones;
    }

    private List<Inscripcion> mostrarInscripcionesPorFechaConcreta() {
        vista.mostrarResultado("Introduce las fechas para filtrar las inscripciones.");
        String fechaInicioStr = vista.leerFechaInsc();
        String fechaFinStr = vista.leerFechaInsc();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorFechas(fechaInicio, fechaFin);

        // Comprobar si hay inscripciones
        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No hay inscripciones para esta fecha.");
            return inscripciones; // Retorna la lista vacía
        }

        // Cabecera de la tabla
        String formato = "| %-15s | %-20s | %-15s | %20s |\n";
        vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");
        vista.mostrarResultado("| Nº Inscripción  | Nombre del Socio     | Excursión       | Fecha Inscripción    |");
        vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");

        // Mostrar cada inscripción
        for (Inscripcion inscripcion : inscripciones) {
            vista.mostrarResultado(String.format(formato,
                    inscripcion.getNumeroInscripcion(),
                    inscripcion.getSocio().getNombre(),
                    inscripcion.getExcursion().getDescripcion(),
                    inscripcion.getFechaInscripcion().toString()));
                vista.mostrarResultado("+-----------------+----------------------+-----------------+----------------------+");
        }
        return inscripciones;
    }

    private void mostrarInscripcionesDeExcursion(Excursion excursion) {
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorExcursion(excursion.getCodigo());

        if (inscripciones.isEmpty()) {
            vista.mostrarResultado("No hay inscripciones para esta excursión.");
            return;
        }

        // Cabecera de la tabla
        String formato = "| %-15s | %-20s | %-15s |\n";
        vista.mostrarResultado("+-----------------+----------------------+-----------------+");
        vista.mostrarResultado("| Nº Inscripción  | Nombre del Socio     | Fecha Inscripción|");
        vista.mostrarResultado("+-----------------+----------------------+-----------------+");

        // Mostrar cada inscripción
        for (Inscripcion inscripcion : inscripciones) {
            vista.mostrarResultado(String.format(formato,
                    inscripcion.getNumeroInscripcion(),
                    inscripcion.getSocio().getNombre(),
                    inscripcion.getFechaInscripcion().toString()));

            vista.mostrarResultado("+-----------------+----------------------+-----------------+");
        }
    }
}