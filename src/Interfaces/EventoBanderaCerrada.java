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
 * Interfaz para notificar al JFrame que se cerró una bandera en una casilla.
 */
public interface EventoBanderaCerrada {

    /**
     * Método que se ejecuta cuando se cierra una bandera en una casilla.
     *
     * @param casilla La casilla en la que se cerró la bandera.
     */
    void ejecutar(Casilla casilla);
}
