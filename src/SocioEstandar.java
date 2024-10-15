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

    //Getters
    public String getNif() {return nif;}
    public Seguro getSeguro() {return seguro;}
    //Setters
    public void setNif(String nif) {this.nif = nif;}
    public void setSeguro(Seguro seguro) {this.seguro = seguro;}

    // Método para modificar el seguro del socio
    // Este método recibe un nuevo objeto Seguro y lo asigna al socio estándar.
    public void modificarSeguro(Seguro nuevoSeguro) {
        this.seguro = nuevoSeguro;  // Asigna el nuevo seguro al socio estándar
    }

    // Implementación del método abstracto 'calcularFacturaMensual'
    // Este método calcula el total a pagar por el socio estándar en su factura mensual.
    // En este caso, sumamos la cuota base de 10€ más el precio del seguro contratado.
    @Override
    public double calcularFacturaMensual() {
        return 10 + seguro.getPrecio(); // Cuota base de 10€ más el precio del seguro
    }

    // Método toString
    // Este método devuelve una representación en forma de cadena del socio estándar,
    // incluyendo su número de socio, nombre, NIF y detalles del seguro.
    @Override
    public String toString() {
        return super.toString() +
                "Tipo de socio: Estándar" + '\'' +
                "NIF = '" + nif + '\'' +
                "Seguro = " + seguro.getTipo() + '\'' +
                "Precio Seguro = " + seguro.getPrecio() +
                 '}';
    }
}
