
import java.awt.Color;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Luis
 */
public class Ventana1 extends javax.swing.JFrame {

    private Clip clip;

    /**
     * Creates new form Ventana1
     */
    public Ventana1() {

        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        ImageIcon salir2 = new ImageIcon(getClass().getResource("/Imagenes/ON.png"));
        Icon fondo2 = new ImageIcon(salir2.getImage().getScaledInstance(on.getWidth(), on.getHeight(), 0));
        on.setIcon(fondo2);
        on.setOpaque(false);
        on.setBorder(null);
        on.setBackground(new Color(0, 0, 0, 0));

        ImageIcon fon = new ImageIcon(getClass().getResource("/Imagenes/fondo.png"));
        Icon fondo5 = new ImageIcon(fon.getImage().getScaledInstance(pantalla.getWidth(), pantalla.getHeight(), 0));
        pantalla.setIcon(fondo5);

        this.repaint();

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

        iniciar.setText("INICIAR");
        iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarActionPerformed(evt);
            }
        });
        JPanel.add(iniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 90, 40));

        on.setText("Music ON");
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

    private void iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarActionPerformed
        this.setVisible(false);
        Ventana2 ventana2 = new Ventana2();
        ventana2.setVisible(true);
    }//GEN-LAST:event_iniciarActionPerformed

    private void onActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onActionPerformed
        if (on.getText() == "Music ON") {

            ImageIcon salir2 = new ImageIcon(getClass().getResource("/Imagenes/ON.png"));
            Icon fondo2 = new ImageIcon(salir2.getImage().getScaledInstance(on.getWidth(), on.getHeight(), 0));
            on.setIcon(fondo2);

            on.setText("Music OFF");
            // Play Audio  File        
            try {
                String filepath = "C:\\Users\\Luis\\Documents\\NetBeansProjects\\Prepadurias2025\\src\\Musica\\lo.wav";
                AudioInputStream aui = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
                try {
                    clip = AudioSystem.getClip();
                    clip.open(aui);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (IOException | LineUnavailableException e) {
                }
            } catch (IOException | UnsupportedAudioFileException e) {
            }

        } else if (on.getText() == "Music OFF") {

            ImageIcon salir3 = new ImageIcon(getClass().getResource("/Imagenes/OFF.png"));
            Icon fondo3 = new ImageIcon(salir3.getImage().getScaledInstance(on.getWidth(), on.getHeight(), 0));
            on.setIcon(fondo3);

            on.setText("Music ON");
            if (clip != null && clip.isRunning()) {
                clip.stop();
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel;
    private javax.swing.JButton iniciar;
    private javax.swing.JToggleButton on;
    private javax.swing.JLabel pantalla;
    // End of variables declaration//GEN-END:variables
}
