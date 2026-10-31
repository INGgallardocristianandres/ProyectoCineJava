package co.edu.uniquindio.poo.proyectocine.model;
public abstract class ReporteFactory {
    public abstract GenerarReporte crearReporte(String tipo);
    public void exportarReporte(String datos, String tipo) {
        crearReporte(tipo).generarReporte(datos);
    }
}
