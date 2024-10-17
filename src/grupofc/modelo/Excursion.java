package grupofc.modelo;

import java.time.LocalDate;

public class Excursion {
    // Atributos de la clase grupofc.modelo.Excursion
    // Estos representan la información básica que debe almacenarse sobre cada excursión.
    private String codigo;         // Código alfanumérico único de la excursión
    private String descripcion;    // Breve descripción de la excursión
    private LocalDate fecha;            // Fecha de inicio de la excursión
    private int numeroDias;        // Número de días que dura la excursión
    private double precioInscripcion; // Precio que debe pagar un socio para inscribirse en la excursión

    // Constructor de la clase grupofc.modelo.Excursion
    public Excursion(String codigo, String descripcion, LocalDate fecha, int numeroDias, double precioInscripcion) {
        this.codigo = codigo;                     // Asigna el código de la excursión
        this.descripcion = descripcion;           // Asigna la descripción de la excursión
        this.fecha = fecha;                       // Asigna la fecha de la excursión
        this.numeroDias = numeroDias;             // Asigna el número de días de la excursión
        this.precioInscripcion = precioInscripcion; // Asigna el precio de la inscripción
    }

    //Getters
    public String getCodigo() {return codigo;}
    public String getDescripcion() {return descripcion;}
    public LocalDate getFecha() {return fecha;}
    public int getNumeroDias() {return numeroDias;}
    public double getPrecioInscripcion() {return precioInscripcion;}
    //Setters
    public void setCodigo(String codigo) {this.codigo = codigo;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
    public void setNumeroDias(int numeroDias) {this.numeroDias = numeroDias;}
    public void setPrecioInscripcion(double precioInscripcion) {this.precioInscripcion = precioInscripcion;}

    public String toString(){
        return "\n- INFORMACIÓN DE LA EXCURSIÓN -" +
                "\nCódigo de la Excursión: " + codigo +
                "\nDescripción: " + descripcion +
                "\nFecha: " + fecha +
                "\nNúmero de Dias: " + numeroDias +
                "\nPrecio Inscripción:" + precioInscripcion;
    }
}