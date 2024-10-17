package grupofc.modelo;

import grupofc.modelo.Socio;

public class SocioInfantil extends Socio {
    // Atributo privado específico de la clase grupofc.modelo.SocioInfantil
    private Socio progenitor;
    private int numeroSocioProgenitor;  // Número de socio del padre o madre que cubre al socio infantil

    // Constructor de la clase grupofc.modelo.SocioInfantil
    // Llama al constructor de la clase padre (grupofc.modelo.Socio) para inicializar los atributos comunes (numeroSocio y nombre)
    // y luego asigna el valor para el atributo propio de la clase grupofc.modelo.SocioInfantil (numeroSocioPadreOMadre).
    public SocioInfantil(int numeroSocio, String nombre, Socio progenitor) {
        super(numeroSocio, nombre);
        this.numeroSocioProgenitor = progenitor.getNumeroSocio();
    }

    //Getters
    public Socio getProgenitor() {return progenitor;}
    //Setters
    public void setNumeroSocioProgenitor(int numeroSocioProgenitor) {this.numeroSocioProgenitor = numeroSocioProgenitor;}


    // Implementación del metodo abstracto 'calcularFacturaMensual'
    // Este metodo calcula el total a pagar por el socio infantil en su factura mensual.
    // En este caso, se aplica un descuento del 50% sobre la cuota base de 10€.
    @Override
    public double calcularFacturaMensual() {
        return 10 * 0.5; // Cuota base de 10€ con un 50% de descuento (0.5)
    }

    // Metodo toString
    // Este metodo devuelve una representación en forma de cadena del socio infantil
    // incluyendo su número de socio, nombre y el número de socio de 1 progenitor.
    @Override
    public String toString() {
        return super.toString() +
                "Tipo de socio: Infantil" +
                "\nNúmero de Socio del progenitor: " + numeroSocioProgenitor;
    }
}