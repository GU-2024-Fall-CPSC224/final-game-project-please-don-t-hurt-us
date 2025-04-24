package edu.gonzaga;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static edu.gonzaga.Board.shotResult.*;


public class Player extends MainGame {
    String name = "";
    Board board = new Board();
    List<Ship> ships = new ArrayList<>();

    
    public void fire(Coordinate coordinate) {
        Board.shotResult result = board.fire(coordinate);
        if (result == Board.shotResult.HIT) {
            System.out.println("Hit!");
            for (Ship ship : ships) {
                if (ship.coordinates.contains(coordinate)) {
                    ship.registerHit(coordinate);
                    if (ship.isSunk()) {
                        System.out.println("You sunk my battleship!");
                    }
                }
            }
        } else if (result == Board.shotResult.MISS) {
            System.out.println("Miss!");
        } else if (result == Board.shotResult.SUNK) {
            System.out.println("You sunk my battleship!");
        }
        // Update the board with the shot result
        board.grid[coordinate.getX()][coordinate.getY()].setShotResult(result);
    }

    void changeDirection(Ship ship, Board.Direction direction) {
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
