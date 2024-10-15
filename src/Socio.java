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

    //Getters
    public int getNumeroSocio() {return numeroSocio;}
    public String getNombre() {return nombre;}

    //Setters
    public void setNumeroSocio(int numeroSocio) {this.numeroSocio = numeroSocio;}
    public void setNombre(String nombre) {this.nombre = nombre;}

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
                "Nombre = '" + getNombre() + '\''
                ;
    }
}
