package EstructurasDeDatos;

import Interfaces.EventoBanderaAbierta;
import Interfaces.EventoPartidaPerdida;
import Interfaces.EventoPartidaGanada;
import Interfaces.EventoBanderaCerrada;
import Interfaces.EventoCasillaAbierta;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luis
 */
/**
 * Clase que representa un grafo utilizado para gestionar el tablero de un
 * juego, como el Buscaminas. Contiene métodos para inicializar el tablero,
 * colocar minas, recorrer el grafo en BFS y DFS, y gestionar eventos
 * relacionados con el juego.
 */
public class Grafo {

    // ATRIBUTOS
    /**
     * Matriz de casillas que representa el tablero.
     */
    public Casilla[][] casillas;

    /**
     * Número de filas del tablero.
     */
    public int numFilas;

    /**
     * Número de columnas del tablero.
     */
    public int numColumnas;

    /**
     * Número de minas en el tablero.
     */
    public int numMinas;

    /**
     * Número de casillas abiertas en el tablero.
     */
    public int numCasillasAbiertas;

    /**
     * Número de banderas colocadas en el tablero.
     */
    public int numeroBanderas;

    /**
     * Contador auxiliar para uso interno.
     */
    public int contador = 0;

    /**
     * Contador de visitas durante recorridos.
     */
    public int visitCounter = 0;

    /**
     * Indica si se debe realizar un recorrido BFS.
     */
    public boolean RecorridoBFS = false;

    /**
     * Indica si se debe realizar un recorrido DFS.
     */
    public boolean RecorridoDFS = false;

    /**
     * Grafo visual utilizando GraphStream.
     */
    public Graph graph;

    // ATRIBUTOS EVENTOS
    /**
     * Evento que se ejecuta cuando el jugador pierde la partida.
     */
    public EventoPartidaPerdida eventoPartidaPerdida;

    /**
     * Evento que se ejecuta cuando se abre una casilla.
     */
    public EventoCasillaAbierta eventoCasillaAbierta;

    /**
     * Evento que se ejecuta cuando se coloca una bandera.
     */
    public EventoBanderaAbierta eventoBanderaAbierta;

    /**
     * Evento que se ejecuta cuando se retira una bandera.
     */
    public EventoBanderaCerrada eventoBanderaCerrada;

    /**
     * Evento que se ejecuta cuando el jugador gana la partida.
     */
    public EventoPartidaGanada eventoPartidaGanada;

    // CONSTRUCTOR
    /**
     * Constructor que inicializa el grafo con un número específico de filas,
     * columnas y minas.
     *
     * @param numFilas Número de filas del tablero.
     * @param numColumnas Número de columnas del tablero.
     * @param numMinas Número de minas en el tablero.
     */
    public Grafo(int numFilas, int numColumnas, int numMinas) {
        this.numeroBanderas = numMinas;
        this.numCasillasAbiertas = 0;

        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;

        inicializarCasilla();
        inicializarGraphStream();
    }

    /**
     * Inicializa el grafo visual utilizando GraphStream.
     */
    public void inicializarGraphStream() {
        graph = new SingleGraph("Buscaminas");
        graph.setAttribute("ui.stylesheet", styleSheet);

        // Construir los nodos
        for (int i = 0; i < casillas.length; i++) { // filas
            for (int j = 0; j < casillas[i].length; j++) { // columnas
                String id = casillas[i][j].getID();
                graph.addNode(id).setAttribute("ui.label", id);
            }
        }

        // Construir las aristas
        for (int i = 0; i < casillas.length; i++) { // filas
            for (int j = 0; j < casillas[i].length; j++) { // columnas
                ListaAdyacencia vecinos = obtenerCasillasAlrededor(i, j);
                NodoAdyacencia nodo = vecinos.cabeza;
                while (nodo != null) {
                    String id1 = casillas[i][j].getID();
                    String id2 = nodo.valor.getID();
                    if (graph.getEdge(id1 + "-" + id2) == null && graph.getEdge(id2 + "-" + id1) == null) {
                        graph.addEdge(id1 + "-" + id2, id1, id2);
                    }
                    nodo = nodo.siguiente;
                }
            }
        }
        colocarMinas();
    }

