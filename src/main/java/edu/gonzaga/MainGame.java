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


/** Main program class for launching your team's program. */
public class MainGame {
    public static void main(String[] args) {
        System.out.println("Hello Team Game");


        // Your code here. Good luck!
        Player player1 = new Player();
        Player player2 = new Player();
        Player currentPlayer = player1;
        Player opponent = player2;

    }

    void initializeGame() {

    }

    void startGame() {
        
    }

    void switchPlayer(Player currentPlayer, Player opponent) {
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;
    }

    boolean checkWinner(Player player) {
        return true;
    }

    void quitGame() {
        System.out.println("Thanks for playing!");
    }

    void forfeitGame(Player player) {
        System.out.println(player.name + " has forfeited the game.");
    }
}
