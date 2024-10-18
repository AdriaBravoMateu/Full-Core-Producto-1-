package Test;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;

import grupoFullCore.modelo.*;

public class CargaDatosIniciales {
        public static void cargarDatos(CentroExcursionista centro) {

                //Instancias de socio estandar
                SocioEstandar socio01 = new SocioEstandar(1, "Socio Estandar 1", "11111111E", new Seguro(TipoSeguro.BASICO, 50));
                SocioEstandar socio02 = new SocioEstandar(2, "Socio Estandar 2", "22222222E", new Seguro(TipoSeguro.BASICO, 50));
                SocioEstandar socio03 = new SocioEstandar(3, "Socio Estandar 3", "33333333E", new Seguro(TipoSeguro.COMPLETO, 100));
                SocioEstandar socio04 = new SocioEstandar(4, "Socio Estandar 4", "44444444E", new Seguro(TipoSeguro.COMPLETO, 100));

                //Instancias de socio federado
                //Primero asignamos cada federación a una variable
                Federacion federacion1 = centro.getFederaciones().get(0);
                Federacion federacion2 = centro.getFederaciones().get(1);
                Federacion federacion3 = centro.getFederaciones().get(2);
                SocioFederado socio05 = new SocioFederado(5, "Socio Federado 1", "11111111F", federacion1);
                SocioFederado socio06 = new SocioFederado(6, "Socio Federado 2", "22222222F", federacion1);
                SocioFederado socio07 = new SocioFederado(7, "Socio Federado 3", "33333333F", federacion2);
                SocioFederado socio08 = new SocioFederado(8, "Socio Federado 4", "44444444F", federacion3);

                //Instancias de socio infantil
                SocioInfantil socio09 = new SocioInfantil(9, "Socio Infantil 1", socio01);
                SocioInfantil socio10 = new SocioInfantil(10, "Socio Infantil 2", socio01);
                SocioInfantil socio11 = new SocioInfantil(11, "Socio Infantil 3", socio05);
                SocioInfantil socio12 = new SocioInfantil(12, "Socio Infantil 4", socio07);

                //Instancias de excursiones
                //Creamos variables para las fechas
                LocalDate fechaExcursion01 = LocalDate.of(2024, 10, 30);
                LocalDate fechaExcursion02 = LocalDate.of(2024, 11, 10);
                LocalDate fechaExcursion03 = LocalDate.of(2024, 11, 19);
                Excursion excursion01 = new Excursion("Exc01", "Excursion 01", fechaExcursion01, 5, 250);
                Excursion excursion02 = new Excursion("Exc02", "Excursion 02", fechaExcursion02, 3, 150);
                Excursion excursion03 = new Excursion("Exc03", "Excursion 03", fechaExcursion03, 7, 400);

                //Instancias de inscripciones
                //Creamos variables para la conversión de fechas
                LocalDate fechaLocalInscripcion01 = LocalDate.of(2024, 10, 18);
                LocalDate fechaLocalInscripcion02 = LocalDate.of(2024, 10, 21);
                Date fechaInscripcion01 = Date.from(fechaLocalInscripcion01.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date fechaInscripcion02 = Date.from(fechaLocalInscripcion02.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Inscripcion inscripcion01 = new Inscripcion(1, fechaInscripcion01, socio01, excursion01);
                Inscripcion inscripcion02 = new Inscripcion(2, fechaInscripcion01, socio09, excursion01);
                Inscripcion inscripcion03 = new Inscripcion(3, fechaInscripcion01, socio10, excursion01);
                Inscripcion inscripcion04 = new Inscripcion(4, fechaInscripcion01, socio05, excursion01);
                Inscripcion inscripcion05 = new Inscripcion(5, fechaInscripcion02, socio07, excursion02);
                Inscripcion inscripcion06 = new Inscripcion(6, fechaInscripcion02, socio08, excursion02);
                Inscripcion inscripcion07 = new Inscripcion(7, fechaInscripcion02, socio06, excursion03);
                Inscripcion inscripcion08 = new Inscripcion(8, fechaInscripcion02, socio08, excursion03);

                //Añadir instancias al programa
                //Socios
                centro.añadirSocioEstandar(socio01);
                centro.añadirSocioEstandar(socio02);
                centro.añadirSocioEstandar(socio03);
                centro.añadirSocioEstandar(socio04);
                centro.añadirSocioFederado(socio05);
                centro.añadirSocioFederado(socio06);
                centro.añadirSocioFederado(socio07);
                centro.añadirSocioFederado(socio08);
                centro.añadirSocioInfantil(socio09);
                centro.añadirSocioInfantil(socio10);
                centro.añadirSocioInfantil(socio11);
                centro.añadirSocioInfantil(socio12);

                //Excursiones
                centro.añadirExcursion(excursion01);
                centro.añadirExcursion(excursion02);
                centro.añadirExcursion(excursion03);

                //Inscripciones
                centro.añadirInscripcion(inscripcion01);
                centro.añadirInscripcion(inscripcion02);
                centro.añadirInscripcion(inscripcion03);
                centro.añadirInscripcion(inscripcion04);
                centro.añadirInscripcion(inscripcion05);
                centro.añadirInscripcion(inscripcion06);
                centro.añadirInscripcion(inscripcion07);
                centro.añadirInscripcion(inscripcion08);
        }
}


