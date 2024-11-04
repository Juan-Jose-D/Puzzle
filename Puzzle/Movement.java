import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Clase dedicada a los movimientos
 */
public class Movement{
    private int columns;
    private  int rows;
    private Board tablero;
    private Tile[][] grid;

    /**
     * Constructor de la clase Move
     * @param w es el ancho del tablero
     * @param h es el alto del tablero
     */
    public Movement(int row, int column) {
        this.rows = row;
        this.columns = column;
    }

    /**
     * metodo que inclina el tablero hacia una direccion y las baldosas se mueven consecuentemente
     * @param grid matriz de objetos a mover
     * @param direction direccion a la que sae va a inclinar el tablero
     * @return matriz de objetos correspondiente despues de la inclinacion
     */
    public Tile[][] tilt(Tile[][] grid, char direction) throws PuzzleException{
        switch (direction) {
            case 'r':
                grid = tiltRight(grid);
                break;
            case 'l':
                grid = tiltLeft(grid);
                break;
            case 'u':
                grid = tiltUp(grid);
                break;
            case 'd':
                grid = tiltDown(grid);
                break;
            default:
                throw new PuzzleException(PuzzleException.WRONG_DIRECTION);
        }

        return grid;
    }


    /**
     * metodo que inclina el tablero hacia la derecha
     * @param matrizTablero
     * @return matriz de objetos correspondiente despues de la inclinacion
     */
    private Tile[][] tiltRight(Tile[][] matrizTablero) {
        for (int i = 0; i < rows; i++) {
            for (int j = columns - 1; j >= 0; j--) {
                if (matrizTablero[i][j] != null) {
                    if (matrizTablero[i][j].isMasterGlued()) {
                        int movimientosPermitidos = auxRight(matrizTablero);

                        for (int k = 0; k < movimientosPermitidos; k++) {
                            if (matrizTablero[i][j + 1] == null) {
                                matrizTablero[i][j + 1] = matrizTablero[i][j];
                                matrizTablero[i][j] = null;
                                j++;
                            }

                            if (i - 1 >= 0 && matrizTablero[i - 1][j] != null && matrizTablero[i - 1][j].isGlued()) {
                                matrizTablero[i - 1][j + 1] = matrizTablero[i - 1][j];
                                matrizTablero[i - 1][j] = null;
                            }

                            if (i + 1 < rows && matrizTablero[i + 1][j] != null && matrizTablero[i + 1][j].isGlued()) {
                                matrizTablero[i + 1][j + 1] = matrizTablero[i + 1][j];
                                matrizTablero[i + 1][j] = null;
                            }
                        } 
                    }else if (matrizTablero[i][j].isGlued()) {
                            continue;
                    } else {
                        for (int k = j; k < columns - 1; k++) {
                            if (matrizTablero[i][k + 1] == null) {
                                matrizTablero[i][k + 1] = matrizTablero[i][k];
                                matrizTablero[i][k] = null;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return matrizTablero;
    }


    private int auxRight(Tile[][] matrizTablero) {
        int minMovimientos = Integer.MAX_VALUE;
    
        for (int i = 0; i < rows; i++) {
            for (int j = columns - 1; j >= 0; j--) {
                if (matrizTablero[i][j] != null && matrizTablero[i][j].isMasterGlued()) {
                    List<Integer> numeros = new ArrayList<>();

                    if (i - 1 >= 0 && matrizTablero[i - 1][j] != null && matrizTablero[i - 1][j].isGlued()) {
                        int cont1 = contarEspaciosADerecha(i - 1, j, matrizTablero);
                        numeros.add(cont1);
                    }
    
                    if (i + 1 < rows && matrizTablero[i + 1][j] != null && matrizTablero[i + 1][j].isGlued()) {
                        int cont2 = contarEspaciosADerecha(i + 1, j, matrizTablero);
                        numeros.add(cont2);
                    }

                    if (j + 1 < columns && matrizTablero[i][j + 1] != null && matrizTablero[i][j + 1].isGlued()) {
                        int cont3 = contarEspaciosADerecha(i, j + 1, matrizTablero);
                        numeros.add(cont3);
                    }

                    if (!numeros.isEmpty()) {
                        int minMovimientoActual = numeros.stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
                        minMovimientos = Math.min(minMovimientos, minMovimientoActual);
                    }
                }
            }
        }
    
        return minMovimientos == Integer.MAX_VALUE ? 0 : minMovimientos;
    }

    private int contarEspaciosADerecha(int row, int col, Tile[][] matrizTablero) {
        int contador = 0;
        while (col + 1 < columns && matrizTablero[row][col + 1] == null) {
            contador++;
            col++;
        }
        return contador;
    }
    
    
    /**
     * metodo que inclina el tablero hacia la izquierda
     * @param grid
     * @return matriz de objetos correspondiente despues de la inclinacion
     */
    private Tile[][] tiltLeft(Tile[][] matrizTablero) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrizTablero[i][j] != null) {
                    if (matrizTablero[i][j].isMasterGlued()) {
                        int movimientosPermitidos = auxLeft(matrizTablero);
                        for (int k = 0; k < movimientosPermitidos; k++) {
                            if (matrizTablero[i][j - 1] == null) {
                                matrizTablero[i][j - 1] = matrizTablero[i][j];
                                matrizTablero[i][j] = null;
                                j--;
                            }
    
                            if (i - 1 >= 0 && matrizTablero[i - 1][j] != null && matrizTablero[i - 1][j].isGlued()) {
                                matrizTablero[i - 1][j - 1] = matrizTablero[i - 1][j];
                                matrizTablero[i - 1][j] = null;
                            }
    
                            if (i + 1 < rows && matrizTablero[i + 1][j] != null && matrizTablero[i + 1][j].isGlued()) {
                                matrizTablero[i + 1][j - 1] = matrizTablero[i + 1][j];
                                matrizTablero[i + 1][j] = null;
                            }
                        }
                    } else if (matrizTablero[i][j].isGlued()) {
                        continue;
                    } else {
                        for (int k = j; k > 0; k--) {
                            if (matrizTablero[i][k - 1] == null) {
                                matrizTablero[i][k - 1] = matrizTablero[i][k];
                                matrizTablero[i][k] = null;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return matrizTablero;
    }
    
    private int auxLeft(Tile[][] matrizTablero) {
        int minMovimientos = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrizTablero[i][j] != null && matrizTablero[i][j].isMasterGlued()) {
                    List<Integer> numeros = new ArrayList<>();
                    if (i - 1 >= 0 && matrizTablero[i - 1][j] != null && matrizTablero[i - 1][j].isGlued()) {
                        int cont1 = contarEspaciosAIzquierda(i - 1, j, matrizTablero);
                        numeros.add(cont1);
                    }
    
                    if (i + 1 < rows && matrizTablero[i + 1][j] != null && matrizTablero[i + 1][j].isGlued()) {
                        int cont2 = contarEspaciosAIzquierda(i + 1, j, matrizTablero);
                        numeros.add(cont2);
                    }
    
                    if (j - 1 >= 0 && matrizTablero[i][j - 1] != null && matrizTablero[i][j - 1].isGlued()) {
                        int cont3 = contarEspaciosAIzquierda(i, j - 1, matrizTablero);
                        numeros.add(cont3);
                    }
    
                    if (!numeros.isEmpty()) {
                        int minMovimientoActual = numeros.stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
                        minMovimientos = Math.min(minMovimientos, minMovimientoActual);
                    }
                }
            }
        }
        return minMovimientos == Integer.MAX_VALUE ? 0 : minMovimientos;
    }
    
    private int contarEspaciosAIzquierda(int row, int col, Tile[][] matrizTablero) {
        int contador = 0;
        while (col - 1 >= 0 && matrizTablero[row][col - 1] == null) {
            contador++;
            col--;
        }
        return contador;
    }


    /**
        * metodo que inclina el tablero hacia arriba
        * @param grid
        * @return matriz de objetos correspondiente despues de la inclinacion
    */
    private Tile[][] tiltUp(Tile[][] matrizTablero) {
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                if (matrizTablero[i][j] != null) {
                    if (matrizTablero[i][j].isMasterGlued()) {
                        int movimientosPermitidos = auxUp(matrizTablero);

                        for (int k = 0; k < movimientosPermitidos; k++) {
                            if (matrizTablero[i - 1][j] == null) {
                                matrizTablero[i - 1][j] = matrizTablero[i][j];
                                matrizTablero[i][j] = null;
                                i--;
                            }

                            if (j - 1 >= 0 && matrizTablero[i][j - 1] != null && matrizTablero[i][j - 1].isGlued()) {
                                matrizTablero[i - 1][j - 1] = matrizTablero[i][j - 1];
                                matrizTablero[i][j - 1] = null;
                            }

                            if (j + 1 < columns && matrizTablero[i][j + 1] != null && matrizTablero[i][j + 1].isGlued()) {
                                matrizTablero[i - 1][j + 1] = matrizTablero[i][j + 1];
                                matrizTablero[i][j + 1] = null;
                            }
                        }
                    } else if (matrizTablero[i][j].isGlued()) {
                        continue;
                    } else {
                        for (int k = i; k > 0; k--) {
                            if (matrizTablero[k - 1][j] == null) {
                                matrizTablero[k - 1][j] = matrizTablero[k][j];
                                matrizTablero[k][j] = null;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return matrizTablero;
    }

    private int auxUp(Tile[][] matrizTablero) {
        int minMovimientos = Integer.MAX_VALUE;
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrizTablero[i][j] != null && matrizTablero[i][j].isMasterGlued()) {
                    List<Integer> numeros = new ArrayList<>();
    
                    if (j - 1 >= 0 && matrizTablero[i][j - 1] != null && matrizTablero[i][j - 1].isGlued()) {
                        int cont1 = contarEspaciosArriba(i, j - 1, matrizTablero);
                        numeros.add(cont1);
                    }

                    if (j + 1 < columns && matrizTablero[i][j + 1] != null && matrizTablero[i][j + 1].isGlued()) {
                        int cont2 = contarEspaciosArriba(i, j + 1, matrizTablero);
                        numeros.add(cont2);
                    }

                    if (i - 1 >= 0 && matrizTablero[i - 1][j] != null && matrizTablero[i - 1][j].isGlued()) {
                        int cont3 = contarEspaciosArriba(i - 1, j, matrizTablero);
                        numeros.add(cont3);
                    }

                    if (!numeros.isEmpty()) {
                        int minMovimientoActual = numeros.stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
                        minMovimientos = Math.min(minMovimientos, minMovimientoActual);
                    }
                }
            }
        }
    
        return minMovimientos == Integer.MAX_VALUE ? 0 : minMovimientos;
    }
    
    private int contarEspaciosArriba(int row, int col, Tile[][] matrizTablero) {
        int contador = 0;
        while (row - 1 >= 0 && matrizTablero[row - 1][col] == null) {
            contador++;
            row--;
        }
        return contador;
    }

    
    private Tile[][] tiltDown(Tile[][] matrizTablero) {
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                if (matrizTablero[i][j] != null) {
                    if (matrizTablero[i][j].isMasterGlued()) {
                        int movimientosPermitidos = auxDown(matrizTablero);
                        for (int k = 0; k < movimientosPermitidos; k++) {
                            if (matrizTablero[i + 1][j] == null) {
                                matrizTablero[i + 1][j] = matrizTablero[i][j];
                                matrizTablero[i][j] = null;
                                i++;
                            }
    
                            if (j - 1 >= 0 && matrizTablero[i][j - 1] != null && matrizTablero[i][j - 1].isGlued()) {
                                matrizTablero[i + 1][j - 1] = matrizTablero[i][j - 1];
                                matrizTablero[i][j - 1] = null;
                            }
    
                            if (j + 1 < columns && matrizTablero[i][j + 1] != null && matrizTablero[i][j + 1].isGlued()) {
                                matrizTablero[i + 1][j + 1] = matrizTablero[i][j + 1];
                                matrizTablero[i][j + 1] = null;
                            }
                        }
                    } else if (matrizTablero[i][j].isGlued()) {
                        continue;
                    } else {
                        for (int k = i; k < rows - 1; k++) {
                            if (matrizTablero[k + 1][j] == null) {
                                matrizTablero[k + 1][j] = matrizTablero[k][j];
                                matrizTablero[k][j] = null;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return matrizTablero;
    }
    
    private int auxDown(Tile[][] matrizTablero) {
        int minMovimientos = Integer.MAX_VALUE;
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < columns; j++) {
                if (matrizTablero[i][j] != null && matrizTablero[i][j].isMasterGlued()) {
                    List<Integer> numeros = new ArrayList<>();
                    if (j - 1 >= 0 && matrizTablero[i][j - 1] != null && matrizTablero[i][j - 1].isGlued()) {
                        int cont1 = contarEspaciosAbajo(i, j - 1, matrizTablero);
                        numeros.add(cont1);
                    }
    
                    if (j + 1 < columns && matrizTablero[i][j + 1] != null && matrizTablero[i][j + 1].isGlued()) {
                        int cont2 = contarEspaciosAbajo(i, j + 1, matrizTablero);
                        numeros.add(cont2);
                    }
    
                    if (i + 1 < rows && matrizTablero[i + 1][j] != null && matrizTablero[i + 1][j].isGlued()) {
                        int cont3 = contarEspaciosAbajo(i + 1, j, matrizTablero);
                        numeros.add(cont3);
                    }
    
                    if (!numeros.isEmpty()) {
                        int minMovimientoActual = numeros.stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
                        minMovimientos = Math.min(minMovimientos, minMovimientoActual);
                    }
                }
            }
        }
        return minMovimientos == Integer.MAX_VALUE ? 0 : minMovimientos;
    }
    
    private int contarEspaciosAbajo(int row, int col, Tile[][] matrizTablero) {
        int contador = 0;
        while (row + 1 < rows && matrizTablero[row + 1][col] == null) {
            contador++;
            row++;
        }
        return contador;
    }


    /**
     * Metodo que encuentra todas las baldosas completamente inmovibles
     * Una baldosa se considera completamente inmóvil si no cambia su posición después
     * de inclinar la matriz en cualquiera de las cuatro direcciones
     * (arriba, abajo, izquierda, derecha).
     *
     * @param grid the matrix to search in
     * @return a list of coordinates of the completely unmovable tiles
     */
    public List<int[]> fixedTiles(Tile[][] grid) {
        List<int[]> unmovableTiles = new ArrayList<>();

        Tile[][] afterTiltRight = tiltRight(copiarMatriz(grid));
        Tile[][] afterTiltLeft = tiltLeft(copiarMatriz(grid));
        Tile[][] afterTiltUp = tiltUp(copiarMatriz(grid));
        Tile[][] afterTiltDown = tiltDown(copiarMatriz(grid));

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != null &&
                        grid[i][j] == afterTiltRight[i][j] &&
                        grid[i][j] == afterTiltLeft[i][j] &&
                        grid[i][j] == afterTiltUp[i][j] &&
                        grid[i][j] == afterTiltDown[i][j]) {

                    unmovableTiles.add(new int[] {i, j});
                }
            }
        }
        return unmovableTiles;
    }

    private Tile[][] copiarMatriz(Tile[][] matrizOriginal) {
        Tile[][] copia = new Tile[matrizOriginal.length][matrizOriginal[0].length];

        for (int i = 0; i < matrizOriginal.length; i++) {
            for (int j = 0; j < matrizOriginal[i].length; j++) {
                if(matrizOriginal[i][j] == null){
                    copia[i][j] = null;
                }else{
                    copia[i][j] = new Tile(matrizOriginal[i][j].getSymbol(), matrizOriginal[i][j].getRow(), matrizOriginal[i][j].getColumn());

                }
            }
        }
        return copia;
    }
}