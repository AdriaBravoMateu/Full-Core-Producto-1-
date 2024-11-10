package CentroExcursionistaAppFC;

import grupoFullCore.modelo.*;
import grupoFullCore.modelo.DAO.*;
import grupoFullCore.modelo.DAO.factory.DAOFactory;

import java.time.LocalDate;

import static grupoFullCore.modelo.TipoSocio.INFANTIL;

public class CargaDatosIniciales {
        public static void cargarDatos() {
                // Obtener DAOs desde el Factory
                SocioDAO socioDAO = DAOFactory.getSocioDAO();
                ExcursionDAO excursionDAO = DAOFactory.getExcursionDAO();
                InscripcionDAO inscripcionDAO = DAOFactory.getInscripcionDAO();
                FederacionDAO federacionDAO = DAOFactory.getFederacionDAO();
                SeguroDAO seguroDAO = DAOFactory.getSeguroDAO();  // Asegúrate de tener un DAO para Seguro

                // Cargar tipos de seguro en la base de datos
                Seguro seguroBasico = new Seguro(TipoSeguro.BASICO);
                Seguro seguroCompleto = new Seguro(TipoSeguro.COMPLETO);
                seguroDAO.agregarSeguro(seguroBasico);
                seguroDAO.agregarSeguro(seguroCompleto);


                // ===================== CREACIÓN Y AGREGACIÓN DE INSTANCIAS DE FEDERACIÓN ======================================
                // Instancias de Federación
                Federacion federacion1 = new Federacion("FED001", "Federación Andaluza");
                Federacion federacion2 = new Federacion("FED002", "Federación Catalana");
                Federacion federacion3 = new Federacion("FED003", "Federación Madrileña");

                // Agregar federaciones a la base de datos
                federacionDAO.agregarFederacion(federacion1);
                federacionDAO.agregarFederacion(federacion2);
                federacionDAO.agregarFederacion(federacion3);


                // ===================== CREACIÓN Y AGREGACIÓN DE INSTANCIAS DE SOCIOS ======================================
                SocioEstandar socio01 = new SocioEstandar("María García", TipoSocio.ESTANDAR, "11111111E", seguroBasico);
                SocioEstandar socio02 = new SocioEstandar("Juan Pérez", TipoSocio.ESTANDAR, "22222222E", seguroBasico);
                SocioEstandar socio03 = new SocioEstandar("Ana López", TipoSocio.ESTANDAR, "33333333E", seguroBasico);
                SocioEstandar socio04 = new SocioEstandar("Pedro Sánchez", TipoSocio.ESTANDAR, "44444444E", seguroCompleto);
                SocioEstandar socio05 = new SocioEstandar("Lucía Fernández", TipoSocio.ESTANDAR, "55555555E", seguroCompleto);

                // Agregar socios estándar
                socioDAO.agregarSocio(socio01);
                socioDAO.agregarSocio(socio02);
                socioDAO.agregarSocio(socio03);
                socioDAO.agregarSocio(socio04);
                socioDAO.agregarSocio(socio05);

                SocioFederado socio06 = new SocioFederado("Carlos Ruiz", TipoSocio.FEDERADO, "11111111F", federacion1);
                SocioFederado socio07 = new SocioFederado("Isabel Díaz", TipoSocio.FEDERADO, "22222222F", federacion2);
                SocioFederado socio08 = new SocioFederado("Marta Sánchez", TipoSocio.FEDERADO, "33333333F", federacion3);
                SocioFederado socio09 = new SocioFederado("Jorge Martín", TipoSocio.FEDERADO, "44444444F", federacion1);
                SocioFederado socio10 = new SocioFederado("Cristina Ramos", TipoSocio.FEDERADO, "55555555F", federacion2);

                // Agregar socios Federados
                socioDAO.agregarSocio(socio06);
                socioDAO.agregarSocio(socio07);
                socioDAO.agregarSocio(socio08);
                socioDAO.agregarSocio(socio09);
                socioDAO.agregarSocio(socio10);


                SocioInfantil socio11 = new SocioInfantil("Luis Hernández", INFANTIL, socio01);
                SocioInfantil socio12 = new SocioInfantil("Nuria Pérez", INFANTIL, socio02);
                SocioInfantil socio13 = new SocioInfantil("Manuel Torres", INFANTIL, socio03);
                SocioInfantil socio14 = new SocioInfantil("Sofía Ruíz", INFANTIL, socio06);
                SocioInfantil socio15 = new SocioInfantil("Raúl Gómez", INFANTIL, socio07);

                // Agregar socios Infantiles a la BBDD
                socioDAO.agregarSocio(socio11);
                socioDAO.agregarSocio(socio12);
                socioDAO.agregarSocio(socio13);
                socioDAO.agregarSocio(socio14);
                socioDAO.agregarSocio(socio15);


                // ===================== CREACIÓN Y AGREGACIÓN DE INSTANCIAS DE EXCURSIÓN ======================================
                // Instancias de Excursiones
                Excursion excursion01 = new Excursion("Viaje a Barcelona", LocalDate.of(2024, 11, 10), 3, 300);
                Excursion excursion02 = new Excursion("Viaje a Madrid por Navidad", LocalDate.of(2024, 12, 18), 3, 400);
                Excursion excursion03 = new Excursion("Camino de Santiago", LocalDate.of(2025, 1, 15), 15, 500);
                Excursion excursion04 = new Excursion("Excursión a Sierra Nevada", LocalDate.of(2025, 2, 20), 5, 350);
                Excursion excursion05 = new Excursion("Descubre Granada", LocalDate.of(2025, 3, 5), 4, 250);

                // Agregar excursiones a la base de datos
                excursionDAO.agregarExcursion(excursion01);
                excursionDAO.agregarExcursion(excursion02);
                excursionDAO.agregarExcursion(excursion03);
                excursionDAO.agregarExcursion(excursion04);
                excursionDAO.agregarExcursion(excursion05);

                // ===================== CREACIÓN Y AGREGACIÓN DE INSTANCIAS DE INSCRIPCIONES ======================================
                // Inscripciones
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 1), socio01, excursion01));
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 2), socio02, excursion02));
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 3), socio03, excursion03));
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 4), socio04, excursion04)); // No tine hijo
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 5), socio05, excursion05)); // No tiene hijo
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 6), socio06, excursion01));
                // Socio 07 tiene hijo pero no inscripción
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 8), socio08, excursion03)); // No tiene hijo
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 9), socio09, excursion04)); // No tiene hijo
                // Socio 10 no tiene hijo ni inscripción
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 11), socio11, excursion01));
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 12), socio12, excursion02));
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 13), socio13, excursion03));
                inscripcionDAO.agregarInscripcion(new Inscripcion(LocalDate.of(2024, 11, 14), socio14, excursion01));
                // Socio 15 no Inscrito
        }
}






