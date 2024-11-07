package CentroExcursionistaAppFC;

import grupoFullCore.modelo.*;
import grupoFullCore.modelo.DAO.*;
import grupoFullCore.modelo.DAO.factory.DAOFactory;

import java.time.LocalDate;

public class CargaDatosIniciales {
        public static void cargarDatos() {
                // Obtener DAOs desde el Factory
                SocioDAO socioDAO = DAOFactory.getSocioDAO();
                ExcursionDAO excursionDAO = DAOFactory.getExcursionDAO();
                InscripcionDAO inscripcionDAO = DAOFactory.getInscripcionDAO();
                FederacionDAO federacionDAO = DAOFactory.getFederacionDAO();
                SeguroDAO seguroDAO = DAOFactory.getSeguroDAO();  // Asegúrate de tener un DAO para Seguro

                // Cargar tipos de seguro en la base de datos (valores iniciales necesarios)
                Seguro seguroBasico = new Seguro(TipoSeguro.BASICO);
                Seguro seguroCompleto = new Seguro(TipoSeguro.COMPLETO);
                seguroDAO.agregarSeguro(seguroBasico);  // Inserta "Basico" en la tabla seguro
                seguroDAO.agregarSeguro(seguroCompleto); // Inserta "Completo" en la tabla seguro

                // Instancias de Federación
                Federacion federacion1 = new Federacion("FED001", "Federación Andaluza");
                Federacion federacion2 = new Federacion("FED002", "Federación Catalana");
                Federacion federacion3 = new Federacion("FED003", "Federación Madrileña");

                // Agregar federaciones a la base de datos
                federacionDAO.agregarFederacion(federacion1);
                federacionDAO.agregarFederacion(federacion2);
                federacionDAO.agregarFederacion(federacion3);

                // Instancias de Socio Estandar
                SocioEstandar socio01 = new SocioEstandar("Socio Estandar 1", TipoSocio.ESTANDAR, "11111111E", seguroBasico);
                SocioEstandar socio02 = new SocioEstandar("Socio Estandar 2", TipoSocio.ESTANDAR,"22222222E", seguroBasico);
                // ... Añadir más socios estándar

                // Instancias de Socio Federado asignando una federación
                SocioFederado socio05 = new SocioFederado("Socio Federado 1", TipoSocio.FEDERADO, "11111111F", federacion1);
                SocioFederado socio06 = new SocioFederado("Socio Federado 2", TipoSocio.FEDERADO, "22222222F", federacion2);

                // Instancias de Excursión
                Excursion excursion01 = new Excursion("Excursion 01", LocalDate.of(2024, 10, 25), 5, 250);
                Excursion excursion02 = new Excursion("Excursion 02", LocalDate.of(2024, 10, 28), 3, 150);
                // ... Añadir más excursiones

                // Instancias de Inscripción
                Inscripcion inscripcion01 = new Inscripcion(LocalDate.of(2024, 10, 18), socio01, excursion01);
                Inscripcion inscripcion02 = new Inscripcion(LocalDate.of(2024, 10, 18), socio05, excursion01);
                // ... Añadir más inscripciones

                // Agregar instancias a la base de datos usando DAOs
                socioDAO.agregarSocio(socio01);
                socioDAO.agregarSocio(socio02);
                socioDAO.agregarSocio(socio05);
                socioDAO.agregarSocio(socio06);
                // ... Agregar otros socios

                excursionDAO.agregarExcursion(excursion01);
                excursionDAO.agregarExcursion(excursion02);
                // ... Agregar otras excursiones

                inscripcionDAO.agregarInscripcion(inscripcion01);
                inscripcionDAO.agregarInscripcion(inscripcion02);
                // ... Agregar otras inscripciones
        }
}
