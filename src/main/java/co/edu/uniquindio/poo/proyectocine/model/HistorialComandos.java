package co.edu.uniquindio.poo.proyectocine.model;

import java.util.Stack;

/**
 * Gestiona el historial de comandos ejecutados en el sistema mediante una pila,
 * facilitando la funcionalidad de deshacer operaciones administrativas.
 *
 */
public class HistorialComandos {

    private Stack<IComando> pilaDeshacer;

    /**
     * Constructor que inicializa la pila del historial.
     */
    public HistorialComandos() {
        this.pilaDeshacer = new Stack<>();
    }

    /**
     * Ejecuta un comando recibido y lo apila en el historial si la ejecución es exitosa.
     *
     * @param comando El comando a ejecutar.
     */
    public void ejecutarComando(IComando comando) {
        comando.ejecutar();
        pilaDeshacer.push(comando);
    }

    /**
     * Deshace el último comando ejecutado en el sistema, extrayéndolo de la pila.
     */
    public void deshacer() {
        if (!pilaDeshacer.isEmpty()) {
            IComando comandoUltimo = pilaDeshacer.pop();
            comandoUltimo.deshacer();
        } else {
            System.out.println("No hay acciones recientes para deshacer.");
        }
    }
}