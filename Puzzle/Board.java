import shapes.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Write a description of class Board here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Board{
    private int height;
    private int width;
    private Tile[][] tablero;
    private Tile[][] tableroFinal;
    private static final int startX = 50;
    private static final int startY = 50;
    private static final int spacing = 5;
    private boolean[][] holes;
    private static final int borderThickness = 10;
    
    /**
     * Constructor del tablero
     * Crea una matriz de objetos de tamaño height * width
     * @param height altura del tablero
     * @param width abchura del tablero
     */
    public Board(int height, int width) throws PuzzleException{
        if (height <= 0 || width <= 0 || height > 50 || width > 50) {
            throw new PuzzleException(PuzzleException.WRONG_DIMENSIONS);
        }
        holes = new boolean[width][height];
        this.height = height;
        this.width =width;
        tablero = new Tile[height][width];  
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                tablero[row][col] = null;
            }
        }
        createBorders();
    }   
    
    /**
     * Constructor del tablero
     * Crea una matriz de objetos de tamaño height * width
     * @param height altura del tablero
     * @param width abchura del tablero
     */
    public Board(int height, int width, char[][] matriz) throws PuzzleException{
        this(height, width);
        if(!check(matriz)){
            throw new PuzzleException(PuzzleException.INVALID_CHARS);
        }
        tableroFinal = new Tile[height][width];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                char symbol = matriz[row][col];
                if (symbol != '.') {
                    tablero[row][col] = new Tile(symbol, calculateX(col), calculateY(row));
                    tablero[row][col].makeVisible();
                }
            }
        }
    }
    
    /**
     * Constructor del tablero
     * Crea una matriz de objetos de tamaño height * width
     * @param height altura del tablero
     * @param width abchura del tablero
     */
    public Board(int height, int width, char[][] starting, char[][] ending) throws PuzzleException{
        this(height, width, starting);
        if(!check(ending)){
            throw new PuzzleException(PuzzleException.INVALID_CHARS);
        }
        if (ending.length != height || ending[0].length != width) {
            throw new PuzzleException(PuzzleException.WRONG_DIMENSIONS);
        }
        int offsetX = 250;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                char symbol = ending[row][col];
                if (symbol != '.') {
                    tableroFinal[row][col] = new Tile(symbol, calculateX(col) + offsetX, calculateY(row));
                    tableroFinal[row][col].makeVisible();
                }
            }
        }
        createBorders(offsetX);
    }    

    /**
     * Add one Tile 
     * 
     * @param Recibe una fila, columna y color.
     * 
     */
    public void addTile(int row, int column, String color) throws PuzzleException {
        if (tablero[row][column] != null) {
            throw new PuzzleException(PuzzleException.POSITION_OCCUPIED);
        }
        if (row < 0 || row >= tablero.length || column < 0 || column >= tablero[0].length ){
            throw new PuzzleException(PuzzleException.WRONG_POSITION);
        }
        char symbol = chooseSymbol(color);
        tablero[row][column] = new Tile(symbol, calculateX(column), calculateY(row));
        tablero[row][column].makeVisible(); 
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
        int oRow = origen[0], oCol = origen[1];
        int dRow = destino[0], dCol = destino[1];
        if (tablero[oRow][oCol] == null || tablero[dRow][dCol] != null) {
            throw new PuzzleException(PuzzleException.REUBICATION_ERROR);
        }
        Tile ficha = tablero[oRow][oCol];
        tablero[oRow][oCol] = null;
        tablero[dRow][dCol] = ficha;
        ficha.moveTo(calculateX(dCol), calculateY(dRow));
    }
    
    /**
     * Delete a Tile 
     * 
     * @param Recibe una fila y una columna.
     */
    public void deleteTile(int row, int column) throws PuzzleException{
        if (tablero[row][column] == null){
            throw new PuzzleException(PuzzleException.NO_TILE);
        }
        tablero[row][column].makeInvisible();
        tablero[row][column] = null;
    }
    
    /**
     * Devuelve matriz con las letras.
     */
    public char[][] actualBoard() throws PuzzleException {
        char[][] matriz = new char[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tablero[row][col] == null) {
                    matriz[row][col] = '.';
                } else {
                    String color = tablero[row][col].getColor();
                    matriz[row][col] = chooseSymbol(color);
                }
            }
        }
        return matriz;
    }
    
    /**
     * Muestra el rompecabezas 
     */
    public void makeVisible() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tablero[row][col] != null) {
                    tablero[row][col].makeVisible();
                }
            }
        }
    }
    
    /**
     * Oculta el rompecabezas
     */
    public void makeInvisible(){
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tablero[row][col] != null) {
                    tablero[row][col].makeInvisible();
                }
            }
        }
    }
    
    /**
     * Permite agujerear una celda vacia 
     */
    public void makeHole(int row, int column) throws PuzzleException {
        if (tablero[row][column] != null) {
            throw new PuzzleException(PuzzleException.HOLE_ERROR);
        } 
        holes[row][column] = true;
        drawHole(row, column);
    }
    
    public void caer(int row, int column) {
        if (holes[row][column]) {
            tablero[row][column].makeInvisible();
            tablero[row][column] = null;
        }
    }
    
        /**
     * get de tablero
     * @return tablero
     */
    public Tile[][] getTablero() {
        return tablero;
    }

    /**
     * set de tablero
     * @param tablero
     */
    public void setTablero(Tile[][] tablero) {
        this.tablero= tablero;
    }
    
    public void updateVisualBoard() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tablero[row][col] != null) {
                    int newX = calculateX(col);
                    int newY = calculateY(row);
                    tablero[row][col].moveTo(newX, newY);
                    caer(row, col);
                }
            }
        }
    }
    
    /**
     * Intercambia tableros de referencia y edición 
     */
    public void exchange() {
        Tile[][] temporal = tablero;
        tablero = tableroFinal;
        tableroFinal = temporal;
        int offsetX = 250; 
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (tablero[row][col] != null) {
                    tablero[row][col].moveTo(calculateX(col), calculateY(row));
                    tablero[row][col].makeVisible();
                }
                if (tableroFinal[row][col] != null) {
                    tableroFinal[row][col].moveTo(calculateX(col) + offsetX, calculateY(row));
                    tableroFinal[row][col].makeVisible();
                }
            }
        }
    }
    
    /**
     * Consulta el número de baldosas que faltan por ubicar
     */
    public int misplacedTiles(){
    int countMisplaced = 0;
    for (int row = 0; row < 4; row++){
        for (int column = 0; column < 4; column++){
            Tile tileInitial = tablero[row][column];
            Tile tileFinal = tableroFinal[row][column];
            if (tileInitial == null && tileFinal != null) {
                countMisplaced++;
            }
            else if (tileInitial != null 
            && tileFinal != null 
            && !tileInitial.getColor().equals(tileFinal.getColor())) {
                countMisplaced++;
            }
        }
    }
    return countMisplaced;
    }
    
    public void addGlue(int row, int column) {
        if (tablero[row][column] != null && !tablero[row][column].isGlued()) {
            tablero[row][column].setMasterGlued(true);
            if (row > 0 && tablero[row - 1][column] != null) {
                tablero[row - 1][column].setGlued(true);
            }
            if (row < tablero.length - 1 && tablero[row + 1][column] != null) {
                tablero[row + 1][column].setGlued(true);
            }
            if (column > 0 && tablero[row][column - 1] != null) {
                tablero[row][column - 1].setGlued(true);
            }
            if (column < tablero[0].length - 1 && tablero[row][column + 1] != null) {
                tablero[row][column + 1].setGlued(true);
            }
        }
    }


    
        /**
     * Metodo auxiliar de makeHole
     */
    private void disappear(int row, int column) {
        if (holes[row][column]) {
            tablero[row][column].makeInvisible();
            tablero[row][column] = null;
        }
    }
    
    /**
     * Metodo auxiliar de makeHole
     */
    private void drawHole(int row, int column) {
        Rectangle vacio = new Rectangle();
        vacio.changeColor("gray");
        vacio.changeSize(50, 50);
        vacio.moveTo(calculateX(column), calculateY(row));
    }
    
    
        /**
     * Calcula x 
     */
    private int calculateX(int col) {
        return startX + col * (Tile.getSize() + spacing);
    }

    
    /**
     * Calcula y
     */
    private int calculateY(int row) {
        return startY + row * (Tile.getSize() + spacing);
    }
    
        /**
     * Devuelve la letra de cada color 
     */
    private char chooseSymbol(String color) throws PuzzleException {
        switch (color) {
            case "gray": return 'h';
            case "magenta": return 'm';
            case "red": return 'r';
            case "green": return 'g';
            case "blue": return 'b';
            case "yellow": return 'y';
            default:
            throw new PuzzleException(PuzzleException.INVALID_COLOR);
        }
    }
    
    private boolean check(char[][] matrixChar){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrixChar[i][j] != 'r' && matrixChar[i][j] != 'y' && matrixChar[i][j] != 'b' && matrixChar[i][j] != 'g' && matrixChar[i][j] != '.'){
                    return false;
                }
            }
        }
        return true;
    }
    
    private void createBorders() {
        int boardWidth = width * (Tile.getSize() + spacing);
        int boardHeight = height * (Tile.getSize() + spacing);

        Rectangle topBorder = new Rectangle();
        topBorder.changeColor("black");
        topBorder.changeSize(borderThickness, boardWidth + 2 * borderThickness);
        topBorder.moveTo(startX - borderThickness, startY - borderThickness);
        topBorder.makeVisible();

        Rectangle bottomBorder = new Rectangle();
        bottomBorder.changeColor("black");
        bottomBorder.changeSize(borderThickness, boardWidth + 2 * borderThickness);
        bottomBorder.moveTo(startX - borderThickness, startY + boardHeight);
        bottomBorder.makeVisible();

        Rectangle leftBorder = new Rectangle();
        leftBorder.changeColor("black");
        leftBorder.changeSize(boardHeight, borderThickness);
        leftBorder.moveTo(startX - borderThickness, startY);
        leftBorder.makeVisible();

        Rectangle rightBorder = new Rectangle();
        rightBorder.changeColor("black");
        rightBorder.changeSize(boardHeight, borderThickness);
        rightBorder.moveTo(startX + boardWidth, startY);
        rightBorder.makeVisible();
    }
    
    private void createBorders(int offset) {
        int boardWidth = width * (Tile.getSize() + spacing);
        int boardHeight = height * (Tile.getSize() + spacing);

        Rectangle topBorder = new Rectangle();
        topBorder.changeColor("black");
        topBorder.changeSize(borderThickness, boardWidth + 2 * borderThickness);
        topBorder.moveTo(startX - borderThickness + offset, startY - borderThickness);
        topBorder.makeVisible();

        Rectangle bottomBorder = new Rectangle();
        bottomBorder.changeColor("black");
        bottomBorder.changeSize(borderThickness, boardWidth + 2 * borderThickness);
        bottomBorder.moveTo(startX - borderThickness + offset, startY + boardHeight);
        bottomBorder.makeVisible();

        Rectangle leftBorder = new Rectangle();
        leftBorder.changeColor("black");
        leftBorder.changeSize(boardHeight, borderThickness);
        leftBorder.moveTo(startX - borderThickness + offset, startY);
        leftBorder.makeVisible();

        Rectangle rightBorder = new Rectangle();
        rightBorder.changeColor("black");
        rightBorder.changeSize(boardHeight, borderThickness);
        rightBorder.moveTo(startX + boardWidth + offset, startY);
        rightBorder.makeVisible();
    }
}
