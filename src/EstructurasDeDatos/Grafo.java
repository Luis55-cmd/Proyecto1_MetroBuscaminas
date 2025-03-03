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
//Clase grafo del tablero
public class Grafo {

    //ATRIBUTOS
    public Casilla[][] casillas;
    public int numFilas;
    public int numColumnas;
    public int numMinas;

    public int numCasillasAbiertas;
    public int numeroBanderas;
    public int contador = 0;
    public boolean RecorridoBFS = false;
    public boolean RecorridoDFS = false;
    public Graph graph;

    //ATRIBUTOS EVENTOS
    EventoPartidaPerdida eventoPartidaPerdida;

    public EventoCasillaAbierta eventoCasillaAbierta;
    public EventoBanderaAbierta eventoBanderaAbierta;
    public EventoBanderaCerrada eventoBanderaCerrada;
    public EventoPartidaGanada eventoPartidaGanada;

    //CONSTRUCTOR
    public Grafo(int numFilas, int numColumnas, int numMinas) {
        this.numeroBanderas = numMinas;
        this.numCasillasAbiertas = 0;

        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;

        inicializarCasilla();
        inicializarGraphStream();
    }

    public void inicializarGraphStream() {

        graph = new SingleGraph("Buscaminas");
        graph.setAttribute("ui.stylesheet", styleSheet);
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                String id = casillas[i][j].getID();
                graph.addNode(id).setAttribute("ui.label", id);
            }
        }
        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
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

    }
    protected String styleSheet = "node { fill-color: black; } node.marked { fill-color: red; }" + "node { text-size: 20; }";

    public void empezarArbol() {
        System.setProperty("org.graphstream.ui", "swing");
        graph.display();
    }

    private void recorrerBFS(int posFila, int posColumna) {
        Cola cola = new Cola();
        cola.encolar(casillas[posFila][posColumna]);
        while (!cola.IsEmpty()) {
            Casilla actual = cola.desencolar();
            eventoCasillaAbierta.ejecutar(actual);
            System.out.println("Visitando (BFS): (" + actual.getPosFila() + ", " + actual.getPosColumna() + ")");

            //graph.getNode(actual.getPosFila() + ", " + actual.getPosColumna()).setAttribute("ui.style", "fill-color: blue;");
            ListaAdyacencia vecinos = obtenerCasillasAlrededor(actual.getPosFila(), actual.getPosColumna());
            NodoAdyacencia nodo = vecinos.cabeza;
            while (nodo != null) {
                Casilla vecino = nodo.valor;
                if (!vecino.isAbierta() && !vecino.isMina()) {
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

    private void recorrerDFS(int posFila, int posColumna) {
        Pila pila = new Pila();
        pila.apilar(casillas[posFila][posColumna]);

        while (!pila.IsEmpty()) {
            Casilla actual = pila.desapilar();

            // Ejecutar evento para cada casilla visitada
            eventoCasillaAbierta.ejecutar(actual);

            // Imprimir en consola la casilla visitada
            System.out.println("Visitando (DFS): (" + actual.getPosFila() + ", " + actual.getPosColumna() + ")");

            ListaAdyacencia casillasAlrededor = obtenerCasillasAlrededor(actual.getPosFila(), actual.getPosColumna());
            NodoAdyacencia nodo = casillasAlrededor.cabeza;

            while (nodo != null) {
                Casilla vecino = nodo.valor;
                if (!vecino.isAbierta() && !vecino.isMina()) {
                    marcarCasillaAbierta(vecino.getPosFila(), vecino.getPosColumna());

                    eventoCasillaAbierta.ejecutar(vecino); // Ejecutar evento para vecino

                    if (vecino.getNumMinasAlrededor() == 0) {
                        pila.apilar(vecino);
                    }

                }
                nodo = nodo.siguiente;
            }
        }
    }

    //CREA LAS CASILLAS RECORRIENDO LA MATRIZ
    public void inicializarCasilla() {
        casillas = new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) { //filas
            for (int j = 0; j < casillas[i].length; j++) { //columnas

                char columnaLetra = (char) ('A' + j);

                casillas[i][j] = new Casilla(i, j);
                casillas[i][j].setID(columnaLetra + "" + i);

            }
        }
        generarMinas();
    }

    //GENERA LAS MINAS EN LAS CASILLAS Y LAS ACTUALIZA
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

    //ACTUALIZO EL NUMERO DE MINAS ALREDEDOR
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

    //LISTA DE CASILLAS ADYACENTES
    private ListaAdyacencia obtenerCasillasAlrededor(int posFila, int posColumna) {
        ListaAdyacencia listaCasillas = new ListaAdyacencia();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila = posFila;
            int tmpPosColumna = posColumna;
            switch (i) {
                case 0:
                    tmpPosFila--;
                    break; //Arriba
                case 1:
                    tmpPosFila--;
                    tmpPosColumna++;
                    break; //Arriba Derecha
                case 2:
                    tmpPosColumna++;
                    break; //Derecha
                case 3:
                    tmpPosColumna++;
                    tmpPosFila++;
                    break; //Derecha Abajo
                case 4:
                    tmpPosFila++;
                    break; //Abajo
                case 5:
                    tmpPosFila++;
                    tmpPosColumna--;
                    break; //Abajo Izquierda
                case 6:
                    tmpPosColumna--;
                    break; //Izquierda
                case 7:
                    tmpPosFila--;
                    tmpPosColumna--;
                    break; //Izquierda Arriba
            }

            if (tmpPosFila >= 0 && tmpPosFila < this.casillas.length
                    && tmpPosColumna >= 0 && tmpPosColumna < this.casillas[0].length) {
                listaCasillas.agregarVecino(this.casillas[tmpPosFila][tmpPosColumna]);
            }

        }
        return listaCasillas;
    }

    //EVENTO DE PARTIDA PERDIDA PARA NOTIFICAR AL JFRAME
    public void setEventoPartidaPerdida(EventoPartidaPerdida eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    //EVENTO DE CASILLA ABIERTA PARA NOTIFICAR AL JFRAME
    public void setEventoCasillaAbierta(EventoCasillaAbierta eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    //EVENTO DE PARTIDA GANADA PARA NOTIFICAR AL JFRAME
    public void setEventoPartidaGanada(EventoPartidaGanada eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }

    //EVENTO DE BANDERA ABIERTA PARA NOTIFICAR AL JFRAME
    public void setEventoBanderaAbierta(EventoBanderaAbierta eventoBanderaAbierta) {
        this.eventoBanderaAbierta = eventoBanderaAbierta;
    }

    //EVENTO DE BANDERA CERRADA PARA NOTIFICAR AL JFRAME
    public void setEventoBanderaCerrada(EventoBanderaCerrada eventoBanderaCerrada) {
        this.eventoBanderaCerrada = eventoBanderaCerrada;
    }

    //LISTA DE CASILLAS CON MINAS
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

    //LISTA DE CASILLAS 
    public ListaAdyacencia obtenerCasillas() {
        ListaAdyacencia casillaslistado = new ListaAdyacencia();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {

                casillaslistado.agregarVecino(casillas[i][j]);
            }

        }
        return casillaslistado;
    }

    public void seleccionarCasilla(int posFila, int posColumna) {

        if (casillas[posFila][posColumna].isBandera()) {
            JOptionPane.showMessageDialog(null, "Error la casilla esta marcada con una bandera", "Error", JOptionPane.ERROR_MESSAGE);

        } else {
            eventoCasillaAbierta.ejecutar(casillas[posFila][posColumna]);

            //LA CASILLA SEA MINA
            if (this.casillas[posFila][posColumna].isMina()) {

                if (eventoPartidaPerdida != null) {
                    eventoPartidaPerdida.ejecutar(obtenerCasillasConMinas());

                }

            } else if (casillas[posFila][posColumna].getNumMinasAlrededor() == 0) {
                marcarCasillaAbierta(posFila, posColumna);
                if (PartidaGanada()) {
                    eventoPartidaGanada.ejecutar(obtenerCasillasConMinas());

                }
                if (RecorridoDFS) {
                    recorrerDFS(posFila, posColumna);
                } else if (RecorridoBFS) {
                    recorrerBFS(posFila, posColumna);
                }

            } else {
                marcarCasillaAbierta(posFila, posColumna);
                if (PartidaGanada()) {
                    eventoPartidaGanada.ejecutar(obtenerCasillasConMinas());

                }
            }

        }

    }

    /*
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

        marcarCasillaAbierta(posFila, posColumna);

        if (PartidaGanada()) {
            eventoPartidaGanada.ejecutar(obtenerCasillasConMinas());
            return;
        }

        if (casillas[posFila][posColumna].getNumMinasAlrededor() == 0) {
            if (RecorridoDFS) {
                recorrerDFS(posFila, posColumna);
            } else if (RecorridoBFS) {
                recorrerBFS(posFila, posColumna);
            }

        }
    }
     */
    //SELECCIONAR UNA CASILLA EN EL JFRAME CON LA BANDERA
    public void seleccionarBandera(int posFila, int posColumna) {
        if (casillas[posFila][posColumna].isAbierta()) {
            JOptionPane.showMessageDialog(null, "Error la casilla ya esta abierta", "Error", JOptionPane.ERROR_MESSAGE);

        } else {
            if (!casillas[posFila][posColumna].isBandera() && contador < numMinas) {
                //numeroBanderas--;
                casillas[posFila][posColumna].setBandera(true);
                eventoBanderaAbierta.ejecutar(casillas[posFila][posColumna]);
            } else if (casillas[posFila][posColumna].isBandera()) {
                //numeroBanderas++;
                casillas[posFila][posColumna].setBandera(false);
                eventoBanderaCerrada.ejecutar(casillas[posFila][posColumna]);
            } else {
                JOptionPane.showMessageDialog(null, "Error no puede colocar mas banderas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //MARCAR UNA CASILLA COMO ABIERTA
    void marcarCasillaAbierta(int posFila, int posColumna) {
        if (!casillas[posFila][posColumna].isAbierta()) {
            numCasillasAbiertas++;
            casillas[posFila][posColumna].setAbierta(true);
        }

    }

    //SI SE ABREN TODAS LAS CASILLAS QUE NO SEAN MINAS ES UNA PARTIDA GANADA
    public boolean PartidaGanada() {
        return numCasillasAbiertas >= (numFilas * numColumnas) - numMinas;

    }

    //IMPRIMIR EN PANTALLA LA LISTA DE ADYACENCIAS DE LAS CASILLAS
    public void imprimirGrafo() {
        System.out.println("    Grafo:");

        for (int i = 0; i < this.casillas.length; i++) {        //fila
            for (int j = 0; j < this.casillas[i].length; j++) {     //columna

                System.out.println("");
                System.out.println("-Casilla/Vertice: [" + j + "," + i + "]");

                System.out.print("Adyacentes: ");
                obtenerCasillasAlrededor(i, j).MostrarLista();
            }
        }
    }

    //IMPRIMO EL TABLERO EN PANTALLA
    public void imprimirTablero() {

        for (int i = 0; i < casillas.length; i++) { //filas
            for (int j = 0; j < casillas[i].length; j++) { //columnas
                System.out.print(casillas[i][j].isMina() ? "*" : "0");

            }
            System.out.println("");
        }
    }

    //IMPRIMO EL TABLERO CON EL NUMERO DE MINAS ALREDEDOR EN PANTALLA
    public void imprimirPistas() {

        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].getNumMinasAlrededor());

            }
            System.out.println("");
        }

    }

    public boolean isRecorridoBFS() {
        return RecorridoBFS;
    }

    public void setRecorridoBFS(boolean RecorridoBFS) {
        this.RecorridoBFS = RecorridoBFS;
    }

    public boolean isRecorridoDFS() {
        return RecorridoDFS;
    }

    public void setRecorridoDFS(boolean RecorridoDFS) {
        this.RecorridoDFS = RecorridoDFS;
    }

}
