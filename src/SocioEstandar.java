public class SocioEstandar extends Socio {
    // Atributos privados específicos de la clase SocioEstandar
    private String nif;      // Número de identificación fiscal del socio estándar
    private Seguro seguro;   // El seguro que el socio estándar ha contratado

    // Constructor de la clase SocioEstandar
    // Llama al constructor de la clase padre (Socio) para inicializar los atributos comunes (numeroSocio y nombre)
    // y luego asigna los valores para los atributos propios de la clase SocioEstandar (nif y seguro).
    public SocioEstandar(int numeroSocio, String nombre, String nif, Seguro seguro) {
        super(numeroSocio, nombre);  // Llama al constructor de la clase Socio
        this.nif = nif;              // Asigna el NIF del socio estándar
        this.seguro = seguro;        // Asigna el seguro contratado por el socio
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar los atributos 'nif' y 'seguro'.

    // Getter para el NIF del socio estándar
    public String getNif() {
        return nif; // Devuelve el NIF del socio estándar
    }

    // Setter para el NIF del socio estándar
    public void setNif(String nif) {
        this.nif = nif; // Permite modificar el NIF del socio estándar
    }

    // Getter para el seguro contratado por el socio estándar
    public Seguro getSeguro() {
        return seguro; // Devuelve el seguro contratado por el socio estándar
    }

    // Setter para el seguro del socio estándar
    public void setSeguro(Seguro seguro) {
        this.seguro = seguro; // Permite modificar el seguro del socio estándar
    }

    // Implementación del método abstracto 'calcularFacturaMensual'
    // Este método calcula el total a pagar por el socio estándar en su factura mensual.
    // En este caso, sumamos la cuota base de 10€ más el precio del seguro contratado.
    @Override
    public double calcularFacturaMensual() {
        return 10 + seguro.getPrecio(); // Cuota base de 10€ más el precio del seguro
    }
}
