import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;  // Importar List para manejar colecciones genéricas

public class Main {
    public static void main(String[] args) {

        // Crear la instancia de CentroExcursionista, que gestionará las operaciones
        CentroExcursionista centro = new CentroExcursionista();

        // 1. Se crean dos tipos de seguro, uno basico y otro completo, que los socios estandar pueden contratar.
        Seguro seguroBasico = new Seguro(TipoSeguro.Basico, 5.0);
        Seguro seguroCompleto = new Seguro(TipoSeguro.Completo, 10.0);

        // 2. Se crean algunas federaciones para los socios federados. Cada federacion tiene un codigo y un nombre.
        Federacion federacionMontana = new Federacion("FED01", "Federación de Montaña");
        Federacion federacionEscalada = new Federacion("FED02", "Federación de Escalada");

        // 3. Se crean instancias de tres tipos de socios: estándar, federado e infantil.
        // Los socios estándar tienen un NIF y un seguro asociado.
        SocioEstandar socioEstandar = new SocioEstandar(1, "Juan Pérez", "12345678A", seguroBasico);
        centro.añadirSocioEstandar(socioEstandar); // Se añade el socio estándar al centro

        // Los socios federados tienen un NIF y están asociados a una federación.
        SocioFederado socioFederado = new SocioFederado(2, "Ana Gómez", "87654321B", federacionMontana);
        centro.añadirSocioFederado(socioFederado); // Se añade el socio federado al centro

        // Los socios infantiles no tienen seguro propio, están cubiertos por el seguro de sus padres.
        // Se guarda el número de socio de uno de sus padres (Juan Pérez).
        SocioInfantil socioInfantil = new SocioInfantil(3, "Pedro Martínez", socioEstandar);  // Hijo de Juan Pérez
        centro.añadirSocioInfantil(socioInfantil); // Se añade el socio infantil al centro

        // 4. Se calcula y se muestra la factura mensual llamando al metodo 'mostrarFacturaMensualPorSocio' del centro.
        // Esto incluye la cuota base de 10€ y cualquier descuento o cargo adicional.
        centro.mostrarFacturaMensualPorSocio(1); // Factura de Juan Pérez
        centro.mostrarFacturaMensualPorSocio(2); // Factura de Ana Gómez
        centro.mostrarFacturaMensualPorSocio(3); // Factura de Pedro Martínez

        // 5. Crear una excursión: Se crea una instancia de la clase 'Excursion'.
        // Esta excursión tiene un código, una descripción, una fecha, una duración en días y un precio de inscripción.
        // Se utiliza 'new Date()' para asignar la fecha actual.
        Excursion excursionMontaña = new Excursion("EX01", "Excursión a la Montaña", new Date(), 2, 50.0);
        centro.añadirExcursion(excursionMontaña); // Se añade la excursión al centro

        // 6. Se crea una inscripción de un socio a una excursión. En este caso, Juan Pérez (socio estándar) se inscribe en la excursión 'Excursión a la Montaña'.
        Inscripcion inscripcionJuan = new Inscripcion(1, new Date(), socioEstandar, excursionMontaña);
        centro.añadirInscripcion(inscripcionJuan); // Se añade la inscripción al centro

        // 7. Se muestran los detalles en consola de la inscripción realizada.
        // Se accede a los datos del socio y la excursión mediante los métodos 'getSocio()' y 'getExcursion()'.
        System.out.println("Detalles de la inscripción de Juan Pérez a la excursión:");
        System.out.println("Socio: " + inscripcionJuan.getSocio().getNombre());  // Muestra el nombre del socio inscrito
        System.out.println("Excursión: " + inscripcionJuan.getExcursion().getDescripcion());  // Muestra la descripción de la excursión
        System.out.println("Fecha de inscripción: " + inscripcionJuan.getFechaInscripcion());  // Muestra la fecha en que se realizó la inscripción

        // 8. Mostrar las excursiones dentro de un rango de fechas (filtrando entre fechas)
        System.out.println("\nExcursiones en el rango de fechas:");
        LocalDate fechaInicio = LocalDate.now().minusDays(1);
        LocalDate fechaFin = LocalDate.now().plusDays(10);
        List<Excursion> excursionesFiltradas = centro.mostrarExcursionesConFiltro(fechaInicio, fechaFin);  // Cambiar ArrayList por List
        for (Excursion ex : excursionesFiltradas) {
            System.out.println(ex.toString());
        }
    }
}
