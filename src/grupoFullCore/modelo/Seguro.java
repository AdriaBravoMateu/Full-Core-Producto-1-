package grupoFullCore.modelo;

public class Seguro {
    // Atributos
    private TipoSeguro tipo;
    private double precio;

    // Constructor
    public Seguro(TipoSeguro tipo) {
        this.tipo = tipo;
        this.precio = precio;
    }

    // Getters
    public TipoSeguro getTipo() {return tipo;}
    public double getPrecio() {return precio;}
    //Setters
    public void setTipo(TipoSeguro tipo) {this.tipo = tipo;}
    public void setPrecio(double precio) {this.precio = precio;}

    // Método toString para mostrar información sobre el seguro
    @Override
    public String toString() {
        return "\n- INFORMACION DEL SEGURO -" +
                "\nTipo: " + tipo +
                "\nPrecio: " + precio;
    }
}
