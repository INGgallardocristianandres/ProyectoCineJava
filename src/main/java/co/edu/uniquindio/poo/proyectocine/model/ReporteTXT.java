package co.edu.uniquindio.poo.proyectocine.model;
public class ReporteTXT extends ReporteFactory {
    @Override public GenerarReporte crearReporte(String tipo) {
        return new GenerarReporteTXT();
    }
}
