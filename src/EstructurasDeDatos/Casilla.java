package EstructurasDeDatos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luis, Zadkiel Avendano
 */
//Clase casilla del tablero
public class Casilla {

    //ATRIBUTOS
    private String ID;
    private int posFila;
    private int posColumna;
    private boolean mina;
    int numMinasAlrededor;
    private boolean abierta;
    ListaAdyacencia lista;
    private boolean bandera;

    //CONSTRUCTOR
    public Casilla(int posFila, int posColumna) {
        this.posFila = posFila;
        this.posColumna = posColumna;
        this.lista = new ListaAdyacencia();
    }

    //INCREMENTAR EL NUMERO DE MINAS ADYACENTES A LA CASILLA CON MINA
    public void incrementarNumeroMinasAlrededor() {
        this.numMinasAlrededor++;
    }

    //GETTERS AND SETTERS
    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public int getNumMinasAlrededor() {
        return numMinasAlrededor;
    }

    public void setNumMinasAlrededor(int numMinasAlrededor) {
        this.numMinasAlrededor = numMinasAlrededor;
    }

    public int getPosFila() {
        return posFila;
    }

    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    public int getPosColumna() {
        return posColumna;
    }

    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

}
