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
 * Clase que representa una pila (estructura de datos LIFO - Last In, First
 * Out). Permite apilar y desapilar elementos, ya sean casillas o nodos de un
 * grafo.
 */
public class Pila {

    // ATRIBUTOS
    /**
     * Referencia al nodo en la cima de la pila.
     */
    private NodoPila cima;

    // CONSTRUCTOR
    /**
     * Constructor que inicializa una pila vacía.
     */
    public Pila() {
        this.cima = null;
    }

    // MÉTODOS
    /**
     * Verifica si la pila está vacía.
     *
     * @return `true` si la pila está vacía, `false` en caso contrario.
     */
    public boolean IsEmpty() {
        return cima == null;
    }

    /**
     * Apila una casilla en la cima de la pila.
     *
     * @param casilla La casilla que se apilará.
     */
    public void apilar(Casilla casilla) {
        NodoPila nuevo = new NodoPila(casilla);
        if (!IsEmpty()) {
            nuevo.pNext = cima;
            cima = nuevo;
        } else {
            cima = nuevo;
        }
    }

    /**
     * Desapila la casilla en la cima de la pila.
     *
     * @return La casilla que se desapiló.
     */
    public Casilla desapilar() {
        if (IsEmpty()) {
            throw new IllegalStateException("La pila está vacía. No se puede desapilar.");
        }
        Casilla casilla = cima.casilla;
        cima = cima.pNext;
        return casilla;
    }

    /**
     * Apila un nodo de un grafo en la cima de la pila.
     *
     * @param nodo El nodo que se apilará.
     */
    public void apilarNodo(Node nodo) {
        NodoPila nuevo = new NodoPila(nodo);
        if (!IsEmpty()) {
            nuevo.pNext = cima;
            cima = nuevo;
        } else {
            cima = nuevo;
        }
    }

    /**
     * Desapila el nodo de un grafo en la cima de la pila.
     *
     * @return El nodo que se desapiló.
     */
    public Node desapilarNodo() {
        if (IsEmpty()) {
            throw new IllegalStateException("La pila está vacía. No se puede desapilar.");
        }
        Node nodo = cima.nodo;
        cima = cima.pNext;
        return nodo;
    }
}
