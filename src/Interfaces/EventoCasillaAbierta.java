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
 * Interfaz para notificar al JFrame que se abrió una casilla.
 */
public interface EventoCasillaAbierta {

    /**
     * Método que se ejecuta cuando se abre una casilla.
     *
     * @param casilla La casilla que se abrió.
     */
    void ejecutar(Casilla casilla);
}
