package EstructurasDeDatos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luis
 */
public class NodoAdyacencia {

    //ATRIBUTOS
    public Casilla valor;
    public NodoAdyacencia siguiente;

    //CONSTRUCTOR
    public NodoAdyacencia(Casilla valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}