    /**
     * Hoja de estilos para el grafo visual.
     */
    public String styleSheet = "node { fill-color: black; } node.marked { fill-color: red; }" + "node { text-size: 20; }";

    /**
     * Pausa la ejecución del programa durante 500 milisegundos.
     */
    public void sleep() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene todas las aristas conectadas a un nodo.
     *
     * @param node El nodo del cual se obtendrán las aristas.
     * @return Un arreglo de aristas conectadas al nodo.
     */
    public Edge[] getEachEdge(Node node) {
        Edge[] edges = new Edge[node.getDegree()];
        for (int i = 0; i < node.getDegree(); i++) {
            edges[i] = node.getEdge(i);
        }
        return edges;
    }

    /**
     * Inicia la visualización del grafo en una ventana.
     */
    public void empezarArbol() {
        System.setProperty("org.graphstream.ui", "swing");
        graph.display();
    }

    /**
     * Reinicia el grafo visual, eliminando atributos y recolocando minas.
     */
    public void resetearArbol() {
        for (Node node : graph) {
            node.removeAttribute("visited");
            node.removeAttribute("ui.class");
            node.setAttribute("ui.label", node.getId());
        }
        colocarMinas();

    }

    /**
     * Coloca minas en el grafo visual.
     */
    private void colocarMinas() {
        for (int i = 0; i < casillas.length; i++) { // filas
            for (int j = 0; j < casillas[i].length; j++) { // columnas
                if (!casillas[i][j].isMina()) {
                    String mina = i + "," + j;
                    if (!mina.equals("0,0")) {
                        Node node = graph.getNode(mina);
                        if (node != null) {
                            node.setAttribute("ui.class", "mina");
                        }
                    }
                }
            }
        }
    }

    /**
     * Verifica si un nodo contiene una mina.
     *
     * @param node El nodo a verificar.
     * @return `true` si el nodo contiene una mina, `false` en caso contrario.
     */
    private boolean esMina(Node node) {
        return obtenerCasillasConMinas().contieneMinaArbol(node.getId());
    }

    /**
     * Recorre el grafo en profundidad (DFS) a partir de un nodo fuente.
     *
     * @param source El nodo desde el cual comenzar el recorrido.
     */
    public void recorrerDFSArbol(Node source) {
        Pila pila = new Pila();
        pila.apilarNodo(source);
        source.setAttribute("visited", true);

        while (!pila.IsEmpty()) {
            Node next = pila.desapilarNodo();

            if (esMina(next)) {
                break;
            }

            next.setAttribute("ui.class", "marked");
            next.setAttribute("ui.label", next.getId() + " (" + (++visitCounter) + ")");
            sleep();

            for (Edge edge : getEachEdge(next)) {
                Node vecino = edge.getOpposite(next);
                if (!vecino.hasAttribute("visited") && !esMina(vecino)) {
                    vecino.setAttribute("visited", true);
                    pila.apilarNodo(vecino);
                }
            }
        }
    }

    /**
     * Recorre el grafo en anchura (BFS) a partir de un nodo fuente.
     *
     * @param source El nodo desde el cual comenzar el recorrido.
     */
    public void recorrerBFSArbol(Node source) {
        Cola cola = new Cola();
        cola.encolarNodo(source);
        source.setAttribute("visited", true);

        while (!cola.IsEmpty()) {
            Node next = cola.desencolarNodo();
            if (esMina(next)) {
                break;
            }

            next.setAttribute("ui.class", "marked");
            next.setAttribute("ui.label", next.getId() + " (" + (++visitCounter) + ")");
            sleep();

            for (Edge edge : getEachEdge(next)) {
                Node vecino = edge.getOpposite(next);
                if (!vecino.hasAttribute("visited") && !esMina(vecino)) {
                    vecino.setAttribute("visited", true);
                    cola.encolarNodo(vecino);
                }
            }
        }
    }

