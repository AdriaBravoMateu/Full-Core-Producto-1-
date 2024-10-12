public class Federacion {
    // Atributos privados de la clase Federacion
    private String codigo;   // Código único de la federación (por ejemplo, "FED01")
    private String nombre;   // Nombre de la federación (por ejemplo, "Federación de Montaña")

    // Constructor de la clase Federacion
    // El constructor inicializa los atributos 'codigo' y 'nombre' con los valores que se pasan al crear un objeto Federacion.
    public Federacion(String codigo, String nombre) {
        this.codigo = codigo;   // Asigna el código único de la federación
        this.nombre = nombre;   // Asigna el nombre de la federación
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar los atributos 'codigo' y 'nombre'.

    // Getter para el código de la federación
    public String getCodigo() {
        return codigo;  // Devuelve el código de la federación
    }

    // Setter para el código de la federación
    public void setCodigo(String codigo) {
        this.codigo = codigo;  // Permite modificar el código de la federación
    }

    // Getter para el nombre de la federación
    public String getNombre() {
        return nombre;  // Devuelve el nombre de la federación
    }

    // Setter para el nombre de la federación
    public void setNombre(String nombre) {
        this.nombre = nombre;  // Permite modificar el nombre de la federación
    }

    @Override
    public String toString() {
        return "Federacion {" +
                "codigo ='" + codigo + '\'' +
                ", nombre ='" + nombre + '\'' +
                 '}';
    }

}
