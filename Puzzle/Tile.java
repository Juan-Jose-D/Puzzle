import shapes.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Clase encargada de las funciones de las fichas
 * 
 * @author ALexandra Moreno & Juan José Díaz
 * @version POOB 2024-2
 */

public class Tile {
    private Rectangle rectangle;
    private char symbol;
    public static final int SIZE = 50;
    private int row;
    private int column;
    private boolean glued;
    private boolean masterGlued;
    
    /**
     * Constructor de fichas
     * @param Recibe un simbolo, la posicion en x y y.
     */
    public Tile(char symbol, int row, int column) {
        this.row = row;
        this.column = column;
        this.symbol = symbol;
        this.rectangle = new Rectangle();
        rectangle.changeSize(SIZE, SIZE);
        rectangle.moveTo(row, column);
        rectangle.changeColor(chooseColor(symbol));
        rectangle.makeVisible();
    }

    /**
     * Obtener la posicion en x
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Obtener la posicion en y
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Dar la posición en y
     */
    public void setColumn(int column) {
        this.column = column;
    }
    
    /**
     * Mueve la ficha a una posicion especifica
     * @param Recibe la posicion en x y y
     */
    public void moveTo(int x, int y) {
        int deltaX = x - rectangle.getX();
        int deltaY = y - rectangle.getY();
        rectangle.moveTo(x, y);
    }

    /**
     * Hace visible la ficha 
     */
    public void makeVisible() {
        rectangle.makeVisible();
    }

    /**
     * Hace invisible la ficha 
     */
    public void makeInvisible() {
        rectangle.makeInvisible();
    }

    /**
     * Devuelve el tamaño de la ficha 
     */
    public static int getSize() {
        return SIZE;
    }

    /**
     * Devuelve el color de la ficha 
     */
    public String getColor() {
        return rectangle.getColor();
    }
    
    /**
     * Devuelve el simbolo de la ficha 
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Cambia el color de la ficha
     */
    public void changeColor(String color) {
        rectangle.changeColor(color);
    }

    /**
     * Marca una ficha como pegada y cambia el color a negro 
     */
    public void setGlued(boolean glued) {
        this.glued = glued;
        if (glued) {
            rectangle.changeColor("black");
        } else {
            rectangle.changeColor(chooseColor(symbol));
        }
    }
    
    /**
     * Marca una ficha como pegada y cambia el color a negro 
     */
    public void setMasterGlued(boolean glued) {
        this.masterGlued = glued;
        if (glued) {
            rectangle.changeColor("black");
        } else {
            rectangle.changeColor(chooseColor(symbol));
        }
    }

    /**
     * Dice si una ficha esta pegada o no 
     */
    public boolean isGlued() {
        return glued;
    }
    
    public boolean isMasterGlued() {
        return masterGlued;
    }
    
    /**
     * Asignada la letra devuelve el color
     */
    private String chooseColor(char symbol) {
        switch (symbol) {
            case 'h': return "gray";
            case 'm': return "magenta";
            case 'r': return "red";
            case 'g': return "green";
            case 'b': return "blue";
            case 'y': return "yellow";
            default: return "white";
        }
    }

    
    
}
