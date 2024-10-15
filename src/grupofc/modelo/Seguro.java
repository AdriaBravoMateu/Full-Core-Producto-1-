package grupofc.modelo;

import grupofc.modelo.TipoSeguro;

public class Seguro {
    // Atributos privados de la clase grupofc.modelo.Seguro
    private TipoSeguro tipo;  // Tipo de seguro (Enum: BASICO o COMPLETO)
    private double precio;    // Precio del seguro

    // Constructor de la clase grupofc.modelo.Seguro
    public Seguro(TipoSeguro tipo, double precio) {
        this.tipo = tipo;      // Asigna el tipo de seguro (BASICO o COMPLETO)
        this.precio = precio;  // Asigna el precio del seguro
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
        return "grupofc.modelo.Seguro {" +
                "Tipo = " + tipo +
                "Precio = " + precio +
                 '}';
    }
}
