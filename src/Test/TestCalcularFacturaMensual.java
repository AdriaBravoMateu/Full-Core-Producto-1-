package Test;

import grupoFullCore.modelo.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCalcularFacturaMensual {
    @Test
    void calcularFacturaMensualPorSocio_deberiaRetornarFacturaCorrecta() {
        // Arrange
        CentroExcursionista centro = new CentroExcursionista();
        SocioEstandar socio = new SocioEstandar(1, "Juan Pérez", "12345678A", new Seguro(TipoSeguro.BASICO));
        Excursion excursion = new Excursion("EX001", "Excursión a la montaña", LocalDate.now().withDayOfMonth(15), 2, 100);
        Date fechaInscripcion = Date.valueOf(LocalDate.now());
        Inscripcion inscripcion = new Inscripcion(1, fechaInscripcion, socio, excursion);

        centro.añadirSocioEstandar(socio);
        centro.añadirExcursion(excursion);
        centro.añadirInscripcion(inscripcion);

        // Act
        double factura = centro.calcularFacturaMensualPorSocio(1);

        // Assert
        assertEquals(150, factura);  // Cuota básica 50 + precio excursión 100
    }
}

