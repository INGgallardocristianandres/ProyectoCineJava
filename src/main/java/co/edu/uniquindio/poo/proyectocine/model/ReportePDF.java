package co.edu.uniquindio.poo.proyectocine.model;
public class ReportePDF extends ReporteFactory {
    @Override public GenerarReporte crearReporte(String tipo) {
        return new GenerarReportePDF();
    }
}
