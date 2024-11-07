package grupoFullCore.modelo;

import java.util.List;

public class SocioFederado extends Socio {
    //Atributos
    private String nif;
    private Federacion federacion;
    private static final double descuentoCuota = 0.05;
    private static final double descuentoExcursion = 0.10;

    public SocioFederado(int numeroSocio, String nombre, String nif, Federacion federacion) {
        super(numeroSocio, nombre);
        this.nif = nif;
        this.federacion = federacion;
    }

    public SocioFederado(int numeroSocio, String nombre, TipoSocio tipo, String nif, Federacion federacion) {
        super(numeroSocio, nombre, tipo);
        this.nif = nif;
        this.federacion = federacion;
    }

    public SocioFederado(String nombre, String nif, Federacion federacion) {
        super(nombre);
        this.nif = nif;
        this.federacion = federacion;
    }

    public SocioFederado(String nombre, TipoSocio tipo, String nif, Federacion federacion) {
        super(nombre, tipo);
        this.nif = nif;
        this.federacion = federacion;
    }

    //Getters
    public String getNif() {return nif;}
    public Federacion getFederacion() {return federacion;}
    //Setters
    public void setNif(String nif) {this.nif = nif;}
    public void setFederacion(Federacion federacion) {this.federacion = federacion;}

    //Método para calcular la factura mensual del socio federado
    public double calcularFacturaMensual(List<Inscripcion> inscripcionesDelMes) {
        double factura = cuotaMensual * (1 - descuentoCuota);
        for (Inscripcion inscripcion : inscripcionesDelMes) {
            double precioExcursion = inscripcion.getExcursion().getPrecioInscripcion();
            factura += precioExcursion * (1 - descuentoExcursion);
        }
        return factura;
    }

    // Metodo toString
    @Override
    public String toString() {
        return super.toString() +
                "Tipo de socio: Federado" +
                "\nNIF: " + nif +
                "\nNombre de la Federación:" + federacion.getNombre();
    }
}
