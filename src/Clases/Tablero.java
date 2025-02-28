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
//clase del tablero del juego
public class Tablero {

    //ATRIBUTOS
    private Grafo grafo;

    //CONSTRUCTOR
    public Tablero(int filas, int columnas, int minas) {
        this.grafo = new Grafo(filas, columnas, minas);
    }

    //GETTERS AND SETTERS
    public Grafo getGrafo() {
        return grafo;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    //MOSTRAR EN PANTALLA PROVICIONAL
    public void imprimirTablero() {
        grafo.imprimirTablero();
    }

    public void imprimirGrafo() {
        grafo.imprimirGrafo();
    }

    public void imprimirPistas() {
        grafo.imprimirPistas();
    }

}
