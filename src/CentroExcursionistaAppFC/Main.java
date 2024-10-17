package CentroExcursionistaAppFC;

import Controlador.ControladorCentroExcursionista;
import grupofc.modelo.CentroExcursionista;
import Vista.VistaCentroExcursionista;
public class Main {
    public static void main(String[] args) {
        // Crear el modelo y la vista de consola
        CentroExcursionista centro = new CentroExcursionista();
        VistaCentroExcursionista vista = new VistaCentroExcursionista();

        // Crear el controlador
        ControladorCentroExcursionista controlador = new ControladorCentroExcursionista(centro, vista);

        // Iniciar la aplicación
        controlador.iniciar();
    }
}
