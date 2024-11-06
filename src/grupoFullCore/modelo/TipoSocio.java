package grupoFullCore.modelo;

public enum TipoSocio {
    ESTANDAR ("Estandar"),
    FEDERADO ("Federado"),
    INFANTIL ("Infantil"),;

    private String tipo;

    TipoSocio(String tipo){
        this.tipo = tipo;
    }


    //Getter y Setter
    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
