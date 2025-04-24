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


        // Your code here. Good luck!
        Player player1 = new Player();
        Player player2 = new Player();
        Player currentPlayer = player1;
        Player opponent = player2;
        initializeGame();

    }

    static void initializeGame() {
        System.out.println("Initializing game...");
        // Initialize the game board, ships, and players
        Board board = new Board();
        Player player1 = new Player();
        Player player2 = new Player();
        player1.name = "Player 1";

        player2.name = "Player 2";

        System.out.println("Player 1, pick your name:");
        Scanner scanner = new Scanner(System.in);
        player1.name = scanner.nextLine();
        System.out.println("Player 2, pick your name:");
        player2.name = scanner.nextLine();
        System.out.println("Welcome " + player1.name + " and " + player2.name + "!");

    }

    void startGame() {
        
        
    }

    void switchPlayer(Player currentPlayer, Player opponent) {
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }

    boolean checkWinner(Player player) {

        for (Ship ship : player.getShips()) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
        
    }

    void quitGame() {
        System.out.println("Thanks for playing!");
    }

    void forfeitGame(Player player) {
        System.out.println(player.name + " has forfeited the game.");
    }
}
