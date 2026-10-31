package co.edu.uniquindio.poo.proyectocine.model;
import java.util.List;
import java.util.ArrayList;

/**
 * Representa a un cliente registrado en el sistema CinemaUQ, el cual hereda
 * de la clase base Usuario y gestiona sus puntos, tarjeta virtual e historial.
 */
public class Cliente extends Usuario {
    private String telefono;
    private TarjetaVirtual tarjetaVirtual;
    private CuentaPuntos puntos;
    private List<Compra> historialcompras;

    /**
     * Constructor para inicializar un nuevo Cliente.
     *
     * @param id Identificador único del usuario.
     * @param nombre Nombre completo del cliente.
     * @param correo Correo electrónico del cliente.
     * @param telefono Número de teléfono de contacto.
     */
    public Cliente(String id, String nombre, String correo, String contrasenia, String telefono) {
        super(id, nombre, correo, contrasenia);
        this.telefono = telefono;
        this.historialcompras = new ArrayList<>();
    }

    /**
     * Permite al cliente consultar la cartelera actual en el cine.
     */
    public void consultarCartelera() {
        System.out.println("Consultando cartelera disponible para el cliente: " + getNombre());
        CinemaUQ.getInstancia().mostrarCartelera();
    }

    /**
     * Registra una nueva compra en el historial del cliente.
     *
     * @param compra La compra realizada a asociar.
     */
    public void realizarCompra(Compra compra) {
        if (compra != null && historialcompras != null) {
            historialcompras.add(compra);
        }
    }

    /**
     * Consulta la cantidad actual de puntos acumulados por el cliente.
     *
     * @return El total de puntos disponibles, o 0 si no cuenta con una cuenta de puntos activa.
     */
    public int consultarPuntos() {
        if (this.puntos != null) {
            return this.puntos.getCantidadPuntos();
        }
        return 0;
    }

    /**
     * Permite redimir una cantidad específica de puntos a cambio de un beneficio.
     *
     * @param cantidad Cantidad de puntos a descontar.
     * @param beneficio Descripción del beneficio obtenido.
     * @return Mensaje de confirmación al redimir puntos.
     */
    public String redimirPuntos(int cantidad, String beneficio) {
        if (this.puntos != null && this.puntos.getCantidadPuntos() >= cantidad) {
            this.puntos.descontarPuntos(cantidad);
            return "Beneficio obtenido: " + beneficio;
        }
        return "No cuenta con suficientes puntos para redimir este beneficio.";
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TarjetaVirtual getTarjetaVirtual() {
        return tarjetaVirtual;
    }

    public void setTarjetaVirtual(TarjetaVirtual tarjetaVirtual) {
        this.tarjetaVirtual = tarjetaVirtual;
    }

    public CuentaPuntos getPuntos() {
        return puntos;
    }

    public void setPuntos(CuentaPuntos puntos) {
        this.puntos = puntos;
    }

    public List<Compra> getHistorialcompras() {
        return historialcompras;
    }

    public void setHistorialcompras(List<Compra> historialcompras) {
        this.historialcompras = historialcompras;
    }

}
