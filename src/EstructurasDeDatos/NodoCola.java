/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDeDatos;
import org.graphstream.graph.*;

/**
 *
 * @author Luis
 */
public class NodoCola {

    Casilla casilla;
    NodoCola pNext;
    Node nodo;

    public NodoCola(Casilla casilla) {
        this.casilla = casilla;
        this.pNext = null;
    }
    
    public NodoCola(Node nodo) {
        this.nodo = nodo;
        this.pNext = null;
    }

}
