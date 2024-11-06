package grupoFullCore.modelo;

import java.util.List;

public abstract class Socio {
    // Atributos
    protected int numeroSocio;
    protected String nombre;
    protected TipoSocio tipoSocio;
    protected static final double cuotaMensual = 10.0;

    // Constructor
    public Socio(int numeroSocio, String nombre) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
    }

    public Socio(int numeroSocio, String nombre, TipoSocio tipoSocio) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.tipoSocio = tipoSocio;
    }

    //Getters
    public int getNumeroSocio() {return numeroSocio;}
    public String getNombre() {return nombre;}

    public String getTipo() {return String.valueOf(tipoSocio);}

    //Setters
    public void setNumeroSocio(int numeroSocio) {this.numeroSocio = numeroSocio;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public void setTipo(TipoSocio tipoSocio) {this.tipoSocio = tipoSocio;}

    // Método para calcular las facturas mensuales de los socios
    public abstract double calcularFacturaMensual(List<Inscripcion> inscripcionesDelMes);

    // Metodo toString
    @Override
    public String toString() {
        return "\n- INFORMACION DEL SOCIO -" +
                "\nNúmero de Socio: " + getNumeroSocio() +
                "\nNombre de Socio: " + getNombre() +
                "\n";
    }
}
