package com.rawad.controller;

import com.rawad.model.GameModel;
import com.rawad.model.Tile;
import com.rawad.model.TileContent;
import com.rawad.model.TileState;
import com.rawad.utils.Difficulty;

/**
 * @author Rawad
 *
 */
public class GameController {

  private GameModel model;

  public void initializeGame(Difficulty difficulty) {

    model = new GameModel();

    model.setDifficulty(difficulty);

    model.setBoard(GameController.generateEmptyBoard(difficulty));

  }

  public void stopGame() {

  }

  public static Tile[][] generateEmptyBoard(Difficulty difficulty) {

    Tile[][] board = new Tile[difficulty.getHeight()][difficulty.getWidth()];

    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {

        Tile tile = new Tile();

        tile.setX(x);
        tile.setY(y);

        tile.setState(TileState.COVERED);
        tile.setContent(TileContent.NONE);

        board[y][x] = tile;

      }
    }

    return board;

  }

  public static Tile[][] generateBoard(Tile[][] board, Difficulty difficulty) {

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
