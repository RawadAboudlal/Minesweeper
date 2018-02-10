package com.rawad.controller;

import javax.swing.JButton;
import com.rawad.utils.Difficulty;
import com.rawad.view.GameView;

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
      gameView.showGamePanel(gameController.getModel());
    });

  }

  public void addMediumButton(JButton mediumButton) {

    mediumButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.MEDIUM);
      gameView.showGamePanel(gameController.getModel());
    });

  }

  public void addHardButton(JButton hardButton) {

    hardButton.addActionListener((e) -> {
      gameController.initializeGame(Difficulty.HARD);
      gameView.showGamePanel(gameController.getModel());
    });

  }

  public void addQuitButton(JButton quitButton) {

    quitButton.addActionListener((e) -> {
      gameController.stopGame();
      gameView.showMenuPanel();
    });

  }

  /**
   * @return the gameController
   */
  public GameController getGameController() {
    return gameController;
  }

}
