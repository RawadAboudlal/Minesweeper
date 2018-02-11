package com.rawad.controller;

import java.util.ArrayDeque;
import java.util.Random;
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

  private Random random;

  public void initializeGame(Difficulty difficulty) {

    model = new GameModel();

    model.setDifficulty(difficulty);
    model.setBoard(GameController.generateEmptyBoard(difficulty));

    random = new Random();

  }

  public void stopGame() {

    model = null;

    random = null;

  }

  public void revealTile(Tile tile) {

    switch (tile.getState()) {
      case COVERED:
        tile.setState(TileState.OPENED);

        this.tileRevealed(tile);

      case FLAGGED:
      case OPENED:
        break;
    }

  }

  private void tileRevealed(Tile tile) {

    if (tile.getContent() == TileContent.MINE) {
      // game over
    } else if (tile.getContent() == TileContent.NONE) {
      this.reavealNeighboringTiles(tile);
    }

  }

  private void reavealNeighboringTiles(Tile tile) {

    ArrayDeque<Tile> tilesToCheck = new ArrayDeque<Tile>();
    ArrayDeque<Tile> checkedTiles = new ArrayDeque<Tile>();

    tilesToCheck.push(tile);

    while (!tilesToCheck.isEmpty()) {

      Tile currentTile = tilesToCheck.pop();

      if (checkedTiles.contains(currentTile)) {
        continue;
      }

      for (int deltaX = -1; deltaX <= 1; deltaX++) {

        int x = currentTile.getX() + deltaX;

        if (x < 0 || x >= model.getDifficulty().getWidth()) {
          continue;
        }

        for (int deltaY = -1; deltaY <= 1; deltaY++) {

          int y = currentTile.getY() + deltaY;

          if (y < 0 || y >= model.getDifficulty().getHeight()) {
            continue;
          }

          Tile tileToCheck = model.getBoard()[y][x];

          if (tileToCheck.getContent() == TileContent.NONE) {
            tileToCheck.setState(TileState.OPENED);
            tilesToCheck.push(tileToCheck);
          } else if (tileToCheck.getContent() == TileContent.MINE) {
            continue;
          } else {
            tileToCheck.setState(TileState.OPENED);
          }

        }
      }

      checkedTiles.push(currentTile);

    }

  }

  public void toggleTileMarked(Tile tile) {

    switch (tile.getState()) {
      case COVERED:
        tile.setState(TileState.FLAGGED);
        break;
      case FLAGGED:
        tile.setState(TileState.COVERED);
        break;
      case OPENED:
        break;
    }

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
