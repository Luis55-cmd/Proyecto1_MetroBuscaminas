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
 * Interfaz para notificar al JFrame que se abrió una bandera en una casilla.
 */
public interface EventoBanderaAbierta {

    /**
     * Método que se ejecuta cuando se abre una bandera en una casilla.
     *
     * @param casilla La casilla en la que se abrió la bandera.
     */
    void ejecutar(Casilla casilla);
}
