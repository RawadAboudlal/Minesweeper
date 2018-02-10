package com.rawad.main;

import com.rawad.controller.GameController;
import com.rawad.model.GameModel;
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

    GameModel gameModel = new GameModel();
    GameView gameView = new GameView(gameModel);

    GameController controller = new GameController();

    gameView.initialize(controller);

  }

}
