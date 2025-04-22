package edu.gonzaga;

import java.util.List;

public class Ship {

    String type;
    int size;   
    List<Coordinate> coordinates;
    boolean isPlaced;
    List<Coordinate> hitCoordinates;

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
        if (coordinates.contains(coordinate)) {
            hitCoordinates.add(coordinate);
        }
    } 
}
