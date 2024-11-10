package grupoFullCoreControlador;

import grupoFullCore.modelo.*;
import grupoFullCore.modelo.DAO.SocioDAO;
import grupoFullCore.modelo.DAO.FederacionDAO;
import grupoFullCore.modelo.DAO.factory.DAOFactory;

import grupoFullCoreVista.VistaSocio;

import java.util.List;

import static grupoFullCore.modelo.TipoSocio.ESTANDAR;
import static grupoFullCore.modelo.TipoSocio.FEDERADO;
import static grupoFullCore.modelo.TipoSocio.INFANTIL;


public class ControladorSocio {
    private SocioDAO socioDAO;
    private FederacionDAO federacionDAO;
    private VistaSocio vista;
    private ControladorExcursion controladorExcursion;
    private ControladorInscripcion controladorInscripcion;
    private CentroExcursionista centroExcursionista;

    public ControladorSocio(VistaSocio vista, ControladorExcursion controladorExcursion, ControladorInscripcion controladorInscripcion) {
        this.socioDAO = DAOFactory.getSocioDAO(); // Obtiene el SocioDAO usando DAOFactory
        this.federacionDAO = DAOFactory.getFederacionDAO(); // Obtiene el FederacionDAO usando DAOFactory
        this.vista = vista;
        this.controladorExcursion = controladorExcursion;
        this.controladorInscripcion = controladorInscripcion;
        this.centroExcursionista = new CentroExcursionista();
    }

    public void setControladorExcursion(ControladorExcursion controladorExcursion) {
        this.controladorExcursion = controladorExcursion;
    }

