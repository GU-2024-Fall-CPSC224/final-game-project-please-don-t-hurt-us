package edu.gonzaga;

public class Coordinate {
    char row;
    int column;

    public Coordinate(char row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isEqual(Coordinate coordinate) {
        // Check if the current coordinate is equal to the given coordinate
        return this.row == coordinate.row && this.column == coordinate.column;
    }

    public boolean isValid() {
        // Check if the coordinate is valid (within the bounds of the board)
        return row >= 'A' && row <= 'J' && column >= 1 && column <= 10;
    }

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
}

