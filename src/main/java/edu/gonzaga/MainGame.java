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
import java.util.Random;
import java.util.Arrays;


/** Main program class for launching your team's program. */
public class MainGame {
    public static void main(String[] args) {
        System.out.println("Hello welcome to the BattleShip game!");
        BattleShipUI ui = new BattleShipUI();
        // Create a new instance of the game UI
        // This will create the UI and display the start screen
        // The UI will handle user interactions and game flow
        // You can add more functionality to the UI class as needed
        // For example, you can add buttons for starting the game, showing instructions, etc.

        Scanner scanner = new Scanner(System.in);

        // Your code here. Good luck!
        Player player1 = new Player();
        Player player2 = new Player();
        initializeGame(scanner, player1, player2); // prompt for names and ship placement
        startGame(scanner, player1, player2); // turn-based loop game logic
        quitGame();
    }

    void initializeGame(Scanner scanner, Player player1, Player player2) {
        System.out.println("Initializing game...");
        // Initialize the game board, ships, and players
        player1.name = "Player 1";

        player2.name = "Player 2";

        System.out.println("Player 1, pick your name:");
        Scanner scanner = new Scanner(System.in);
        player1.name = scanner.nextLine();
        System.out.println("Player 2, pick your name:");
        player2.name = scanner.nextLine();
        System.out.println("Welcome " + player1.name + " and " + player2.name + "!");

    }

    void startGame(Scanner scanner, Player player1, Player player2) {
        
        
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

    public void quitGame() {
        System.exit(0);
        System.out.println("Thanks for playing!");
    }

    public void forfeitGame(Player player) {
        System.out.println(player.name + " has forfeited the game.");
    }
}