    /**
     * Recorre el grafo en anchura (BFS) a partir de una posición específica.
     *
     * @param posFila Fila de la casilla inicial.
     * @param posColumna Columna de la casilla inicial.
     */
    private void recorrerBFS(int posFila, int posColumna) {
        Cola cola = new Cola();
        cola.encolar(casillas[posFila][posColumna]);
        while (!cola.IsEmpty()) {
            Casilla actual = cola.desencolar();
            eventoCasillaAbierta.ejecutar(actual);

            ListaAdyacencia vecinos = obtenerCasillasAlrededor(actual.getPosFila(), actual.getPosColumna());
            NodoAdyacencia nodo = vecinos.cabeza;
            while (nodo != null) {
                Casilla vecino = nodo.valor;
                if (!vecino.isAbierta() && !vecino.isMina() && !vecino.isBandera()) {
                    marcarCasillaAbierta(vecino.getPosFila(), vecino.getPosColumna());
                    eventoCasillaAbierta.ejecutar(vecino);
                    if (vecino.getNumMinasAlrededor() == 0) {
                        cola.encolar(vecino);
                    }
                }
                nodo = nodo.siguiente;
            }
        }
    }

    /**
     * Recorre el grafo en profundidad (DFS) a partir de una posición
     * específica.
     *
     * @param posFila Fila de la casilla inicial.
     * @param posColumna Columna de la casilla inicial.
     */
    private void recorrerDFS(int posFila, int posColumna) {
        Pila pila = new Pila();
        pila.apilar(casillas[posFila][posColumna]);

        while (!pila.IsEmpty()) {
            Casilla actual = pila.desapilar();
            eventoCasillaAbierta.ejecutar(actual);

            ListaAdyacencia casillasAlrededor = obtenerCasillasAlrededor(actual.getPosFila(), actual.getPosColumna());
            NodoAdyacencia nodo = casillasAlrededor.cabeza;

            while (nodo != null) {
                Casilla vecino = nodo.valor;
                if (!vecino.isAbierta() && !vecino.isMina() && !vecino.isBandera()) {
                    marcarCasillaAbierta(vecino.getPosFila(), vecino.getPosColumna());
                    eventoCasillaAbierta.ejecutar(vecino);
                    if (vecino.getNumMinasAlrededor() == 0) {
                        pila.apilar(vecino);
                    }
                }
                nodo = nodo.siguiente;
            }
        }
    }

