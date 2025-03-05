package Jframes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import EstructurasDeDatos.*;
import Clases.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.graphstream.graph.*;

import javax.swing.JOptionPane;

/**
 *
 * @author Luis, Zadkiel Avendano
 */
/**
 * Ventana2 es una clase que representa la ventana del juego MetroBuscaminas.
 * Esta ventana incluye la lógica del juego, como la creación del tablero, la
 * colocación de banderas, la interacción con las casillas y la gestión de
 * eventos relacionados con el juego.
 */
public class Ventana2 extends javax.swing.JFrame {

    /**
     * Matriz de botones que representa el tablero del juego.
     */
    private JButton[][] botonesTablero;

    /**
     * Instancia del tablero del Buscaminas.
     */
    Tablero tableroBuscaminas;

    /**
     * Indica si se debe colocar una bandera en la casilla seleccionada.
     */
    boolean colocarbandera;

    /**
     * Indica si se debe usar la pala para barrer la casilla seleccionada.
     */
    boolean colocarpala;

    /**
     * Indica si el juego ha terminado.
     */
    boolean juegoTerminado;

    /**
     * Almacena la casilla seleccionada por el usuario.
     */
    String casillaSeleccionada;

    /**
     * Nodo inicial para el recorrido del árbol.
     */
    Node startNode;

    /**
     * Contador para gestionar el número de banderas colocadas.
     */
    int contador = 0;

    /**
     * Referencia a la ventana principal del juego (Ventana1).
     */
    public static Ventana1 v1;

    /**
     * Instancia de la clase Funciones para reutilizar métodos comunes.
     */
    Funciones funciones = new Funciones();

    /**
     * Constructor de la clase Ventana2. Inicializa los componentes de la
     * ventana, establece el título, la posición y configura los botones e
     * imágenes.
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
        funciones.colocarImagen("/Imagenes/ON.png", on);
        funciones.colocarImagen("/Imagenes/pala.png", pala);
        funciones.colocarImagen("/Imagenes/bandera.png", bandera);
        funciones.colocarImagen("/Imagenes/arbol.png", arbol);
        funciones.colocarImagen("/Imagenes/guardar.png", guardar);
        funciones.colocarImagen("/Imagenes/cargar.png", cargar);
        funciones.colocarImagen("/Imagenes/crear.png", crear);
        funciones.colocarImagen("/Imagenes/bienvenida.png", bienvenida);
        bienvenida.setBackground(new Color(0, 0, 0, 0));

        f.setText("3");
        c.setText("3");
        m.setText("3");
        panelDerecha.setVisible(false);

    }

    /**
     * Obtiene la casilla seleccionada por el usuario.
     *
     * @return La casilla seleccionada.
     */
    public String getCasillaSeleccionada() {
        return casillaSeleccionada;
    }

    /**
     * Establece la casilla seleccionada por el usuario.
     *
     * @param casillaSeleccionada La casilla seleccionada.
     */
    public void setCasillaSeleccionada(String casillaSeleccionada) {
        this.casillaSeleccionada = casillaSeleccionada;
    }

    /**
     * Elimina los botones del tablero cuando el juego termina.
     */
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
     * Inicia un nuevo juego. Lee los valores de filas, columnas y minas de los
     * campos de texto, valida que estén dentro de los rangos permitidos y llama
     * al método {@link #cargarCasillas(int, int)} para crear el tablero.
     * Muestra un mensaje de error si los valores ingresados no son válidos.
     */
    private void juegoNuevo() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
            cargarCasillas(filas, columnas);
            crearTableroBuscaminas(filas, columnas, minas);
            repaint();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        GuardarPartidaItem.setEnabled(true);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }
    
    /**
     * Abre un cuadro de diálogo para cargar un archivo CSV que contiene el estado de un juego guardado.
     * Restablece los botones del menú y la interfaz de usuario, y carga el estado del juego desde el archivo.
     * 
     * @throws HeadlessException si el código está en un entorno de servidor y no hay interfaz gráfica disponible.
     * @throws NumberFormatException si los datos del archivo CSV no pueden ser convertidos a números.
     * @throws IOException si ocurre un error al leer el archivo CSV.
     */
    
    
    /**
    * Guarda el estado actual del juego en un archivo CSV seleccionado por el usuario.
    * Muestra un cuadro de diálogo para seleccionar la ubicación y el nombre del archivo.
    * Incluye la configuración del tablero y el estado de cada casilla.
    * 
    * @throws HeadlessException si el código está en un entorno de servidor y no hay interfaz gráfica disponible.
    * @throws IOException si ocurre un error al guardar el archivo CSV.
    */
    private void guardarJuego(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filtro);