//package CentroExcursionistaAppFC;
//
//import grupoFullCore.modelo.*;
//import grupoFullCore.modelo.DAO.*;
//import grupoFullCore.modelo.DAO.factory.DAOFactory;
//
//import java.time.LocalDate;
//
//public class CargaDatosIniciales {
//        public static void cargarDatos() {
//                // Obtener DAOs desde el Factory
//                SocioDAO socioDAO = DAOFactory.getSocioDAO();
//                ExcursionDAO excursionDAO = DAOFactory.getExcursionDAO();
//                InscripcionDAO inscripcionDAO = DAOFactory.getInscripcionDAO();
//                FederacionDAO federacionDAO = DAOFactory.getFederacionDAO();
//                SeguroDAO seguroDAO = DAOFactory.getSeguroDAO();  // Asegúrate de tener un DAO para Seguro
//
//                // Cargar tipos de seguro en la base de datos (valores iniciales necesarios)
//                Seguro seguroBasico = new Seguro(TipoSeguro.BASICO);
//                Seguro seguroCompleto = new Seguro(TipoSeguro.COMPLETO);
//                seguroDAO.agregarSeguro(seguroBasico);  // Inserta "Basico" en la tabla seguro
//                seguroDAO.agregarSeguro(seguroCompleto); // Inserta "Completo" en la tabla seguro
//
//                // Instancias de Federación
//                Federacion federacion1 = new Federacion("FED001", "Federación Andaluza");
//                Federacion federacion2 = new Federacion("FED002", "Federación Catalana");
//                Federacion federacion3 = new Federacion("FED003", "Federación Madrileña");
//
//                // Agregar federaciones a la base de datos
//                federacionDAO.agregarFederacion(federacion1);
//                federacionDAO.agregarFederacion(federacion2);
//                federacionDAO.agregarFederacion(federacion3);
//
//                // Instancias de Socio Estandar
//                SocioEstandar socio01 = new SocioEstandar("Socio Estandar 1", TipoSocio.ESTANDAR, "11111111E", seguroBasico);
//                SocioEstandar socio02 = new SocioEstandar("Socio Estandar 2", TipoSocio.ESTANDAR,"22222222E", seguroBasico);
//                // ... Añadir más socios estándar
//
//                // Instancias de Socio Federado asignando una federación
//                SocioFederado socio05 = new SocioFederado("Socio Federado 1", TipoSocio.FEDERADO, "11111111F", federacion1);
//                SocioFederado socio06 = new SocioFederado("Socio Federado 2", TipoSocio.FEDERADO, "22222222F", federacion2);
//
//                // Instancias de Excursión
//                Excursion excursion01 = new Excursion("Excursion 01", LocalDate.of(2024, 10, 25), 5, 250);
//                Excursion excursion02 = new Excursion("Excursion 02", LocalDate.of(2024, 10, 28), 3, 150);
//                // ... Añadir más excursiones
//
//                // Instancias de Inscripción
//                Inscripcion inscripcion01 = new Inscripcion(LocalDate.of(2024, 10, 18), socio01, excursion01);
//                Inscripcion inscripcion02 = new Inscripcion(LocalDate.of(2024, 10, 18), socio05, excursion01);
//                // ... Añadir más inscripciones
//
//                // Agregar instancias a la base de datos usando DAOs
//                socioDAO.agregarSocio(socio01);
//                socioDAO.agregarSocio(socio02);
//                socioDAO.agregarSocio(socio05);
//                socioDAO.agregarSocio(socio06);
//                // ... Agregar otros socios
//
//                excursionDAO.agregarExcursion(excursion01);
//                excursionDAO.agregarExcursion(excursion02);
//                // ... Agregar otras excursiones
//
//                inscripcionDAO.agregarInscripcion(inscripcion01);
//                inscripcionDAO.agregarInscripcion(inscripcion02);
//                // ... Agregar otras inscripciones
//        }
//}
