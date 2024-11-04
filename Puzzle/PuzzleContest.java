import java.util.*;

public class PuzzleContest {
    private List<char[][]> solutionPath;
    private boolean isSolved;
    private int rows;
    private int columns;
    
    public PuzzleContest() {
        this.solutionPath = new ArrayList<>();
        this.isSolved = false;
    }
    
    public boolean solve(char[][] starting, char[][] ending) {
        this.solutionPath.clear();
        this.isSolved = false;
        this.rows = starting.length;
        this.columns = starting[0].length;
        
        if (starting.length != ending.length || starting[0].length != ending[0].length) {
            return false;
        }
        
        Set<String> visited = new HashSet<>();
        this.isSolved = dfs(copyBoard(starting), ending, visited, new ArrayList<>());
        return this.isSolved;
    }
    
    public void simulate(char[][] starting, char[][] ending) throws PuzzleException{
        if (!this.isSolved) {
            if (!solve(starting, ending)) {
                throw new PuzzleException(PuzzleException.IMPOSSIBLE);
            }
        }
        
        System.out.println("Simulación de movimientos para resolver el puzzle:");
        Puzzle visualPuzzle = new Puzzle(starting, ending);
        visualPuzzle.makeVisible();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        for (int i = 1; i < solutionPath.size(); i++) {
            char direction = determineDirection(solutionPath.get(i-1), solutionPath.get(i));
            if (direction != ' ') {
                visualPuzzle.tilt(direction);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        System.out.println("¡Puzzle resuelto!");
    }
    
    private boolean dfs(char[][] current, char[][] ending, Set<String> visited, List<char[][]> path) {
        if (Arrays.deepEquals(current, ending)) {
            path.add(copyBoard(current));
            solutionPath = new ArrayList<>(path);
            return true;
        }
        
        String boardState = boardToString(current);
        if (visited.contains(boardState)) {
            return false;
        }
        
        visited.add(boardState);
        path.add(copyBoard(current));
        
        char[] directions = {'r', 'l', 'u', 'd'};
        for (char direction : directions) {
            char[][] nextState = tilt(copyBoard(current), direction);
            
            if (!Arrays.deepEquals(nextState, current)) {
                if (dfs(nextState, ending, visited, path)) {
                    return true;
                }
            }
        }
        
        path.remove(path.size() - 1);
        return false;
    }
    
    private char[][] tilt(char[][] board, char direction) {
        switch (direction) {
            case 'r': return tiltRight(board);
            case 'l': return tiltLeft(board);
            case 'u': return tiltUp(board);
            case 'd': return tiltDown(board);
            default: return board;
        }
    }
    
    private char[][] tiltRight(char[][] board) {
        char[][] result = copyBoard(board);
        for (int i = 0; i < rows; i++) {
            for (int j = columns - 2; j >= 0; j--) {
                if (result[i][j] != '.' && result[i][j] != ' ') {
                    int newCol = j;
                    while (newCol < columns - 1 && (result[i][newCol + 1] == '.' || result[i][newCol + 1] == ' ')) {
                        result[i][newCol + 1] = result[i][newCol];
                        result[i][newCol] = '.';
                        newCol++;
                    }
                }
            }
        }
        return result;
    }
    
    private char[][] tiltLeft(char[][] board) {
        char[][] result = copyBoard(board);
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                if (result[i][j] != '.' && result[i][j] != ' ') {
                    int newCol = j;
                    while (newCol > 0 && (result[i][newCol - 1] == '.' || result[i][newCol - 1] == ' ')) {
                        result[i][newCol - 1] = result[i][newCol];
                        result[i][newCol] = '.';
                        newCol--;
                    }
                }
            }
        }
        return result;
    }
    
    private char[][] tiltUp(char[][] board) {
        char[][] result = copyBoard(board);
        for (int j = 0; j < columns; j++) {
            for (int i = 1; i < rows; i++) {
                if (result[i][j] != '.' && result[i][j] != ' ') {
                    int newRow = i;
                    while (newRow > 0 && (result[newRow - 1][j] == '.' || result[newRow - 1][j] == ' ')) {
                        result[newRow - 1][j] = result[newRow][j];
                        result[newRow][j] = '.';
                        newRow--;
                    }
                }
            }
        }
        return result;
    }
    
    private char[][] tiltDown(char[][] board) {
        char[][] result = copyBoard(board);
        for (int j = 0; j < columns; j++) {
            for (int i = rows - 2; i >= 0; i--) {
                if (result[i][j] != '.' && result[i][j] != ' ') {
                    int newRow = i;
                    while (newRow < rows - 1 && (result[newRow + 1][j] == '.' || result[newRow + 1][j] == ' ')) {
                        result[newRow + 1][j] = result[newRow][j];
                        result[newRow][j] = '.';
                        newRow++;
                    }
                }
            }
        }
        return result;
    }
    
    private char determineDirection(char[][] before, char[][] after) {
        char[] directions = {'r', 'l', 'u', 'd'};
        for (char direction : directions) {
            char[][] result = tilt(copyBoard(before), direction);
            if (Arrays.deepEquals(result, after)) {
                return direction;
            }
        }
        return ' ';
    }
    
    private char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }
    
    private String boardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            sb.append(Arrays.toString(row));
        }
        return sb.toString();
    }
}