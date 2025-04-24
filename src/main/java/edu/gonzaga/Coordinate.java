package edu.gonzaga;

import java.util.Objects;

public class Coordinate {
    char row;
    int column;

    public Coordinate(char row, int column) {
        this.row = row; // A to J
        this.column = column; // 1 to 10 
    }

    public boolean isEqual(Coordinate coordinate) {
        // Check if the current coordinate is equal to the given coordinate
        return this.row == coordinate.row && this.column == coordinate.column;
    }

    public boolean isValid() {
        // Check if the coordinate is valid (within the bounds of the board 2d list)
        if (getX() < 0 || getX() >= Board.SIZE || getY() < 0 || getY() >= Board.SIZE) {
            return false; // Invalid row or column
        }
    }
    
    @Override
    public String toString() {
        // Convert the coordinate to a string representation
        return String.format("%c%d", row, column);
    }

    public void fromString(String coordinateString) {
        // Convert a string representation of a coordinate to a Coordinate object
        this.row = coordinateString.charAt(0);
        this.column = Integer.parseInt(coordinateString.substring(1));
    }

    public char getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getX() {
        return row - 'A';
    }

    public int getY() {
        return column - 1;
    }

    public void setX(int x) {
        this.row = (char)('A' + x);
    }

    public void setY(int y) {
        this.column = y + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}