        int seleccion = fileChooser.showSaveDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(archivo)) {
                // Guardar configuración
                pw.println(tableroBuscaminas.getGrafo().numFilas + ","
                        + tableroBuscaminas.getGrafo().numColumnas + ","
                        + tableroBuscaminas.getGrafo().numMinas);

                // Guardar casillas
                for (int i = 0; i < tableroBuscaminas.getGrafo().casillas.length; i++) {
                    for (int j = 0; j < tableroBuscaminas.getGrafo().casillas[i].length; j++) {
                        Casilla casilla = tableroBuscaminas.getGrafo().casillas[i][j];
                        pw.println(j + "," + i + ","
                                + casilla.isMina() + ","
                                + casilla.isBandera() + ","
                                + casilla.isAbierta() + ","
                                + casilla.getNumMinasAlrededor());
                    }
                }
                JOptionPane.showMessageDialog(this, "Partida guardada exitosamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void juegoCargado(){
        
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fileChooser.setFileFilter(filtro);

        int seleccion = fileChooser.showOpenDialog(this);
        contador = 0; // Reiniciar contador

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // restablece los botones del menu
            juegoTerminado = false;
            pala.setEnabled(true);
            bandera.setEnabled(true);

            File archivo = fileChooser.getSelectedFile();

            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                // Leer configuración inicial
                String[] datosTablero = br.readLine().split(",");
                int filas = Integer.parseInt(datosTablero[0]);
                int columnas = Integer.parseInt(datosTablero[1]);
                int minas = Integer.parseInt(datosTablero[2]);

                // Inicializar tablero
                Casilla[][] tableroCargado = new Casilla[filas][columnas];
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    int columna = Integer.parseInt(datos[0]);
                    int fila = Integer.parseInt(datos[1]);
                    boolean esMina = Boolean.parseBoolean(datos[2]);
                    boolean esBandera = Boolean.parseBoolean(datos[3]);
                    boolean esAbierta = Boolean.parseBoolean(datos[4]);
                    int numMinas = Integer.parseInt(datos[5]);

                    Casilla casilla = new Casilla(fila, columna);
                    casilla.setMina(esMina);
                    casilla.setBandera(esBandera);
                    casilla.setAbierta(esAbierta);
                    casilla.setNumMinasAlrededor(numMinas);
                    tableroCargado[fila][columna] = casilla;

                    if (esBandera) {
                        contador++;
                    }
                }

                // Actualizar interfaz
                panelDerecha.setVisible(true);
                panelInformativo.setVisible(false);
                descargarControles();
                cargarCasillas(filas, columnas);
                cargarTableroBuscaminas(filas, columnas, minas, tableroCargado);
                banderas.setText("Banderas: " + (minas - contador));
                repaint();

            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            GuardarPartidaItem.setEnabled(true);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * Configura los eventos del grafo para manejar la lógica del juego, como la
     * partida perdida, ganada, la colocación de banderas y la apertura de
     * casillas.
     */
    private void configurarEventosGrafo() {

        tableroBuscaminas.getGrafo().setEventoPartidaPerdida((ListaAdyacencia t) -> {
            NodoAdyacencia recorrer = t.cabeza;
            while (recorrer != null) {
                int i = recorrer.valor.getPosFila();
                int j = recorrer.valor.getPosColumna();
                botonesTablero[i][j].setIcon(null);
                funciones.colocarImagen("/Imagenes/Bomba.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBackground(new Color(139, 69, 19));
                recorrer = recorrer.siguiente;
            }
            colocarbandera = false;
            colocarpala = false;
            juegoTerminado = true;
            pala.setEnabled(false);
            bandera.setEnabled(false);
            funciones.colocarImagen(null, SeleccionadoButton);
            JOptionPane.showMessageDialog(null, "PISASTE UNA MINA", "PERDISTE!!!", JOptionPane.INFORMATION_MESSAGE);
        });

        tableroBuscaminas.getGrafo().setEventoPartidaGanada((ListaAdyacencia t) -> {
            NodoAdyacencia recorrer = t.cabeza;
            while (recorrer != null) {
                int i = recorrer.valor.getPosFila();
                int j = recorrer.valor.getPosColumna();
                botonesTablero[i][j].setIcon(null);
                funciones.colocarImagen("/Imagenes/Bomba.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBackground(new Color(139, 69, 19));
                recorrer = recorrer.siguiente;
            }
            colocarbandera = false;
            colocarpala = false;
            juegoTerminado = true;
            pala.setEnabled(false);
            bandera.setEnabled(false);
            funciones.colocarImagen(null, SeleccionadoButton);
            JOptionPane.showMessageDialog(null, "PARTIDA GANADA", "GANASTE!!!", JOptionPane.INFORMATION_MESSAGE);
        });

        tableroBuscaminas.getGrafo().setEventoBanderaAbierta((Casilla t) -> {
            int i = t.getPosFila();
            int j = t.getPosColumna();
            if (tableroBuscaminas.getGrafo().numeroBanderas <= 0) {
                JOptionPane.showMessageDialog(null, "No hay más banderas para colocar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                funciones.colocarImagen("/Imagenes/banderaCasilla.png", botonesTablero[i][j]);
                tableroBuscaminas.getGrafo().numeroBanderas--;
                banderas.setText("Banderas: " + tableroBuscaminas.getGrafo().numeroBanderas);
            }
        });

        tableroBuscaminas.getGrafo().setEventoBanderaCerrada((Casilla t) -> {
            int i = t.getPosFila();
            int j = t.getPosColumna();
            if (tableroBuscaminas.getGrafo().numeroBanderas >= tableroBuscaminas.getGrafo().numMinas) {
                JOptionPane.showMessageDialog(null, "No hay banderas en el tablero para quitar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                funciones.colocarImagen("/Imagenes/CasillaBloqueada.png", botonesTablero[i][j]);
                tableroBuscaminas.getGrafo().numeroBanderas++;
                banderas.setText("Banderas: " + tableroBuscaminas.getGrafo().numeroBanderas);
            }
        });

        tableroBuscaminas.getGrafo().setEventoCasillaAbierta((Casilla t) -> {
            int i = t.getPosFila();
            int j = t.getPosColumna();
            int k = t.getNumMinasAlrededor();
            Border line = BorderFactory.createLineBorder(Color.GRAY, 2);
            botonesTablero[i][j].setEnabled(false);
            botonesTablero[i][j].setText(k == 0 ? "" : k + "");
            botonesTablero[i][j].setIcon(null);

            if (botonesTablero[i][j].getText().equals(Integer.toString(1)) && !t.isMina()) {
                botonesTablero[i][j].setText("");
                funciones.colocarImagen("/Imagenes/uno.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBorder(line);
            } else if (botonesTablero[i][j].getText().equals(Integer.toString(2)) && !t.isMina()) {
                botonesTablero[i][j].setText("");
                funciones.colocarImagen("/Imagenes/dos.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBorder(line);
            } else if (botonesTablero[i][j].getText().equals(Integer.toString(3)) && !t.isMina()) {
                botonesTablero[i][j].setText("");
                funciones.colocarImagen("/Imagenes/tres.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBorder(line);
            } else if (botonesTablero[i][j].getText().equals(Integer.toString(4)) && !t.isMina()) {
                botonesTablero[i][j].setText("");
                funciones.colocarImagen("/Imagenes/cuatro.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBorder(line);
            } else {

                botonesTablero[i][j].setText("");
                funciones.colocarImagen("/Imagenes/CasillaDesbloqueada.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBorder(line);
            }

        });

    }

    /**
     * Crea un nuevo tablero de Buscaminas con las dimensiones y número de minas
     * especificados.
     *
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     * @param minas Número de minas en el tablero.
     */
    private void crearTableroBuscaminas(int filas, int columnas, int minas) {
        tableroBuscaminas = new Tablero(filas, columnas, minas);

        configurarEventosGrafo();
    }

    /**
     * Carga las casillas del tablero en el panel. Crea una matriz de botones
     * con las dimensiones especificadas, establece su posición y tamaño, agrega
     * un ActionListener a cada botón y los añade al panel.
     *
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     */
    public void cargarCasillas(int filas, int columnas) {

        int posXreferencia = 25;
        int posYreferencia = 25;
        int anchoCasilla = 40;
        int altoCasilla = 40;
        Border line = BorderFactory.createLineBorder(Color.GRAY, 2);
        panelTablero.removeAll();

        botonesTablero = new JButton[filas][columnas];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new JButton();
                JButton boton = botonesTablero[i][j] = new JButton();
                boton.setName(i + "," + j);

                boton.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        String[] coordenadas = boton.getName().split(",");
                        int fila = Integer.parseInt(coordenadas[0]) + 1;
                        int columna = Integer.parseInt(coordenadas[1]) + 1;
                        char columnaLetra = (char) ('A' + columna - 1);
                        casillaText.setText("Casilla: " + columnaLetra + fila);
                        repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        casillaText.setText("");
                    }
                });

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
                funciones.colocarImagen("/Imagenes/CasillaBloqueada.png", botonesTablero[i][j]);
                botonesTablero[i][j].setBorder(line);
            }
        }
        panelTablero.revalidate();
        panelTablero.repaint();
    }

    /**
     * Maneja el evento de clic en una casilla del tablero. Obtiene la fila y
     * columna del botón clickeado, muestra un mensaje con las coordenadas y
     * cambia el icono del botón.
     *
     * @param e El evento de acción.
     */
    public void btnClick(MouseEvent e) {

        JButton btn = (JButton) e.getSource();
        //System.out.println(btn.getName());

        String[] coordenada = btn.getName().split(",");
        setCasillaSeleccionada(btn.getName());
        int fila = Integer.parseInt(coordenada[0]);
        int columna = Integer.parseInt(coordenada[1]);

        if (colocarbandera == true) {

            tableroBuscaminas.getGrafo().seleccionarBandera(fila, columna);

        } else if (colocarbandera == false && colocarpala == true) {
            if (botondfs.isSelected()) {
                tableroBuscaminas.getGrafo().setRecorridoDFS(true);
                tableroBuscaminas.getGrafo().setRecorridoBFS(false);
                tableroBuscaminas.getGrafo().seleccionarCasilla(fila, columna);
            } else if (botonbfs.isSelected()) {
                tableroBuscaminas.getGrafo().setRecorridoBFS(true);
                tableroBuscaminas.getGrafo().setRecorridoDFS(false);
                tableroBuscaminas.getGrafo().seleccionarCasilla(fila, columna);
            }

        } else if (juegoTerminado == true) {
            JOptionPane.showMessageDialog(null, "El juego termino, no puede seleccionar más casillas", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Porfavor seleccione la pala si desea barrer una casilla", "Error", JOptionPane.ERROR_MESSAGE);
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

        GrupoBotones = new javax.swing.ButtonGroup();
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
        SeleccionadoText = new javax.swing.JLabel();
        SeleccionadoButton = new javax.swing.JButton();
        casillaText = new javax.swing.JLabel();
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
        botondfs = new javax.swing.JRadioButton();
        botonbfs = new javax.swing.JRadioButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        NuevaPartidaItem = new javax.swing.JMenuItem();
        GuardarPartidaItem = new javax.swing.JMenuItem();
        CargarPartidaItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ComoJugarItem = new javax.swing.JMenuItem();
        CreditosItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelInformativo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        bienvenida.setBorderPainted(false);
        bienvenida.setEnabled(false);
        bienvenida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bienvenidaActionPerformed(evt);
            }
        });
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

        bandera.setToolTipText("Seleccionar bandera");
        bandera.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bandera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                banderaActionPerformed(evt);
            }
        });
        panelDerecha.add(bandera, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 70, 70));

        pala.setToolTipText("Seleccionar pala");
        pala.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palaActionPerformed(evt);
            }
        });
        panelDerecha.add(pala, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 70, 70));

        banderas.setText("Banderas: ");
        panelDerecha.add(banderas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 100, 20));

        cargar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarActionPerformed(evt);
            }
        });
        panelDerecha.add(cargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 70, 70));

        arbol.setToolTipText("Ver arbol de recorrido");
        arbol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        arbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arbolActionPerformed(evt);
            }
        });
        panelDerecha.add(arbol, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 70, 70));

        guardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        panelDerecha.add(informacionbandera, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 20, 20));

        informacionarbol.setText("...");
        informacionarbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informacionarbolActionPerformed(evt);
            }
        });
        panelDerecha.add(informacionarbol, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 20, 20));

        informacionpala.setText("...");
        informacionpala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informacionpalaActionPerformed(evt);
            }
        });
        panelDerecha.add(informacionpala, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 20, 20));

        SeleccionadoText.setText("Seleccionado:");
        panelDerecha.add(SeleccionadoText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, -1));

        SeleccionadoButton.setEnabled(false);
        SeleccionadoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeleccionadoButtonActionPerformed(evt);
            }
        });
        panelDerecha.add(SeleccionadoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 25, 25));

        casillaText.setText("Casilla");
        panelDerecha.add(casillaText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 90, 20));

        getContentPane().add(panelDerecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 190, 490));

        panelSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelSuperior.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 107, 660, -1));

        on.setText("Music ON");
        on.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        crear.setToolTipText("Nuevo juego");
        crear.setContentAreaFilled(false);
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

        GrupoBotones.add(botondfs);
        botondfs.setSelected(true);
        botondfs.setText("Depth-First Search");
        botondfs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botondfsActionPerformed(evt);
            }
        });
        panelSuperior.add(botondfs, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        GrupoBotones.add(botonbfs);
        botonbfs.setText("Breadth-First Search");
        botonbfs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonbfsActionPerformed(evt);
            }
        });
        panelSuperior.add(botonbfs, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, -1, -1));

        getContentPane().add(panelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 110));

        jMenu1.setText("Opciones");

        NuevaPartidaItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        NuevaPartidaItem.setText("Nueva Partida");
        NuevaPartidaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaPartidaItemActionPerformed(evt);
            }
        });
        jMenu1.add(NuevaPartidaItem);

        GuardarPartidaItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        GuardarPartidaItem.setText("Guardar Partida");
        GuardarPartidaItem.setEnabled(false);
        GuardarPartidaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarPartidaItemActionPerformed(evt);
            }
        });
        jMenu1.add(GuardarPartidaItem);

        CargarPartidaItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        CargarPartidaItem.setText("Cargar Partida");
        CargarPartidaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CargarPartidaItemActionPerformed(evt);
            }
        });
        jMenu1.add(CargarPartidaItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        ComoJugarItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        ComoJugarItem.setText("Como jugar");
        ComoJugarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComoJugarItemActionPerformed(evt);
            }
        });
        jMenu2.add(ComoJugarItem);

        CreditosItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        CreditosItem.setText("Creditos");
        CreditosItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreditosItemActionPerformed(evt);
            }
        });
        jMenu2.add(CreditosItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método que se ejecuta al hacer clic en el botón de música. Controla la
     * reproducción y detención de la música, así como el cambio de imagen y
     * texto del botón.
     *
     * @param evt Evento de acción generado al hacer clic en el botón.
     */
    private void onActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onActionPerformed

        if ("Music ON".equals(on.getText())) {

            funciones.colocarImagen("/Imagenes/OFF.png", on);

            on.setText("Music OFF");
            if (Ventana1.clip != null && Ventana1.clip.isRunning()) {
                Ventana1.clip.stop();
            }

        } else if ("Music OFF".equals(on.getText())) {
            funciones.colocarImagen("/Imagenes/ON.png", on);

            on.setText("Music ON");
            // Play Audio  File
            try {
                URL audioPath = getClass().getClassLoader().getResource("Musica/song.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioPath);
                Ventana1.clip = AudioSystem.getClip();
                Ventana1.clip.open(audioStream);
                Ventana1.clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            }

        }
    }//GEN-LAST:event_onActionPerformed

    private void cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cActionPerformed

    private void mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "crear". Este botón
     * permite crear un nuevo tablero de juego, reiniciando el estado del juego.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed

        juegoTerminado = false;
        pala.setEnabled(true);
        bandera.setEnabled(true);
        juegoNuevo();


    }//GEN-LAST:event_crearActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "pala". Este botón
     * permite seleccionar la herramienta "pala" para interactuar con el
     * tablero.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void palaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palaActionPerformed

        colocarpala = true;
        colocarbandera = false;
        funciones.colocarImagen("/Imagenes/pala.png", SeleccionadoButton);

    }//GEN-LAST:event_palaActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "bandera". Este
     * botón permite seleccionar la herramienta "bandera" para interactuar con
     * el tablero.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void banderaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_banderaActionPerformed

        colocarbandera = true;
        colocarpala = false;
        funciones.colocarImagen("/Imagenes/bandera.png", SeleccionadoButton);


    }//GEN-LAST:event_banderaActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "cargar". Este
     * botón permite cargar una partida desde un archivo CSV.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void cargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarActionPerformed
        juegoCargado();
    }//GEN-LAST:event_cargarActionPerformed
    /**
     * Método que carga el tablero de Buscaminas desde un archivo CSV.
     *
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     * @param minas Número de minas en el tablero.
     * @param tableroCargado Matriz de casillas cargada desde el archivo.
     */
    private void cargarTableroBuscaminas(int filas, int columnas, int minas, Casilla[][] tableroCargado) {
        tableroBuscaminas = new Tablero(filas, columnas, minas);
        configurarEventosGrafo();

        // Cargar estado de las casillas
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = tableroCargado[i][j];
                Casilla casillaGrafo = tableroBuscaminas.getGrafo().casillas[i][j];

                casillaGrafo.setMina(casilla.isMina());
                casillaGrafo.setBandera(casilla.isBandera());
                casillaGrafo.setAbierta(casilla.isAbierta());
                casillaGrafo.setNumMinasAlrededor(casilla.getNumMinasAlrededor());

                if (casilla.isAbierta()) {
                    tableroBuscaminas.getGrafo().numCasillasAbiertas++;
                    tableroBuscaminas.getGrafo().eventoCasillaAbierta.ejecutar(casillaGrafo);
                }
                if (casilla.isBandera()) {
                    tableroBuscaminas.getGrafo().eventoBanderaAbierta.ejecutar(casillaGrafo);
                    contador++;
                }
            }
        }
        tableroBuscaminas.getGrafo().numeroBanderas = minas - contador;
        banderas.setText("Banderas: " + tableroBuscaminas.getGrafo().numeroBanderas);
        // Verificar si la partida ya está ganada
        if (tableroBuscaminas.getGrafo().PartidaGanada()) {
            tableroBuscaminas.getGrafo().eventoPartidaGanada.ejecutar(
                    tableroBuscaminas.getGrafo().obtenerCasillasConMinas()
            );
        }
    }

    /**
     * Método que se ejecuta cuando se hace clic en el botón "arbol". Este botón
     * permite visualizar el árbol de recorrido (DFS o BFS) desde una casilla
     * seleccionada.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void arbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arbolActionPerformed
        funciones.colocarImagen("/Imagenes/arbol.png", SeleccionadoButton);
        if (getCasillaSeleccionada() != null) {

            tableroBuscaminas.getGrafo().empezarArbol();
            tableroBuscaminas.getGrafo().resetearArbol();
            Node startNode = tableroBuscaminas.getGrafo().graph.getNode(getCasillaSeleccionada());

            if (botondfs.isSelected()) {
                tableroBuscaminas.getGrafo().setRecorridoDFS(true);
                tableroBuscaminas.getGrafo().setRecorridoBFS(false);
            } else if (botonbfs.isSelected()) {
                tableroBuscaminas.getGrafo().setRecorridoDFS(false);
                tableroBuscaminas.getGrafo().setRecorridoBFS(true);
            }

            if (tableroBuscaminas.getGrafo().RecorridoDFS) {

                tableroBuscaminas.getGrafo().recorrerDFSArbol(startNode);
            } else if (tableroBuscaminas.getGrafo().RecorridoBFS) {

                tableroBuscaminas.getGrafo().recorrerBFSArbol(startNode);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Todavia no se ha seleccionado ninguna casilla", "Error", JOptionPane.ERROR_MESSAGE);

        }


    }//GEN-LAST:event_arbolActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "guardar". Este
     * botón permite guardar la partida actual en un archivo CSV.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        //ESTE BOTON ES PARA GUARDAR UNA PARTIDA EN UN ARCHIVO CSV
        guardarJuego();
    }//GEN-LAST:event_guardarActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón
     * "informacionbandera". Muestra un mensaje informativo sobre el uso del
     * botón "bandera".
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void informacionbanderaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informacionbanderaActionPerformed
        JOptionPane.showMessageDialog(null, "Con este botón podra colocar y quitar banderas al tablero", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_informacionbanderaActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "informacionpala".
     * Muestra un mensaje informativo sobre el uso del botón "pala".
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void informacionpalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informacionpalaActionPerformed
        JOptionPane.showMessageDialog(null, "Con este botón podra barrer las casillas del tablero", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_informacionpalaActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "informacionarbol".
     * Muestra un mensaje informativo sobre el uso del botón "arbol".
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void informacionarbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informacionarbolActionPerformed
        JOptionPane.showMessageDialog(null, "Con este botón podra ver el árbol de recorrido", "Información", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_informacionarbolActionPerformed

    private void bienvenidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bienvenidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bienvenidaActionPerformed

    private void botondfsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botondfsActionPerformed


    }//GEN-LAST:event_botondfsActionPerformed

    private void botonbfsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonbfsActionPerformed

    }//GEN-LAST:event_botonbfsActionPerformed

    private void SeleccionadoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeleccionadoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SeleccionadoButtonActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "ComoJugarItem".
     * Muestra un mensaje informativo con los integrantes del grupo.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void ComoJugarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComoJugarItemActionPerformed
    String mensaje = """
                     Descubre todas las casillas del tablero que no contienen minas y marca las casillas sospechosas con banderas para indicar la posible ubicacion de minas.
                     
                     1. Inicio del Juego:
                        - Al iniciar el juego, veras un tablero compuesto por casillas cubiertas.
                        - Seleccionando al pala y haciendo clic en una casilla, se descubrira su contenido.
                     
                     2. Casillas Vacias:
                        - Si la casilla esta vacia, se revelara el numero de minas adyacentes.
                        - Las casillas adyacentes pueden ser horizontales, verticales o diagonales.
                     
                     3. Descubrimiento Automatico:
                        - Si la casilla seleccionada no contiene minas y esta vacia, el juego revelara automaticamente las casillas adyacentes que tambien esten vacias, creando areas seguras.
                     
                     4. Casillas con Minas:
                        - Si la casilla contiene una mina, el juego terminara y habras perdido.
                     
                     5. Marcado de Casillas:
                        - Puedes marcar las casillas que sospechas contienen minas seleccionando la bandera y haciendo click a la mina.
                        - Esto colocara una bandera en la casilla seleccionada.
                     
                     6. Ganador:
                        - Ganas el juego si logras descubrir todas las casillas que no contienen minas sin detonar ninguna.
                     
                     Buena suerte y diviertete jugando Buscaminas!""";
        JOptionPane.showMessageDialog(this, mensaje);
    }//GEN-LAST:event_ComoJugarItemActionPerformed
    /**
     * Método que se ejecuta cuando se hace clic en el botón "CreditosItem".
     * Muestra un mensaje informativo con los integrantes del grupo.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void CreditosItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreditosItemActionPerformed
        JOptionPane.showMessageDialog(this, "Integrantes del grupo\nLuis Guerra y Zadkiel Avedaño");
    }//GEN-LAST:event_CreditosItemActionPerformed

     /**
     * Método que se ejecuta cuando se hace clic en el botón "cargar" o se usa el comando 'Ctrl+O'. Este
     * botón permite cargar una partida desde un archivo CSV.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void CargarPartidaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CargarPartidaItemActionPerformed
        juegoCargado();
    }//GEN-LAST:event_CargarPartidaItemActionPerformed
     /**
     * Método que se ejecuta cuando se hace clic en el botón "guardar" o se usa el comando 'Ctrl+S'. Este
     * botón permite guardar una partida en un archivo CSV.
     *
     * @param evt El evento de acción generado por el clic en el botón.
     */
    private void GuardarPartidaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarPartidaItemActionPerformed
        guardarJuego();
    }//GEN-LAST:event_GuardarPartidaItemActionPerformed

    private void NuevaPartidaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevaPartidaItemActionPerformed
        juegoTerminado = false;
        pala.setEnabled(true);
        bandera.setEnabled(true);
        juegoNuevo();
    }//GEN-LAST:event_NuevaPartidaItemActionPerformed

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
            java.util.logging.Logger.getLogger(Ventana2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Ventana2().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem CargarPartidaItem;
    private javax.swing.JMenuItem ComoJugarItem;
    private javax.swing.JMenuItem CreditosItem;
    private javax.swing.ButtonGroup GrupoBotones;
    private javax.swing.JMenuItem GuardarPartidaItem;
    private javax.swing.JMenuItem NuevaPartidaItem;
    private javax.swing.JButton SeleccionadoButton;
    private javax.swing.JLabel SeleccionadoText;
    private javax.swing.JButton arbol;
    private javax.swing.JButton bandera;
    private javax.swing.JLabel banderas;
    private javax.swing.JButton bienvenida;
    private javax.swing.JRadioButton botonbfs;
    private javax.swing.JRadioButton botondfs;
    private javax.swing.JTextField c;
    private javax.swing.JButton cargar;
    private javax.swing.JLabel casillaText;
    private javax.swing.JButton crear;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
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
