package co.edu.uniquindio.poo.proyectocine.model;

/** Entrada asociada a una función y a un asiento. */
public class Entrada {
    private final String id;
    private final double valor;
    private final Funcion funcion;
    private final Asiento asiento;

    /** Crea una entrada y reserva el asiento indicado. */
    public Entrada(String id, double valor, Funcion funcion, Asiento asiento) {
        this.id = id; this.valor = valor; this.funcion = funcion; this.asiento = asiento;
    }

    /**
     * Crea una entrada sin asociarla inicialmente a una función.
     *
     * @param id identificador de la entrada
     * @param valor valor pagado
     * @param asiento asiento asignado
     */
    public Entrada(String id, double valor, Asiento asiento) {
        this(id, valor, null, asiento);
    }
    /** @return resumen de los datos principales de la entrada */
    public String getDetalles() { return "Entrada " + id + " - valor: " + valor; }
    public String getId() { return id; }
    public double getValor() { return valor; }
    public Funcion getFuncion() { return funcion; }
    public Asiento getAsiento() { return asiento; }
}
