package com.minesweeper.main;

import com.minesweeper.controller.GameController;
import com.minesweeper.view.GameView;

/**
 * @author Rawad
 *
 */
public class AppStart {

  /**
   * @param args
   */
  public static void main(String[] args) {

    GameView gameView = new GameView();

    GameController controller = new GameController();

    gameView.initialize(controller);

  }

}
