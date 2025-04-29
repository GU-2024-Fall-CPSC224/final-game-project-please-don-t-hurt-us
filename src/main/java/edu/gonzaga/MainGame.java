/*
 * Final project main driver class
 * 
 * 
 * Project Description: BattleShip Game
 * 
 * 
 * Contributors: Stuart Goldkamp, Jason Truong
 * 
 * 
 * Copyright: 2025
 */
package edu.gonzaga;
import java.util.Scanner;

import edu.gonzaga.Board.shotResult;

import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;



/** Main program class for launching your team's program. */
public class MainGame {
    public static void main(String[] args) {
        System.out.println("Hello welcome to the BattleShip game!");

        Scanner scanner = new Scanner(System.in);

        // Your code here. Good luck!
        Player player1 = new Player();
        Player player2 = new Player();
        
    }

    public static void initializeGame(Scanner scanner, Player player1, Player player2) {
        System.out.println("Initializing game...");
        // Initialize the game board, ships, and players
        player1.name = "Player 1";

        player2.name = "Player 2";

        System.out.println("Player 1, pick your name:");
        Scanner scanner = new Scanner(System.in);
        player1.name = scanner.nextLine();
        System.out.println("Player 2, pick your name:");
        player2.name = scanner.nextLine().trim().toUpperCase();
        System.out.println("Welcome " + player1.getName() + " and " + player2.getName() + "!");

        System.out.println("\n" + player1.getName() + ", place your ships on the board:");
        setupPlayerShips(scanner, player1);
        System.out.println("\n" + player2.getName() + ", place your ships on the board:");
        setupPlayerShips(scanner, player2);

    }

    private static void setupPlayerShips(Scanner scanner, Player player) {
        String[] shipTypes = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};
        int[] shipSizes = {5, 4, 3, 3, 2};

        for (int i = 0; i < shipTypes.length; i++) {
            Ship ship = new Ship(shipTypes[i], shipSizes[i]);
            player.getShips().add(ship);
            boolean placed = false;

            while (!placed) {
                System.out.println("\nPlacing " + ship.getType() + " (size " + ship.getSize() + ")");
                System.out.println("Enter starting coordinate (e.g., A5): ");
                String coordInput = scanner.nextLine().trim().toUpperCase();
                Coordinate start;
                try {
                    char row = coordInput.charAt(0);
                    int col = Integer.parseInt(coordInput.substring(1));
                    start = new Coordinate(row, col);
                } catch (Exception e) {
                    System.out.println("Invalid coordinate input, try again.");
                    continue;
                }
                
                if (!start.isValid()) {
                    System.out.println("Coordinate out of board bounds. Try again.");
                    continue;
                }
                
                System.out.println("Enter direction (UP, DOWN, LEFT, RIGHT): ");
                String dirInput = scanner.nextLine().trim().toUpperCase();
                Board.Direction direction;
                try {
                    direction = Board.Direction.valueOf(dirInput);
                } catch (Exception e) {
                    System.out.println("Invalid direction. Try again.");
                    continue;
                }
                if (!player.getBoard().canPlaceShip(ship, start, direction)) {
                    System.out.println("Cannot place ship at that location. Try different coordinate/direction.");
                    continue;
                }
                // Calculate the ship's coordinates based on start and direction
                List<Coordinate> shipCoords = computeShipCoordinates(start, direction, ship.getSize());
                player.getBoard().placeShip(ship, start, direction);
                ship.placeShip(shipCoords);
                placed = true;
                System.out.println(ship.getType() + " placed at coordinates: " + shipCoords);
                player.getBoard().display();
            }
        }
    }

private static List<Coordinate> computeShipCoordinates(Coordinate start, Board.Direction direction, int size) {
    List<Coordinate> coords = new ArrayList<>();
    int startRow = start.getX(); // row index 
    int startCol = start.getY(); // column index 

    for (int i = 0; i < size; i++) {
        int newRow = startRow;
        int newCol = startCol;
        switch (direction) {
            case UP:
                newRow = startRow - i; // up decreases the row index (letter)
                break;
            case DOWN:
                newRow = startRow + i; // down increases the row index
                break;
            case LEFT:
                newCol = startCol - i; // left decreases the column index (number)
                break;
            case RIGHT:
                newCol = startCol + i; // right increases the column index
                break;
        }
        Coordinate c = new Coordinate((char) ('A' + newRow), newCol + 1);
        coords.add(c);
    }
    return coords;
}

    public static void startGame(Scanner scanner, Player player1, Player player2) {
        MainGame gameInstance = new MainGame();
        Player currentPlayer = player1;
        Player opponent = player2;

        while (true) {
            System.out.println("\n" + currentPlayer.getName() + ", it's your turn.");
            System.out.println("Opponent's board:");
            // Display the opponent board to show the result of previous shots
            opponent.getBoard().display();
            System.out.println("Enter coordinate to fire (e.g. A5) or 'F' to forfeit:");
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("F")) {
                gameInstance.forfeitGame(currentPlayer);
                System.out.println(opponent.getName() + " wins the game!");
                break;
            }
            
            Coordinate target;
            try {
                char row = input.charAt(0);
                int col = Integer.parseInt(input.substring(1));
                target = new Coordinate(row, col);
            } catch (Exception e) {
                System.out.println("Invalid coordinate format. Try again.");
                continue;
            }
            
            if (!target.isValid()) {
                System.out.println("Coordinate out of bounds. Try again.");
                continue;
            }
            
            Board.shotResult result = opponent.getBoard().fire(target);


            if(result == Board.shotResult.ALREADY_SHOT) {
                continue;
            }
            
            if (gameInstance.checkWinner(opponent)) {
                System.out.println("\n" + currentPlayer.getName() + " wins the game!");
                break;
            }
            
            Player temp = currentPlayer;
            currentPlayer = opponent;
            opponent = temp;
        }
    }
    
    void switchPlayer(Player currentPlayer, Player opponent) {
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }
    
    public boolean checkWinner(Player player) {
        for (Ship ship : player.getShips()) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
    
    // End the game – note that we print the thanks message before exiting
    public static void quitGame() {
        System.out.println("Thanks for playing!");
        System.exit(0);
    }
    
    // Called when a player forfeits the game
    public void forfeitGame(Player player) {
        System.out.println(player.getName() + " has forfeited the game.");
    }
}