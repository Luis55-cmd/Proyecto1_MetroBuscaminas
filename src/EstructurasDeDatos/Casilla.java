package EstructurasDeDatos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luis, Zadkiel Avendano
 */
/**
 * Representa una casilla en el tablero del juego Buscaminas.
 */
public class Casilla {

    // ATRIBUTOS
    private String ID;
    private String Etiqueta;
    private int posFila;
    private int posColumna;
    private boolean mina;
    private int numMinasAlrededor;
    private boolean abierta;
    private ListaAdyacencia lista;
    private boolean bandera;

    /**
     * Constructor que inicializa una casilla con su posición en el tablero.
     *
     * @param posFila Posición de la casilla en la fila.
     * @param posColumna Posición de la casilla en la columna.
     */
    public Casilla(int posFila, int posColumna) {
        this.posFila = posFila;
        this.posColumna = posColumna;
        this.lista = new ListaAdyacencia();
    }

    /**
     * Incrementa el número de minas adyacentes a esta casilla.
     */
    public void incrementarNumeroMinasAlrededor() {
        this.numMinasAlrededor++;
    }

    // GETTERS AND SETTERS
    /**
     * Obtiene la etiqueta de la casilla.
     *
     * @return Etiqueta de la casilla.
     */
    public String getEtiqueta() {
        return Etiqueta;
    }

    /**
     * Establece la etiqueta de la casilla.
     *
     * @param Etiqueta Etiqueta a asignar.
     */
    public void setEtiqueta(String Etiqueta) {
        this.Etiqueta = Etiqueta;
    }

    /**
     * Verifica si la casilla tiene una bandera.
     *
     * @return true si la casilla tiene una bandera, false en caso contrario.
     */
    public boolean isBandera() {
        return bandera;
    }

    /**
     * Asigna o quita una bandera en la casilla.
     *
     * @param bandera true para colocar una bandera, false para quitarla.
     */
    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    /**
     * Obtiene el identificador de la casilla.
     *
     * @return ID de la casilla.
     */
    public String getID() {
        return ID;
    }

    /**
     * Establece el identificador de la casilla.
     *
     * @param ID Identificador a asignar.
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Verifica si la casilla está abierta.
     *
     * @return true si la casilla está abierta, false en caso contrario.
     */
    public boolean isAbierta() {
        return abierta;
    }

    /**
     * Establece el estado de la casilla (abierta o cerrada).
     *
     * @param abierta true para abrir la casilla, false para cerrarla.
     */
    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    /**
     * Obtiene el número de minas alrededor de la casilla.
     *
     * @return Número de minas adyacentes.
     */
    public int getNumMinasAlrededor() {
        return numMinasAlrededor;
    }

    /**
     * Establece el número de minas alrededor de la casilla.
     *
     * @param numMinasAlrededor Número de minas a asignar.
     */
    public void setNumMinasAlrededor(int numMinasAlrededor) {
        this.numMinasAlrededor = numMinasAlrededor;
    }

    /**
     * Obtiene la posición de la casilla en la fila.
     *
     * @return Número de fila de la casilla.
     */
    public int getPosFila() {
        return posFila;
    }

    /**
     * Establece la posición de la casilla en la fila.
     *
     * @param posFila Número de fila a asignar.
     */
    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    /**
     * Obtiene la posición de la casilla en la columna.
     *
     * @return Número de columna de la casilla.
     */
    public int getPosColumna() {
        return posColumna;
    }

    /**
     * Establece la posición de la casilla en la columna.
     *
     * @param posColumna Número de columna a asignar.
     */
    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    /**
     * Verifica si la casilla contiene una mina.
     *
     * @return true si la casilla tiene una mina, false en caso contrario.
     */
    public boolean isMina() {
        return mina;
    }

    /**
     * Establece si la casilla contiene una mina.
     *
     * @param mina true si la casilla tiene una mina, false en caso contrario.
     */
    public void setMina(boolean mina) {
        this.mina = mina;
    }
}
