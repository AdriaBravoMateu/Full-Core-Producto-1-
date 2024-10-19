package grupoFullCoreVista;

//======================================================================================================================
//IMPORTACIONES
import java.util.Scanner;
import java.util.List;
import grupoFullCore.modelo.Federacion;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
//Gestión de excepciones
import java.util.InputMismatchException;
//======================================================================================================================


public class VistaCentroExcursionista {
    private Scanner scanner;

    public VistaCentroExcursionista() {
        scanner = new Scanner(System.in);
    }

    // MENÚ PRINCIPAL
    public void mostrarMenuPrincipal() {
        System.out.println("\nBienvenido al Centro Excursionista Senderos y Montañas");
        System.out.println("1. Gestión de Excursiones");
        System.out.println("2. Gestión de Socios");
        System.out.println("3. Gestión de Inscripciones");
        System.out.println("4. Salir");
        System.out.print("Elige una opción: ");
    }

    /* -----------------------------------------------------------------------------------------------------------------
    ----------------------------- SUBMENÚ DE EXCURSIONES----------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */
    public void mostrarMenuExcursiones() {
        System.out.println("\nGestión de Excursiones");
        System.out.println("1. Añadir Excursión");
        System.out.println("2. Mostrar Excursiones con filtro entre fechas");
        System.out.println("3. Volver al menú principal");
        System.out.print("Elige una opción: ");
    }

    /* -----------------------------------------------------------------------------------------------------------------
    ----------------------------- SUBMENÚ DE SOCIOS --------------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */
    public void mostrarMenuSocios() {
        System.out.println("\nGestión de Socios");
        System.out.println("1. Añadir Socio");
        System.out.println("2. Modificar tipo de seguro de un socio estándar");
        System.out.println("3. Eliminar Socio");
        System.out.println("4. Mostrar Socios (Todos o por tipo)");
        System.out.println("5. Mostrar Factura mensual por socio");
        System.out.println("6. Volver al menú principal");
        System.out.print("Elige una opción: ");
    }


    // Menú auxiliar SOCIOS
    public void mostrarTipoSocios(){
        System.out.println("1. Añadir Socio Estándar");
        System.out.println("2. Añadir Socio Federado");
        System.out.println("3. Añadir Socio Infantil");
        System.out.print("Elige una opción: ");
    }

    public void seleccionarTipoSeguro(){
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
            opcion = scanner.nextInt();
            scanner.nextLine();
            try {
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

    // Menú para elegir entre todos los socios o filtrar por tipo
    public void mostrarOpcionMostrarSocios() {
        System.out.println("1. Mostrar todos los socios");
        System.out.println("2. Filtrar por tipo de socio");
        System.out.print("Elige una opción: ");
    }

    // Menú para filtrar por tipo de socio
    public void mostrarOpcionesFiltrarSocios() {
        System.out.println("1. Socios Estándar");
        System.out.println("2. Socios Federados");
        System.out.println("3. Socios Infantiles");
        System.out.print("Elige una opción: ");
    }

    /* -----------------------------------------------------------------------------------------------------------------
    ----------------------------- SUBMENÚ DE INSCRIPCIONES -------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */
    public void mostrarMenuInscripciones() {
        System.out.println("\nGestión de Inscripciones");
        System.out.println("1. Añadir Inscripción");
        System.out.println("2. Eliminar Inscripción");
        System.out.println("3. Mostrar Inscripciones (filtrar por socio y/o fechas)");
        System.out.println("4. Volver al menú principal");
        System.out.print("Elige una opción: ");
    }




    // =============================== Métodos para leer datos de EXCURSIONES ==========================================
    public String leerCodigoExcursion() {
        System.out.print("Introduce el código de la excursión: ");
        return scanner.nextLine();
    }

    public String leerDescripcionExcursion() {
        System.out.print("Introduce la descripción de la excursión: ");
        return scanner.nextLine();
    }

    public int leerNumeroDiasExcursion() {
        int numeroDias = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de días de la excursión: ");
                numeroDias = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Consumir la entrada incorrecta
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
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido para el precio.");
                scanner.nextLine();  // Consumir la entrada incorrecta
            }
        }

        return precio;
    }



    // =============================== Métodos para leer datos de SOCIOS ===================================================
    public String leerNombreSocio() {
        String nombre = "";
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("Introduce el nombre del socio: ");
            nombre = scanner.nextLine();
            if (!nombre.trim().isEmpty()) {
                entradaValida = true;
            } else {
                System.out.println("Error: El nombre no puede estar vacío.");
            }
        }

        return nombre;
    }


    public int leerNumeroSocio() {
        int numeroSocio = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de socio: ");
                numeroSocio = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                entradaValida = true;  // Si no ocurre excepción, marcamos como válida
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();  // Consumir la entrada incorrecta
            }
        }

        return numeroSocio;
    }

    public String leerNif() {
        System.out.print("Introduce el NIF del socio: ");
        return scanner.nextLine();
    }


    public int leerNumeroSocioProgenitor() {
        int numeroProgenitor = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de socio del progenitor: ");
                numeroProgenitor = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }

        return numeroProgenitor;
    }


    // =============================== Métodos para leer datos de INSCRIPCIONES ========================================
    public int leerNumeroInscripcion() {
        int numeroInscripcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce el número de inscripción: ");
                numeroInscripcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }

        return numeroInscripcion;
    }

    public int mostrarFiltroInscripciones() {
        mostrarResultado("1. Ver todas las inscripciones");
        mostrarResultado("2. Filtrar por socio");
        mostrarResultado("3. Filtrar por fechas");
        mostrarResultado("Seleccione una opción:");

        return leerOpcion();
    }



    // =============================== Métodos genéricos para mostrar datos ============================================
    public void mostrarResultado(String resultado) {
        System.out.println(resultado);
    }

    public int leerOpcion() {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                entradaValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Consumir la entrada incorrecta
            }
        }

        return opcion;
    }

    public String leerFecha() {
        LocalDate fecha = null;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Introduce la fecha de la excursión (YYYY-MM-DD): ");
                String fechaStr = scanner.next();
                fecha = LocalDate.parse(fechaStr);  // Validar que sea una fecha válida
                entradaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Debe ingresar una fecha válida en el formato YYYY-MM-DD.");
            }
        }

        return fecha.toString();
    }
}