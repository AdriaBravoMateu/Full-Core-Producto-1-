import java.util.Date;

public class Excursion {
    // Atributos de la clase Excursion
    // Estos representan la información básica que debe almacenarse sobre cada excursión.sdfsdfsdf
    private String codigo;         // Código alfanumérico único de la excursión
    private String descripcion;    // Breve descripción de la excursión
    private Date fecha;            // Fecha de inicio de la excursión
    private int numeroDias;        // Número de días que dura la excursión
    private double precioInscripcion; // Precio que debe pagar un socio para inscribirse en la excursión

    // Constructor de la clase Excursion
    // El constructor permite inicializar una nueva excursión con sus datos básicos.
    public Excursion(String codigo, String descripcion, Date fecha, int numeroDias, double precioInscripcion) {
        this.codigo = codigo;                     // Asigna el código de la excursión
        this.descripcion = descripcion;           // Asigna la descripción de la excursión
        this.fecha = fecha;                       // Asigna la fecha de la excursión
        this.numeroDias = numeroDias;             // Asigna el número de días de la excursión
        this.precioInscripcion = precioInscripcion; // Asigna el precio de la inscripción
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar los atributos privados de la clase.

    // Getter para el código de la excursión
    public String getCodigo() {
        return codigo; // Devuelve el código de la excursión
    }

    // Setter para el código de la excursión
    public void setCodigo(String codigo) {
        this.codigo = codigo; // Permite modificar el código de la excursión
    }

    // Getter para la descripción de la excursión
    public String getDescripcion() {
        return descripcion; // Devuelve la descripción de la excursión
    }

    // Setter para la descripción de la excursión
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion; // Permite modificar la descripción de la excursión
    }

    // Getter para la fecha de la excursión
    public Date getFecha() {
        return fecha; // Devuelve la fecha de la excursión
    }

    // Setter para la fecha de la excursión
    public void setFecha(Date fecha) {
        this.fecha = fecha; // Permite modificar la fecha de la excursión
    }

    // Getter para el número de días que dura la excursión
    public int getNumeroDias() {
        return numeroDias; // Devuelve el número de días de la excursión
    }

    // Setter para el número de días de la excursión
    public void setNumeroDias(int numeroDias) {
        this.numeroDias = numeroDias; // Permite modificar la duración de la excursión
    }

    // Getter para el precio de inscripción de la excursión
    public double getPrecioInscripcion() {
        return precioInscripcion; // Devuelve el precio de la inscripción
    }

    // Setter para el precio de inscripción de la excursión
    public void setPrecioInscripcion(double precioInscripcion) {
        this.precioInscripcion = precioInscripcion; // Permite modificar el precio de la inscripción
    }
}
