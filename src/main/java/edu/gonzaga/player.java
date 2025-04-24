package edu.gonzaga;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Player extends MainGame {
    String name = "";
    Board board = new Board();
    List<Ship> ships = new ArrayList<>();

    void placeShip(Ship ship, Coordinate coordinate, Direction direction) {
        
        if (!board.canPlaceShip(ship, coordinate, direction)) {
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

            grid[x][y].placeShip(ship);
        }
    }
    void fire(Coordinate coordinate) {
        // Check if the shot hits or misses a ship
        shotResult result = board.fire(coordinate);
        if (result == shotResult.HIT) {
            System.out.println("Hit!");
            for (Ship ship : ships) {
                if (ship.coordinates.contains(coordinate)) {
                    ship.registerHit(coordinate);
                    if (ship.isSunk()) {
                        System.out.println("You sunk my battleship!");
                    }
                }
            }
        } else if (result == shotResult.MISS) {
            System.out.println("Miss!");
        } else if (result == shotResult.SUNK) {
            System.out.println("You sunk my battleship!");
        }
        // Update the board with the shot result
        board.grid[coordinate.getX()][coordinate.getY()].setShotResult(result);
    }

    void changeDirection(Ship ship, Direction direction) {
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    enum shotResult {
        HIT,MISS
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
