package grupoFullCoreVista;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaExcursion {
    private Scanner scanner;

    public VistaExcursion() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenuExcursiones() {
        System.out.println("\nGestión de Excursiones");
        System.out.println("1. Añadir Excursión");
        System.out.println("2. Mostrar Excursiones con filtro entre fechas");
        System.out.println("0. Volver al menú principal");
        System.out.print("Elige una opción: ");
    }

    public int leerCodigoExcursion() {
        System.out.print("Introduce el código de la excursión: ");
        return Integer.parseInt(scanner.nextLine());  // Leer el código de la excursión
    }

    public String leerDescripcionExcursion() {
        System.out.print("Introduce la descripción de la excursión: ");
        return scanner.nextLine();  // Leer la descripción de la excursión
    }

    public int leerNumeroDiasExcursion() {
        int numeroDias = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de días de la excursión: ");
                numeroDias = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea pendiente
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();  // Consumir la entrada incorrecta
            }
        }

        return numeroDias;
    }

    public double leerPrecioInscripcion() {
        double precio = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce el precio de inscripción: ");
                precio = scanner.nextDouble();
                scanner.nextLine();  // Consumir el salto de línea pendiente
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido para el precio.");
                scanner.nextLine();  // Consumir la entrada incorrecta
            }
        }

        return precio;
    }

    public String leerFecha(String mensaje) {
        LocalDate fecha = null;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print(mensaje + " (YYYY-MM-DD): ");
                String fechaStr = scanner.next();
                fecha = LocalDate.parse(fechaStr);  // Validar que sea una fecha válida
                entradaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Debe ingresar una fecha válida en el formato YYYY-MM-DD.");
            }
        }
        return fecha.toString();
    }

    public void mostrarResultado(String resultado) {
        System.out.println(resultado);
    }

    public int leerOpcion() {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea pendiente
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();  // Consumir la entrada incorrecta
            }
        }

        return opcion;
    }
    public String leerFechaExcursion() {
        return leerFecha("Introduce la fecha de la excursión");
    }
}
