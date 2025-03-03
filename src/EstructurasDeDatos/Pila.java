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
public class Pila {

    //ATRIBUTOS
    NodoPila cima;

    //CONSTRUCTOR
    public Pila() {
        this.cima = null;
    }

    public boolean IsEmpty() {
        return cima == null;
    }

    //Agregar al inicio
    public void apilar(Casilla casilla) {

        NodoPila nuevo = new NodoPila(casilla);
        if (!IsEmpty()) {
            nuevo.pNext = cima;
            cima = nuevo;
        } else {
            cima = nuevo;
        }

    }

    //Eliminar al inicio
    public Casilla desapilar() {

        Casilla casilla = cima.casilla;
        cima = cima.pNext;
        return casilla;
    }

    //Agregar al inicio
    public void apilarNodo(Node nodo) {

        NodoPila nuevo = new NodoPila(nodo);
        if (!IsEmpty()) {
            nuevo.pNext = cima;
            cima = nuevo;
        } else {
            cima = nuevo;
        }

    }

    //Eliminar al inicio
    public Node desapilarNodo() {

        Node nodo = cima.nodo;
        cima = cima.pNext;
        return nodo;
    }

}
