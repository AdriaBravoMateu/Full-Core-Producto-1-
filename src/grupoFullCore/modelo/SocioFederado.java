package grupoFullCore.modelo;

public class SocioFederado extends Socio {

    private String nif;
    private Federacion federacion;

    public SocioFederado(int numeroSocio, String nombre, String nif, Federacion federacion) {
        super(numeroSocio, nombre);    // Llama al constructor de la clase grupofc.modelo.Socio
        this.nif = nif;                // Asigna el NIF del socio federado
        this.federacion = federacion;  // Asigna la federación a la que pertenece el socio federado
    }

    //Getters
    public String getNif() {return nif;}
    public Federacion getFederacion() {return federacion;}
    //Setters
    public void setNif(String nif) {this.nif = nif;}
    public void setFederacion(Federacion federacion) {this.federacion = federacion;}

// Implementación del metodo abstracto 'calcularFacturaMensual'.
// Este metodo calcula el total a pagar por el socio federado en su factura mensual.
// En este caso, se aplica un descuento del 5% sobre la cuota base de 10€.

    @Override
    public double calcularFacturaMensual() {
        return 10 * 0.95; // Cuota base de 10€ con un 5% de descuento (0.95)
    }

    // Metodo toString
    // Este metodo devuelve una representación en forma de cadena del socio federado
    // incluyendo su número de socio, nombre, nif y la federación a la que pertenece.
    @Override
    public String toString() {
        return super.toString() +
                "Tipo de socio: Federado" +
                "\nNIF: " + nif +
                "\nNombre de la Federación:" + federacion.getNombre();
    }
}
