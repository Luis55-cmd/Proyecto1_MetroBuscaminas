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
public class Cola {

    NodoCola frente, fin;

    public void encolar(Casilla casilla) {
        NodoCola nuevo = new NodoCola(casilla);
        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.pNext = nuevo;
            fin = nuevo;
        }
    }

    public Casilla desencolar() {
        if (frente == null) {
            return null;
        }
        Casilla casilla = frente.casilla;
        frente = frente.pNext;
        if (frente == null) {
            fin = null;
        }
        return casilla;
    }

    public void encolarNodo(Node nodo) {
        NodoCola nuevo = new NodoCola(nodo);
        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.pNext = nuevo;
            fin = nuevo;
        }
    }

    public Node desencolarNodo() {
        if (frente == null) {
            return null;
        }
        Node nodo = frente.nodo;
        frente = frente.pNext;
        if (frente == null) {
            fin = null;
        }
        return nodo;
    }

    public boolean IsEmpty() {
        return frente == null;
    }

}
