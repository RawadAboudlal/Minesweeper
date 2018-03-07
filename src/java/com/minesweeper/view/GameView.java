package com.minesweeper.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import com.minesweeper.controller.GameController;
import com.minesweeper.controller.UIController;
import com.minesweeper.model.GameModel;
import com.minesweeper.model.Tile;
import com.minesweeper.model.TileContent;
import com.minesweeper.model.TileState;
import com.minesweeper.view.textures.Textures;

/**
 * @author Rawad
 *
 */
public class GameView {

  private static final String GAME_NAME = "Minesweeper";

  private static final String MENU = "menu";
  private static final String GAME = "game";
  private static final String CUSTOM_GAME = "custom";

  private static final int DEFAULT_WIDTH = 640;
  private static final int DEFAULT_HEIGHT = 480;

  private JFrame frame;

  private JPanel basePanel;

  private JPanel menuPanel;
  private JPanel gamePanel;
  private JPanel customGamePanel;

  private JPanel boardPanel;
  private JLabel flaggedLabel;

  private JButton resetButton;

  private CustomCardLayout cardLayout;
  private GridLayout boardLayout;

  private UIController uiController;

  private TileView[][] tileViews;

  private GameModel gameModel;

  /**
   * Must be called from the AWT Event Dispatch Thread.
   * 
   * @param gameController
   */
  public void initialize(GameController gameController) {

    uiController = new UIController(gameController, this);

    this.gameModel = gameController.getModel();

    Textures.loadTextures();

    EventQueue.invokeLater(() -> {
      GameView.this.initGui();
    });

  }

  private void initGui() {

    menuPanel = new JPanel();

    GridBagLayout menuLayout = new GridBagLayout();

    menuPanel.setLayout(menuLayout);

    JButton easyDifficultyButton = new JButton("Easy");
    JButton mediumDifficultyButton = new JButton("Medium");
    JButton hardDifficultyButton = new JButton("Hard");
    JButton customGameButton = new JButton("Custom...");
    JButton quitButton = new JButton("Quit");

    uiController.addEasyButton(easyDifficultyButton);
    uiController.addMediumButton(mediumDifficultyButton);
    uiController.addHardButton(hardDifficultyButton);
    uiController.addCustomGameButton(customGameButton);
    uiController.addQuitButton(quitButton);

    GridBagConstraints constraints = new GridBagConstraints();

    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.insets = new Insets(0, 0, 10, 0);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    menuPanel.add(easyDifficultyButton, constraints);

    constraints.gridx = 1;
    constraints.gridy = 2;
    constraints.insets = new Insets(0, 0, 10, 0);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    menuPanel.add(mediumDifficultyButton, constraints);

    constraints.gridx = 1;
    constraints.gridy = 3;
    constraints.insets = new Insets(0, 0, 10, 0);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    menuPanel.add(hardDifficultyButton, constraints);

    constraints.gridx = 1;
    constraints.gridy = 4;
    constraints.insets = new Insets(0, 0, 10, 0);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    menuPanel.add(customGameButton, constraints);

    constraints.gridx = 1;
    constraints.gridy = 5;
    constraints.insets = new Insets(0, 0, 0, 0);
    constraints.fill = GridBagConstraints.HORIZONTAL;

    menuPanel.add(quitButton, constraints);

    menuPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

    gamePanel = new JPanel();
    this.createGamePanel();

    customGamePanel = new JPanel();

    customGamePanel.setLayout(new GridBagLayout());

    JLabel widthLabel = new JLabel("Width");
    JLabel heightLabel = new JLabel("Height");
    JLabel numberOfMinesLabel = new JLabel("Number of Mines");

    JSpinner widthSelector = new JSpinner();
    JSpinner heightSelector = new JSpinner();
    JSpinner numberOfMinesSelector = new JSpinner();

    widthSelector.setEditor(new JSpinner.NumberEditor(widthSelector));
    heightSelector.setEditor(new JSpinner.NumberEditor(heightSelector));
    numberOfMinesSelector.setEditor(new JSpinner.NumberEditor(numberOfMinesSelector));

    JButton playCustomGameButton = new JButton("Play");
    JButton mainMenuButton = new JButton("Main Menu");

    uiController.addMainMenuButton(mainMenuButton);

    constraints = new GridBagConstraints();

    customGamePanel.add(widthLabel, constraints);

    customGamePanel.add(heightLabel, constraints);

    customGamePanel.add(numberOfMinesLabel, constraints);

    customGamePanel.add(widthSelector, constraints);

    customGamePanel.add(heightSelector, constraints);

    customGamePanel.add(numberOfMinesSelector, constraints);

    customGamePanel.add(playCustomGameButton, constraints);

    customGamePanel.add(mainMenuButton, constraints);

    cardLayout = new CustomCardLayout();

    basePanel = new JPanel(cardLayout);

    basePanel.add(menuPanel, MENU);
    basePanel.add(gamePanel, GAME);
    basePanel.add(customGamePanel, CUSTOM_GAME);

    frame = new JFrame(GAME_NAME);

    frame.setContentPane(basePanel);

    uiController.addFrame(frame);

    frame.setLocationByPlatform(true);

    this.showMenuPanel();

    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setVisible(true);

  }

