package edu.gonzaga;
import java.util.ArrayList;
import java.util.List;

public class Ship {

    String type;
    int size;   
    List<Coordinate> coordinates;
    boolean isPlaced;
    List<Coordinate> hitCoordinates;

    public Ship(String type, int size) {
        this.type = type;
        this.size = size;
        this.coordinates = new ArrayList<>();
        this.hitCoordinates = new ArrayList<>();
        this.isPlaced = false;
    }

    public void placeShip(List<Coordinate> coords) {
        this.coordinates = coords;
        this.isPlaced = true;
    }

    boolean isSunk() {
        // Check if the ship is sunk (all coordinates have been hit)
        for (Coordinate coordinate : coordinates) {
            if (!hitCoordinates.contains(coordinate)) {
                return false;
            }
        }
        return true;
    }

    void registerHit(Coordinate coordinate) {
        // Register a hit on the ship at the given coordinate
        if (coordinates.contains(coordinate) && !hitCoordinates.contains(coordinate)) {
            hitCoordinates.add(coordinate);
        }
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public List<Coordinate> getHitCoordinates() {
        return hitCoordinates;
    }
}
