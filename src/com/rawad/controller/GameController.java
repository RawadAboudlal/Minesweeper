package com.rawad.controller;

import com.rawad.model.GameModel;
import com.rawad.model.Tile;
import com.rawad.utils.Difficulty;

/**
 * @author Rawad
 *
 */
public class GameController {

  private GameModel model;

  private Difficulty difficulty;

  public void initializeGame(Difficulty difficulty) {

    this.difficulty = difficulty;

    this.model = new GameModel();

  }

  public void stopGame() {

  }

  /**
   * {@code startingTileX}, {@code startingTileY} should not be a bomb.
   * 
   * @param difficulty
   * @param startingTileX
   * @param startingTileY
   * @return
   */
  public static Tile[][] generateBoard(Difficulty difficulty, int startingTileX,
      int startingTileY) {

    Tile[][] board = new Tile[difficulty.getWidth()][difficulty.getHeight()];

    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        // TODO: Board generator.
      }
    }

    return board;

  }

  /**
   * @return the model
   */
  public GameModel getModel() {
    return model;
  }

}
