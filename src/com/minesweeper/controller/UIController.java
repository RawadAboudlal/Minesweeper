package com.minesweeper.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
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

  private GameController gameController;
  private GameView gameView;
  private GameModel gameModel;

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

  public void addQuitButton(JButton quitButton) {

    quitButton.addActionListener((e) -> {
      gameController.terminate();
      gameView.stop();
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

  public void addTileView(TileView tileView) {

    tileView.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent e) {}

      @Override
      public void mousePressed(MouseEvent e) {}

      @Override
      public void mouseExited(MouseEvent e) {}

      @Override
      public void mouseEntered(MouseEvent e) {}

      @Override
      public void mouseClicked(MouseEvent e) {

        TileView tile = (TileView) e.getSource();

        if (!tile.isEnabled()) {
          return;
        }

        if (e.getButton() == MouseEvent.BUTTON1) {

          Tile[] tilesToUpdate = gameController.revealTile(tile.getTile());

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

}
