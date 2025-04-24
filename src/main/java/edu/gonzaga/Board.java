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
        if (!coordinate.isValid()) {
            return false; // Invalid coordinate
        }
        if (ship.isPlaced()) {
            return false; // Ship already placed
        }
        // Check boundaries based on the direction
        if (direction == Direction.UP) {
            if (coordinate.getY() - ship.getSize() + 1 < 0) {
                return false; // Ship would go out of bounds
            }
        } else if (direction == Direction.DOWN) {
            if (coordinate.getY() + ship.getSize() - 1 >= SIZE) {
                return false; // Ship would go out of bounds
            }
        } else if (direction == Direction.LEFT) {
            if (coordinate.getX() - ship.getSize() + 1 < 0) {
                return false; // Ship would go out of bounds
            }
        } else if (direction == Direction.RIGHT) {
            if (coordinate.getX() + ship.getSize() - 1 >= SIZE) {
                return false; // Ship would go out of bounds
            }
        }

        // Check for collisions with other ships
        for (int i = 0; i < ship.getSize(); i++) {
            int x = coordinate.getX();
            int y = coordinate.getY();

            // Adjust coordinates based on the direction
            if (direction == Direction.UP) {
                y = coordinate.getY() - i;
            } else if (direction == Direction.DOWN) {
                y = coordinate.getY() + i;
            } else if (direction == Direction.LEFT) {
                x = coordinate.getX() - i;
            } else if (direction == Direction.RIGHT) {
                x = coordinate.getX() + i;
            }


            if (grid[x][y].getShip() != null) {
                return false;
            }
        }
        int x = coordinate.getX();
        int y = coordinate.getY();
        // Check for any existing ship at the current coordinate
        if (grid[x][y].getShip() != null) {
            return false; // Collision with another ship
        }
    

        // If all checks pass, the ship can be placed
        return true; // Ship can be placed

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
