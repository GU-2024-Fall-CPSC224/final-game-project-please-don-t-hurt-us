package edu.gonzaga;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class BattleShipUI {
    

    public BattleShipUI() {
        JFrame frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // create a start screen panel
        JPanel startScreen = new JPanel();
        startScreen.setLayout(new GridLayout(3, 1));
        

        JLabel titleLabel = new JLabel("Welcome to Battleship!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        JLabel creditsLabel = new JLabel("Created by: Stuart Goldkamp, Jason Truong", SwingConstants.CENTER);
        JButton startButton = new JButton("Start Game");
        JButton instructionsButton = new JButton("Instructions");
        JButton exitButton = new JButton("Exit");

        startScreen.add(titleLabel);
        startScreen.add(creditsLabel);
        startScreen.add(startButton);
        startScreen.add(instructionsButton);
        startScreen.add(exitButton);


        frame.add(startScreen, BorderLayout.CENTER);
        frame.setVisible(true);

        //Rules screen for battleship
        JPanel rulesScreen = new JPanel();
        rulesScreen.setLayout(new GridLayout(5, 1));


        JLabel rulesTitleLabel = new JLabel("Game Rules", SwingConstants.CENTER);
        rulesTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rulesScreen.add(rulesTitleLabel);
        
        JLabel rules = new JLabel("1. Each player places their ships on the grid. \n 2. Players take turns to attack each other's ships.", SwingConstants.HORIZONTAL);
        JLabel rules2 = new JLabel("3. The first player to sink all of the opponent's ships wins the game. 4. Ships can be placed horizontally or vertically, but not diagonally.", SwingConstants.HORIZONTAL);
        //JLabel rules3 = new JLabel("4. Ships can be placed horizontally or vertically, but not diagonally.", SwingConstants.HORIZONTAL);
        
        rulesScreen.add(rules);
        rulesScreen.add(rules2);
        //rulesScreen.add(rules3);
        rules.setFont(new Font("Arial", Font.PLAIN, 10));
        rules.setForeground(Color.BLACK);
        rules2.setFont(new Font("Arial", Font.PLAIN, 10));
        rules2.setForeground(Color.BLACK);
        //rules3.setFont(new Font("Arial", Font.PLAIN, 10));
        //rules3.setForeground(Color.BLACK);
        
        JButton backButton = new JButton("Back to Start Screen");
        rulesScreen.add(backButton);
        


        // Action listener for the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(startScreen);
                frame.add(rulesScreen, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });
        // Action listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(rulesScreen);
                frame.add(startScreen, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });
        // Action listener for the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // Action listener for the instructions button
        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(startScreen);
                frame.add(rulesScreen, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });
    }
}
