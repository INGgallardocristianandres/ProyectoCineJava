package co.edu.uniquindio.poo.proyectocine.model;
/**
 * Representa al usuario administrador del sistema CinemaUQ,
 * encargado de gestionar el catálogo, funciones y utilizar el historial de comandos.
 */
public class Administrador extends Usuario{
    private HistorialComandos historialComandos;

    /**
     * Constructor del Administrador.
     *
     * @param id Identificador único.
     * @param nombre Nombre del administrador.
     * @param correo Correo electrónico.
     */
    public Administrador(String id, String nombre, String correo) {
        super(id, nombre, correo);
        this.historialComandos = new HistorialComandos();
    }

    /**
     * Realiza una acción administrativa utilizando el patrón Command.
     *
     * @param comando Comando específico que desea ejecutar.
     */
    public void realizarAccion(IComando comando) {
        this.historialComandos.ejecutarComando(comando);
    }

    /**
     * Revierte la última acción administrativa realizada.
     */
    public void revertirUltimaAccion() {
        this.historialComandos.deshacer();
    }
}
