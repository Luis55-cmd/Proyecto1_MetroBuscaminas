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
 * Clase que representa un nodo en una pila. Cada nodo puede contener una
 * casilla o un nodo de un grafo, junto con una referencia al siguiente nodo en
 * la pila.
 */
public class NodoPila {

    // ATRIBUTOS
    /**
     * Referencia al siguiente nodo en la pila.
     */
    public NodoPila pNext;

    /**
     * La casilla asociada al nodo.
     */
    public Casilla casilla;

    /**
     * El nodo de un grafo asociado al nodo.
     */
    public Node nodo;

    // CONSTRUCTORES
    /**
     * Constructor que inicializa un nodo con una casilla específica.
     *
     * @param casilla La casilla que se asignará al nodo.
     */
    public NodoPila(Casilla casilla) {
        this.casilla = casilla;
        this.pNext = null;
    }

    /**
     * Constructor que inicializa un nodo con un nodo de un grafo específico.
     *
     * @param nodo El nodo de un grafo que se asignará al nodo.
     */
    public NodoPila(Node nodo) {
        this.nodo = nodo;
        this.pNext = null;
    }
}
