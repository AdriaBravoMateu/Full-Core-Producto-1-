package Test;

import grupoFullCore.modelo.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TestMostrarInscripciones {

    @Test
    void mostrarInscripcionesPorSocio_deberiaRetornarInscripcionesCorrectas() {
        // Arrange
        CentroExcursionista centro = new CentroExcursionista();
        SocioEstandar socio = new SocioEstandar(13, "Juan Pérez", "12345678A", new Seguro(TipoSeguro.BASICO));
        Excursion excursion = new Excursion("EX001", "Excursión a la montaña", LocalDate.now(), 2, 100);
        Date fechaInscripcion = Date.valueOf(LocalDate.now());
        Inscripcion inscripcion = new Inscripcion(1, fechaInscripcion.toLocalDate(), socio, excursion);

        centro.añadirSocioEstandar(socio);
        centro.añadirExcursion(excursion);
        centro.añadirInscripcion(inscripcion);

        // Act
        List<Inscripcion> inscripciones = centro.mostrarInscripcionesPorSocio(13);

        // Assert
        assertEquals(1, inscripciones.size());
        assertTrue(inscripciones.contains(inscripcion));
    }
}