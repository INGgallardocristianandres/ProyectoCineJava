package co.edu.uniquindio.poo.proyectocine.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Ejecución temporal de consola para validar el dominio antes de conectar JavaFX.
 */
public final class MainPrueba {
    private MainPrueba() { }

    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL SISTEMA CINEMA UQ ===");

        Pelicula pelicula = new Pelicula("P-001", "Película de prueba",
                GeneroPelicula.ACCION, 120, ClasificacionPelicula.GENERAL);
        Sala sala = new Sala(1, 20);
        Funcion funcion = new Funcion("F-001", LocalDateTime.now().plusDays(1),
                12000, pelicula, sala, true);

        CinemaUQ cine = CinemaUQ.getInstancia();
        cine.agregarFuncion(funcion);
        cine.iniciarSistema();
        cine.mostrarCartelera();

        Cliente cliente = new Cliente("C-001", "Cliente de prueba", "cliente@cine.com",
                "1234", "3000000000");
        cliente.setTarjetaVirtual(new TarjetaVirtual("T-001", 50000, EstadoTarjeta.ACTIVA));
        cine.registrarCliente(cliente);

        IProducto producto = new AdicionalQuesoDecorator(
                new ProductoConfiteria("PR-001", "Palomitas", 8000, "Comida", true));
        CompraFacade facade = new CompraFacade(new PasarelaPagoAdapter(
                new PasarelaExterna("demo-key", "https://demo.cine")));

        boolean compraExitosa = facade.procesarCompra(cliente, funcion,
                List.of(sala.getAsientos().get(0)), List.of(producto));
        System.out.println("Compra exitosa: " + compraExitosa);
        System.out.println("Saldo restante: " + cliente.getTarjetaVirtual().consultarSaldo());
        System.out.println("Movimientos: " + cliente.getTarjetaVirtual().getMovimientos().size());
        System.out.println("Compras del cliente: " + cliente.getHistorialcompras().size());

        Administrador administrador = new Administrador("A-001", "Administrador",
                "admin@cine.com", "1234", "TOTAL");
        administrador.generarReporte("Ventas de prueba", "TXT");
        administrador.generarReporte("Asistencia de prueba", "PDF");

        HistorialComandos historial = new HistorialComandos();
        historial.ejecutarComando(new GestionarPeliculaCommand(administrador, pelicula, "actualizar"));
        historial.deshacer();

        System.out.println("=== FIN DE LA PRUEBA ===");
    }
}