  private void createGamePanel() {

    boardPanel = new JPanel();
    boardLayout = new GridLayout(1, 1);

    boardPanel.setLayout(boardLayout);

    gamePanel.add(boardPanel);

    JPanel userFeedbackPanel = new JPanel();

    BoxLayout userFeedbackLayout = new BoxLayout(userFeedbackPanel, BoxLayout.PAGE_AXIS);
    userFeedbackPanel.setLayout(userFeedbackLayout);

    flaggedLabel = new JLabel();
    this.updateNumberOfFlagged(0, 0);

    userFeedbackPanel.add(flaggedLabel);

    resetButton = new JButton();
    JButton changeDifficultyButton = new JButton("Change Difficulty");

    uiController.addResetButton(resetButton);
    uiController.addChangeDifficultyButton(changeDifficultyButton);

    userFeedbackPanel.add(resetButton);
    userFeedbackPanel.add(changeDifficultyButton);

    gamePanel.add(userFeedbackPanel);

  }

  public void setupGame() {

    boardPanel.removeAll();

    Tile[][] board = gameModel.getBoard();

    boardLayout.setColumns(gameModel.getDifficulty().getWidth());
    boardLayout.setRows(gameModel.getDifficulty().getHeight());

    tileViews =
        new TileView[gameModel.getDifficulty().getHeight()][gameModel.getDifficulty().getWidth()];

    for (int y = 0; y < board.length; y++) {

      Tile[] row = board[y];

      for (int x = 0; x < row.length; x++) {

        Tile tile = row[x];

        TileView tileView = new TileView(tile);
        tileViews[y][x] = tileView;

        uiController.addTileView(tileView);

        boardPanel.add(tileView);

      }

    }

    boardPanel.repaint();

    resetButton.setText("Start Over");
    resetButton.setEnabled(false);
    resetButton.repaint();

    this.updateNumberOfFlagged(uiController.getGameController().getFlaggedTiles(),
        gameModel.getDifficulty().getNumberOfMines());

    frame.pack();

  }

  public void reset() {

    // Don't reset if the frame is not made.
    if (frame != null) {

      this.setupGame();

    }

  }

  public void showMenuPanel() {

    cardLayout.show(basePanel, MENU);

    resetMinimumSize();

  }

  public void showGamePanel() {

    cardLayout.show(basePanel, GAME);

    resetMinimumSize();

  }

  public void showCustomGamePanel() {

    cardLayout.show(basePanel, CUSTOM_GAME);

    resetMinimumSize();

  }

  private void resetMinimumSize() {

    Insets insets = frame.getInsets();

    Dimension currentCompSize = cardLayout.findCurrentComponent(basePanel).getPreferredSize();

    frame.setMinimumSize(new Dimension(currentCompSize.width + insets.left + insets.right,
        currentCompSize.height + insets.top + insets.bottom));

    frame.pack();

  }

  public void postInitialGameState() {

    resetButton.setEnabled(true);
    resetButton.repaint();

  }

  public void showLoss(Tile lostTile) {

    tileViews[lostTile.getY()][lostTile.getX()].setTriggered();
    this.updateTile(lostTile);

    resetButton.setText("Play Again");
    resetButton.repaint();

    this.disableAllTiles();
    this.revealRemainingMines();

  }

  public void showWin() {

    this.disableAllTiles();
    this.revealRemainingMines();

    JOptionPane.showMessageDialog(frame, String.format("Congratulations!"));

  }

  private void disableAllTiles() {

    for (Component tileView : boardPanel.getComponents()) {
      tileView.setEnabled(false);
    }

  }

  private void revealRemainingMines() {

    // Shows all the remaining mines.
    for (int y = 0; y < gameModel.getDifficulty().getHeight(); y++) {
      for (int x = 0; x < gameModel.getDifficulty().getWidth(); x++) {

        TileView tileView = tileViews[y][x];

        Tile tile = gameModel.getBoard()[y][x];

        if (tile.getContent() == TileContent.MINE && tile.getState() != TileState.FLAGGED) {
          tile.setState(TileState.OPENED);
          tileView.update();
        }

      }
    }

  }

  public void updateTile(Tile tile) {
    tileViews[tile.getY()][tile.getX()].update();
  }

  public void updateNumberOfFlagged(int numberOfFlagged, int numberOfMines) {
    flaggedLabel.setText(String.format("Flagged: %s/%s", numberOfFlagged, numberOfMines));
    flaggedLabel.repaint();
  }

  public void stop() {
    frame.dispose();
  }

}
