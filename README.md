# MetroBuscaminas

MetroBuscaminas es una implementación del clásico juego de lógica "Buscaminas", desarrollado en Java utilizando la biblioteca gráfica Swing. El objetivo del juego es descubrir todas las casillas del tablero que no contienen minas, marcando las casillas sospechosas con banderas.

## Funcionalidades Implementadas

- ✅ **Creación de Tableros Personalizados:** Permite al usuario especificar las dimensiones del tablero (entre 3x3 y 10x10) y la cantidad de minas a ocultar.
- 🚧 **Selección de Casillas:** El usuario puede seleccionar casillas para revelar su contenido. Si la casilla contiene una mina, el juego termina. Si está vacía, se revela el número de minas adyacentes.
- 🚧 **Barrido Automático:** Al seleccionar una casilla vacía, el juego automáticamente revela las casillas adyacentes que también estén vacías, creando áreas seguras.
- 🚧 **Marcado de Casillas:** El usuario puede marcar casillas sospechosas con banderas para indicar la posible ubicación de minas.
- 🚧 **Guardar y Cargar Juegos:** Permite guardar el estado actual del tablero en un archivo CSV y cargarlo posteriormente para continuar jugando.
- 🚧 **Interfaz Gráfica:** Interfaz de usuario desarrollada en Java Swing.

## Aspectos Técnicos

- 🚧 **Estructura de Datos:** El tablero del juego se implementa utilizando un grafo, con una lista de adyacencia para representar las relaciones entre las casillas.
- 🚧 **Generación Aleatoria de Minas:** Se utiliza la clase `Random` de Java para generar aleatoriamente la ubicación de las minas en el tablero.
- 🚧 **Algoritmos de Búsqueda:** Implementamos los algoritmos de búsqueda en amplitud (BFS) y búsqueda en profundidad (DFS) para el barrido automático de casillas vacías.
- 🚧 **Interfaz Gráfica con Swing:** La interfaz gráfica se construye utilizando la biblioteca Swing de Java, con componentes como `JFrame`, `JPanel`, `JButton`, `JLabel` y `JOptionPane`.
- 🚧 **Persistencia de Datos:** Utilizamos la clase `JFileChooser` para permitir al usuario seleccionar la ubicación y el nombre del archivo CSV al guardar y cargar el juego.
