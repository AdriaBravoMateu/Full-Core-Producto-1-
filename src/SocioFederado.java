public class SocioFederado extends Socio {

    private String nif;
    private Federacion federacion;

    public SocioFederado(int numeroSocio, String nombre, String nif, Federacion federacion) {
        super(numeroSocio, nombre);    // Llama al constructor de la clase Socio
        this.nif = nif;                // Asigna el NIF del socio federado
        this.federacion = federacion;  // Asigna la federación a la que pertenece el socio federado
    }

    // Getter para devolver el NIF del socio federado
    public String getNif() {return nif;}

    // Setter para poder modificar el NIF del socio federado
    public void setNif(String nif) {this.nif = nif;}

    // Getter para devolver la federación del socio federado
    public Federacion getFederacion() {return federacion;}

    // Setter para poder modificar la federación a la que pertenece el socio federado
    public void setFederacion(Federacion federacion) {this.federacion = federacion;}

// Implementación del metodo abstracto 'calcularFacturaMensual'.
// Este metodo calcula el total a pagar por el socio federado en su factura mensual.
// En este caso, se aplica un descuento del 5% sobre la cuota base de 10€.

    @Override
    public double calcularFacturaMensual() {
        return 10 * 0.95; // Cuota base de 10€ con un 5% de descuento (0.95)
    }

    @Override
    public String toString() {
        return "SocioFederado{" +
                "nif='" + nif + '\'' +
                ", federacion=" + federacion.getNombre() +
                '}';
    }


}
