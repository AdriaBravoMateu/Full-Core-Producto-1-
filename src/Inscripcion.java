import java.util.Date;

public class Inscripcion {
    // Atributos privados de la clase Inscripcion
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

    //Getters
    public int getNumeroInscripcion() {return numeroInscripcion;}
    public Date getFechaInscripcion() {return fechaInscripcion;}
    public Socio getSocio() {return socio;}
    public Excursion getExcursion() {return excursion;}
    //Setters
    public void setNumeroInscripcion(int numeroInscripcion) {this.numeroInscripcion = numeroInscripcion;}
    public void setFechaInscripcion(Date fechaInscripcion) {this.fechaInscripcion = fechaInscripcion;}
    public void setSocio(Socio socio) {this.socio = socio;}
    public void setExcursion(Excursion excursion) {this.excursion = excursion;}


    public String toString(){
        return "Inscripción {Número de inscripción = " + numeroInscripcion + "\n " +
                "Fecha de la excursión = " + fechaInscripcion + "\n " +
                "Número de socio = " + socio + "\n " +
                "Excursión = " + excursion +
                 "}";
    }
}
