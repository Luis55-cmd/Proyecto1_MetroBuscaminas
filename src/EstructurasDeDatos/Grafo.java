package EstructurasDeDatos;

import Clases.*;
import Interfacez.*;

import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Luis
 */
public class Grafo {

    //ATRIBUTOS
    public Casilla[][] casillas;
    public int numFilas;
    public int numColumnas;
    public int numMinas;
    //ListaAdyacencia lista[];
    public int numCasillasAbiertas;
    public int numeroBanderas;
    public int contador = 0;
    EventoPartidaPerdida eventoPartidaPerdida;

    EventoCasillaAbierta eventoCasillaAbierta;
    EventoBanderaAbierta eventoBanderaAbierta;
    EventoBanderaCerrada eventoBanderaCerrada;
    EventoPartidaGanada eventoPartidaGanada;

    //CONSTRUCTOR
    public Grafo(int numFilas, int numColumnas, int numMinas) {
        this.numeroBanderas = numMinas;

        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;

        inicializarCasilla();
    }

    //INICIO LAS CASILLAS
    public void inicializarCasilla() {
        casillas = new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) { //filas
            for (int j = 0; j < casillas[i].length; j++) { //columnas

                char columnaLetra = (char) ('A' + j);

                casillas[i][j] = new Casilla(i, j);
                casillas[i][j].setID(Integer.parseInt(i + "" + j));

            }
        }
        generarMinas();
    }

    //GENERO LAS MINAS EN LAS CASILLAS
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

    public void setEventoCasillaAbierta(EventoCasillaAbierta eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    public void setEventoPartidaGanada(EventoPartidaGanada eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }

    public void setEventoBanderaAbierta(EventoBanderaAbierta eventoBanderaAbierta) {
        this.eventoBanderaAbierta = eventoBanderaAbierta;
    }

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

    //Aqui debemos hacer el bfs y dfs
    //SELECCIONAR UNA CASILLA EN EL JFRAME
    public void seleccionarCasilla(int posFila, int posColumna) {

        if (casillas[posFila][posColumna].isBandera()) {
            JOptionPane.showMessageDialog(null, "Error la casilla esta marcada con una bandera", "Error", JOptionPane.ERROR_MESSAGE);

        } else {
            eventoCasillaAbierta.ejecutar(casillas[posFila][posColumna]);

            if (this.casillas[posFila][posColumna].isMina()) {

                if (eventoPartidaPerdida != null) {
                    eventoPartidaPerdida.ejecutar(obtenerCasillasConMinas());

                }
            } else if (casillas[posFila][posColumna].getNumMinasAlrededor() == 0) {
                marcarCasillaAbierta(posFila, posColumna);
                if (PartidaGanada()) {
                    eventoPartidaGanada.ejecutar(obtenerCasillasConMinas());
                    System.out.println("ganaste");
                }
                ListaAdyacencia casillasAlrededor = obtenerCasillasAlrededor(posFila, posColumna);
                NodoAdyacencia actual = casillasAlrededor.cabeza;
                while (actual != null) {
                    if (!actual.valor.isAbierta()) {
                        seleccionarCasilla(actual.valor.getPosFila(), actual.valor.getPosColumna());

                    }
                    actual = actual.siguiente;
                }

            } else {
                marcarCasillaAbierta(posFila, posColumna);
                if (PartidaGanada()) {
                    eventoPartidaGanada.ejecutar(obtenerCasillasConMinas());
                    System.out.println("ganaste");
                }
            }

        }

    }

    public void seleccionarBandera(int posFila, int posColumna) {
        if (casillas[posFila][posColumna].isAbierta()) {
            JOptionPane.showMessageDialog(null, "Error la casilla ya esta abierta", "Error", JOptionPane.ERROR_MESSAGE);

        } else {
            if (!casillas[posFila][posColumna].isBandera() && contador < numMinas) {

                numeroBanderas--;
                contador++;
                casillas[posFila][posColumna].setBandera(true);
                eventoBanderaAbierta.ejecutar(casillas[posFila][posColumna]);
            } else if (casillas[posFila][posColumna].isBandera()) {
                numeroBanderas++;
                contador--;
                casillas[posFila][posColumna].setBandera(false);
                eventoBanderaCerrada.ejecutar(casillas[posFila][posColumna]);
            } else {
                JOptionPane.showMessageDialog(null, "Error no puede colocar mas banderas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void marcarCasillaAbierta(int posFila, int posColumna) {
        if (!casillas[posFila][posColumna].isAbierta()) {
            numCasillasAbiertas++;
            casillas[posFila][posColumna].setAbierta(true);
        }

    }

    boolean PartidaGanada() {
        return numCasillasAbiertas >= (numFilas * numColumnas) - numMinas;

    }

    //IMPRIMIR EN PANTALLA LA LISTA DE ADYACENCIAS DE LAS CASILLAS
    public void imprimirGrafo() {
        System.out.println("    Grafo:");

        for (int i = 0; i < this.casillas.length; i++) {
            for (int j = 0; j < this.casillas[i].length; j++) {

                System.out.println("");
                System.out.println("-Casilla/Vertice: [" + j + "," + i + "]");
                ListaAdyacencia casillasAdyacentes = obtenerCasillasAlrededor(i, j);
                System.out.print("Adyacentes: ");
                casillasAdyacentes.MostrarLista();
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

}
