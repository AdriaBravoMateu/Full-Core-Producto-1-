package grupoFullCoreVista;

import grupoFullCore.modelo.Federacion;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

public class VistaSocio {
    private Scanner scanner;

    public VistaSocio() {
        scanner = new Scanner(System.in);
    }

    // Métodos faltantes
    public void mostrarOpcionMostrarSocios() {
        System.out.println("1. Mostrar todos los socios");
        System.out.println("2. Filtrar por tipo de socio");
        System.out.println("0. Cancelar.");
        System.out.print("Elige una opción: ");
    }

    public void mostrarOpcionesFiltrarSocios() {
        System.out.println("1. Socios Estándar");
        System.out.println("2. Socios Federados");
        System.out.println("3. Socios Infantiles");
        System.out.print("Elige una opción: ");
    }

    public void mostrarMenuSocios() {
        System.out.println("\nGestión de Socios");
        System.out.println("1. Añadir Socio");
        System.out.println("2. Modificar tipo de seguro de un socio estándar");
        System.out.println("3. Eliminar Socio");
        System.out.println("4. Mostrar Socios (Todos o por tipo)");
        System.out.println("5. Mostrar Factura mensual por socio");
        System.out.println("0. Volver al menú principal");
        System.out.print("Elige una opción: ");
    }

    public void mostrarTipoSocios() {
        System.out.println("1. Añadir Socio Estándar");
        System.out.println("2. Añadir Socio Federado");
        System.out.println("3. Añadir Socio Infantil");
        System.out.println("0. Cancelar.");
        System.out.print("Elige una opción: ");
    }

    public void seleccionarTipoSeguro() {
        System.out.println("1. Básico");
        System.out.println("2. Completo");
    }

    public int mostrarFederaciones(List<Federacion> federaciones) {
        int opcion = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.println("Seleccione una federación:");
            for (int i = 0; i < federaciones.size(); i++) {
                System.out.printf("%d. %s\n", (i + 1), federaciones.get(i).getNombre());
            }
            System.out.print("Elige una opción: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea pendiente
                if (opcion >= 1 && opcion <= federaciones.size()) {
                    entradaValida = true;
                } else {
                    System.out.println("Error: Selección fuera de rango.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }
        return opcion;
    }

    public String leerNombreSocio() {
        System.out.print("Introduce el nombre del socio: ");
        return scanner.nextLine();
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

    public String leerNif() {
        System.out.print("Introduce el NIF del socio: ");
        return scanner.nextLine();  // Leer el NIF del socio
    }

    public int leerNumeroSocioProgenitor() {
        int numeroProgenitor = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de socio del progenitor: ");
                numeroProgenitor = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea pendiente
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }
        return numeroProgenitor;
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
