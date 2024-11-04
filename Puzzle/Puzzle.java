import shapes.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Simulador de Tilting Tiles
 * 
 * @author ALexandra Moreno & Juan José Díaz
 * @version POOB 2024-2
 */
public class Puzzle {
    private Board grid;
    private Board finalGrid; 
    private int width;
    private int height;

    /**
     * Constructor que crea un rompecabezas vacío de dimensiones width x height
     *
     * @param width  Número de columnas del rompecabezas.
     * @param height Número de filas del rompecabezas.
     */
    public Puzzle(int h, int w) throws PuzzleException{
        grid = new Board(h, w);
    }
    
    /**
     * Constructor del Puzzle final
     *
     * @param Recibe una matriz de caracteres individuales.
     *                  'r', 'g', 'b', 'y', '.'
     *                  Ejemplo:
     *                  {{'.', 'r', '.', '.'},{'r', 'g', 'y', 'b'},{'.', 'b', '.', '.'},{'.', 'y', 'r', '.'}}
     */
    public Puzzle(char[][] ending) throws PuzzleException{
        this.height = ending.length;
        this.width = ending[0].length;
        finalGrid = new Board(height, width, ending);
    }
    
    /**
     * Constructor de Puzzle inicial y final
     *
     * @param Recibe una matriz de caracteres individuales.
     *                  'r', 'g', 'b', 'y', '.'
     *                  Ejemplo:
     *                  {{'.', 'r', '.', '.'},{'r', 'g', 'y', 'b'},{'.', 'b', '.', '.'},{'.', 'y', 'r', '.'}}
     *                  {{'.', '.', '.', 'r'},{'r', 'g', 'y', 'b'},{'.', '.', '.', 'b'},{'.', '.', 'y', 'r'}}
     */
    public Puzzle(char[][] starting, char[][] ending) throws PuzzleException {
        this.height = ending.length;
        this.width = ending[0].length;
        grid = new Board(height, width, starting, ending);
    }

    /**
     * Add one Tile 
     * 
     * @param Recibe una fila, columna y color.
     * 
     */
    public void addTile(int row, int column, String color) throws PuzzleException {
        grid.addTile(row, column, color);
    }

    /**
     * Relocate a Tile
     *
     * @param Recibe dos arreglos, uno de origen y otro de destino.
     *                  Ejemplo:
     *                  Origen: {1,1}
     *                  Destino: {0,0}
     */
    public void relocateTile(int[] origen, int[] destino) throws PuzzleException {
        grid.relocateTile(origen, destino);
    }
    
    
    /**
     * Delete a Tile 
     * 
     * @param Recibe una fila y una columna.
     */
    public void deleteTile(int row, int column) throws PuzzleException{
        grid.deleteTile(row, column);
    }
    
    /**
     * Devuelve matriz con las letras.
     */
    public char[][] actualArrangement() throws PuzzleException {
        return grid.actualBoard();
    }
    
    /**
     * Ladea el tablero en la dirección indicada.
     *
     * @param direction La dirección en la que se debe ladear el tablero. 
     *                  'r' Derecha.
     *                  'l' Izquierda.
     *                  'd' Abajo.
     *                  'u' Arriba.
     */
    public void tilt(char direction) throws PuzzleException {
        Movement m = new Movement(width,height);
        grid.setTablero(m.tilt(grid.getTablero(), direction));
        grid.updateVisualBoard();
    }
    
    public void addGlue(int row, int column){
        grid.addGlue(row, column);
    }

    /**
     * Muestra el rompecabezas 
     */
    public void makeVisible() {
        grid.makeVisible();
    }
    
    /**
     * Oculta el rompecabezas 
     */
    public void makeInvisible() {
        grid.makeInvisible();
    }
    
    /**
     * Permite agujerear una celda vacia 
     */
    public void makeHole(int row, int column) throws PuzzleException {
        grid.makeHole(row, column);
    }
        
    /**
     * Intercambia tableros de referencia y edición 
     */
    public void exchange() {
        grid.exchange();
    }
    
    /**
     * Consulta el número de baldosas que faltan por ubicar
     */
    public int misplacedTiles(){
        return grid.misplacedTiles();
    }
    
    /**
     * Cierra el simulador
     */
    public void finish(){
        System.exit(0);
    }

}
