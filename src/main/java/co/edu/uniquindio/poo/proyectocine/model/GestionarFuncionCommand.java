package co.edu.uniquindio.poo.proyectocine.model;

/**
 * Comando administrativo para gestionar una función.
 */
public class GestionarFuncionCommand implements IComando {
    private final Administrador administrador;
    private final Funcion funcion;
    private final String accion;

    public GestionarFuncionCommand(Administrador administrador, Funcion funcion, String accion) {
        this.administrador = administrador; this.funcion = funcion; this.accion = accion;
    }
    @Override public void ejecutar() {
        administrador.gestionarFuncion(funcion, accion);
    }
    @Override public void deshacer() {
        System.out.println("Se deshizo la gestión de la función " + funcion.getId());
    }
}
