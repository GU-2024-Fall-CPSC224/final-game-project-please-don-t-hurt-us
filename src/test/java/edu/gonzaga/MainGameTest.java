package edu.gonzaga;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;


public class MainGameTest {
    @Test
    void alwaysTrue() {
        Assertions.assertTrue(true);
    }
    //Cooordinate class tests
    @Test
    void testCoordinateCreation() {
        Coordinate coordinate = new Coordinate('A', 1);
        assertEquals('A', coordinate.getRow());
        assertEquals(1, coordinate.getColumn());
    }
    @Test
    void testCoordinateEquality() {
        Coordinate coordinate1 = new Coordinate('A', 1);
        Coordinate coordinate2 = new Coordinate('A', 1);
        assertTrue(coordinate1.isEqual(coordinate2));
    }
    @Test
    void testCoordinateValidity() {
        Coordinate coordinate = new Coordinate('A', 1);
        assertTrue(coordinate.isValid());
    }
    @Test
    void testCoordinateInvalidity() {
        Coordinate coordinate = new Coordinate('K', 11);
        assertFalse(coordinate.isValid());
    }
    @Test
    void testCoordinateToString() {
        Coordinate coordinate = new Coordinate('A', 1);
        assertEquals("A1", coordinate.toString());
    }
    
    @Test
    void testCoordinateToStringWithInvalidInput() {
        Coordinate coordinate = new Coordinate('K', 11);
        assertEquals("K11", coordinate.toString());
    }
    @Test
    void testCoordinateFromString() {
        Coordinate coordinate = new Coordinate('A', 1);
        coordinate.fromString("B2");
        assertEquals('B', coordinate.getRow());
        assertEquals(2, coordinate.getColumn());
    }
    @Test
    void testCoordinateFromStringWithInvalidInput() {
        Coordinate coordinate = new Coordinate('A', 1);
        coordinate.fromString("K11");
        assertEquals('K', coordinate.getRow());
        assertEquals(11, coordinate.getColumn());
    }
    @Test
    void testCoordinateFromStringWithValidInput() {
        Coordinate coordinate = new Coordinate('A', 1);
        coordinate.fromString("C3");
        assertEquals('C', coordinate.getRow());
        assertEquals(3, coordinate.getColumn());
    }
    @Test
    void testCoordinateXandY() {
        Coordinate coordinate = new Coordinate('A', 1);
        assertEquals(0, coordinate.getX());
        assertEquals(0, coordinate.getY());
    }
    @Test
    void testCoordinateSetXandY() {
        Coordinate coordinate = new Coordinate('A', 1);
        coordinate.setX(2);
        coordinate.setY(3);
        assertEquals('C', coordinate.getRow());
        assertEquals(4, coordinate.getColumn());
    }
    @Test
    void testCoordinateSetXandYWithInvalidInput() {
        Coordinate coordinate = new Coordinate('A', 1);
        coordinate.setX(11);
        coordinate.setY(12);
        assertEquals('L', coordinate.getRow());
        assertEquals(13, coordinate.getColumn());
    }
    @Test
    void testCoordinateSetXandYWithValidInput() {
        Coordinate coordinate = new Coordinate('A', 1);
        coordinate.setX(5);
        coordinate.setY(6);
        assertEquals('F', coordinate.getRow());
        assertEquals(7, coordinate.getColumn());
    }

    @Test
    void testCoordinateToStringWithValidInput() {
        Coordinate coordinate = new Coordinate('A', 1);
        assertEquals("A1", coordinate.toString());
    }
    //Ship class tests
    @Test
    void testShipCreation() {
        Ship ship = new Ship("Battleship", 4);
        assertEquals("Battleship", ship.getType());
        assertEquals(4, ship.getSize());
        assertFalse(ship.isPlaced());
    }
    @Test
    void testShipPlacement() {
        Ship ship = new Ship("Battleship", 4);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate('A', 1));
        coordinates.add(new Coordinate('A', 2));
        coordinates.add(new Coordinate('A', 3));
        coordinates.add(new Coordinate('A', 4));
        ship.placeShip(coordinates);
        assertTrue(ship.isPlaced());
        assertEquals(coordinates, ship.getCoordinates());
    }
    @Test
    void testShipSunk() {
        Ship ship = new Ship("Battleship", 4);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate('A', 1));
        coordinates.add(new Coordinate('A', 2));
        coordinates.add(new Coordinate('A', 3));
        coordinates.add(new Coordinate('A', 4));
        ship.placeShip(coordinates);
        ship.registerHit(new Coordinate('A', 1));
        ship.registerHit(new Coordinate('A', 2));
        ship.registerHit(new Coordinate('A', 3));
        ship.registerHit(new Coordinate('A', 4));
        assertTrue(ship.isSunk());
    }
    @Test
    void testShipRegisterHit() {
        Ship ship = new Ship("Battleship", 4);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate('A', 1));
        coordinates.add(new Coordinate('A', 2));
        ship.placeShip(coordinates);
        ship.registerHit(new Coordinate('A', 1));
        assertTrue(ship.getHitCoordinates().contains(new Coordinate('A', 1)));
    }
    @Test
    void testShipRegisterHitWithInvalidInput() {
        Ship ship = new Ship("Battleship", 4);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate('A', 1));
        coordinates.add(new Coordinate('A', 2));
        ship.placeShip(coordinates);
        ship.registerHit(new Coordinate('B', 1));
        assertFalse(ship.getHitCoordinates().contains(new Coordinate('B', 1)));
    }
    @Test
    void testShipRegisterHitWithValidInput() {
        Ship ship = new Ship("Battleship", 4);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate('A', 1));
        coordinates.add(new Coordinate('A', 2));
        ship.placeShip(coordinates);
        ship.registerHit(new Coordinate('A', 1));
        assertTrue(ship.getHitCoordinates().contains(new Coordinate('A', 1)));
    }
    @Test
    void testShipRegisterHitWithMultipleHits() {
        Ship ship = new Ship("Battleship", 4);
        List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate('A', 1));
        coordinates.add(new Coordinate('A', 2));
        ship.placeShip(coordinates);
        ship.registerHit(new Coordinate('A', 1));
        ship.registerHit(new Coordinate('A', 2));
        assertTrue(ship.getHitCoordinates().contains(new Coordinate('A', 1)));
        assertTrue(ship.getHitCoordinates().contains(new Coordinate('A', 2)));
    }
    //Board class tests
    @Test
    void testBoardCreation() {
        Board board = new Board();
        assertNotNull(board);
        assertEquals(10, board.getSize());
    }
    
}
