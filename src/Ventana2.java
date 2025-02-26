/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author Luis
 */
public class Ventana2 extends javax.swing.JFrame {

    /**
     * Matriz de botones que representa el tablero del juego.
     */
    private JButton[][] botonesTablero;

    TableroBuscaminas tableroBuscaminas;

    boolean colocarbandera;
    boolean colocarpala;
    boolean juegoTerminado;

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

        on.setOpaque(false);
        on.setBorder(null);
        on.setBackground(new Color(0, 0, 0, 0));
        colocarImagen("/Imagenes/ON.png", on);
        colocarImagen("/Imagenes/pala.png", pala);
        colocarImagen("/Imagenes/bandera.png", bandera);
        colocarImagen("/Imagenes/arbol.png", arbol);
        colocarImagen("/Imagenes/guardar.png", guardar);
        colocarImagen("/Imagenes/cargar.png", cargar);
        colocarImagen("/Imagenes/crear.png", crear);

        colocarImagen("/Imagenes/bienvenida.png", bienvenida);
        bienvenida.setBackground(new Color(0, 0, 0, 0));
        

        f.setText("3");
        c.setText("3");
        m.setText("3");
        panelDerecha.setVisible(false);

    }

    //Coloca una imagen en un boton
    private void colocarImagen(String link, JButton boton) {
        ImageIcon palaa = new ImageIcon(getClass().getResource(link));
        Icon x = new ImageIcon(palaa.getImage().getScaledInstance(boton.getWidth(), boton.getHeight(), 0));
        boton.setIcon(x);

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
            banderas.setText("Banderas: " + m.getText());

            if (filas < 3 || filas > 10 || columnas < 3 || columnas > 10 || minas < 1 || minas > filas * columnas) {
                throw new IllegalArgumentException("Valores fuera de rango");
            }
            panelDerecha.setVisible(true);
            panelInformativo.setVisible(false);
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

                colocarImagen("/Imagenes/Bomba.png", botonesTablero[i][j]);

                botonesTablero[i][j].setBackground(new Color(139, 69, 19));

                recorrer = recorrer.siguiente;
            }
            colocarbandera = false;
            colocarpala = false;
            juegoTerminado = true;
            pala.setEnabled(false);
            bandera.setEnabled(false);
            JOptionPane.showMessageDialog(null, "PISASTE UNA MINA", "PERDISTE!!!", JOptionPane.INFORMATION_MESSAGE);

        });

        tableroBuscaminas.setEventoPartidaGanada((ListaAdyacencia t) -> {

            NodoAdyacencia recorrer = t.cabeza;
            while (recorrer != null) {
                int i = recorrer.valor.getPosFila();
                int j = recorrer.valor.getPosColumna();

                botonesTablero[i][j].setIcon(null);

                colocarImagen("/Imagenes/Bomba.png", botonesTablero[i][j]);

                botonesTablero[i][j].setBackground(new Color(139, 69, 19));

                recorrer = recorrer.siguiente;

            }
            colocarbandera = false;
            colocarpala = false;
            juegoTerminado = true;
            pala.setEnabled(false);
            bandera.setEnabled(false);
            JOptionPane.showMessageDialog(null, "PARTIDA GANADA", "GANASTE!!!", JOptionPane.INFORMATION_MESSAGE);

        });
        tableroBuscaminas.setEventoBanderaAbierta((ObjetoCasilla t) -> {

            int i = t.getPosFila();
            int j = t.getPosColumna();

            if (tableroBuscaminas.numeroBanderas < 0) {
                JOptionPane.showMessageDialog(null, "No hay mas banderas para colocar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                colocarImagen("/Imagenes/banderaCasilla.png", botonesTablero[i][j]);

                banderas.setText("Banderas: " + tableroBuscaminas.numeroBanderas);
            }

        });

        tableroBuscaminas.setEventoBanderaCerrada((ObjetoCasilla t) -> {
            int i = t.getPosFila();
            int j = t.getPosColumna();
            if (tableroBuscaminas.numeroBanderas > tableroBuscaminas.numMinas) {
                JOptionPane.showMessageDialog(null, "No hay banderas en el tablero para quitar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                colocarImagen("/Imagenes/CasillaBloqueada.png", botonesTablero[i][j]);

                banderas.setText("Banderas: " + tableroBuscaminas.numeroBanderas);
            }

        });
        tableroBuscaminas.setEventoCasillaAbierta((ObjetoCasilla t) -> {
            int i = t.getPosFila();
            int j = t.getPosColumna();
            int k = t.getNumMinasAlrededor();
            Border line = BorderFactory.createLineBorder(Color.GRAY, 2);

            botonesTablero[i][j]
                    .setText(k == 0 ? "" : k + "");
            botonesTablero[i][j].setIcon(null);

            if (botonesTablero[i][j].getText().equals(Integer.toString(1)) && !t.isMina()) {
                botonesTablero[i][j].setText("");

                colocarImagen("/Imagenes/uno.png", botonesTablero[i][j]);

                botonesTablero[i][j].setBorder(line);
            } else if (botonesTablero[i][j].getText().equals(Integer.toString(2)) && !t.isMina()) {
                botonesTablero[i][j].setText("");
                colocarImagen("/Imagenes/dos.png", botonesTablero[i][j]);

                botonesTablero[i][j].setBorder(line);
            } else if (botonesTablero[i][j].getText().equals(Integer.toString(3)) && !t.isMina()) {
                botonesTablero[i][j].setText("");

                colocarImagen("/Imagenes/tres.png", botonesTablero[i][j]);

                botonesTablero[i][j].setBorder(line);
            } else if (botonesTablero[i][j].getText().equals(Integer.toString(4)) && !t.isMina()) {
                botonesTablero[i][j].setText("");
                colocarImagen("/Imagenes/cuatro.png", botonesTablero[i][j]);

                botonesTablero[i][j].setBorder(line);

            } else {
                botonesTablero[i][j].setText("");
                colocarImagen("/Imagenes/CasillaDesbloqueada.png", botonesTablero[i][j]);

                botonesTablero[i][j].setBorder(line);
            }

            /*
            
             */
        });

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
                botonesTablero[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        btnClick(e);
                    }
                });

                panelTablero.add(botonesTablero[i][j]);

                colocarImagen("/Imagenes/CasillaBloqueada.png", botonesTablero[i][j]);

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
    private void btnClick(MouseEvent e) {

        JButton btn = (JButton) e.getSource();
        String[] coordenada = btn.getName().split(",");
        int fila = Integer.parseInt(coordenada[0]);
        int columna = Integer.parseInt(coordenada[1]);
        JOptionPane.showMessageDialog(rootPane, fila + "," + columna);

        if (colocarbandera == true) {

            tableroBuscaminas.seleccionarBandera(fila, columna);

        } else if (colocarbandera == false && colocarpala == true) {
            tableroBuscaminas.seleccionarCasilla(fila, columna);
        } else if (juegoTerminado == true) {
            JOptionPane.showMessageDialog(null, "No puede seleccionar mas casillas", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Porfavor seleccione la pala si desea barrer una casilla", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Creates new form Ventana2
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInformativo = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bienvenida = new javax.swing.JButton();
        panelTablero = new javax.swing.JPanel();
        panelDerecha = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        bandera = new javax.swing.JButton();
        pala = new javax.swing.JButton();
        banderas = new javax.swing.JLabel();
        cargar = new javax.swing.JButton();
        arbol = new javax.swing.JButton();
        guardar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        informacionbandera = new javax.swing.JButton();
        informacionarbol = new javax.swing.JButton();
        informacionpala = new javax.swing.JButton();
        panelSuperior = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        on = new javax.swing.JButton();
        f = new javax.swing.JTextField();
        c = new javax.swing.JTextField();
        m = new javax.swing.JTextField();
        crear = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dfs = new javax.swing.JRadioButton();
        bfs = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelInformativo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Las minas deben ser como máximo el número de casillas de la cuadrícula.");
        panelInformativo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Luego presione el Emoji para crear un tablero.");
        panelInformativo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Para empezar a jugar primero rellene el número de filas y columnas");
        panelInformativo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("con un número entre 3 y 10.");
        panelInformativo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 30));
        panelInformativo.add(bienvenida, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 220, 80));

        getContentPane().add(panelInformativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 110, 630, 490));

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

        bandera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banderaActionPerformed(evt);
            }
        });
        panelDerecha.add(bandera, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 70, 70));

        pala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palaActionPerformed(evt);
            }
        });
        panelDerecha.add(pala, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 70, 70));

        banderas.setText("Banderas: ");
        panelDerecha.add(banderas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 100, 20));

        cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarActionPerformed(evt);
            }
        });
        panelDerecha.add(cargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 70, 70));

        arbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arbolActionPerformed(evt);
            }
        });
        panelDerecha.add(arbol, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 70, 70));

        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });
        panelDerecha.add(guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 70, 70));

        jLabel9.setText("Cargar juego");
        panelDerecha.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, -1, -1));

        jLabel10.setText("Guardar juego");
        panelDerecha.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        informacionbandera.setText("...");
        informacionbandera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informacionbanderaActionPerformed(evt);
            }
        });
        panelDerecha.add(informacionbandera, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 20, 20));

        informacionarbol.setText("...");
        informacionarbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informacionarbolActionPerformed(evt);
            }
        });
        panelDerecha.add(informacionarbol, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 20, 20));

        informacionpala.setText("...");
        informacionpala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informacionpalaActionPerformed(evt);
            }
        });
        panelDerecha.add(informacionpala, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 20, 20));

        getContentPane().add(panelDerecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 190, 490));

        panelSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelSuperior.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 107, 660, -1));

        on.setText("Music ON");
        on.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onActionPerformed(evt);
            }
        });
        panelSuperior.add(on, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 50, 40));

        f.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        panelSuperior.add(f, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 110, -1));

        c.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cActionPerformed(evt);
            }
        });
        panelSuperior.add(c, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 110, -1));

        m.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mActionPerformed(evt);
            }
        });
        panelSuperior.add(m, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 110, -1));

        crear.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearActionPerformed(evt);
            }
        });
        panelSuperior.add(crear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 90));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Filas:");
        panelSuperior.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Columnas:");
        panelSuperior.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Minas:");
        panelSuperior.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, -1));
        panelSuperior.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 107, 660, -1));

        dfs.setText("Depth-First Search");
        panelSuperior.add(dfs, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        bfs.setText("Breadth-First Search");
        panelSuperior.add(bfs, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, -1, -1));

        getContentPane().add(panelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onActionPerformed
        if (on.getText() == "Music ON") {

            colocarImagen("/Imagenes/OFF.png", on);

            on.setText("Music OFF");
            if (v1.clip != null && v1.clip.isRunning()) {
                v1.clip.stop();
            }

        } else if (on.getText() == "Music OFF") {
            colocarImagen("/Imagenes/ON.png", on);

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
    }//GEN-LAST:event_onActionPerformed

    private void cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cActionPerformed

    private void mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mActionPerformed

    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed
        juegoTerminado = false;
        pala.setEnabled(true);
        bandera.setEnabled(true);
        juegoNuevo();


    }//GEN-LAST:event_crearActionPerformed

    private void palaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palaActionPerformed
        colocarpala = true;
        colocarbandera = false;
    }//GEN-LAST:event_palaActionPerformed

    private void banderaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banderaActionPerformed
        colocarbandera = true;
        colocarpala = true;

    }//GEN-LAST:event_banderaActionPerformed

    private void cargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cargarActionPerformed

    private void arbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arbolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_arbolActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guardarActionPerformed

    private void informacionbanderaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informacionbanderaActionPerformed
        JOptionPane.showMessageDialog(null, "Con este botón podra colocar y quitar banderas al tablero", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_informacionbanderaActionPerformed

    private void informacionpalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informacionpalaActionPerformed
        JOptionPane.showMessageDialog(null, "Con este botón podra barrer las casillas del tablero", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_informacionpalaActionPerformed

    private void informacionarbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informacionarbolActionPerformed
        JOptionPane.showMessageDialog(null, "Con este botón podra ver el árbol de recorrido", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_informacionarbolActionPerformed

    /**
     * Maneja el evento de click en el botón "Crear". Llama al método
     * {@link #iniciarJuego()} para iniciar el juego.
     *
     * @param evt El evento de acción.
     */
    /**
     * Maneja el evento de click en el botón de encendido/apagado de la música.
     * Cambia el icono y el texto del botón, y detiene o inicia la reproducción
     * de la música según corresponda.
     *
     * @param evt El evento de acción.
     */
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton arbol;
    private javax.swing.JButton bandera;
    private javax.swing.JLabel banderas;
    private javax.swing.JRadioButton bfs;
    private javax.swing.JButton bienvenida;
    private javax.swing.JTextField c;
    private javax.swing.JButton cargar;
    private javax.swing.JButton crear;
    private javax.swing.JRadioButton dfs;
    private javax.swing.JTextField f;
    private javax.swing.JButton guardar;
    private javax.swing.JButton informacionarbol;
    private javax.swing.JButton informacionbandera;
    private javax.swing.JButton informacionpala;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField m;
    private javax.swing.JButton on;
    private javax.swing.JButton pala;
    private javax.swing.JPanel panelDerecha;
    private javax.swing.JPanel panelInformativo;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPanel panelTablero;
    // End of variables declaration//GEN-END:variables
}