    public void setControladorInscripcion(ControladorInscripcion controladorInscripcion) {
        this.controladorInscripcion = controladorInscripcion;
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

        mostrarTodosLosSocios(); // mostrar socios existentes antes de agregar uno nuevo

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
                socioDAO.agregarSocio(socio); // Guardar el nuevo socio en la base de datos
                vista.mostrarResultado("Socio añadido correctamente.");
                return socio;
            }
        }
        return null;
    }

    private Socio agregarSocioEstandar() {
        String nombre = vista.leerNombreSocio();
        String nif = vista.leerNif();
        TipoSeguro seguroEnum = seleccionarSeguro();
        Seguro seguro = new Seguro(seguroEnum);
        return new SocioEstandar(nombre, ESTANDAR, nif, seguro);
    }

    private Socio agregarSocioFederado() {
        String nombre = vista.leerNombreSocio();
        String nif = vista.leerNif();
        List<Federacion> federaciones = federacionDAO.mostrarFederaciones(); // Obtener federaciones desde FederacionDAO

        int opcionFederacion = vista.mostrarFederaciones(federaciones);
        SocioFederado socio = null;

        if (opcionFederacion >= 1 && opcionFederacion <= federaciones.size()) {
            Federacion federacionSeleccionada = federaciones.get(opcionFederacion - 1);
            socio = new SocioFederado(nombre, FEDERADO, nif, federacionSeleccionada);
        } else {
            vista.mostrarResultado("Opción de federación no válida.");
        }

        return socio;
    }

    private Socio agregarSocioInfantil() {
        String nombre = vista.leerNombreSocio();
        vista.mostrarResultado("Selecciona el número de socio del progenitor.");
        mostrarSociosEstandarYFederados();

        int numeroSocioProgenitor = vista.leerNumeroSocioProgenitor();
        Socio progenitor = socioDAO.buscarSocioPorNumero(numeroSocioProgenitor);

        if (progenitor != null && (progenitor instanceof SocioEstandar || progenitor instanceof SocioFederado)) {
            return new SocioInfantil(nombre, INFANTIL, progenitor);
        } else {
            vista.mostrarResultado("Progenitor no encontrado o no válido.");
            return null;
        }
    }

    private int solicitarNumeroSocioValido() {
        int numeroSocio;
        do {
            numeroSocio = vista.leerNumeroSocio();
            if (socioDAO.buscarSocioPorNumero(numeroSocio) != null) {
                vista.mostrarResultado("El número de socio ya existe. Introduzca otro número.");
            }
        } while (socioDAO.buscarSocioPorNumero(numeroSocio) != null);
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

    private void modificarSeguroSocioEstandar() {
        mostrarSociosEstandar();
        int numeroSocio = vista.leerNumeroSocio();
        Socio socio = socioDAO.buscarSocioPorNumero(numeroSocio);

        if (socio instanceof SocioEstandar) {
            TipoSeguro nuevoSeguroEnum = seleccionarSeguro();

            // Verifica que el tipo de seguro no sea nulo antes de actualizar
            if (nuevoSeguroEnum != null) {
                // Actualiza solo el tipo de seguro en la base de datos usando el método específico del DAO
                socioDAO.actualizarTipoSeguro((SocioEstandar) socio, nuevoSeguroEnum);
                vista.mostrarResultado("Seguro modificado correctamente.");
            } else {
                vista.mostrarResultado("Error: El tipo de seguro no puede ser nulo.");
            }
        } else {
            vista.mostrarResultado("El socio no es un socio estándar.");
        }
    }

    private void eliminarSocio() {
        mostrarTodosLosSocios();
        int numeroSocio = vista.leerNumeroSocio();
        try {
            socioDAO.eliminarSocio(numeroSocio);
            // vista.mostrarResultado("Socio eliminado correctamente.");
        } catch (Exception e) {
            vista.mostrarResultado(/*"Error al eliminar el socio: " + */e.getMessage());
        }
    }


    public void mostrarFacturaMensualPorSocio() {
        mostrarTodosLosSocios();
        int numeroSocio = vista.leerNumeroSocio();
        double importeFactura = centroExcursionista.calcularFacturaMensualPorSocio(numeroSocio);
        vista.mostrarResultado("La factura mensual es: " + importeFactura + "€");
    }



    private void mostrarSocios() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarOpcionesMostrarSocios();
            int opcion = vista.leerOpcion();

            switch (opcion) {
                case 1:
                    mostrarTodosLosSocios();
                    break;
                case 2:
                    mostrarSociosPorTipo();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    vista.mostrarResultado("Opción no válida.");
            }
        }
    }
    private void mostrarSociosPorTipo() {
        boolean opcionValida = false;
        while (!opcionValida) {
            vista.mostrarSubmenuTipoSocio();
            int tipoOpcion = vista.leerOpcion();

            switch (tipoOpcion) {
                case 1:
                    mostrarSociosEstandar();
                    opcionValida = true;
                    break;
                case 2:
                    mostrarSociosFederado();
                    opcionValida = true;
                    break;
                case 3:
                    mostrarSociosInfantil();
                    opcionValida = true;
                    break;
                case 0:
                    opcionValida = true;
                    break;
                default:
                    vista.mostrarResultado("Opción de tipo de socio no válida.");
            }
        }
    }


    private void mostrarSociosEstandarYFederados() {
        List<Socio> socios = socioDAO.mostrarSocios();
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado("+--------------+----------------------+------------+");
        vista.mostrarResultado("| Número Socio | Nombre               | Tipo       |");
        vista.mostrarResultado("+--------------+----------------------+------------+");

        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar || socio instanceof SocioFederado) {
                String tipoSocio = socio instanceof SocioEstandar ? "Estándar" : "Federado";
                vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
            }
        }
        vista.mostrarResultado("+--------------+----------------------+------------+");
    }

    private void mostrarSociosEstandar() {
        List<Socio> socios = socioDAO.mostrarSocios();
        vista.mostrarEncabezadoSocios();
        for (Socio socio : socios) {
            if (socio instanceof SocioEstandar) {
                vista.mostrarSocio(socio, "Estándar");
            }
        }
        vista.mostrarFinTabla();
    }

    private void mostrarSociosFederado() {
        List<Socio> socios = socioDAO.mostrarSocios();
        vista.mostrarEncabezadoSocios();
        for (Socio socio : socios) {
            if (socio instanceof SocioFederado) {
                vista.mostrarSocio(socio, "Federado");
            }
        }
        vista.mostrarFinTabla();
    }

    private void mostrarSociosInfantil() {
        List<Socio> socios = socioDAO.mostrarSocios();
        vista.mostrarEncabezadoSocios();
        for (Socio socio : socios) {
            if (socio instanceof SocioInfantil) {
                vista.mostrarSocio(socio, "Infantil");
            }
        }
        vista.mostrarFinTabla();
    }


    public void mostrarTodosLosSocios() {
        List<Socio> socios = socioDAO.mostrarSocios();
        String formato = "| %-12s | %-20s | %-10s |\n";
        vista.mostrarResultado("+--------------+----------------------+------------+");
        vista.mostrarResultado("| Número Socio | Nombre               | Tipo       |");
        vista.mostrarResultado("+--------------+----------------------+------------+");

        for (Socio socio : socios) {
            String tipoSocio = socio instanceof SocioEstandar ? "Estándar" :
                    socio instanceof SocioFederado ? "Federado" : "Infantil";
            vista.mostrarResultado(String.format(formato, socio.getNumeroSocio(), socio.getNombre(), tipoSocio));
        }
        vista.mostrarResultado("+--------------+----------------------+------------+");
    }
}
