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
        return true; // Placeholder return value
    }

    shotResult fire(Coordinate coordinate) {
        // Check if the shot hits or misses a ship
        // This method should check the grid at the given coordinate and return the result
        int x = coordinate.getX(); 
        int y = coordinate.getY();
        
        // check bounds & return MISS
        if (x < 0 || x >= 11 || y < 0 || y >= 11) {
            System.out.println("Shot fired is out of bounds!");
            return shotResult.MISS;
        } 

        Cell targetCell = grid[x][y]; // get target cell
        //check cell status for previous shots
        if (targetCell.isHit || targetCell.isMiss) {
            System.out.println("Invalid shot, please pick another position!");
            return targetCell.getShotResult();
        }

        // check for hit
        if (targetCell.hasShip) {
            targetCell.markHit(); // mark target cell as hit
            targetCell.getShip().registerHit(coordinate); // updates the ship object
            // check for SUNK else HIT
            if (targetCell.getShip().isSunk()) {
                targetCell.setShotResult(shotResult.SUNK);
                return shotResult.SUNK;
            } else {
                targetCell.setShotResult(shotResult.HIT);
                return shotResult.HIT;
            }
        } else { // check for MISS
            targetCell.isMiss = true;
            targetCell.setShotResult(shotResult.MISS);
            return shotResult.MISS;
        }
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
