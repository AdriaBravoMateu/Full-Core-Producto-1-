package grupoFullCoreVista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class VistaInscripcion {
    private Scanner scanner;

    public VistaInscripcion() {
        scanner = new Scanner(System.in);
    }



    public void mostrarMenuInscripciones() {
        System.out.println("\nGestión de Inscripciones");
        System.out.println("1. Añadir Inscripción");
        System.out.println("2. Eliminar Inscripción");
        System.out.println("3. Mostrar Inscripciones (filtrar por socio y/o fechas)");
        System.out.println("0. Volver al menú principal");
        System.out.print("Elige una opción: ");
    }

    public int leerNumeroInscripcion() {
        int numeroInscripcion = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de inscripción: ");
                numeroInscripcion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea pendiente
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();  // Consumir la entrada incorrecta
            }
        }
        return numeroInscripcion;
    }

    public int mostrarFiltroInscripciones() {
        System.out.println("1. Ver todas las inscripciones");
        System.out.println("2. Filtrar por socio");
        System.out.println("3. Filtrar por fechas");
        System.out.println("0. Cancelar.");
        System.out.print("Seleccione una opción: ");
        return leerOpcion();
    }

    public int leerNumeroSocio() {
        int numeroSocio = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de socio: ");
                numeroSocio = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea pendiente
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();  // Consumir la entrada incorrecta
            }
        }
        return numeroSocio;
    }

    public String leerCodigoExcursion() {
        System.out.print("Introduce el código de la excursión: ");
        return scanner.nextLine();  // Leer el código de la excursión
    }

    public LocalDate leerFechaInsc() {
        LocalDate fecha = null;
        boolean entradaValida = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!entradaValida) {
            try {
                System.out.print("Introduce la fecha de la inscripción (YYYY-MM-DD): ");
                String fechaStr = scanner.next();
                fecha = LocalDate.parse(fechaStr, formatter);  // Validar que sea una fecha válida
                entradaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Debe ingresar una fecha válida en el formato YYYY-MM-DD.");
            }
        }

        return fecha;  // Devuelve directamente un LocalDate
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

}
