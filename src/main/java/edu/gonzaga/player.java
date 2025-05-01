package edu.gonzaga;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import static edu.gonzaga.Board.shotResult.*;

public class Player {  
    String name = "";
    Board board = new Board();
    List<Ship> ships = new ArrayList<>();

    public void fire(Coordinate coordinate) {
        Board.shotResult result = board.fire(coordinate); // call to Board's method
    
        // Show messages based on result
        if(result == Board.shotResult.HIT) {
            JOptionPane.showMessageDialog(null, "Hit!");
            for (Ship ship : ships) {
                if (ship.getCoordinates().contains(coordinate)) {
                    ship.registerHit(coordinate);
                    if (ship.isSunk()) {
                        JOptionPane.showMessageDialog(null, "You sunk my " + ship.getType() + "!");
                    }
                }
            }
        } else if(result == Board.shotResult.MISS) {
            JOptionPane.showMessageDialog(null, "Miss!");
        } else if(result == Board.shotResult.SUNK) {
            JOptionPane.showMessageDialog(null, "You sunk my battleship!");
        } else if(result == Board.shotResult.ALREADY_SHOT) {
            JOptionPane.showMessageDialog(null, "Already shot at this position!");
        }
    
        // Optional: Update visual board state
        board.getGrid()[coordinate.getX()][coordinate.getY()].setShotResult(result);
    }
    

    // Method stubs for additional functionality.
    void changeDirection(Ship ship, Board.Direction direction) {
        // method to turn ship if needed
    }

    void removeShip(Ship ship, Coordinate coordinate) {
        // method to remove ship from board if needed
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public List<Ship> getShips() {
        return ships;
    }
    
    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
