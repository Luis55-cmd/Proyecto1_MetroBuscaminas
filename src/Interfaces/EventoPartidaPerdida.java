package Interfaces;

import EstructurasDeDatos.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author Luis
 */
/**
 * Interfaz para notificar al JFrame que se perdió la partida y revelar las
 * casillas con minas.
 */
public interface EventoPartidaPerdida {

    /**
     * Método que se ejecuta cuando se pierde la partida.
     *
     * @param lista La lista de casillas con minas que se revelarán.
     */
    void ejecutar(ListaAdyacencia lista);
}
