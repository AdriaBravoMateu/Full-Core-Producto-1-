public class SocioInfantil extends Socio {
    // Atributo privado específico de la clase SocioInfantil
    private int numeroSocioPadreOMadre;  // Número de socio del padre o madre que cubre al socio infantil

    // Constructor de la clase SocioInfantil
    // Llama al constructor de la clase padre (Socio) para inicializar los atributos comunes (numeroSocio y nombre)
    // y luego asigna el valor para el atributo propio de la clase SocioInfantil (numeroSocioPadreOMadre).
    public SocioInfantil(int numeroSocio, String nombre, int numeroSocioPadreOMadre) {
        super(numeroSocio, nombre);  // Llama al constructor de la clase Socio
        this.numeroSocioPadreOMadre = numeroSocioPadreOMadre;  // Asigna el número de socio del padre o madre
    }

    // Métodos Getters y Setters
    // Estos métodos permiten acceder y modificar el atributo 'numeroSocioPadreOMadre'.

    // Getter para el número de socio del padre o madre
    public int getNumeroSocioPadreOMadre() {
        return numeroSocioPadreOMadre; // Devuelve el número de socio del padre o madre
    }

    // Setter para el número de socio del padre o madre
    public void setNumeroSocioPadreOMadre(int numeroSocioPadreOMadre) {
        this.numeroSocioPadreOMadre = numeroSocioPadreOMadre; // Permite modificar el número de socio del padre o madre
    }

    // Implementación del método abstracto 'calcularFacturaMensual'
    // Este método calcula el total a pagar por el socio infantil en su factura mensual.
    // En este caso, se aplica un descuento del 50% sobre la cuota base de 10€.
    @Override
    public double calcularFacturaMensual() {
        return 10 * 0.5; // Cuota base de 10€ con un 50% de descuento (0.5)
    }
}
