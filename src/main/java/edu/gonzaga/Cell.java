package edu.gonzaga;

public class Cell {
    boolean hasShip;
    boolean isHit;
    boolean isMiss;
    Ship ship;

    void markHit() {
        isHit = true;
    }
}
