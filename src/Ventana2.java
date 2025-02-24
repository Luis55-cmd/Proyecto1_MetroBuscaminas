
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.Border;

/**
 * Ventana principal del juego MetroBuscaminas. Permite al usuario configurar el
 * tamaño del tablero y la cantidad de minas, e iniciar el juego.
 *
 * @author Luis, Zadkiel Avendano
 */
public class Ventana2 extends javax.swing.JFrame {

    /**
     * Matriz de botones que representa el tablero del juego.
     */
    private JButton[][] botonesTablero;

    TableroBuscaminas tableroBuscaminas;
    /**
     * Referencia a la ventana principal del juego (Ventana1).
     */
    public static Ventana1 v1;

    /**
     * Constructor de la clase Ventana2. Inicializa los componentes de la
     * ventana, establece el título.
     */
    public Ventana2() {

        initComponents();
        setTitle("MetroBuscaminas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false); // Impide cambiar la dimension de la ventana
        ImageIcon salir2 = new ImageIcon(getClass().getResource("/Imagenes/ON.png"));
        Icon fondo2 = new ImageIcon(salir2.getImage().getScaledInstance(on.getWidth(), on.getHeight(), 0));
        on.setIcon(fondo2);
        on.setOpaque(false);
        on.setBorder(null);
        on.setBackground(new Color(0, 0, 0, 0));
        f.setText("3");
        c.setText("3");
        m.setText("3");

    }

    void descargarControles() {
        if (botonesTablero != null) {
            for (int i = 0; i < botonesTablero.length; i++) {
                for (int j = 0; j < botonesTablero[i].length; j++) {
                    if (botonesTablero[i][j] != null) {
                        getContentPane().remove(botonesTablero[i][j]);
                    }
                }
            }
        }

    }

    /**
     * Inicia el juego. Lee los valores de filas, columnas y minas de los campos
     * de texto, valida que estén dentro de los rangos permitidos y llama al
     * método {@link #cargarCasillas(int, int, int)} para crear el tablero.
     * Muestra un mensaje de error si los valores ingresados no son válidos.
     */
    private void juegoNuevo() {

        try {
            int filas = Integer.parseInt(f.getText());
            int columnas = Integer.parseInt(c.getText());
            int minas = Integer.parseInt(m.getText());

            if (filas < 3 || filas > 10 || columnas < 3 || columnas > 10 || minas < 1 || minas > filas * columnas) {
                throw new IllegalArgumentException("Valores fuera de rango");
            }
            descargarControles();
            cargarCasillas();
            crearTableroBuscaminas();
            repaint();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void crearTableroBuscaminas() {

        tableroBuscaminas = new TableroBuscaminas(Integer.parseInt(f.getText()), Integer.parseInt(c.getText()), Integer.parseInt(m.getText()));
        tableroBuscaminas.imprimirTablero(); //En la consola

        //Sin Labmda expresion
        /*
        tableroBuscaminas.setEventoPartidaPerdida(new EventoPartidaPerdida() {
            @Override
            public void ejecutar(ListaAdyacencia t) {
                NodoAdyacencia recorrer = t.cabeza;
                while (recorrer != null) {
                    botonesTablero[recorrer.valor.getPosFila()][recorrer.valor.getPosColumna()].setText("*");
                    recorrer = recorrer.siguiente;
                }
            }
        
        });
         */
        //Con Labmda expresion
        tableroBuscaminas.setEventoPartidaPerdida((ListaAdyacencia t) -> {
            NodoAdyacencia recorrer = t.cabeza;
            while (recorrer != null) {
                int i = recorrer.valor.getPosFila();
                int j = recorrer.valor.getPosColumna();
                botonesTablero[i][j].setIcon(null);
                ImageIcon ca = new ImageIcon(getClass().getResource("/Imagenes/Bomba.png"));
                Icon cb = new ImageIcon(ca.getImage().getScaledInstance(botonesTablero[i][j].getWidth(), botonesTablero[i][j].getHeight(), 0));
                botonesTablero[i][j].setIcon(cb);
                botonesTablero[i][j].setBackground(new Color(139, 69, 19));
                recorrer = recorrer.siguiente;
            }
            JOptionPane.showMessageDialog(null, "PISASTE UNA MINA");

        });
        tableroBuscaminas.setEventoPartidaPerdida2((ListaAdyacencia t) -> {
            NodoAdyacencia recorrer = t.cabeza;
            while (recorrer != null) {
                botonesTablero[recorrer.valor.getPosFila()][recorrer.valor.getPosColumna()].setEnabled(false);

                recorrer = recorrer.siguiente;
            }

        });

        tableroBuscaminas.setEventoPartidaGanada((ListaAdyacencia t) -> {

            NodoAdyacencia recorrer = t.cabeza;
            while (recorrer != null) {
                int i = recorrer.valor.getPosFila();
                int j = recorrer.valor.getPosColumna();

                botonesTablero[i][j].setIcon(null);
                ImageIcon ca = new ImageIcon(getClass().getResource("/Imagenes/Bomba.png"));
                Icon cb = new ImageIcon(ca.getImage().getScaledInstance(botonesTablero[i][j].getWidth(), botonesTablero[i][j].getHeight(), 0));
                botonesTablero[i][j].setIcon(cb);
                botonesTablero[i][j].setBackground(new Color(139, 69, 19));

                recorrer = recorrer.siguiente;
            }

            JOptionPane.showMessageDialog(null, "PARTIDA GANADA!!");

        });

        tableroBuscaminas.setEventoCasillaAbierta((ObjetoCasilla t) -> {
            botonesTablero[t.getPosFila()][t.getPosColumna()].setEnabled(false);
            botonesTablero[t.getPosFila()][t.getPosColumna()]
                    .setText(t.getNumMinasAlrededor() == 0 ? "" : t.getNumMinasAlrededor() + "");

            //botonesTablero[t.getPosFila()][t.getPosColumna()].setIcon(null);
            /*
            ImageIcon c = new ImageIcon(getClass().getResource("/Imagenes/CasillaBloqueada.png"));
                Icon cb = new ImageIcon(c.getImage().getScaledInstance(botonesTablero[i][j].getWidth(), botonesTablero[i][j].getHeight(), 0));
                botonesTablero[i][j].setIcon(cb);
                botonesTablero[i][j].setBorder(line);
             */
        });
        f.setText("");
        c.setText("");
        m.setText("");

    }

    /**
     * Carga las casillas del tablero en el panel. Crea una matriz de botones
     * con las dimensiones especificadas, establece su posición y tamaño, agrega
     * un ActionListener a cada botón y los añade al panel.
     *
     * @param filas La cantidad de filas del tablero.
     * @param columnas La cantidad de columnas del tablero.
     * @param minas La cantidad de minas en el tablero.
     */
    public void cargarCasillas() {

        int posXreferencia = 25;
        int posYreferencia = 25;
        int anchoCasilla = 40;
        int altoCasilla = 40;
        Border line = BorderFactory.createLineBorder(Color.GRAY, 2);
        panelTablero.removeAll();

        botonesTablero = new JButton[Integer.parseInt(f.getText())][Integer.parseInt(c.getText())];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setName(i + "," + j);

                if (i == 0 && j == 0) {
                    botonesTablero[i][j].setBounds(posXreferencia, posYreferencia, anchoCasilla, altoCasilla);
                } else if (i == 0 && j != 0) {
                    botonesTablero[i][j].setBounds(botonesTablero[i][j - 1].getX() + botonesTablero[i][j - 1].getWidth(), posYreferencia, anchoCasilla, altoCasilla);

                } else {
                    botonesTablero[i][j].setBounds(botonesTablero[i - 1][j].getX(), botonesTablero[i - 1][j].getY() + botonesTablero[i - 1][j].getWidth(), anchoCasilla, altoCasilla);
                }
                botonesTablero[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnClick(e);
                    }
                });

                panelTablero.add(botonesTablero[i][j]);

                ImageIcon c = new ImageIcon(getClass().getResource("/Imagenes/CasillaBloqueada.png"));
                Icon cb = new ImageIcon(c.getImage().getScaledInstance(botonesTablero[i][j].getWidth(), botonesTablero[i][j].getHeight(), 0));
                botonesTablero[i][j].setIcon(cb);
                botonesTablero[i][j].setBorder(line);

            }
        }

