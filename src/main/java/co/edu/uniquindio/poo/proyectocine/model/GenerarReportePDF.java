package co.edu.uniquindio.poo.proyectocine.model;
public class GenerarReportePDF implements GenerarReporte {
    @Override public void generarReporte(String datos) {
        System.out.println("[PDF] " + (datos == null ? "" : datos)); }
}
