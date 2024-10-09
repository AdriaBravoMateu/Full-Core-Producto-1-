public abstract class Socio {
    // Atributos protegidos de la clase Socio
    // Estos representan los datos generales de cualquier socio en el sistema.
    protected int numeroSocio;   // Número de socio único para identificar al socio
    protected String nombre;     // Nombre del socio

    // Constructor de la clase Socio
    // Este constructor inicializa el número de socio y el nombre, que son comunes para todos los tipos de socios.
    public Socio(int numeroSocio, String nombre) {
        this.numeroSocio = numeroSocio; // Asigna el número de socio
        this.nombre = nombre;           // Asigna el nombre del socio
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar los atributos numeroSocio y nombre.

    // Getter para el número de socio
    public int getNumeroSocio() {
        return numeroSocio; // Devuelve el número de socio
    }

    // Setter para el número de socio
    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio; // Permite modificar el número de socio
    }

    // Getter para el nombre del socio
    public String getNombre() {
        return nombre; // Devuelve el nombre del socio
    }

    // Setter para el nombre del socio
    public void setNombre(String nombre) {
        this.nombre = nombre; // Permite modificar el nombre del socio
    }

    // Método abstracto
    // Este método debe ser implementado por las clases que hereden de 'Socio'.
    // Cada tipo de socio calculará su factura mensual de forma distinta.
    public abstract double calcularFacturaMensual();

    // Método toString
    // Este método devuelve una representación en forma de cadena del socio,
    // incluyendo su número de socio y nombre.
    @Override
    public String toString() {
        return "Socio {" +
                "Número de Socio = " + getNumeroSocio() + '\'' +
                ", Nombre = '" + getNombre() + '\''
                ;
    }
}
