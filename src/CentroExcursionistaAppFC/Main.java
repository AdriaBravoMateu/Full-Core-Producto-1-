package CentroExcursionistaAppFC;

import grupoFullCoreControlador.ControladorExcursion;
import grupoFullCoreControlador.ControladorSocio;
import grupoFullCoreControlador.ControladorInscripcion;
import grupoFullCore.modelo.CentroExcursionista;
import grupoFullCoreVista.VistaExcursion;
import grupoFullCoreVista.VistaSocio;
import grupoFullCoreVista.VistaInscripcion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear el modelo del centro excursionista
        CentroExcursionista centro = new CentroExcursionista();

        // Crear las vistas
        VistaSocio vistaSocios = new VistaSocio();
        VistaExcursion vistaExcursiones = new VistaExcursion();
        VistaInscripcion vistaInscripciones = new VistaInscripcion();

        // Crear los controladores
        ControladorSocio controladorSocio = new ControladorSocio(centro, vistaSocios);
        ControladorExcursion controladorExcursion = new ControladorExcursion(centro, vistaExcursiones);
        ControladorInscripcion controladorInscripcion = new ControladorInscripcion(centro, vistaInscripciones);

        // Cargar datos iniciales para pruebas
        CentroExcursionistaAppFC.CargaDatosIniciales.cargarDatos(centro);

        // Iniciar la aplicación con el menú principal
        iniciarAplicacion(controladorSocio, controladorExcursion, controladorInscripcion);
    }

    // Método para iniciar la aplicación y mostrar el menú principal
    private static void iniciarAplicacion(ControladorSocio controladorSocios, ControladorExcursion controladorExcursiones, ControladorInscripcion controladorInscripciones) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            // Mostrar el menú principal
            System.out.println("\nBienvenido al Centro Excursionista Senderos y Montañas");
            System.out.println("1. Gestión de Excursiones");
            System.out.println("2. Gestión de Socios");
            System.out.println("3. Gestión de Inscripciones");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Iniciar gestión de excursiones
                    controladorExcursiones.gestionarExcursiones();
                    break;
                case 2:
                    // Iniciar gestión de socios
                    controladorSocios.gestionarSocios();
                    break;
                case 3:
                    // Iniciar gestión de inscripciones
                    controladorInscripciones.gestionarInscripciones();
                    break;
                case 0:
                    // Salir de la aplicación
                    salir = true;
                    System.out.println("Gracias por usar la aplicación. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción correcta.");
            }
        }
        scanner.close();
    }
}
