package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class BattleShipUI {
    private JFrame frame;
    private CardLayout cardLayout;
    
    // Screens
    private JPanel startScreen, rulesScreen, placementScreen, gameScreen;
    
    // --- Ship Placement Screen Components ---
    private JButton[] placementGridButtons = new JButton[100];
    private JLabel placementInstructionLabel;
    private JComboBox<String> directionComboBox;
    private JButton placeShipButton;
    private JButton nextPlayerButton;
    private int currentShipIndex; // index of the ship currently being placed
    private String[] shipTypes = {"Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer"};
    private int[] shipSizes = {5, 4, 3, 3, 2};
    private int selectedPlacementRow = -1, selectedPlacementCol = -1;
    
    // --- Battle (Firing) Screen Components ---
    private JButton[] gameGridButtons = new JButton[100];
    private JLabel statusLabel;
    
    // --- Players and Game State ---
    private Player player1, player2;
    private Player currentPlacementPlayer; // used during ship placement phase
    private Player currentPlayer, currentOpponent; // used during battle phase

    public BattleShipUI() {
        frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        
        cardLayout = new CardLayout();
        frame.setLayout(cardLayout);
        
        createStartScreen();
        createRulesScreen();
        createPlacementScreen();
        createGameScreen();
        
        // Add screens to the CardLayout
        frame.add(startScreen, "startScreen");
        frame.add(rulesScreen, "rulesScreen");
        frame.add(placementScreen, "placementScreen");
        frame.add(gameScreen, "gameScreen");
        
        frame.setVisible(true);
    }
    
    // ---------------- Start Screen ----------------
    private void createStartScreen() {
        startScreen = new JPanel(new GridLayout(5, 1));
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
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get player names via dialogs.
                String name1 = JOptionPane.showInputDialog(frame, "Player 1, enter your name:");
                String name2 = JOptionPane.showInputDialog(frame, "Player 2, enter your name:");
                if(name1 == null || name1.trim().isEmpty()) { name1 = "PLAYER 1"; }
                if(name2 == null || name2.trim().isEmpty()) { name2 = "PLAYER 2"; }
                
                // Initialize players.
                player1 = new Player();
                player2 = new Player();
                player1.setName(name1.toUpperCase());
                player2.setName(name2.toUpperCase());
                player1.setBoard(new Board());
                player2.setBoard(new Board());
                
                // Start placement with player 1.
                currentPlacementPlayer = player1;
                currentShipIndex = 0;
                updatePlacementInstruction();
                resetPlacementGrid();
                showScreen("placementScreen");
            }
        });
        
        instructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { showScreen("rulesScreen"); }
        });
        
        exitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ System.exit(0); }
        });
    }
    
    // ---------------- Rules Screen ----------------
    private void createRulesScreen() {
        rulesScreen = new JPanel(new BorderLayout());
        JTextArea rulesText = new JTextArea();
        rulesText.setEditable(false);
        rulesText.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesText.setText("Game Rules:\n" +
                          "1. Each player places their ships on the board interactively.\n" +
                          "2. On the placement screen choose a square and a direction to place a ship.\n" +
                          "3. After both players have placed ships, take turns firing at each other's board.\n" +
                          "4. The first player to sink all of the opponent's ships wins.\n" +
                          "5. Ships can be placed horizontally or vertically, not diagonally.");
        JButton backButton = new JButton("Back to Start");
        rulesScreen.add(rulesText, BorderLayout.CENTER);
        rulesScreen.add(backButton, BorderLayout.SOUTH);
        
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ showScreen("startScreen"); }
        });
    }
    
    // ---------------- Ship Placement Screen ----------------
    private void createPlacementScreen() {
        placementScreen = new JPanel(new BorderLayout());
        
        placementInstructionLabel = new JLabel("", SwingConstants.CENTER);
        placementInstructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        placementScreen.add(placementInstructionLabel, BorderLayout.NORTH);
        
        JPanel gridPanel = new JPanel(new GridLayout(10, 10));
        for(int i = 0; i < 100; i++){
            final int index = i;
            placementGridButtons[i] = new JButton();
            placementGridButtons[i].setBackground(Color.LIGHT_GRAY);
            placementGridButtons[i].setPreferredSize(new Dimension(50, 50));
            placementGridButtons[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    // When a square is clicked, record its coordinate.
                    int r = index / 10;
                    int c = index % 10;
                    selectedPlacementRow = r;
                    selectedPlacementCol = c;
                    clearPlacementHighlights();
                    placementGridButtons[index].setBackground(Color.YELLOW);
                }
            });
            gridPanel.add(placementGridButtons[i]);
        }
        placementScreen.add(gridPanel, BorderLayout.CENTER);
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        directionComboBox = new JComboBox<>(new String[]{"UP", "DOWN", "LEFT", "RIGHT"});
        placeShipButton = new JButton("Place Ship");
        nextPlayerButton = new JButton("Finish Placement");
        nextPlayerButton.setEnabled(false);
        
        controlPanel.add(new JLabel("Direction:"));
        controlPanel.add(directionComboBox);
        controlPanel.add(placeShipButton);
        controlPanel.add(nextPlayerButton);
        placementScreen.add(controlPanel, BorderLayout.SOUTH);
        
        // Place Ship: verify selection and attempt to place current ship.
        placeShipButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(selectedPlacementRow == -1 || selectedPlacementCol == -1){
                    JOptionPane.showMessageDialog(frame, "Please select a square to place your ship.");
                    return;
                }
                
                char rowChar = (char) ('A' + selectedPlacementRow);
                int colNum = selectedPlacementCol + 1;
                Coordinate startCoord = new Coordinate(rowChar, colNum);
                String dirStr = (String) directionComboBox.getSelectedItem();
                Board.Direction direction;
                try {
                    direction = Board.Direction.valueOf(dirStr.toUpperCase());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid direction selection.");
                    return;
                }
                int shipSize = shipSizes[currentShipIndex];
                String shipType = shipTypes[currentShipIndex];
                Ship ship = new Ship(shipType, shipSize);
                
                if (currentPlacementPlayer.getBoard().canPlaceShip(ship, startCoord, direction)) {
                    List<Coordinate> coords = computeShipCoordinates(startCoord, direction, shipSize);
                    ship.placeShip(coords);
                    currentPlacementPlayer.getShips().add(ship);
                    currentPlacementPlayer.getBoard().placeShip(ship, startCoord, direction);
                    markPlacementCells(coords, Color.GREEN);
                    
                    JOptionPane.showMessageDialog(frame, shipType + " placed at: " + coords.toString());
                    
                    currentShipIndex++;
                    if (currentShipIndex >= shipTypes.length) {
                        nextPlayerButton.setEnabled(true);
                        placeShipButton.setEnabled(false);
                        placementInstructionLabel.setText(currentPlacementPlayer.getName() + 
                            ", you have finished placing ships. Click 'Finish Placement' to pass the board.");
                    } else {
                        updatePlacementInstruction();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid placement for " + shipType + ". Try again.");
                }
            }
        });
        
        nextPlayerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (currentPlacementPlayer == player1) {
                    currentPlacementPlayer = player2;
                    currentShipIndex = 0;
                    resetPlacementGrid();
                    updatePlacementInstruction();
                    placeShipButton.setEnabled(true);
                    nextPlayerButton.setEnabled(false);
                } else {
                    // Both players have placed their ships; switch to battle mode.
                    currentPlayer = player1;
                    currentOpponent = player2;
                    updateStatusLabel();
                    resetGameGrid();
                    showScreen("gameScreen");
                }
            }
        });
    }
    
    // ---------------- Battle (Firing) Screen ----------------
    private void createGameScreen() {
        gameScreen = new JPanel(new BorderLayout());
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gameScreen.add(statusLabel, BorderLayout.NORTH);
        
        JPanel gridPanel = new JPanel(new GridLayout(10, 10));
        for(int i = 0; i < 100; i++){
            final int index = i;
            gameGridButtons[i] = new JButton();
            gameGridButtons[i].setBackground(Color.LIGHT_GRAY);
            gameGridButtons[i].setPreferredSize(new Dimension(50, 50));
            gameGridButtons[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    processShot(index);
                }
            });
            gridPanel.add(gameGridButtons[i]);
        }
        gameScreen.add(gridPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton forfeitButton = new JButton("Forfeit Game");
        JButton exitGameButton = new JButton("Exit Game");
        bottomPanel.add(forfeitButton);
        bottomPanel.add(exitGameButton);
        gameScreen.add(bottomPanel, BorderLayout.SOUTH);
        
        forfeitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to forfeit?", "Forfeit Game", JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(frame, currentPlayer.getName() + " forfeited. " + currentOpponent.getName() + " wins!");
                    System.exit(0);
                }
            }
        });
        
        exitGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }
    
    // ---------------- Helper Methods ----------------
    private List<Coordinate> computeShipCoordinates(Coordinate start, Board.Direction direction, int size) {
        List<Coordinate> coords = new ArrayList<>();
        int startRow = start.getX();
        int startCol = start.getY();
        for (int i = 0; i < size; i++) {
            int newRow = startRow;
            int newCol = startCol;
            switch (direction) {
                case UP:
                    newRow = startRow - i;
                    break;
                case DOWN:
                    newRow = startRow + i;
                    break;
                case LEFT:
                    newCol = startCol - i;
                    break;
                case RIGHT:
                    newCol = startCol + i;
                    break;
            }
            Coordinate c = new Coordinate((char) ('A' + newRow), newCol + 1);
            coords.add(c);
        }
        return coords;
    }
    
    private void updatePlacementInstruction() {
        String shipType = shipTypes[currentShipIndex];
        int shipSize = shipSizes[currentShipIndex];
        placementInstructionLabel.setText(currentPlacementPlayer.getName() + ": Place your " + shipType + " (size " + shipSize + ")");
    }
    
    private void markPlacementCells(List<Coordinate> coords, Color color) {
        for(Coordinate c : coords) {
            int index = c.getX() * 10 + c.getY();
            placementGridButtons[index].setBackground(color);
        }
    }
    
    private void clearPlacementHighlights() {
        for (int i = 0; i < 100; i++){
            if(!placementGridButtons[i].getBackground().equals(Color.GREEN)) {
                placementGridButtons[i].setBackground(Color.LIGHT_GRAY);
            }
        }
    }
    
    private void resetPlacementGrid() {
        for (int i = 0; i < 100; i++){
            placementGridButtons[i].setBackground(Color.LIGHT_GRAY);
        }
        selectedPlacementRow = -1;
        selectedPlacementCol = -1;
    }
    
    private void resetGameGrid() {
        for (int i = 0; i < 100; i++){
            gameGridButtons[i].setBackground(Color.LIGHT_GRAY);
            gameGridButtons[i].setEnabled(true);
        }
    }
    
    private void updateStatusLabel() {
        statusLabel.setText(currentPlayer.getName() + "'s turn. Fire on " + currentOpponent.getName() + "'s board.");
    }
    
    private void processShot(int index) {
        int row = index / 10;
        int col = index % 10;
        Coordinate target = new Coordinate((char) ('A' + row), col + 1);
        
        Board.shotResult result = currentOpponent.getBoard().fire(target);
        if(result == Board.shotResult.ALREADY_SHOT){
            JOptionPane.showMessageDialog(frame, "Already shot here. Choose another square.");
            return;
        } else if(result == Board.shotResult.HIT || result == Board.shotResult.SUNK){
            gameGridButtons[index].setBackground(Color.RED);
            JOptionPane.showMessageDialog(frame, "Hit!");
        } else if(result == Board.shotResult.MISS){
            gameGridButtons[index].setBackground(Color.BLUE);
            JOptionPane.showMessageDialog(frame, "Miss!");
        }
        
        if(checkWinner(currentOpponent)){
            JOptionPane.showMessageDialog(frame, currentPlayer.getName() + " wins the game!");
            System.exit(0);
        }
        
        // Swap turns.
        Player temp = currentPlayer;
        currentPlayer = currentOpponent;
        currentOpponent = temp;
        updateStatusLabel();
        refreshGameGrid();
    }
    
    private void refreshGameGrid() {
        for(int i = 0; i < 100; i++){
            int row = i / 10;
            int col = i % 10;
            Coordinate c = new Coordinate((char) ('A' + row), col + 1);
            if(currentOpponent.getBoard().isShotAt(c)){
                Board.shotResult res = currentOpponent.getBoard().getShotResultAt(c);
                if(res == Board.shotResult.HIT || res == Board.shotResult.SUNK){
                    gameGridButtons[i].setBackground(Color.RED);
                    gameGridButtons[i].setEnabled(false);
                } else if(res == Board.shotResult.MISS){
                    gameGridButtons[i].setBackground(Color.BLUE);
                    gameGridButtons[i].setEnabled(false);
                }
            } else {
                gameGridButtons[i].setBackground(Color.LIGHT_GRAY);
                gameGridButtons[i].setEnabled(true);
            }
        }
    }
    
    private boolean checkWinner(Player player) {
        for(Ship ship : player.getShips()){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
    }
    
    private void showScreen(String name) {
        cardLayout.show(frame.getContentPane(), name);
    }
    
    // ---------------- Main ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new BattleShipUI();
            }
        });
    }
}
