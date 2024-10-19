package grupoFullCore.modelo;

public enum TipoSeguro {
    BASICO(5.0),
    COMPLETO(10.0);

    private double precio;

    TipoSeguro(double precio){
        this.precio = precio;
    }


    //Getter y Setter
    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
