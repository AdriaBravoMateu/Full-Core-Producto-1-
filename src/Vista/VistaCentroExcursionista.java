package Vista;
import java.util.Scanner;
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


    public int mostrarNumeroSocioProgenitor(){
        System.out.println("Introduce el número de socio del progenitor seleccionado:");
        return scanner.nextInt();
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

    public String leerFechaExcursion() {
        System.out.print("Introduce la fecha de la excursión (YYYY-MM-DD): ");
        return scanner.next();
    }

    public int leerNumeroDiasExcursion() {
        System.out.print("Introduce el número de días de la excursión: ");
        return scanner.nextInt();
    }

    public double leerPrecioInscripcion() {
        System.out.print("Introduce el precio de inscripción: ");
        return scanner.nextDouble();
    }



// =============================== Métodos para leer datos de SOCIOS ===================================================
    public String leerNombreSocio() {
        System.out.print("Introduce el nombre del socio: ");
        return scanner.next();
    }

    public int leerNumeroSocio() {
        System.out.print("Introduce el número de socio: ");
        return scanner.nextInt();
    }

    public String leerNif() {
        System.out.print("Introduce el NIF del socio: ");
        return scanner.next();
    }

    public String leerTipoSeguro() {
        System.out.print("Introduce el tipo de seguro (Basico/Completo): ");
        return scanner.next();
    }

    public int leerNumeroSocioProgenitor() {
        System.out.print("Introduce el número de socio del progenitor: ");
        return scanner.nextInt();
    }

    public String leerFederacion() {
        System.out.print("Introduce el nombre de la federación: ");
        return scanner.next();
    }



    // =============================== Métodos para leer datos de INSCRIPCIONES ========================================
    public int leerNumeroInscripcion() {
        System.out.print("Introduce el número de inscripción: ");
        return scanner.nextInt();
    }



    // =============================== Métodos genéricos para mostrar datos ============================================
    public void mostrarResultado(String resultado) {
        System.out.println(resultado);
    }

    public int leerOpcion() {
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }
}