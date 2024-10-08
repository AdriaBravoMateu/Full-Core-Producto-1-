import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // 1. Crear algunos seguros
        // Se crean dos tipos de seguro, uno básico y otro completo, que los socios estándar pueden contratar.
        Seguro seguroBasico = new Seguro("Básico", 5.0);
        Seguro seguroCompleto = new Seguro("Completo", 10.0);

        // 2. Crear algunas federaciones
        // Las federaciones se crean para los socios federados. Cada federación tiene un código y un nombre.
        Federacion federacionMontana = new Federacion("FED01", "Federación de Montaña");
        Federacion federacionEscalada = new Federacion("FED02", "Federación de Escalada");

        // 3. Crear algunos socios
        // Se crean instancias de tres tipos de socios: estándar, federado e infantil.
        // Los socios estándar tienen un NIF y un seguro asociado.
        SocioEstandar socioEstandar = new SocioEstandar(1, "Juan Pérez", "12345678A", seguroBasico);

        // Los socios federados tienen un NIF y están asociados a una federación.
        SocioFederado socioFederado = new SocioFederado(2, "Ana Gómez", "87654321B", federacionMontana);

        // Los socios infantiles no tienen seguro propio, están cubiertos por el seguro de sus padres,
        // en este caso se guarda el número de socio de uno de sus padres (Juan Pérez).
        SocioInfantil socioInfantil = new SocioInfantil(3, "Pedro Martínez", 1);  // Hijo de Juan Pérez

        // 4. Mostrar la factura mensual para cada socio
        // Se calcula la factura mensual llamando al método 'calcularFacturaMensual()' de cada tipo de socio.
        // Esto incluye la cuota base de 10€ y cualquier descuento o cargo adicional.
        System.out.println("Factura mensual de Juan Pérez (Socio Estándar): " + socioEstandar.calcularFacturaMensual());
        System.out.println("Factura mensual de Ana Gómez (Socio Federado): " + socioFederado.calcularFacturaMensual());
        System.out.println("Factura mensual de Pedro Martínez (Socio Infantil): " + socioInfantil.calcularFacturaMensual());

        // 5. Crear una excursión
        // Se crea una instancia de la clase 'Excursion'. Esta excursión tiene un código, una descripción,
        // una fecha, una duración en días y un precio de inscripción.
        // Se utiliza 'new Date()' para asignar la fecha actual.
        Excursion excursionMontaña = new Excursion("EX01", "Excursión a la Montaña", new Date(), 2, 50.0);

        // 6. Crear una inscripción
        // Se crea una inscripción de un socio a una excursión. En este caso, Juan Pérez (socio estándar)
        // se inscribe en la excursión 'Excursión a la Montaña'.
        Inscripcion inscripcionJuan = new Inscripcion(1, new Date(), socioEstandar, excursionMontaña);

        // 7. Mostrar detalles de la inscripción
        // Se imprimen en la consola los detalles de la inscripción realizada. Se accede a los datos del
        // socio y la excursión mediante los métodos 'getSocio()' y 'getExcursion()', y luego se muestran
        // el nombre del socio, la descripción de la excursión y la fecha de inscripción.
        System.out.println("Inscripción de Juan Pérez a la excursión: ");
        System.out.println("Socio: " + inscripcionJuan.getSocio().getNombre()); // Muestra el nombre del socio inscrito
        System.out.println("Excursión: " + inscripcionJuan.getExcursion().getDescripcion()); // Muestra la descripción de la excursión
        System.out.println("Fecha de inscripción: " + inscripcionJuan.getFechaInscripcion()); // Muestra la fecha en que se realizó la inscripción
    }
}
