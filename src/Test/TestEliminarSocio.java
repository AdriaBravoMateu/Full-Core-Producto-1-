package Test;

import grupoFullCore.modelo.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
public class TestEliminarSocio {

    @Test
    void eliminarSocio_noDeberiaEliminarSocioConInscripciones() {
        // Arrange
        CentroExcursionista centro = new CentroExcursionista();
        SocioEstandar socio = new SocioEstandar(1, "Juan Pérez", "12345678A", new Seguro(TipoSeguro.BASICO));
        Excursion excursion = new Excursion("EX001", "Excursión a la montaña", LocalDate.now().plusDays(10), 2, 100);
        Date fechaInscripcion = Date.valueOf(LocalDate.now());
        Inscripcion inscripcion = new Inscripcion(1, fechaInscripcion.toLocalDate(), socio, excursion);

        centro.añadirSocioEstandar(socio);
        centro.añadirExcursion(excursion);
        centro.añadirInscripcion(inscripcion);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            centro.eliminarSocio(1);
        });

        assertEquals("No se puede eliminar un socio con inscripciones activas.", exception.getMessage());
    }
}