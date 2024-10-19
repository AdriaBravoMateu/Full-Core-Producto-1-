package grupoFullCore.modelo;

import java.time.LocalDate;

public class Excursion {
    // Atributos
    private String codigo;
    private String descripcion;
    private LocalDate fecha;
    private int numeroDias;
    private double precioInscripcion;

    // Constructor
    public Excursion(String codigo, String descripcion, LocalDate fecha, int numeroDias, double precioInscripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.numeroDias = numeroDias;
        this.precioInscripcion = precioInscripcion;
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