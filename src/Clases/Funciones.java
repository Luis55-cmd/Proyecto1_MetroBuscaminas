package Clases;


import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Zadkiel
 */
public class Funciones {
    
    //Coloca una imagen en un boton
    public void colocarImagen(String link, AbstractButton boton) {
        if (link != null && !"".equals(link)){
            ImageIcon image = new ImageIcon(getClass().getResource(link));
            Icon x = new ImageIcon(image.getImage().getScaledInstance(boton.getWidth(), boton.getHeight(), 0));
            boton.setIcon(x);
            boton.setDisabledIcon(x);
        } else {
            boton.setIcon(null);
            boton.setDisabledIcon(null);
        }
    }
}
