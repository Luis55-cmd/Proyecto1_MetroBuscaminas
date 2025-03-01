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
//INTERFAZ PARA NOTIFICAR AL JFRAME QUE SE GANO LA PARTIDA Y REVELE LAS CASILLAS CON MINAS
public interface EventoPartidaGanada {

    void ejecutar(ListaAdyacencia lista);

}
