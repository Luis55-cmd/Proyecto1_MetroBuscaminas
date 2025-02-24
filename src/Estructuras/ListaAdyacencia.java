/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Luis
 */
//CLASE LISTA DE ADYANCENCIA
public class ListaAdyacencia {

    //ATRIBUTOS
    ObjetoCasilla valor;
    NodoAdyacencia cabeza;
    NodoAdyacencia cola;

    //CONSTRUCTOR
    public ListaAdyacencia(ObjetoCasilla valor) {
        this.valor = valor;
        this.cabeza = null;
        this.cola = null;
    }

    //CONSTRUCTOR
    public ListaAdyacencia() {
        this.valor = valor;
        this.cabeza = null;
        this.cola = null;
    }

    //PREGUNTA SI LA LISTA ESTA VACIA
    public boolean IsEmpty() {
        return cabeza == null;

    }

    //AGREGA UN NODO A LA LISTA
    public void agregarVecino(ObjetoCasilla vecino) {
        NodoAdyacencia nuevo = new NodoAdyacencia(vecino);
        if (!IsEmpty()) {
            this.cola.siguiente = nuevo;
            this.cola = nuevo;
        } else {
            this.cabeza = this.cola = nuevo;
        }
    }

    //MUESTRA LA LISTA
    public void MostrarLista() {
        NodoAdyacencia actual = cabeza;
        if (!IsEmpty()) {
            while (actual != null) {
                System.out.print("[" + actual.valor.getPosColumna() + ", " + actual.valor.getPosFila() + "] ");
                actual = actual.siguiente;
            }
            System.out.println();

        }
    }

}
