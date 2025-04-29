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
        System.out.println("DEBUG: canPlaceShip called for ship " + ship.getType() +
                           " starting at " + coordinate + " in direction " + direction);
    
        if (!coordinate.isValid()) {
            System.out.println("DEBUG: Coordinate " + coordinate + " is not valid.");
            return false;
        }
        if (ship.isPlaced()) {
            System.out.println("DEBUG: Ship " + ship.getType() + " is already placed.");
            return false;
        }
        
        // Boundary checks using the proper indices
        if (direction == Direction.UP) {
            if (coordinate.getX() - ship.getSize() + 1 < 0) {
                System.out.println("DEBUG: Boundary check failed for UP: " +
                        (coordinate.getX() - ship.getSize() + 1) + " < 0");
                return false;
            }
        } else if (direction == Direction.DOWN) {
            if (coordinate.getX() + ship.getSize() - 1 >= SIZE) {
                System.out.println("DEBUG: Boundary check failed for DOWN: " +
                        (coordinate.getX() + ship.getSize() - 1) + " >= " + SIZE);
                return false;
            }
        } else if (direction == Direction.LEFT) {
            if (coordinate.getY() - ship.getSize() + 1 < 0) {
                System.out.println("DEBUG: Boundary check failed for LEFT: " +
                        (coordinate.getY() - ship.getSize() + 1) + " < 0");
                return false;
            }
        } else if (direction == Direction.RIGHT) {
            if (coordinate.getY() + ship.getSize() - 1 >= SIZE) {
                System.out.println("DEBUG: Boundary check failed for RIGHT: " +
                        (coordinate.getY() + ship.getSize() - 1) + " >= " + SIZE);
                return false;
            }
        }
        
        // Collision check over the ship's length:
        for (int i = 0; i < ship.getSize(); i++) {
            int row = coordinate.getX();
            int col = coordinate.getY();
            switch (direction) {
                case UP:
                    row = coordinate.getX() - i;
                    break;
                case DOWN:
                    row = coordinate.getX() + i;
                    break;
                case LEFT:
                    col = coordinate.getY() - i;
                    break;
                case RIGHT:
                    col = coordinate.getY() + i;
                    break;
            }
            System.out.println("DEBUG: Checking cell at row " + row + ", col " + col);
            if (grid[col][row].getShip() != null) {
                System.out.println("DEBUG: Collision at cell (row " + row + ", col " + col + ") with ship " +
                grid[col][row].getShip().getType());
                return false;
            }
        }
        
        System.out.println("DEBUG: canPlaceShip returning true for ship " + ship.getType());
        return true;
    }
    

    public shotResult fire(Coordinate coordinate) {
        int row = coordinate.getX();   
        int col = coordinate.getY();      
        
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("Shot fired is out of bounds!");
            return shotResult.MISS;
        }
        
        Cell targetCell = grid[col][row];
        
        // Check if this cell has already been shot at
        if (targetCell.isHit || targetCell.isMiss) {
            System.out.println("You have already shot at this position. Please choose another coordinate.");
            return shotResult.ALREADY_SHOT;

        }
        
        // Check for a hit on a ship
        if (targetCell.hasShip) {
            targetCell.markHit();                
            targetCell.getShip().registerHit(coordinate);  
            
            if (targetCell.getShip().isSunk()) {
                targetCell.setShotResult(shotResult.SUNK);
                return shotResult.SUNK;
            } else {
                targetCell.setShotResult(shotResult.HIT);
                return shotResult.HIT;
            }
        } else {  
            targetCell.isMiss = true;
            targetCell.setShotResult(shotResult.MISS);
            return shotResult.MISS;
        }
    }
    
    public void placeShip(Ship ship, Coordinate coordinate, Direction direction) {
    if (!canPlaceShip(ship, coordinate, direction)) {
        System.out.println("Cannot place ship at the given location.");
        return;
    }

    for (int i = 0; i < ship.getSize(); i++) {
        int row = coordinate.getX();
        int col = coordinate.getY();
        // Adjust coordinates based on the direction using our intended logic:
        switch (direction) {
            case UP:
                row = coordinate.getX() - i;
                break;
            case DOWN:
                row = coordinate.getX() + i;
                break;
            case LEFT:
                col = coordinate.getY() - i;
                break;
            case RIGHT:
                col = coordinate.getY() + i;
                break;
        }
        System.out.println("DEBUG: Placing ship " + ship.getType() + " at cell (" + row + ", " + col + ")");
        // Correct the grid access order: first index is column, second index is row.
        grid[col][row].setShip(ship);
    }
}

   

    void display() {
        // Display the board to the console
        System.out.print("   ");
        for (int x = 0; x < SIZE; x++) {
            System.out.print((x + 1) + " ");
        }
        System.out.println();
        for (int y = 0; y < SIZE; y++) {
            char rowLabel = (char) ('A' + y);
            System.out.print(rowLabel + "  ");
            for (int x = 0; x < SIZE; x++) {
                Cell cell = grid[x][y];
                char displayChar = '.';
                
                if (cell.isHit) {
                    displayChar = 'X';
                } else if (cell.isMiss) {
                    displayChar = 'O';
                } else if (cell.getShip() != null) {
                }
                
                System.out.print(displayChar + " ");
            }
            System.out.println();
        }
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    enum shotResult {
        HIT, MISS, SUNK, ALREADY_SHOT
    }

    public int getSize() {
        return SIZE;
    }
    public Cell[][] getGrid() {
        return grid;
    }
    
}
