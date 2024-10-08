public class SocioFederado extends Socio {
    // Atributos privados específicos de la clase SocioFederado
    private String nif;             // Número de identificación fiscal del socio federado
    private Federacion federacion;  // Federación a la que pertenece el socio federado

    // Constructor de la clase SocioFederado
    // Llama al constructor de la clase padre (Socio) para inicializar los atributos comunes (numeroSocio y nombre)
    // y luego asigna los valores para los atributos propios de la clase SocioFederado (nif y federacion).
    public SocioFederado(int numeroSocio, String nombre, String nif, Federacion federacion) {
        super(numeroSocio, nombre);    // Llama al constructor de la clase Socio
        this.nif = nif;                // Asigna el NIF del socio federado
        this.federacion = federacion;  // Asigna la federación a la que pertenece el socio federado
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar los atributos 'nif' y 'federacion'.

    // Getter para el NIF del socio federado
    public String getNif() {
        return nif; // Devuelve el NIF del socio federado
    }

    // Setter para el NIF del socio federado
    public void setNif(String nif) {
        this.nif = nif; // Permite modificar el NIF del socio federado
    }

    // Getter para la federación del socio federado
    public Federacion getFederacion() {
        return federacion; // Devuelve la federación a la que pertenece el socio federado
    }

    // Setter para la federación del socio federado
    public void setFederacion(Federacion federacion) {
        this.federacion = federacion; // Permite modificar la federación a la que pertenece el socio
    }

    // Implementación del método abstracto 'calcularFacturaMensual'
    // Este método calcula el total a pagar por el socio federado en su factura mensual.
    // En este caso, se aplica un descuento del 5% sobre la cuota base de 10€.
    @Override
    public double calcularFacturaMensual() {
        return 10 * 0.95; // Cuota base de 10€ con un 5% de descuento (0.95)
    }
}
