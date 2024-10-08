import java.util.Date;

public class Inscripcion {
    // Atributos privados de la clase Inscripcion PRUEBA DAN
    private int numeroInscripcion;   // Número único que identifica la inscripción
    private Date fechaInscripcion;   // Fecha en la que se realizó la inscripción
    private Socio socio;             // Socio que realiza la inscripción
    private Excursion excursion;     // Excursión a la que se inscribe el socio

    // Constructor de la clase Inscripcion
    // El constructor inicializa los atributos 'numeroInscripcion', 'fechaInscripcion', 'socio' y 'excursion' con los valores que se pasan al crear un objeto Inscripcion.
    public Inscripcion(int numeroInscripcion, Date fechaInscripcion, Socio socio, Excursion excursion) {
        this.numeroInscripcion = numeroInscripcion;     // Asigna el número de inscripción único
        this.fechaInscripcion = fechaInscripcion;       // Asigna la fecha en la que se hizo la inscripción
        this.socio = socio;                             // Asigna el socio que se inscribe a la excursión
        this.excursion = excursion;                     // Asigna la excursión a la que se inscribe el socio
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar los atributos de 'numeroInscripcion', 'fechaInscripcion', 'socio' y 'excursion'.

    // Getter para el número de inscripción
    public int getNumeroInscripcion() {
        return numeroInscripcion;  // Devuelve el número de la inscripción
    }

    // Setter para el número de inscripción
    public void setNumeroInscripcion(int numeroInscripcion) {
        this.numeroInscripcion = numeroInscripcion;  // Permite modificar el número de inscripción
    }

    // Getter para la fecha de inscripción
    public Date getFechaInscripcion() {
        return fechaInscripcion;  // Devuelve la fecha en la que se realizó la inscripción
    }

    // Setter para la fecha de inscripción
    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;  // Permite modificar la fecha de inscripción
    }

    // Getter para el socio que se inscribe
    public Socio getSocio() {
        return socio;  // Devuelve el socio que realizó la inscripción
    }

    // Setter para el socio que se inscribe
    public void setSocio(Socio socio) {
        this.socio = socio;  // Permite modificar el socio que se inscribe
    }

    // Getter para la excursión a la que se inscribe el socio
    public Excursion getExcursion() {
        return excursion;  // Devuelve la excursión a la que el socio se ha inscrito
    }

    // Setter para la excursión
    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;  // Permite modificar la excursión a la que se inscribe el socio
    }
}
