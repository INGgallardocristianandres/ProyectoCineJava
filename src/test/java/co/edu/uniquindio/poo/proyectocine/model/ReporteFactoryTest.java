package co.edu.uniquindio.poo.proyectocine.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReporteFactoryTest {

    @Test
    void testCrearReporteTXT() {
        ReporteFactory factory = new ReporteTXT();
        GenerarReporte reporte = factory.crearReporte("Ventas");
        assertNotNull(reporte, "Debe crear la instancia del reporte");
        assertTrue(reporte instanceof GenerarReporteTXT, "La instancia debe ser de tipo GenerarReporteTXT");
    }

    @Test
    void testCrearReportePDF() {
        ReporteFactory factory = new ReportePDF();
        GenerarReporte reporte = factory.crearReporte("Ventas");
        assertNotNull(reporte, "Debe crear la instancia del reporte");
        assertTrue(reporte instanceof GenerarReportePDF, "La instancia debe ser de tipo GenerarReportePDF");
    }
}