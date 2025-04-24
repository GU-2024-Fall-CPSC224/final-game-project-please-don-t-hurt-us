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

    public void placeShip(Ship ship, Coordinate coordinate, Direction direction) {
        
        if (!canPlaceShip(ship, coordinate, direction)) {
            System.out.println("Cannot place ship at the given location.");
            return;
        }

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

            //place ship in the cell
            grid[x][y].setShip(ship); // set ship in the cell
        }
        ship.placeShip(null);
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
        HIT, MISS, SUNK
    }

    public int getSize() {
        return SIZE;
    }
    public Cell[][] getGrid() {
        return grid;
    }
    
}
