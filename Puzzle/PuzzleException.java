/**
 * Clase de excepciones para el Puzzle
 * 
 * @author Alexandra Moreno, Juan José Díaz
 * @version 2024-2
 */
public class PuzzleException extends Exception {
    public static final String INVALID_CHARS = "La matriz no es valida";
    public static final String WRONG_DIMENSIONS = "Solo se permiten valores entre 1 y 50.";
    public static final String POSITION_OCCUPIED = "Ya hay una ficha en esa posición.";
    public static final String WRONG_POSITION = "Posición incorrecta";
    public static final String INVALID_COLOR ="Color no válido: ";
    public static final String IMPOSSIBLE ="No es posible resolver el puzzle.";
    public static final String WRONG_DIRECTION = "Direccion incorrecta. Validas: 'r', 'l', 'u', o 'd'.' ";
    public static final String NO_TILE ="No hay ficha en esta posición.";
    public static final String REUBICATION_ERROR = "Error en la reubicación.";
    public static final String HOLE_ERROR = "Solo puede agujerear celdas vacias.";
    
    /**
     * Imprime un mensaje
     * @param String mensaje
     */
    public PuzzleException(String mensaje) {
        super(mensaje);
    }
}