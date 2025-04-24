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

    void placeShip(Ship ship, Coordinate coordinate, Direction Direction) {
        // Check if the ship can be placed on the board at the given coordinate and direction
        if (board.canPlaceShip(ship, coordinate, Direction)) {
            // select the direction of the ship
            // double check if the ship can be placed on the board at the given coordinate and direction
            // Place the ship on the board
            ship.isPlaced = true;
            ship.coordinates = new ArrayList<>();
            for (int i = 0; i < ship.size; i++) {
                Coordinate newCoordinate = new Coordinate(coordinate.getX(), coordinate.getY());
                if (Direction == Direction.UP) {
                    newCoordinate.setX(newCoordinate.getX() - i);
                } else if (Direction == Direction.DOWN) {
                    newCoordinate.setX(newCoordinate.getX() + i);
                } else if (Direction == Direction.LEFT) {
                    newCoordinate.setY(newCoordinate.getY() - i);
                } else if (Direction == Direction.RIGHT) {
                    newCoordinate.setY(newCoordinate.getY() + i);
                }
                ship.coordinates.add(newCoordinate);
            }
            // Add the ship to the player's list of ships
            ships.add(ship);
            // Update the board with the ship's coordinates
            for (Coordinate c : ship.coordinates) {
                board.grid[c.getX()][c.getY()].setShip(ship);
            }
        } else {
            System.out.println("Cannot place ship at this location.");
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
