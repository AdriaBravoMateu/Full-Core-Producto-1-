package Vista;

import grupofc.modelo.*;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Scanner;

public class Main {

    // Lista estática para almacenar las excursiones
    private static List<Excursion> excursionList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        // Bucle principal del menú
        while (!salir) {
            // Presentar el menú principal
            System.out.println("¡Bienvenido al centro de Excursiones de FULL CORE!");
            System.out.println("...................................................");
            System.out.println("***** ADMINISTRACIÓN DEL CENTRO EXCURSIONISTA *****");
            System.out.println("...................................................");
            System.out.println("(1) Administrar excursiones");
            System.out.println("(2) Administrar socios");
            System.out.println("(3) Administrar inscripciones");
            System.out.println("(0) Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    menuExcursiones(scanner);
                    break;
                case 2:
                    menuSocios(scanner);
                    break;
                case 3:
                    menuInscripciones(scanner);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción correcta.");
                    break;
            }
            System.out.println(); // Separación entre ciclos del menú
        }
        scanner.close();
    }

    // Submenú para administrar excursiones
    public static void menuExcursiones(Scanner scanner) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n***** Gestión de Excursiones *****\n");
            System.out.println("(1) Agregar Excursión");
            System.out.println("(2) Eliminar Excursión");
            System.out.println("(0) Volver al Menú Principal");
            System.out.print("\nSeleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    añadirExcursion(scanner);
                    System.out.println("\nHas seleccionado: Agregar Excursión.\n");
                    break;
                case 2:
                    eliminarExcursion(scanner);
                    System.out.println("\nHas seleccionado: Eliminar Excursión.\n");
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción correcta.\n");
                    break;
            }
            System.out.println();
        }
    }

    // Submenú para administrar socios
    public static void menuSocios(Scanner scanner) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n***** Gestión de Socios *****\n");
            System.out.println("(1) Agregar nuevo Socio");
            System.out.println("(2) Modificar Tipo de Seguro de Socio Estándar");
            System.out.println("(3) Eliminar Socio (Solo si no está inscrito a una excursión)");
            System.out.println("(4) Mostrar Socios");
            System.out.println("(5) Mostrar Factura Mensual de un Socio");
            System.out.println("(0) Volver al Menú Principal");
            System.out.print("\nSeleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    añadirSocio(scanner);
                    System.out.println("\nHas seleccionado: Agregar nuevo Socio.\n");
                    break;
                case 2:
                    // Aquí iría el código para modificar el seguro de un socio estándar
                    System.out.println("\nHas seleccionado: Modificar Tipo de Seguro de Socio Estándar.\n");
                    break;
                case 3:
                    eliminarSocio(scanner);
                    System.out.println("\nHas seleccionado: Eliminar Socio.\n");
                    break;
                case 4:
                    mostrarSocios(scanner);
                    System.out.println("\nHas seleccionado: Mostrar Socios.\n");
                    break;
                case 5:
                    mostrarFactura(scanner);
                    System.out.println("\nHas seleccionado: Mostrar Factura Mensual de un Socio.\n");
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, seleccione una opción correcta.\n");
                    break;
            }
            System.out.println();
        }
    }

    // Submenú para administrar inscripciones
    public static void menuInscripciones(Scanner scanner) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n***** Gestión de Inscripciones *****\n");
            System.out.println("(1) Agregar nueva Inscripción");
            System.out.println("(2) Eliminar Inscripción");
            System.out.println("(3) Mostrar Inscripción");
            System.out.println("(0) Volver al Menú Principal");
            System.out.print("\nSeleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    añadirInscripcion(scanner);
                    System.out.println("\nHas seleccionado: Agregar nueva Inscripción.\n");
                    break;
                case 2:
                    eliminarInscripcion(scanner);
                    System.out.println("\nHas seleccionado: Eliminar Inscripción.\n");
                    break;
                case 3:
                    mostrarInscripcion(scanner);
                    System.out.println("\nHas seleccionado: Mostrar Inscripción.\n");
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, seleccione una opción correcta.\n");
                    break;
            }
            System.out.println();
        }
    }

    // Metodo para añadir una excursión
    public static void añadirExcursion(Scanner scanner) {
        System.out.println("\nAñadiendo una nueva excursión...");

        // Solicitar datos de la excursión al usuario
        System.out.print("Introduce el código de la excursión: ");
        String codigo = scanner.nextLine();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Introduce la descripción de la excursión: ");
        String descripcion = scanner.nextLine();

        System.out.print("Introduce la fecha (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine(); // Se asume que manejas la conversión a LocalDate
        LocalDate fechaLocal = LocalDate.parse(fechaStr); // Conversión a LocalDate

        // Convertir LocalDate a Date
        Date fecha = java.sql.Date.valueOf(fechaLocal); // Conversión de LocalDate a Date

        System.out.print("Introduce el número de días: ");
        int numeroDias = scanner.nextInt();

        System.out.print("Introduce el precio de inscripción: ");
        double precioInscripcion = scanner.nextDouble();

        // Crear una nueva excursión usando el constructor
        Excursion nuevaExcursion = new Excursion(codigo, descripcion, fecha, numeroDias, precioInscripcion);

        // Añadir la nueva excursión a la lista
        excursionList.add(nuevaExcursion);

        // Mostrar la excursión añadida utilizando el metodo toString()
        System.out.println("Excursión añadida con éxito: " + nuevaExcursion);
    }

    // Metodo para eliminar una excursión
    public static void eliminarExcursion(Scanner scanner) {
        System.out.println("\nEliminando una excursión...");

        // Mostrar las excursiones existentes
        System.out.println("Excursiones disponibles:");
        for (Excursion excursion : excursionList) {
            System.out.println(excursion);
        }

        System.out.print("Introduce el código de la excursión a eliminar: ");
        String codigo = scanner.nextLine(); // Asegúrate de que el tipo de 'codigo' es String

        // Buscar y eliminar la excursión por código
        boolean encontrado = false;
        for (int i = 0; i < excursionList.size(); i++) {
            if (excursionList.get(i).getCodigo().equals(codigo)) {
                excursionList.remove(i);
                System.out.println("Excursión eliminada con éxito.");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ninguna excursión con el código: " + codigo);
        }
    }
}
