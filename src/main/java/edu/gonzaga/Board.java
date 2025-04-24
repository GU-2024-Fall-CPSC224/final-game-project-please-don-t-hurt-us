package edu.gonzaga;

public class Board {
    
    Cell[][] grid;
    public static final int SIZE = 10;
    
    // Constructor: initialize the board grid
    public Board() {
        grid = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    boolean canPlaceShip(Ship ship, Coordinate coordinate, Direction direction) {
        // Check if the ship can be placed on the board at the given coordinate and direction
        // This method should check for boundaries and collisions with other ships
        return true; // Placeholder return value
    }

    shotResult fire(Coordinate coordinate) {
        // Check if the shot hits or misses a ship
        // This method should check the grid at the given coordinate and return the result
        return shotResult.MISS; // Placeholder return value
    }
   

    void display() {
        // Display the board to the console
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    enum shotResult {
        HIT, MISS, SUNK
    }
    
}
