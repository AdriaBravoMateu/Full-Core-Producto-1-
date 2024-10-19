package CentroExcursionistaAppFC;

import Test.CargaDatosIniciales;
import grupoFullCoreControlador.ControladorCentroExcursionista;
import grupoFullCore.modelo.CentroExcursionista;
import grupoFullCoreVista.VistaCentroExcursionista;
public class Main {
    public static void main(String[] args) {
        // Crear el modelo y la vista de consola
        CentroExcursionista centro = new CentroExcursionista();
        VistaCentroExcursionista vista = new VistaCentroExcursionista();

        // Crear el controlador
        ControladorCentroExcursionista controlador = new ControladorCentroExcursionista(centro, vista);

        //Carga de datos para pruebas
        CargaDatosIniciales.cargarDatos(centro);

        // Iniciar la aplicación
        controlador.iniciar();
    }
}
