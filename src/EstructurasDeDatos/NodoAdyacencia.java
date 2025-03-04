package EstructurasDeDatos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luis
 */
/**
 * Clase que representa un nodo en una lista de adyacencia. Cada nodo contiene
 * una casilla y una referencia al siguiente nodo en la lista.
 */
public class NodoAdyacencia {

    // ATRIBUTOS
    /**
     * La casilla que representa el valor del nodo.
     */
    public Casilla valor;

    /**
     * Referencia al siguiente nodo en la lista de adyacencia.
     */
    public NodoAdyacencia siguiente;

    // CONSTRUCTOR
    /**
     * Constructor que inicializa un nodo con una casilla específica.
     *
     * @param valor La casilla que se asignará como valor del nodo.
     */
    public NodoAdyacencia(Casilla valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
