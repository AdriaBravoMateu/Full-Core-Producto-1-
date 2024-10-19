package grupoFullCore.modelo;

import java.util.Date;

public class Inscripcion {
    // Atributos
    private int numeroInscripcion;
    private Date fechaInscripcion;
    private Socio socio;
    private Excursion excursion;

    // Constructor
    public Inscripcion(int numeroInscripcion, Date fechaInscripcion, Socio socio, Excursion excursion) {
        this.numeroInscripcion = numeroInscripcion;
        this.fechaInscripcion = fechaInscripcion;
        this.socio = socio;
        this.excursion = excursion;
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
        return "\n- INFORMACIÓN DE LA INSCRIPCIÓN -" +
                "\nNúmero de Inscripción: " + numeroInscripcion +
                "\nFecha de la Excursión: " + fechaInscripcion +
                "\nNúmero de Socio: " + socio +
                "\nExcursión: " + excursion;
    }
}
