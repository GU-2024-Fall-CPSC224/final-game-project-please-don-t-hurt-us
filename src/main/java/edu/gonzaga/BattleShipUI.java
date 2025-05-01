package edu.gonzaga;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class BattleShipUI {
    

    public BattleShipUI() {
        JFrame frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // create a start screen panel
        JPanel startScreen = new JPanel();
        startScreen.setLayout(new GridLayout(3, 1));
        
        String carrierPNGLocation = "C:\\Users\\Stuart G\\Coding\\Software Development\\final-game-project-please-don-t-hurt-us\\src\\Images\\Aircraft Carrier.png";
        String cruiserPNGLocation = "C:\\Users\\Stuart G\\Coding\\Software Development\\final-game-project-please-don-t-hurt-us\\src\\Images\\Cruiser.png";
        String destroyerPNGLocation = "C:\\Users\\Stuart G\\Coding\\Software Development\\final-game-project-please-don-t-hurt-us\\src\\Images\\Destroyer.png";
        String scoutPNGLocation = "C:\\Users\\Stuart G\\Coding\\Software Development\\final-game-project-please-don-t-hurt-us\\src\\Images\\Scout.png";
        String gridSquarePNGLocation = "C:\\Users\\Stuart G\\Coding\\Software Development\\final-game-project-please-don-t-hurt-us\\src\\Images\\Grid Square.png";
        String gridSquarePNGHitPip = "C:\\Users\\Stuart G\\Coding\\Software Development\\final-game-project-please-don-t-hurt-us\\src\\Images\\Hit Pip Mark.png";
        String gridSquarePNGMissSquare = "C:\\Users\\Stuart G\\Coding\\Software Development\\final-game-project-please-don-t-hurt-us\\src\\Images\\Miss Square.png";        
        
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

        
        rulesScreen.add(rules);
        rulesScreen.add(rules2);
        //rulesScreen.add(rules3);
        rules.setFont(new Font("Arial", Font.PLAIN, 16));
        rules.setForeground(Color.BLACK);
        rules2.setFont(new Font("Arial", Font.PLAIN, 16));
        rules2.setForeground(Color.BLACK);
        
        JButton backButton = new JButton("Back to Start Screen");
        rulesScreen.add(backButton);
        
        //Game Screen'
        
        JPanel gameScreen = new JPanel();
        gameScreen.setLayout(new BorderLayout());

        JLabel gameTitleLabel = new JLabel("Battleship Game", SwingConstants.CENTER);
        gameTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gameScreen.add(gameTitleLabel, BorderLayout.NORTH);

        //create the axis labels for the grid
        JPanel axisXPanel = new JPanel();
        axisXPanel.setLayout(new GridLayout(1, 11)); 
        axisXPanel.add(new JLabel(" ")); 
        for (int i = 0; i < 10; i++) {
            axisXPanel.add(new JLabel(String.valueOf(i + 1)));
        }
        gameScreen.add(axisXPanel, BorderLayout.NORTH);
        JPanel axisYPanel = new JPanel();
        axisYPanel.setLayout(new GridLayout(11, 1));
        for (int i = 0; i < 11; i++) {
            axisYPanel.add(new JLabel(String.valueOf((char) ('A' + i))));
        }
        gameScreen.add(axisYPanel, BorderLayout.WEST);
        // Create a grid of buttons for the game screen
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 10));  // 10x10 grid layout for battleship grid
        for (int i = 0; i < 100; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(10, 10));  // Adjust the size of each grid button
            button.setBackground(Color.LIGHT_GRAY);
            button.setIcon(new ImageIcon(gridSquarePNGLocation)); // Set the icon for the button
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border to the button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    

                    System.out.println("Grid button clicked!");
                }
            });
            gridPanel.add(button);
        }

        // Buttons below the grid
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton forfeitButton = new JButton("Forfeit Game");
        JButton exitGameButton = new JButton("Exit Game");

        buttonPanel.add(forfeitButton);
        buttonPanel.add(exitGameButton);

        // Action listener for the forfeit button
        forfeitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to forfeit?", "Forfeit Game", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(frame, "You have forfeited the game.");

                    System.exit(0);
                }
            }
        });

        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the game?", "Exit Game", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        // Action listener for the exit game button
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit the game?", "Exit Game", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        gameScreen.add(gridPanel, BorderLayout.CENTER);
        gameScreen.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(startScreen);
                frame.add(gameScreen, BorderLayout.CENTER);
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
