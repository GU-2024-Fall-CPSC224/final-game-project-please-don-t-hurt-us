package edu.gonzaga;

public class Board {

    Cell[][] grid;
    public static final int SIZE = 10;
    
    // Constructor: initialize the board grid with row-major ordering:
    // grid[row][col]
    public Board() {
        grid = new Cell[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col] = new Cell();
            }
        }
    }
    
    // Checks if a ship can be placed at the given coordinate in the specified direction.
    // Uses standard ordering: row = coordinate.getX() and col = coordinate.getY().
    boolean canPlaceShip(Ship ship, Coordinate coordinate, Direction direction) {
        if (!coordinate.isValid()) {
            System.out.println("Invalid coordinate: " + coordinate);
            return false;
        }
        if (ship.isPlaced()) {
            System.out.println("This ship has already been placed.");
            return false;
        }
        
        int startRow = coordinate.getX(); // 0-based row
        int startCol = coordinate.getY(); // 0-based column

        // Boundary checks.
        if (direction == Direction.UP && startRow - ship.getSize() + 1 < 0) {
            System.out.println("Cannot place ship going UP: Out of bounds.");
            return false;
        } else if (direction == Direction.DOWN && startRow + ship.getSize() - 1 >= SIZE) {
            System.out.println("Cannot place ship going DOWN: Out of bounds.");
            return false;
        } else if (direction == Direction.LEFT && startCol - ship.getSize() + 1 < 0) {
            System.out.println("Cannot place ship going LEFT: Out of bounds.");
            return false;
        } else if (direction == Direction.RIGHT && startCol + ship.getSize() - 1 >= SIZE) {
            System.out.println("Cannot place ship going RIGHT: Out of bounds.");
            return false;
        }
        
        // Collision check: make sure every cell is clear.
        for (int i = 0; i < ship.getSize(); i++) {
            int row = startRow;
            int col = startCol;
            switch (direction) {
                case UP:
                    row = startRow - i;
                    break;
                case DOWN:
                    row = startRow + i;
                    break;
                case LEFT:
                    col = startCol - i;
                    break;
                case RIGHT:
                    col = startCol + i;
                    break;
            }
            if (grid[row][col].getShip() != null) {
                System.out.println("Cannot place ship: Collision detected at " + convertCoordinate(row, col) + ".");
                return false;
            }
        }
        return true;
    }
    
    // Fires at the given coordinate and returns the shot result.
    public shotResult fire(Coordinate coordinate) {
        int row = coordinate.getX();  // row index
        int col = coordinate.getY();  // column index
        
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("Shot is out of bounds.");
            return shotResult.MISS;
        }
        
        Cell targetCell = grid[row][col];
        
        // If a shot has already been taken here...
        if (targetCell.isHit || targetCell.isMiss) {
            System.out.println("You have already fired at " + convertCoordinate(row, col) + ".");
            return shotResult.ALREADY_SHOT;
        }
        
        // Register a hit if a ship is present.
        if (targetCell.hasShip) {
            targetCell.markHit();
            targetCell.getShip().registerHit(coordinate);
            if (targetCell.getShip().isSunk()) {
                targetCell.setShotResult(shotResult.SUNK);
                System.out.println("It's a hit! You sunk a ship!");
                return shotResult.SUNK;
            } else {
                targetCell.setShotResult(shotResult.HIT);
                System.out.println("It's a hit!");
                return shotResult.HIT;
            }
        } else {
            targetCell.isMiss = true;
            targetCell.setShotResult(shotResult.MISS);
            System.out.println("You missed the shot at " + convertCoordinate(row, col) + ".");
            return shotResult.MISS;
        }
    }
    
    // Places a ship on the board at the given starting coordinate and direction.
    public void placeShip(Ship ship, Coordinate coordinate, Direction direction) {
        if (!canPlaceShip(ship, coordinate, direction)) {
            System.out.println("Ship placement failed.");
            return;
        }
        int startRow = coordinate.getX();
        int startCol = coordinate.getY();
        for (int i = 0; i < ship.getSize(); i++) {
            int row = startRow;
            int col = startCol;
            switch (direction) {
                case UP:
                    row = startRow - i;
                    break;
                case DOWN:
                    row = startRow + i;
                    break;
                case LEFT:
                    col = startCol - i;
                    break;
                case RIGHT:
                    col = startCol + i;
                    break;
            }
            grid[row][col].setShip(ship);
        }
        System.out.println("Ship has been placed.");
    }
    
    // Returns true if the cell at the given coordinate has been shot.
    public boolean isShotAt(Coordinate coordinate) {
        int row = coordinate.getX();
        int col = coordinate.getY();
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return false;
        }
        return (grid[row][col].isHit || grid[row][col].isMiss);
    }
    
    // Returns the shot result at the given coordinate.
    public shotResult getShotResultAt(Coordinate coordinate) {
        int row = coordinate.getX();
        int col = coordinate.getY();
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return null;
        }
        return grid[row][col].getShotResult();
    }
    
    // Displays the board to the console.
    void display() {
        System.out.print("   ");
        for (int col = 0; col < SIZE; col++) {
            System.out.print((col + 1) + " ");
        }
        System.out.println();
        for (int row = 0; row < SIZE; row++) {
            System.out.print((char)('A' + row) + "  ");
            for (int col = 0; col < SIZE; col++) {
                Cell cell = grid[row][col];
                char displayChar = '.';
                if (cell.isHit) {
                    displayChar = 'X';
                } else if (cell.isMiss) {
                    displayChar = 'O';
                }
                System.out.print(displayChar + " ");
            }
            System.out.println();
        }
    }
    
    // Helper method to convert row and column indices to user-friendly coordinates (e.g., (9,1) becomes "J2")
    private String convertCoordinate(int row, int col) {
        return "" + (char)('A' + row) + (col + 1);
    }
    
    // Enum for ship placement directions.
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    
    // Enum for shot results.
    public enum shotResult {
        HIT, MISS, SUNK, ALREADY_SHOT
    }
    
    public int getSize() {
        return SIZE;
    }
    
    public Cell[][] getGrid() {
        return grid;
    }
}
