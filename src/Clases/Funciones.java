package Clases;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Zadkiel
 */
/**
 * Clase que proporciona funciones útiles para la manipulación de componentes
 * gráficos, como la colocación de imágenes en botones y botones de alternancia
 * (JToggleButton).
 */
public class Funciones {

    /**
     * Coloca una imagen en un botón (JButton) a partir de una ruta de archivo.
     * Si la ruta es nula o vacía, se elimina la imagen del botón.
     *
     * @param link La ruta del archivo de imagen relativa al classpath.
     * @param boton El botón en el que se colocará la imagen.
     */
    public void colocarImagen(String link, JButton boton) {
        if (link != null && !"".equals(link)) {
            ImageIcon image = new ImageIcon(getClass().getResource(link));
            Icon x = new ImageIcon(image.getImage().getScaledInstance(boton.getWidth(), boton.getHeight(), 0));
            boton.setIcon(x);
            boton.setDisabledIcon(x);
        } else {
            boton.setIcon(null);
            boton.setDisabledIcon(null);
        }
    }

    /**
     * Coloca una imagen en un botón de alternancia (JToggleButton) a partir de
     * una ruta de archivo. Si la ruta es nula o vacía, se elimina la imagen del
     * botón.
     *
     * @param link La ruta del archivo de imagen relativa al classpath.
     * @param boton El botón de alternancia en el que se colocará la imagen.
     */
    public void colocarImagen(String link, JToggleButton boton) {
        if (link != null && !"".equals(link)) {
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
