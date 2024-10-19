package grupoFullCore.modelo;

public class Federacion {
    // Atributos
    private String codigo;
    private String nombre;

    // Constructor
    public Federacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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