    /**
     * Inicializa la matriz de casillas del tablero.
     */
    public void inicializarCasilla() {
        casillas = new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) { // filas
            for (int j = 0; j < casillas[i].length; j++) { // columnas
                char columnaLetra = (char) ('A' + j);
                casillas[i][j] = new Casilla(i, j);
                casillas[i][j].setEtiqueta(columnaLetra + "," + i);
                casillas[i][j].setID(i + "," + j);
            }
        }
        generarMinas();
    }

    /**
     * Genera minas aleatorias en el tablero.
     */
    private void generarMinas() {
        int minasGeneradas = 0;
        while (minasGeneradas != numMinas) {
            int posTempFila = (int) (Math.random() * casillas.length);
            int posTempColumna = (int) (Math.random() * casillas[0].length);
            if (!casillas[posTempFila][posTempColumna].isMina()) {
                casillas[posTempFila][posTempColumna].setMina(true);
                minasGeneradas++;
            }
        }
        actualizarNumeroMinasAlrededor();
    }

    /**
     * Actualiza el número de minas alrededor de cada casilla.
     */
    private void actualizarNumeroMinasAlrededor() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    ListaAdyacencia casillasAlrededor = obtenerCasillasAlrededor(i, j);
                    NodoAdyacencia actual = casillasAlrededor.cabeza;
                    while (actual != null) {
                        actual.valor.incrementarNumeroMinasAlrededor();
                        actual = actual.siguiente;
                    }
                }
            }
        }
    }

    /**
     * Obtiene las casillas adyacentes a una posición específica.
     *
     * @param posFila Fila de la casilla.
     * @param posColumna Columna de la casilla.
     * @return Una lista de casillas adyacentes.
     */
    private ListaAdyacencia obtenerCasillasAlrededor(int posFila, int posColumna) {
        ListaAdyacencia listaCasillas = new ListaAdyacencia();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila = posFila;
            int tmpPosColumna = posColumna;
            switch (i) {
                case 0:
                    tmpPosFila--;
                    break; // Arriba
                case 1:
                    tmpPosFila--;
                    tmpPosColumna++;
                    break; // Arriba Derecha
                case 2:
                    tmpPosColumna++;
                    break; // Derecha
                case 3:
                    tmpPosColumna++;
                    tmpPosFila++;
                    break; // Derecha Abajo
                case 4:
                    tmpPosFila++;
                    break; // Abajo
                case 5:
                    tmpPosFila++;
                    tmpPosColumna--;
                    break; // Abajo Izquierda
                case 6:
                    tmpPosColumna--;
                    break; // Izquierda
                case 7:
                    tmpPosFila--;
                    tmpPosColumna--;
                    break; // Izquierda Arriba
            }

            if (tmpPosFila >= 0 && tmpPosFila < this.casillas.length
                    && tmpPosColumna >= 0 && tmpPosColumna < this.casillas[0].length) {
                listaCasillas.agregarVecino(this.casillas[tmpPosFila][tmpPosColumna]);
            }
        }
        return listaCasillas;
    }

    /**
     * Establece el evento de partida perdida.
     *
     * @param eventoPartidaPerdida El evento a ejecutar cuando se pierde la
     * partida.
     */
    public void setEventoPartidaPerdida(EventoPartidaPerdida eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    /**
     * Establece el evento de casilla abierta.
     *
     * @param eventoCasillaAbierta El evento a ejecutar cuando se abre una
     * casilla.
     */
    public void setEventoCasillaAbierta(EventoCasillaAbierta eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    /**
     * Establece el evento de partida ganada.
     *
     * @param eventoPartidaGanada El evento a ejecutar cuando se gana la
     * partida.
     */
    public void setEventoPartidaGanada(EventoPartidaGanada eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }

    /**
     * Establece el evento de bandera abierta.
     *
     * @param eventoBanderaAbierta El evento a ejecutar cuando se coloca una
     * bandera.
     */
    public void setEventoBanderaAbierta(EventoBanderaAbierta eventoBanderaAbierta) {
        this.eventoBanderaAbierta = eventoBanderaAbierta;
    }

    /**
     * Establece el evento de bandera cerrada.
     *
     * @param eventoBanderaCerrada El evento a ejecutar cuando se retira una
     * bandera.
     */
    public void setEventoBanderaCerrada(EventoBanderaCerrada eventoBanderaCerrada) {
        this.eventoBanderaCerrada = eventoBanderaCerrada;
    }

    /**
     * Obtiene una lista de casillas que contienen minas.
     *
     * @return Una lista de casillas con minas.
     */
    public ListaAdyacencia obtenerCasillasConMinas() {
        ListaAdyacencia casillasConMinas = new ListaAdyacencia();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMina()) {
                    casillasConMinas.agregarVecino(casillas[i][j]);
                }
            }
        }
        return casillasConMinas;
    }

    /**
     * Obtiene una lista de todas las casillas del tablero.
     *
     * @return Una lista de todas las casillas.
     */
    public ListaAdyacencia obtenerCasillas() {
        ListaAdyacencia casillaslistado = new ListaAdyacencia();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillaslistado.agregarVecino(casillas[i][j]);
            }
        }
        return casillaslistado;
    }

    /**
     * Selecciona una casilla en el tablero, abriéndola o marcándola
     *
     *
     * @param posFila Fila de la casilla.
     * @param posColumna Columna de la casilla.
     */
    public void seleccionarCasilla(int posFila, int posColumna) {
        if (casillas[posFila][posColumna].isBandera()) {
            JOptionPane.showMessageDialog(null, "Error: la casilla está marcada con una bandera", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        eventoCasillaAbierta.ejecutar(casillas[posFila][posColumna]);

        if (casillas[posFila][posColumna].isMina()) {
            if (eventoPartidaPerdida != null) {
                eventoPartidaPerdida.ejecutar(obtenerCasillasConMinas());
            }
            return;
        }

        if (casillas[posFila][posColumna].getNumMinasAlrededor() == 0) {
            marcarCasillaAbierta(posFila, posColumna);
            if (RecorridoDFS) {
                recorrerDFS(posFila, posColumna);
            } else if (RecorridoBFS) {
                recorrerBFS(posFila, posColumna);
            }
        } else {
            marcarCasillaAbierta(posFila, posColumna);
        }

        if (PartidaGanada()) {
            eventoPartidaGanada.ejecutar(obtenerCasillasConMinas());
            return;
        }
    }

    

    /**
     * Selecciona una casilla para colocar o retirar una bandera.
     *
     * @param posFila Fila de la casilla.
     * @param posColumna Columna de la casilla.
     */
    public void seleccionarBandera(int posFila, int posColumna) {
        if (casillas[posFila][posColumna].isAbierta()) {
            JOptionPane.showMessageDialog(null, "Error: la casilla ya está abierta", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println(contador);
            if (!casillas[posFila][posColumna].isBandera() && contador < numMinas) {
                casillas[posFila][posColumna].setBandera(true);
                eventoBanderaAbierta.ejecutar(casillas[posFila][posColumna]);
                contador++;
            } else if (casillas[posFila][posColumna].isBandera()) {
                casillas[posFila][posColumna].setBandera(false);
                eventoBanderaCerrada.ejecutar(casillas[posFila][posColumna]);
                contador--;
            } else {
                JOptionPane.showMessageDialog(null, "Error: no puede colocar más banderas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Marca una casilla como abierta.
     *
     * @param posFila Fila de la casilla.
     * @param posColumna Columna de la casilla.
     */
    void marcarCasillaAbierta(int posFila, int posColumna) {
        if (!casillas[posFila][posColumna].isAbierta()) {
            numCasillasAbiertas++;
            casillas[posFila][posColumna].setAbierta(true);
        }
    }

    /**
     * Verifica si el jugador ha ganado la partida.
     *
     * @return `true` si todas las casillas sin minas están abiertas, `false` en
     * caso contrario.
     */
    public boolean PartidaGanada() {

        return numCasillasAbiertas >= (numFilas * numColumnas) - numMinas;

    }

    /**
     * Verifica si se está realizando un recorrido BFS.
     *
     * @return `true` si se está realizando un recorrido BFS, `false` en caso
     * contrario.
     */
    public boolean isRecorridoBFS() {
        return RecorridoBFS;
    }

    /**
     * Establece si se debe realizar un recorrido BFS.
     *
     * @param RecorridoBFS `true` para habilitar el recorrido BFS, `false` para
     * deshabilitarlo.
     */
    public void setRecorridoBFS(boolean RecorridoBFS) {
        this.RecorridoBFS = RecorridoBFS;
    }

    /**
     * Verifica si se está realizando un recorrido DFS.
     *
     * @return `true` si se está realizando un recorrido DFS, `false` en caso
     * contrario.
     */
    public boolean isRecorridoDFS() {
        return RecorridoDFS;
    }

    /**
     * Establece si se debe realizar un recorrido DFS.
     *
     * @param RecorridoDFS `true` para habilitar el recorrido DFS, `false` para
     * deshabilitarlo.
     */
    public void setRecorridoDFS(boolean RecorridoDFS) {
        this.RecorridoDFS = RecorridoDFS;
    }
}
