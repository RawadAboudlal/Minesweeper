package com.minesweeper.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

  private static final int DEFAULT_WIDTH = 640;
  private static final int DEFAULT_HEIGHT = 480;

  private JFrame frame;

  private JPanel basePanel;

  private JPanel menuPanel;
  private JPanel gamePanel;

  private JPanel boardPanel;
  private JLabel flaggedLabel;

  private JButton resetButton;

  private CardLayout cardLayout;
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

    frame = new JFrame(GAME_NAME);

    basePanel = new JPanel();
    menuPanel = new JPanel();
    gamePanel = new JPanel();

    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

    JButton easyDifficultyButton = new JButton("Easy");
    JButton mediumDifficultyButton = new JButton("Medium");
    JButton hardDifficultyButton = new JButton("Hard");
    JButton quitButton = new JButton("Quit");

    uiController.addEasyButton(easyDifficultyButton);
    uiController.addMediumButton(mediumDifficultyButton);
    uiController.addHardButton(hardDifficultyButton);
    uiController.addQuitButton(quitButton);

    menuPanel.add(easyDifficultyButton);
    menuPanel.add(mediumDifficultyButton);
    menuPanel.add(hardDifficultyButton);
    menuPanel.add(quitButton);

    this.createGamePanel();

    cardLayout = new CardLayout();

    basePanel.setLayout(cardLayout);

    basePanel.add(menuPanel, MENU);
    basePanel.add(gamePanel, GAME);

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

    gamePanel.setPreferredSize(null);
    menuPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

    cardLayout.show(basePanel, MENU);

    frame.pack();

  }

  public void showGamePanel() {

    menuPanel.setPreferredSize(null);

    cardLayout.show(basePanel, GAME);

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
