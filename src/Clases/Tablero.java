package Clases;

import EstructurasDeDatos.Grafo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luis
 */
/**
 * Clase que representa el tablero de un juego, utilizando un grafo para
 * gestionar la lógica del tablero, como la disposición de las celdas, minas y
 * pistas.
 */
public class Tablero {

    // ATRIBUTOS
    private Grafo grafo; // Grafo que representa la estructura del tablero.

    // CONSTRUCTOR
    /**
     * Constructor que inicializa el tablero con un número específico de filas,
     * columnas y minas.
     *
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     * @param minas Número de minas que se colocarán en el tablero.
     */
    public Tablero(int filas, int columnas, int minas) {
        this.grafo = new Grafo(filas, columnas, minas);
        
    }

    // GETTERS AND SETTERS
    /**
     * Obtiene el grafo asociado al tablero.
     *
     * @return El grafo que representa el tablero.
     */
    public Grafo getGrafo() {
        return grafo;
    }

    /**
     * Establece un nuevo grafo para el tablero.
     *
     * @param grafo El grafo que se asignará al tablero.
     */
    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

}
