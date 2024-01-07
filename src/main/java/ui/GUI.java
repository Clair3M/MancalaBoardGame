package ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;

import java.io.IOException;

import mancala.MancalaGame;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;
import mancala.PitNotFoundException;
import mancala.Player;
import mancala.Saver;
import mancala.KalahRules;

/**
 * A class that holds the logic for creating a GUI for MancalaGame
 * 
 * @see MancalaGame
 */
public class GUI extends JFrame  {

    private JPanel gameContainer;
    private JMenuBar menuBar;
    private PositionAwareButton[][] buttons;
    private MancalaGame game; 
    private Player player1;
    private JButton playerOneButton;
    private Player player2;
    private JButton playerTwoButton;
    private JLabel currentPlayer;
    private JLabel invalidMove;

    /**
     * Creates a GUI object
     */
    public GUI() {
        super();
        basicSetUp();
    }

    /**
     * Sets up all of the elements of the GUI
     */
    private void basicSetUp() {
        setTitle("Mancala");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        makeChooseRules();
        makePlayerPanel();

        setupGameContainer();
        makeMenu();
        setJMenuBar(menuBar);
        setPreferredSize(new Dimension(700, 200));
        pack();
        updateView();
    }

    /**
     * Creates a pop up for the user to select between Kalah or Ayoayo game rules
     */
    private void makeChooseRules() {
        String[] options = {"Mancala", "Ayoayo"};
        int choice = JOptionPane.showOptionDialog(this,
            "What Game Mode Would You Like to Play?", "Game Mode",
            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
        int ruleNum = 0;
        if (choice == 0) {
            ruleNum = 1;
            setTitle("Mancala");
        } else if (choice == 1) {
            ruleNum = 2;
            setTitle("Ayoayo");
        } else {
            System.exit(0);
        }
        game = new MancalaGame(ruleNum);
    }

    /**
     * Creates a pop up for the user to enter the names of the two players
     */
    private void makePlayerPanel() {
        String playerOneName = JOptionPane.showInputDialog("Enter Player 1's Name");
        if (playerOneName != null && !playerOneName.isEmpty()){
            player1 = new Player(playerOneName);
        } else {
            player1 = new Player("player1");
        }
        String playerTwoName = JOptionPane.showInputDialog("Enter Player 2's Name");
        if (playerTwoName != null && !playerTwoName.isEmpty()){
            player2 = new Player(playerTwoName);
        } else {
            player2 = new Player("player2");
        }
        game.setPlayers(player1, player2);
    }

    /**
     * Updates the game board with the current status of the pits and stores
     */
    protected void updateView() {
        getContentPane().setBackground(Color.red);
        updatePlayerText();
        invalidMove.setVisible(false);
        int activeSide;
        if (game.getCurrentPlayer().equals(game.getPlayer(1))) {
            activeSide = 1;
        } else if (game.getCurrentPlayer().equals(game.getPlayer(2))) {
            activeSide = 0;
        } else {
            activeSide = -1;
        }
        try {
            for (int y = 0; y < 2; y ++) {
                for (int x = 0; x < 6 ; x++) {
                    buttons[y][x].setText(Integer.toString(
                        game.getNumStones(((-1 + y) * -12) + (x + y + ((-1 + y) * x * 2)))));
                    if (y == activeSide) {
                        buttons[y][x].setEnabled(true);
                    } else {
                        buttons[y][x].setEnabled(false);
                    }
                }
            }
        } catch (PitNotFoundException err) {
            JOptionPane.showMessageDialog(this, err.getMessage(), "Pit Indexing Error Occured",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initializes the buttons that represent the player stores
     */
    private void initializePlayers() {
        playerOneButton = new JButton();
        playerTwoButton = new JButton();
        currentPlayer = new JLabel("", SwingConstants.CENTER);
        updatePlayerText();
    }

    /**
     * Updates the player stores with the current number of pits and displays the current player
     */
    private void updatePlayerText() {
        playerOneButton.setText(Integer.toString(game.getStoreCount(1)) + "\n" + 
            game.getPlayer(1).toString());
        playerTwoButton.setText(Integer.toString(game.getStoreCount(2)) + "\n" + 
            game.getPlayer(2).toString());
        currentPlayer.setText("Current Player: " + game.getCurrentPlayer().getName());
    }

    /**
     * Creates the menu bar
     */
    private void makeMenu() {
        menuBar = new JMenuBar();

        JMenu gameSettings = new JMenu("Game");
        JMenuItem gameRules = new JMenuItem("See Rules");
        gameRules.addActionListener(e -> { displayRules(); });
        JMenuItem newGame = new JMenuItem("New game");
        newGame.addActionListener(e -> { newGame(); });
        JMenuItem saveGame = new JMenuItem("Save game");
        saveGame.addActionListener(e -> { saveGame(); });
        JMenu load = new JMenu("Load");
        JMenuItem loadGame = new JMenuItem("Load game");
        loadGame.addActionListener(e -> { loadGame(); });
        gameSettings.add(gameRules);
        gameSettings.add(newGame);
        gameSettings.add(saveGame);
        gameSettings.add(loadGame);

        JMenu playerSettings = new JMenu("Player");
        JMenuItem savePlayer = new JMenuItem("Save player");
        savePlayer.addActionListener(e -> { savePlayer(); });
        JMenuItem loadPlayer = new JMenuItem("Load player");
        loadPlayer.addActionListener(e -> { loadPlayer(); });
        JMenuItem playerInfo = new JMenuItem("Player info");
        playerInfo.addActionListener(e -> { showPlayerInfo(); });
        playerSettings.add(savePlayer);
        playerSettings.add(loadPlayer);
        playerSettings.add(playerInfo);
        
        menuBar.add(gameSettings);
        menuBar.add(playerSettings);
    }

    private void displayRules() {
        String gameRules = null;
        String fileText = "The text didn't read I guess";
        if (game.getBoard().getClass().equals(KalahRules.class)) {
            gameRules = "src/main/java/mancala/MancalaRules.html";
        } else {
            gameRules = "src/main/java/mancala/AyoayoRules.html";
        }
        try {
            fileText = Saver.readTextFile(gameRules);
        } catch (IOException err) {
            JOptionPane.showMessageDialog(this, "Could Not Open Rules",
                "Could Not Load File", JOptionPane.ERROR_MESSAGE);
        }
        JFrame rulesFrame = new JFrame();
        JLabel rulesText = new JLabel(fileText);
        rulesFrame.setTitle("Rules");
        rulesFrame.add(rulesText);
        rulesFrame.pack();
        rulesFrame.setVisible(true);
    }

    /**
     * Prompts the user for creating a new game
     */
    private void newGame() {
        int selection = JOptionPane.showConfirmDialog(null, 
        "<html>Starting a new game will delete current game.<br/>Would you like to save?<html/>",
        "Save Game?", JOptionPane.YES_NO_OPTION);
        if (selection == 0) {
            saveGame();
            restart();
        } else if (selection == 1) {
            restart();
        }
    }

    /**
     * Saves the current game to a file
     */
    private void saveGame() {
        String saveFileName = JOptionPane.showInputDialog("Enter file name to save to");
        try {
            if (saveFileName != null && saveFileName != ""){
                Saver.saveObject(game, saveFileName);
                JOptionPane.showMessageDialog(this, "File Saved", "File Saved", 
                    JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException err) {
            JOptionPane.showMessageDialog(this, err.getMessage(), "Could Not Save File", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Save a selected player to a file
     */
    private void savePlayer() {
        String[] options = {game.getPlayer(1).getName(), game.getPlayer(2).getName()};
        Player player;
        int choice = JOptionPane.showOptionDialog(
            this,
            "Which player do you want to save?", 
            "Save Player",
            JOptionPane.YES_NO_CANCEL_OPTION, 
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        if (choice == 1) {
            player = game.getPlayer(2);
        } else if (choice == 0) {
            player = game.getPlayer(1);
        } else {
            return;
        }
        String saveFileName = JOptionPane.showInputDialog("Enter file name to save to");
        try {
            Saver.saveObject(player, saveFileName);
        } catch (IOException err) {
            JOptionPane.showMessageDialog(this, "No file name provided", 
                "Could Not Save Player to File", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads a game from a provided file
     */
    private void loadGame() {
        String saveFileName = JOptionPane.showInputDialog("Enter file name to load from");
        try {
            game = (MancalaGame) Saver.loadObject(saveFileName);
            player1 = game.getPlayer(1);
            player2 = game.getPlayer(2);
        } catch (IOException err) {
            JOptionPane.showMessageDialog(this, "Something went wrong", "Could Not Load File",
                JOptionPane.ERROR_MESSAGE);
        } catch (ClassCastException err) {
            JOptionPane.showMessageDialog(this, "File does not contain a game",
                "Could Not Load File", JOptionPane.ERROR_MESSAGE);
        }
        updateView();
        pack();
    }

    /**
     * Loads a player from a provided files
     */
    private void loadPlayer() {
        String saveFileName = JOptionPane.showInputDialog("Enter file name to load from");
        if (saveFileName == null) {
            return;
        }
        Player newPlayer;
        try {
            String[] options = {game.getPlayer(1).getName(), game.getPlayer(2).getName()};
            newPlayer = (Player) Saver.loadObject(saveFileName);
            int choice = JOptionPane.showOptionDialog(this, 
                "Which player do you want to replace?", "Load Player",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
            if (choice == 1) {
                player2 = newPlayer;
                game.replacePlayer(newPlayer, 2);
            } else if (choice == 0) {
                player1 = newPlayer;
                game.replacePlayer(newPlayer, 1);
            } else {
                JOptionPane.showMessageDialog(this,"Cancel" ,"Canceling Player Load", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException err) {
            JOptionPane.showMessageDialog(this, "Something went wrong", "Could Not Load File",
                JOptionPane.ERROR_MESSAGE);
        } catch (ClassCastException err) {
            JOptionPane.showMessageDialog(this, "File does not contain player profile",
                "Could Not Load File", JOptionPane.ERROR_MESSAGE);
        }
        updateView();
        pack();
    }

    /**
     * Creates a pop up that displays the game statistics of the current players
     */
    private void showPlayerInfo() {
        JFrame playerPanel = new JFrame();
        playerPanel.setTitle("Player Info");
        playerPanel.setLayout(new BorderLayout());
        JPanel p1Panel = makePlayerInfoPanel(game.getPlayer(1));
        p1Panel.setPreferredSize( new Dimension(150, 80));
        JPanel p2Panel = makePlayerInfoPanel(game.getPlayer(2));
        playerPanel.add(p1Panel, BorderLayout.LINE_START);
        playerPanel.add(p2Panel, BorderLayout.LINE_END);
        playerPanel.pack();
        playerPanel.setVisible(true);
    }
    
    /**
     * Creates a panel that holds the game statistics of a player
     * 
     * @param player the player who's statistics will be displayed
     * @return A JPanel that holds the statistics of the given player
     */
    private JPanel makePlayerInfoPanel(Player player) {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        JLabel playerName = new JLabel(player.getName(), SwingConstants.CENTER);
        JLabel playerInfo = new JLabel(player.getUserProfile().toStringHTML());
        playerPanel.add(playerName);
        playerPanel.add(playerInfo);
        return playerPanel;
    }

    /**
     * Calls the move method of the Mancala Game class for a chosen pit
     * 
     * @param e the event that triggered the move
     */
    private void makeMove(ActionEvent e) {
        PositionAwareButton clicked = (PositionAwareButton) (e.getSource());
        try {
            game.move(((-1 + (clicked.getDown() - 1)) * -12) + ((clicked.getAcross() - 1) +
                (clicked.getDown() - 1) + ((-1 + (clicked.getDown() - 1)) * 
                (clicked.getAcross() - 1) * 2)));
            updateView();
        } catch (InvalidMoveException err) {
            invalidMove.setText(err.getMessage());
            invalidMove.setVisible(true);
        }
    }

    /**
     * Creates the label that tells the player if a move is invalid
     */
    private void makeInvalidMove() {
        invalidMove = new JLabel("Invalid Move", SwingConstants.CENTER);
        invalidMove.setVisible(false);
    }

    /**
     * Checks if the game is over and ends the game if it is
     */
    private void checkGameState() {
        int selection = 0;
        JOptionPane gameOver = new JOptionPane();
        
        String button = "Play Again?";
        if (game.isGameOver()) {
            game.addGamePlayed();
            Player winner = null;
            try{
                winner = game.getWinner();
            } catch (GameNotOverException err) {
                return;
            }
            updateView();
            StringBuilder congrats = new StringBuilder();
            congrats.append("<html>");
            if (winner.equals(null)) {
                congrats.append("Tie Game!");
            } else {
                congrats.append(winner.toString() + " Wins!");
                game.addPlayerWin(winner);
            }
            congrats.append("<br/>Play again?<html/>");
            //make it so that the number of game won/player increases
            selection = gameOver.showConfirmDialog(null, congrats, button, 
                JOptionPane.YES_NO_OPTION);
            if (selection == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else {
                restart();
            }
        }
    }

    /**
     * Creates the main game board pit buttons
     * 
     * @param wide the number of pits wide the panel is
     * @param tall the number of pits tall the panel is
     * @return The panel with the game board pit buttons
     */
    private JPanel makeGameBoardButtons(int wide, int tall) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(tall,wide));
        for (int y = 0; y < tall; y++) {
                for (int x = 0; x < wide; x++) {
                    buttons[y][x] = new PositionAwareButton();
                    buttons[y][x].setAcross(x + 1);
                    buttons[y][x].setDown(y + 1);
                    buttons[y][x].addActionListener(e -> {
                        makeMove(e);
                        checkGameState();
                    });
                    panel.add(buttons[y][x]);
                }
            }
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Restarts the game and prompts the user if they want to use the same players
     */
    protected void restart() {
        int newPlayers;
        makeChooseRules();
        newPlayers = JOptionPane.showOptionDialog(this, "Do you want to use new players?", 
            "New Players", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, null, null);
        if (newPlayers == 0) {
            makePlayerPanel();
        }
        game.setPlayers(player1, player2);
        updateView();
    }

    /**
     * Sets up the main game panel
     */
    private void setupGameContainer(){
        gameContainer = new JPanel(new BorderLayout());
        gameContainer.setBackground(new Color(250, 212, 255));
        add(gameContainer, BorderLayout.CENTER);
        initializePlayers();
        makeInvalidMove();
        gameContainer.add(invalidMove, BorderLayout.PAGE_END);
        gameContainer.add(makeGameBoardButtons(6, 2), BorderLayout.CENTER);
        gameContainer.add(playerOneButton, BorderLayout.LINE_END);
        gameContainer.add(playerTwoButton, BorderLayout.LINE_START);
        gameContainer.add(currentPlayer, BorderLayout.PAGE_START);
    }

    public static void main(String[] args) {
        GUI mancala = new GUI();
        mancala.setVisible(true);
    }
}