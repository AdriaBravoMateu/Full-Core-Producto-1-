package grupoFullCore.modelo;

import java.util.List;

public class SocioInfantil extends Socio {
    // Atributos
    private Socio progenitor;
    private int numeroSocioProgenitor;
    private static final double descuentoCuota = 0.50;

    public SocioInfantil(int numeroSocio, String nombre, Socio progenitor) {
        super(numeroSocio, nombre);
        this.progenitor = progenitor;  // Asigna el objeto progenitor completo
        if (progenitor != null) {
            this.numeroSocioProgenitor = progenitor.getNumeroSocio();
        }
    }

    public SocioInfantil(int numeroSocio, String nombre, TipoSocio tipo, Socio progenitor) {
        super(numeroSocio, nombre, tipo);
        this.progenitor = progenitor;  // Asigna el objeto progenitor completo
        if (progenitor != null) {
            this.numeroSocioProgenitor = progenitor.getNumeroSocio();
        }
    }

    public SocioInfantil(String nombre, Socio progenitor) {
        super(nombre);
        this.progenitor = progenitor;  // Asigna el objeto progenitor completo
        if (progenitor != null) {
            this.numeroSocioProgenitor = progenitor.getNumeroSocio();
        }
    }

    public SocioInfantil(String nombre, TipoSocio tipo, Socio progenitor) {
        super(nombre, tipo);
        this.progenitor = progenitor;  // Asigna el objeto progenitor completo
        if (progenitor != null) {
            this.numeroSocioProgenitor = progenitor.getNumeroSocio();
        }
    }

    // Getters
    public Socio getProgenitor() {
        return progenitor;
    }

    public int getNumeroSocioProgenitor() {
        return numeroSocioProgenitor;
    }

    // Setters
    public void setNumeroSocioProgenitor(int numeroSocioProgenitor) {
        this.numeroSocioProgenitor = numeroSocioProgenitor;
    }

    // Método para calcular la factura mensual del socio infantil
    @Override
    public double calcularFacturaMensual(List<Inscripcion> inscripcionesDelMes) {
        double factura = cuotaMensual * (1 - descuentoCuota);
        for (Inscripcion inscripcion : inscripcionesDelMes) {
            double precioExcursion = inscripcion.getExcursion().getPrecioInscripcion();
            factura += precioExcursion;
        }
        return factura;
    }

    // Método toString
    @Override
    public String toString() {
        return super.toString() +
                "Tipo de socio: Infantil" +
                "\nNúmero de Socio del progenitor: " + numeroSocioProgenitor;
    }
}
