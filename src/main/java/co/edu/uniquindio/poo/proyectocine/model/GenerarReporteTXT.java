package co.edu.uniquindio.poo.proyectocine.model;
public class GenerarReporteTXT implements GenerarReporte {
    @Override public void generarReporte(String datos) { System.out.println(datos == null ? "" : datos); }
}
