package grupoFullCore.modelo;

import java.util.List;

public class SocioInfantil extends Socio {
    // Atributos
    private Socio progenitor;
    private int numeroSocioProgenitor;
    private static final double descuentoCuota = 0.50;

    public SocioInfantil(int numeroSocio, String nombre, Socio progenitor) {
        super(numeroSocio, nombre);
        this.numeroSocioProgenitor = progenitor.getNumeroSocio();
    }

    //Getters
    public Socio getProgenitor() {return progenitor;}
    //Setters
    public void setNumeroSocioProgenitor(int numeroSocioProgenitor) {this.numeroSocioProgenitor = numeroSocioProgenitor;}


    //Método para calcular la factura mensual del socio infantil
    @Override
    public double calcularFacturaMensual(List<Inscripcion> inscripcionesDelMes) {
        double factura = cuotaMensual * (1 - descuentoCuota);
        for (Inscripcion inscripcion : inscripcionesDelMes) {
            double precioExcursion = inscripcion.getExcursion().getPrecioInscripcion();
            factura += precioExcursion;
        }
        return factura;
    }

    // Metodo toString
    @Override
    public String toString() {
        return super.toString() +
                "Tipo de socio: Infantil" +
                "\nNúmero de Socio del progenitor: " + numeroSocioProgenitor;
    }
}