        panelTablero.revalidate();
        panelTablero.repaint();

    }

    /**
     * Maneja el evento de click en una casilla del tablero. Obtiene la fila y
     * columna del botón clickeado, muestra un mensaje con las coordenadas y
     * cambia el icono del botón.
     *
     * @param e El evento de acción.
     */
    private void btnClick(ActionEvent e) {

        JButton btn = (JButton) e.getSource();
        String[] coordenada = btn.getName().split(",");
        int fila = Integer.parseInt(coordenada[0]);
        int columna = Integer.parseInt(coordenada[1]);
        JOptionPane.showMessageDialog(rootPane, fila + "," + columna);
        tableroBuscaminas.seleccionarCasilla(fila, columna);

        /*
        ImageIcon c2 = new ImageIcon(getClass().getResource("/Imagenes/CasillaDesbloqueada.png"));
        Icon cb2 = new ImageIcon(c2.getImage().getScaledInstance(btn.getWidth(), btn.getHeight(), 0));
        btn.setIcon(cb2);
         */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelTablero = new javax.swing.JPanel();
        panelDerecha = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        panelSuperior = new javax.swing.JPanel();
        crear = new javax.swing.JButton();
        on = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        m = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        f = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        c = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout panelTableroLayout = new javax.swing.GroupLayout(panelTablero);
        panelTablero.setLayout(panelTableroLayout);
        panelTableroLayout.setHorizontalGroup(
                panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 440, Short.MAX_VALUE)
        );
        panelTableroLayout.setVerticalGroup(
                panelTableroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 490, Short.MAX_VALUE)
        );

        getContentPane().add(panelTablero, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 440, 490));

        panelDerecha.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        panelDerecha.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -150, 50, 640));

        getContentPane().add(panelDerecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 190, 490));

        panelSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        crear.setText("Crear");
        crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearActionPerformed(evt);
            }
        });
        panelSuperior.add(crear, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 92, 80));

        on.setText("Music ON");
        on.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onActionPerformed(evt);
            }
        });
        panelSuperior.add(on, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 50, 40));

        jButton6.setText("jButton6");
        panelSuperior.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, -1, 43));
        panelSuperior.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 107, 660, -1));
        panelSuperior.add(m, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 110, -1));

        jLabel1.setText("Minas:");
        panelSuperior.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, -1));
        panelSuperior.add(f, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 110, -1));

        jLabel2.setText("Filas:");
        panelSuperior.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));
        panelSuperior.add(c, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 110, -1));

        jLabel3.setText("Columnas:");
        panelSuperior.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        getContentPane().add(panelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 110));

        pack();
    }// </editor-fold>                        

    /**
     * Maneja el evento de click en el botón "Crear". Llama al método
     * {@link #iniciarJuego()} para iniciar el juego.
     *
     * @param evt El evento de acción.
     */
    private void crearActionPerformed(java.awt.event.ActionEvent evt) {

        juegoNuevo();

    }

    /**
     * Maneja el evento de click en el botón de encendido/apagado de la música.
     * Cambia el icono y el texto del botón, y detiene o inicia la reproducción
     * de la música según corresponda.
     *
     * @param evt El evento de acción.
     */
    private void onActionPerformed(java.awt.event.ActionEvent evt) {

        if (on.getText() == "Music ON") {

            ImageIcon salir3 = new ImageIcon(getClass().getResource("/Imagenes/OFF.png"));
            Icon fondo3 = new ImageIcon(salir3.getImage().getScaledInstance(on.getWidth(), on.getHeight(), 0));
            on.setIcon(fondo3);

            on.setText("Music OFF");
            if (v1.clip != null && v1.clip.isRunning()) {
                v1.clip.stop();
            }

        } else if (on.getText() == "Music OFF") {
            ImageIcon salir2 = new ImageIcon(getClass().getResource("/Imagenes/ON.png"));
            Icon fondo2 = new ImageIcon(salir2.getImage().getScaledInstance(on.getWidth(), on.getHeight(), 0));
            on.setIcon(fondo2);

            on.setText("Music ON");
            // Play Audio  File
            try {
                URL audioPath = getClass().getClassLoader().getResource("Musica/song.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
                v1.clip = AudioSystem.getClip();
                v1.clip.open(audioStream);
                v1.clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

        }

    }

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
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField c;
    private javax.swing.JButton crear;
    private javax.swing.JTextField f;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField m;
    private javax.swing.JButton on;
    private javax.swing.JPanel panelDerecha;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPanel panelTablero;
    // End of variables declaration
}
