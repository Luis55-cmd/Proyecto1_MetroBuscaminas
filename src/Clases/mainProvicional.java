package Clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Luis
 */

//Main provicional para ver en consola "Se borrara luego"
public class mainProvicional {
    //MAIN TEMPORAL DE PRUEBA
    public static void main(String[] args) {
        Tablero tablero = new Tablero(3, 3, 2);
        System.out.println("Tablero con minas reveladas: ");
        tablero.imprimirTablero();
        System.out.println("-----------");
        System.out.println("Tablero con pistas: ");
        tablero.imprimirPistas();
        System.out.println("-----------");
        tablero.imprimirGrafo();

    }
}
