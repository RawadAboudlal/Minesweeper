package com.minesweeper.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.AbstractSpinnerModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import com.minesweeper.model.GameModel;
import com.minesweeper.model.GameState;
import com.minesweeper.model.Tile;
import com.minesweeper.utils.Difficulty;
import com.minesweeper.view.GameView;
import com.minesweeper.view.TileView;

/**
 * @author Rawad
 *
 */
public class UIController {

  private static final int MAX_WIDTH = 100;
  private static final int MAX_HEIGHT = 45;

  private static final int MIN_WIDTH = 2;
  private static final int MIN_HEIGHT = 2;

  private static final int MAX_NUMBER_OF_MINES = (MAX_WIDTH - 1) * (MAX_HEIGHT - 1);
  private static final int MIN_NUMBER_OF_MINES = 2;

  private static final int DEFAULT_WIDTH = Difficulty.EASY.getWidth();
  private static final int DEFAULT_HEIGHT = Difficulty.EASY.getHeight();

  private static final int DEFAULT_NUMBER_OF_MINES = Difficulty.EASY.getNumberOfMines();

  private GameController gameController;
  private GameView gameView;
  private GameModel gameModel;

  private JSpinner widthSelector;
  private JSpinner heightSelector;
  private JSpinner numberOfMinesSelector;

  /**
   * @param gameController
   * @param gameView
   */
  public UIController(GameController gameController, GameView gameView) {
    super();
    this.gameController = gameController;
    this.gameView = gameView;
    this.gameModel = gameController.getModel();
  }

  public void addEasyButton(JButton easyButton) {

    easyButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.EASY);
      gameView.setupGame();
      gameView.showGamePanel();
    });

  }

  public void addMediumButton(JButton mediumButton) {

    mediumButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.MEDIUM);
      gameView.setupGame();
      gameView.showGamePanel();
    });

  }

  public void addHardButton(JButton hardButton) {

    hardButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.HARD);
      gameView.setupGame();
      gameView.showGamePanel();
    });

  }

  public void addCustomGameButton(JButton customGameButton) {

    customGameButton.addActionListener((e) -> {
      gameView.showCustomGamePanel();
    });

  }


  public void addQuitButton(JButton quitButton) {

    quitButton.addActionListener((e) -> {
      gameController.terminate();
      gameView.stop();
    });

  }

  public void addMainMenuButton(JButton menuButton) {

    menuButton.addActionListener((e) -> {
      gameView.showMenuPanel();
    });

  }

  public void addResetButton(JButton resetButton) {

    resetButton.addActionListener((e) -> {
      gameController.reset();
      gameView.reset();
    });

  }

  public void addChangeDifficultyButton(JButton changeDifficultyButton) {

    changeDifficultyButton.addActionListener((e) -> {
      gameController.stopGame();
      gameView.showMenuPanel();
    });

  }

  public void addPlayCustomDifficultyButton(JButton playCustomDifficultyButton,
      JSpinner widthSelector, JSpinner heightSelector, JSpinner numberOfMinesSelector) {

    this.widthSelector = widthSelector;
    this.heightSelector = heightSelector;
    this.numberOfMinesSelector = numberOfMinesSelector;

    playCustomDifficultyButton.addActionListener((e) -> {

      int width = (int) UIController.this.widthSelector.getValue();
      int height = (int) UIController.this.heightSelector.getValue();
      int numberOfMines = (int) UIController.this.numberOfMinesSelector.getValue();

      int maxNumberOfMines = UIController.getMaxNumberOfMines(width, height);

      if (numberOfMines > maxNumberOfMines) {
        System.err.println(String.format(
            "Maximum number of mines exceeded; using the current maximum, %s, instead.",
            maxNumberOfMines));
        numberOfMines = maxNumberOfMines;
      }

      gameController.initializeGame(new Difficulty(width, height, numberOfMines));
      gameView.setupGame();
      gameView.showGamePanel();

    });

  }

  public void addTileView(TileView tileView) {

    tileView.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent e) {}

      @Override
      public void mousePressed(MouseEvent e) {}

      @Override
      public void mouseExited(MouseEvent e) {

        TileView tile = (TileView) e.getSource();

        tile.setHovered(false);
        tile.repaint();

      }

      @Override
      public void mouseEntered(MouseEvent e) {

        TileView tile = (TileView) e.getSource();

        tile.setHovered(true);
        tile.repaint();

      }

      @Override
      public void mouseClicked(MouseEvent e) {

        TileView tile = (TileView) e.getSource();

        if (!tile.isEnabled()) {
          return;
        }

        if (e.getButton() == MouseEvent.BUTTON1) {

          GameState prevState = gameModel.getGameState();

          Tile[] tilesToUpdate = gameController.revealTile(tile.getTile());

          if (prevState == GameState.INITIAL && gameModel.getGameState() == GameState.PLAYING) {
            gameView.postInitialGameState();
          }

          for (Tile tileToUpdate : tilesToUpdate) {
            gameView.updateTile(tileToUpdate);
          }

          if (gameModel.getGameState() == GameState.WIN) {
            gameView.showWin();
          } else if (gameModel.getGameState() == GameState.LOSS) {
            gameView.showLoss(gameController.getLastRevealedTile());
          }

        } else if (e.getButton() == MouseEvent.BUTTON3) {

          gameController.toggleTileFlagged(tile.getTile());
          gameView.updateTile(tile.getTile());

          gameView.updateNumberOfFlagged(gameController.getFlaggedTiles(),
              gameModel.getDifficulty().getNumberOfMines());

          tile.repaint();

        }

      }

    });

  }

  public void addFrame(JFrame frame) {

    frame.addWindowListener(new WindowListener() {

      @Override
      public void windowOpened(WindowEvent e) {}

      @Override
      public void windowClosing(WindowEvent e) {

        gameController.stopGame();

        gameView.stop();

      }

      @Override
      public void windowClosed(WindowEvent e) {}

      @Override
      public void windowIconified(WindowEvent e) {}

      @Override
      public void windowDeiconified(WindowEvent e) {}

      @Override
      public void windowActivated(WindowEvent e) {}

      @Override
      public void windowDeactivated(WindowEvent e) {}

    });

  }

  /**
   * @return the gameController
   */
  public GameController getGameController() {
    return gameController;
  }
  
  public static final AbstractSpinnerModel getWidthSpinnerEditor() {
    return new SpinnerNumberModel(DEFAULT_WIDTH, MIN_WIDTH, MAX_WIDTH, 1);
  }

  public static final AbstractSpinnerModel getHeightSpinnerEditor() {
    return new SpinnerNumberModel(DEFAULT_HEIGHT, MIN_HEIGHT, MAX_HEIGHT, 1);
  }

  public static final AbstractSpinnerModel getNumberOfMinesSpinnerEditor() {
    return new SpinnerNumberModel(DEFAULT_NUMBER_OF_MINES, MIN_NUMBER_OF_MINES, MAX_NUMBER_OF_MINES,
        1);
  }

  public static final int getMaxNumberOfMines(int width, int height) {
    return (width - 1) * (height - 1);
  }

}
