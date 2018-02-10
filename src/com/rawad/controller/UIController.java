package com.rawad.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import com.rawad.utils.Difficulty;
import com.rawad.view.GameView;
import com.rawad.view.TileView;

/**
 * @author Rawad
 *
 */
public class UIController {

  private GameController gameController;
  private GameView gameView;

  /**
   * @param gameController
   * @param gameView
   */
  public UIController(GameController gameController, GameView gameView) {
    super();
    this.gameController = gameController;
    this.gameView = gameView;
  }

  public void addEasyButton(JButton easyButton) {

    easyButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.EASY);
      gameView.createGamePanel(gameController.getModel());
    });

  }

  public void addMediumButton(JButton mediumButton) {

    mediumButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.MEDIUM);
      gameView.createGamePanel(gameController.getModel());
    });

  }

  public void addHardButton(JButton hardButton) {

    hardButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.HARD);
      gameView.createGamePanel(gameController.getModel());
    });

  }

  public void addQuitButton(JButton quitButton) {

    quitButton.addActionListener((e) -> {
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

        if (e.getButton() == MouseEvent.BUTTON1) {
          gameController.revealTile(((TileView) e.getSource()).getTile());
        } else if (e.getButton() == MouseEvent.BUTTON2) {
          gameController.toggleTileMarked(((TileView) e.getSource()).getTile());
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

        frame.dispose();

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
