package co.edu.uniquindio.poo.proyectocine.model;
/**
 * Interfaz que define el patrón Command. Permite encapsular una solicitud
 * como un objeto, permitiendo parametrizar clientes y soportar operaciones
 * de ejecución y reversión.
 */
public interface IComando {

    /**
     * Ejecuta la acción o tarea asignada.
     */
    void ejecutar();

    /**
     * Revierte los cambios realizados por la ejecución de la acción.
     */
    void deshacer();
}