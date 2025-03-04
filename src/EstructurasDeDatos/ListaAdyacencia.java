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
 * Clase que representa una lista de adyacencia utilizada para gestionar las
 * conexiones entre casillas en un grafo. Permite agregar vecinos, verificar si
 * la lista está vacía, y mostrar la lista de casillas adyacentes.
 */
public class ListaAdyacencia {

    // ATRIBUTOS
    /**
     * Valor de la casilla actual.
     */
    public Casilla valor;

    /**
     * Referencia al primer nodo de la lista.
     */
    public NodoAdyacencia cabeza;

    /**
     * Referencia al último nodo de la lista.
     */
    public NodoAdyacencia cola;

    // CONSTRUCTORES
    /**
     * Constructor que inicializa la lista de adyacencia con una casilla
     * específica.
     *
     * @param valor La casilla que representa el valor inicial de la lista.
     */
    public ListaAdyacencia(Casilla valor) {
        this.valor = valor;
        this.cabeza = null;
        this.cola = null;
    }

    /**
     * Constructor vacío que inicializa una lista de adyacencia sin valores.
     */
    public ListaAdyacencia() {
        this.valor = null;
        this.cabeza = null;
        this.cola = null;
    }

    // MÉTODOS
    /**
     * Verifica si la lista de adyacencia está vacía.
     *
     * @return `true` si la lista está vacía, `false` en caso contrario.
     */
    public boolean IsEmpty() {
        return cabeza == null;
    }

    /**
     * Agrega un vecino (casilla adyacente) a la lista de adyacencia.
     *
     * @param vecino La casilla que se agregará como vecino.
     */
    public void agregarVecino(Casilla vecino) {
        NodoAdyacencia nuevo = new NodoAdyacencia(vecino);
        if (!IsEmpty()) {
            this.cola.siguiente = nuevo;
            this.cola = nuevo;
        } else {
            this.cabeza = this.cola = nuevo;
        }
    }

    /**
     * Verifica si la lista contiene una casilla con un ID específico.
     *
     * @param valor El ID de la casilla a buscar.
     * @return `true` si la casilla está en la lista, `false` en caso contrario.
     */
    public boolean contieneMinaArbol(String valor) {
        NodoAdyacencia actual = cabeza;
        while (actual != null) {
            if (actual.valor.getID().equals(valor)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

}
