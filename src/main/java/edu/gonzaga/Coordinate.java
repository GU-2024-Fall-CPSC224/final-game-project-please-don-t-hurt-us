package edu.gonzaga;

public class Coordinate {
    char row;
    int column;

    void isEqual(Coordinate coordinate) {
        // Check if the current coordinate is equal to the given coordinate
        return this.row == coordinate.row && this.column == coordinate.column;
    }

    void isValid() {
        // Check if the coordinate is valid (within the bounds of the board)
        return row >= 'A' && row <= 'J' && column >= 1 && column <= 10;
    }

    void toString() {
        // Convert the coordinate to a string representation
        return String.format("%c%d", row, column);
    }

    void fromString(String coordinateString) {
        // Convert a string representation of a coordinate to a Coordinate object
        this.row = coordinateString.charAt(0);
        this.column = Integer.parseInt(coordinateString.substring(1));
    }
    
}
