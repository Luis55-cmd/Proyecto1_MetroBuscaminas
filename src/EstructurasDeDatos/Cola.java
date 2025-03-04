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
/**
 * Implementa una estructura de datos de tipo Cola para almacenar objetos de
 * tipo Casilla o Node.
 */
public class Cola {

    /**
     * Referencia al primer elemento de la cola.
     */
    NodoCola frente;

    /**
     * Referencia al último elemento de la cola.
     */
    NodoCola fin;

    /**
     * Agrega una casilla al final de la cola.
     *
     * @param casilla La casilla a encolar.
     */
    public void encolar(Casilla casilla) {
        NodoCola nuevo = new NodoCola(casilla);
        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.pNext = nuevo;
            fin = nuevo;
        }
    }

    /**
     * Extrae y devuelve la casilla al frente de la cola.
     *
     * @return La casilla al frente de la cola o null si está vacía.
     */
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

    /**
     * Agrega un nodo al final de la cola.
     *
     * @param nodo El nodo a encolar.
     */
    public void encolarNodo(Node nodo) {
        NodoCola nuevo = new NodoCola(nodo);
        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.pNext = nuevo;
            fin = nuevo;
        }
    }

    /**
     * Extrae y devuelve el nodo al frente de la cola.
     *
     * @return El nodo al frente de la cola o null si está vacía.
     */
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

    /**
     * Verifica si la cola está vacía.
     *
     * @return true si la cola está vacía, false en caso contrario.
     */
    public boolean IsEmpty() {
        return frente == null;
    }
}
