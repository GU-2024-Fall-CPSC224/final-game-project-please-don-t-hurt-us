package edu.gonzaga;

public class Cell {
    boolean hasShip;
    boolean isHit;
    boolean isMiss;
    Ship ship;
    Board.shotResult shotResult;

    public void markHit() {
        isHit = true;
    }
    public void setShip(Ship ship) {
        this.ship = ship;
        this.hasShip = (ship != null);
    }   
    public Ship getShip() {
        return ship;
    }

    public void setShotResult(Board.shotResult result) {
        if (result == Board.shotResult.HIT) {
            isHit = true;
        } else if (result ==  Board.shotResult.MISS) {
            isMiss = true;
        }
        this.shotResult = result;
    }

    public Board.shotResult getShotResult() {
        return shotResult;
    }
}
