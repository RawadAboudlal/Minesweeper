package com.rawad.main;

import com.rawad.controller.GameController;
import com.rawad.view.GameView;

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
