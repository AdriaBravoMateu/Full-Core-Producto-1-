public class Seguro {
    // Atributos privados de la clase Seguro
    private String tipo;     // Tipo de seguro (puede ser "Básico", "Completo", etc.)
    private double precio;   // Precio del seguro

    // Constructor de la clase Seguro
    // El constructor inicializa los atributos 'tipo' y 'precio' con los valores que se pasen al crear un objeto Seguro.
    public Seguro(String tipo, double precio) {
        this.tipo = tipo;      // Asigna el tipo de seguro
        this.precio = precio;  // Asigna el precio del seguro
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar los atributos 'tipo' y 'precio'.

    // Getter para el tipo de seguro
    public String getTipo() {
        return tipo;  // Devuelve el tipo de seguro
    }

    // Setter para el tipo de seguro
    public void setTipo(String tipo) {
        this.tipo = tipo;  // Permite modificar el tipo de seguro
    }

    // Getter para el precio del seguro
    public double getPrecio() {
        return precio;  // Devuelve el precio del seguro
    }

    // Setter para el precio del seguro
    public void setPrecio(double precio) {
        this.precio = precio;  // Permite modificar el precio del seguro
    }
}
