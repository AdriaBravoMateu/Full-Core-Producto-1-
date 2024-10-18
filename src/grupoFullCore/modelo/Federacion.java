package grupoFullCore.modelo;

public class Federacion {
    // Atributos privados de la clase grupofc.modelo.Federacion
    private String codigo;   // Código único de la federación (por ejemplo, "FED01")
    private String nombre;   // Nombre de la federación (por ejemplo, "Federación de Montaña")

    // Constructor de la clase grupofc.modelo.Federacion
    // El constructor inicializa los atributos 'codigo' y 'nombre' con los valores que se pasan al crear un objeto grupofc.modelo.Federacion.
    public Federacion(String codigo, String nombre) {
        this.codigo = codigo;   // Asigna el código único de la federación
        this.nombre = nombre;   // Asigna el nombre de la federación
    }

    // Getters
    public String getCodigo() {return codigo;}
    public String getNombre() {return nombre;}
    //Setters
    public void setCodigo(String codigo) {this.codigo = codigo;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    @Override
    public String toString() {
        return "\n- INFORMACIÓN DE LA FEDERACIÓN -" +
                "\nCódigo: " + codigo +
                "\nNombre: " + nombre;
    }

}
