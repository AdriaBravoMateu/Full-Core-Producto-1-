package grupoFullCore.modelo;

public enum TipoSeguro {
    BASICO(5.0),
    COMPLETO(10.0);

    private double precio;

    TipoSeguro(double precio){
        this.precio = precio;
    }

    // Método personalizado para convertir un String a TipoSeguro sin importar mayúsculas/minúsculas
    public static TipoSeguro fromString(String tipo) {
        try {
            return TipoSeguro.valueOf(tipo.toUpperCase()); // Convierte el String a mayúsculas antes de buscar
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de seguro no válido: " + tipo);
        }
    }

    //Getter y Setter
    public double getPrecio(){
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
