package grupoFullCore.modelo;

import java.util.List;

public class SocioEstandar extends Socio {
    // Atributos
    private String nif;
    private Seguro seguro;

    // Constructor
    public SocioEstandar(int numeroSocio, String nombre, String nif, Seguro seguro) {
        super(numeroSocio, nombre);
        this.nif = nif;
        this.seguro = seguro;
    }

    public SocioEstandar(int numeroSocio, String nombre, TipoSocio tipo,  String nif, Seguro seguro) {
        super(numeroSocio, nombre, tipo);
        this.nif = nif;
        this.seguro = seguro;
    }

    public SocioEstandar(String nombre, String nif, Seguro seguro) {
        super(nombre);
        this.nif = nif;
        this.seguro = seguro;
    }

    public SocioEstandar(String nombre, TipoSocio tipo,  String nif, Seguro seguro) {
        super(nombre, tipo);
        this.nif = nif;
        this.seguro = seguro;
    }

    //Getters
    public String getNif() {return nif;}
    public Seguro getSeguro() {return seguro;}
    //Setters
    public void setNif(String nif) {this.nif = nif;}
    public void setSeguro(Seguro seguro) {this.seguro = seguro;}

    // Metodo para modificar el seguro del socio
    public void modificarSeguro(Seguro nuevoSeguro) {
        this.seguro = nuevoSeguro;
    }

    //Método para calcular la factura mensual del socio estandar
    @Override
    public double calcularFacturaMensual(List<Inscripcion> inscripcionesDelMes) {
        double factura = cuotaMensual;
        for (Inscripcion inscripcion : inscripcionesDelMes) {
            double precioExcursion = inscripcion.getExcursion().getPrecioInscripcion();
            factura += precioExcursion + seguro.getPrecio();
        }
        return factura;
    }

    // Metodo toString
    @Override
    public String toString() {
        return super.toString() +
                "Tipo de socio: Estándar" +
                "\nNIF: " + nif +
                "\nTipo de Seguro: " + seguro.getTipo() +
                "\nPrecio del Seguro: " + seguro.getPrecio();
    }
}
