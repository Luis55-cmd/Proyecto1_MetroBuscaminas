package Jframes;

import Clases.*;
import java.awt.Color;
import javax.sound.sampled.*;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Luis, Zadkiel Avendano
 */
/**
 * Ventana1 es una clase que representa la ventana principal del juego
 * MetroBuscaminas. Esta ventana incluye funcionalidades como la reproducción de
 * música y la navegación a otras ventanas.
 */
public class Ventana1 extends javax.swing.JFrame {

    /**
     * Clip de audio para reproducir música en la ventana.
     */
    public static Clip clip;

    /**
     * Instancia de la clase Funciones para reutilizar métodos comunes.
     */
    Funciones funciones = new Funciones();

    /**
     * Constructor de la clase Ventana1. Inicializa la ventana, configura el
     * título, la posición, la imagen de fondo y la música.
     */
    public Ventana1() {
        setTitle("MetroBuscaminas");
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        funciones.colocarImagen("/Imagenes/ON.png", on);
        funciones.colocarImagen("/Imagenes/Iniciar.png", iniciar);
        iniciar.setBackground(new Color(0, 0, 0, 0));
        on.setOpaque(false);
        on.setBorder(null);
        on.setBackground(new Color(0, 0, 0, 0));

        // Configuración del fondo de la ventana
        ImageIcon fon = new ImageIcon(getClass().getResource("/Imagenes/fondo.png"));
        Icon fondo5 = new ImageIcon(fon.getImage().getScaledInstance(pantalla.getWidth(), pantalla.getHeight(), 0));
        pantalla.setIcon(fondo5);

        this.repaint();
        // Configuración de la música 
        try {
            URL audioPath = getClass().getClassLoader().getResource("Musica/song.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Manejo de excepciones en caso de error al cargar la música
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPanel = new javax.swing.JPanel();
        iniciar = new javax.swing.JButton();
        on = new javax.swing.JToggleButton();
        pantalla = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        JPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iniciar.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        iniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarActionPerformed(evt);
            }
        });
        JPanel.add(iniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 130, 60));

        on.setText("Music ON");
        on.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        on.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onActionPerformed(evt);
            }
        });
        JPanel.add(on, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 50, 40));

        pantalla.setText("pantalla");
        JPanel.add(pantalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 350));

        getContentPane().add(JPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que se ejecuta al hacer clic en el botón "Iniciar". Oculta la
     * ventana actual y muestra la Ventana2.
     *
     * @param evt Evento de acción generado al hacer clic en el botón.
     */

    private void iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarActionPerformed
        //BOTON PARA INICIAR EL JUEGO

        this.setVisible(false);
        Ventana2 ventana2 = new Ventana2();
        ventana2.setVisible(true);
    }//GEN-LAST:event_iniciarActionPerformed

    /**
     * Método que se ejecuta al hacer clic en el botón de música. Controla la
     * reproducción y detención de la música, así como el cambio de imagen y
     * texto del botón.
     *
     * @param evt Evento de acción generado al hacer clic en el botón.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    private void onActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onActionPerformed
        //BOTON PARA LA MUSICA

        if ("Music ON".equals(on.getText())) {

            funciones.colocarImagen("/Imagenes/OFF.png", on);

            on.setText("Music OFF");
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }

        } else if ("Music OFF".equals(on.getText())) {
            funciones.colocarImagen("/Imagenes/ON.png", on);

            on.setText("Music ON");
            // Play Audio  File           
            try {
                URL audioPath = getClass().getClassLoader().getResource("Musica/song.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_onActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Ventana1().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel;
    private javax.swing.JButton iniciar;
    private javax.swing.JToggleButton on;
    private javax.swing.JLabel pantalla;
    // End of variables declaration//GEN-END:variables
}
