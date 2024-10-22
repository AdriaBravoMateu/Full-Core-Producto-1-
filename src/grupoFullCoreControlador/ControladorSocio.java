package grupoFullCoreControlador;

import grupoFullCore.modelo.*;
import grupoFullCoreVista.VistaSocio;
import java.util.List;

public class ControladorSocio {
    private CentroExcursionista centro;
    private VistaSocio vista;

    public ControladorSocio(CentroExcursionista centro, VistaSocio vista) {
        this.centro = centro;
        this.vista = vista;
    }

    public void gestionarSocios() {
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

    public Socio agregarSocio() {
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
            }
            if (socio != null) {
                return socio;
            }
        }
        return null;
    }

    private Socio agregarSocioEstandar() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = solicitarNumeroSocioValido();
        String nif = vista.leerNif();
        TipoSeguro seguroEnum = seleccionarSeguro();
        Seguro seguro = new Seguro(seguroEnum);
        SocioEstandar socio = new SocioEstandar(numeroSocio, nombre, nif, seguro);
        centro.añadirSocioEstandar(socio);
        vista.mostrarResultado("Socio Estándar añadido correctamente.");
        return socio;
    }

    private Socio agregarSocioFederado() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = solicitarNumeroSocioValido();
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

    private Socio agregarSocioInfantil() {
        String nombre = vista.leerNombreSocio();
        int numeroSocio = solicitarNumeroSocioValido();
        vista.mostrarResultado("Selecciona el número de socio del progenitor.");
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
                    vista.mostrarResultado("Opción de seguro no válida.");
            }
        }
        return seguroEnum;
    }

    // Método para modificar el tipo de seguro de un socio estándar
// Método para modificar el tipo de seguro de un socio estándar
    private void modificarSeguroSocioEstandar() {
        // Mostrar todos los socios estándar
        mostrarSociosEstandar();

        // Leer el número de socio
        int numeroSocio = vista.leerNumeroSocio();

        // Buscar el socio en la lista de socios
        Socio socio = centro.buscarSocioPorNumero(numeroSocio);

        // Verificar si el socio existe y es de tipo SocioEstandar
        if (socio == null || !(socio instanceof SocioEstandar)) {
            vista.mostrarResultado("El socio con número " + numeroSocio + " no existe o no es un socio estándar.");
            return;  // Terminar el método si no se encuentra el socio
        }

        // Si el socio existe y es de tipo SocioEstandar, hacer el casting
        SocioEstandar socioEstandar = (SocioEstandar) socio;

        // Seleccionar el nuevo tipo de seguro
        vista.mostrarResultado("Selecciona el nuevo tipo de seguro.");
        TipoSeguro nuevoSeguroEnum = seleccionarSeguro();

        // Modificar el seguro del socio
        Seguro nuevoSeguro = new Seguro(nuevoSeguroEnum);
        centro.modificarSeguroSocioEstandar(numeroSocio, nuevoSeguro);

        // Mostrar mensaje de confirmación
        vista.mostrarResultado("Seguro modificado correctamente.");
    }


    private void eliminarSocio() {
        mostrarTodosLosSocios();  // Mostrar todos los socios antes de eliminar
        int numeroSocio = vista.leerNumeroSocio();  // Leer el número del socio
        Socio socioAEliminar = centro.buscarSocioPorNumero(numeroSocio);  // Buscar socio

        if (socioAEliminar == null) {
            vista.mostrarResultado("Error: El socio con número " + numeroSocio + " no existe.");
        } else {
            try {
                centro.eliminarSocio(numeroSocio);  // Intentar eliminar el socio
                vista.mostrarResultado("Socio eliminado correctamente.");
            } catch (Exception e) {
                vista.mostrarResultado("Error al eliminar el socio: " + e.getMessage());  // Manejar la excepción
            }
        }
    }

    private void mostrarSocios() {
        boolean cancelar = false;
        while (!cancelar) {
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
                        case 0:
                            cancelar = true;
                            break;
                        default:
                            vista.mostrarResultado("Opción no válida.");
                    }
                    break;
                case 0:
                    cancelar = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }

    private void mostrarFacturaMensualPorSocio() {
        mostrarTodosLosSocios();
        int numeroSocio = vista.leerNumeroSocio();
        Socio socio = centro.buscarSocioPorNumero(numeroSocio);
        if (socio != null) {
            double factura = centro.calcularFacturaMensualPorSocio(numeroSocio);
            vista.mostrarResultado("Factura del mes actual: " + factura);
        } else {
            vista.mostrarResultado("El socio no existe.");
        }
    }

    private void mostrarSociosEstandarYFederados() {
        List<Socio> socios = centro.mostrarSocios();
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado("+--------------+----------------------+------------+");
        vista.mostrarResultado("| Número Socio | Nombre               | Tipo       |");
        vista.mostrarResultado("+--------------+----------------------+------------+");
        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar || socio instanceof SocioFederado) {
                String tipoSocio = socio instanceof SocioEstandar ? "Estándar" : "Federado";
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
                vista.mostrarResultado("+--------------+----------------------+------------+");
            }
        }
    }

    private void mostrarSociosEstandar() {
        List<Socio> socios = centro.mostrarSocios();
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado("+--------------+----------------------+------------+");
        vista.mostrarResultado("| Número Socio | Nombre               | Tipo       |");
        vista.mostrarResultado("+--------------+----------------------+------------+");
        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar) {
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), "Estándar"));
                vista.mostrarResultado("+--------------+----------------------+------------+");
            }
        }
    }

    private void mostrarSociosFederados() {
        List<Socio> socios = centro.mostrarSocios();
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado("+--------------+----------------------+------------+");
        vista.mostrarResultado("| Número Socio | Nombre               | Tipo       |");
        vista.mostrarResultado("+--------------+----------------------+------------+");
        for (Socio socio : socios) {
            if (socio instanceof SocioFederado) {
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), "Federado"));
                vista.mostrarResultado("+--------------+----------------------+------------+");
            }
        }
    }

    private void mostrarSociosInfantiles() {
        List<Socio> socios = centro.mostrarSocios();
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado("+--------------+----------------------+------------+");
        vista.mostrarResultado("| Número Socio | Nombre               | Tipo       |");
        vista.mostrarResultado("+--------------+----------------------+------------+");
        for (Socio socio : socios) {
            if (socio instanceof SocioInfantil) {
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), "Infantil"));
                vista.mostrarResultado("+--------------+----------------------+------------+");
            }
        }
    }

    public void mostrarTodosLosSocios() {
        List<Socio> socios = centro.mostrarSocios();
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado("+--------------+----------------------+------------+");
        vista.mostrarResultado("| Número Socio | Nombre               | Tipo       |");
        vista.mostrarResultado("+--------------+----------------------+------------+");
        for (Socio socio : socios) {
            String tipoSocio = socio instanceof SocioEstandar ? "Estándar" :
                    socio instanceof SocioFederado ? "Federado" : "Infantil";
            vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
            vista.mostrarResultado("+--------------+----------------------+------------+");
        }
    }
}
