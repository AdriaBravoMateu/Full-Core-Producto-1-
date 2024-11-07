package Test;

import grupoFullCore.modelo.CentroExcursionista;
import grupoFullCore.modelo.Excursion;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
public class TestAñadirExcursion {
    @Test
    void añadirExcursion_deberiaAgregarExcursionALaLista() {
        // Arrange
        CentroExcursionista centro = new CentroExcursionista();
        Excursion excursion = new Excursion(1, "Excursión a la montaña", LocalDate.of(2024, 10, 25), 2, 100);

        // Act
        centro.añadirExcursion(excursion);

        // Assert
        assertTrue(centro.mostrarExcursionesConFiltro(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31)).contains(excursion));
    }
}