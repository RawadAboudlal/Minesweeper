package com.minesweeper.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import com.minesweeper.model.GameModel;
import com.minesweeper.model.Tile;
import com.minesweeper.model.TileContent;
import com.minesweeper.model.TileState;
import com.minesweeper.utils.Difficulty;

/**
 * @author Rawad
 *
 */
public class GameController {

  private GameModel model = new GameModel();

  private Random random;

  private int flaggedTiles;

  private boolean firstTile;

  public void initializeGame(Difficulty difficulty) {

    model.setDifficulty(difficulty);
    model.setBoard(GameController.generateEmptyBoard(difficulty));

    random = new Random();

    flaggedTiles = 0;

    firstTile = true;

  }

  public void reset() {

    // Don't reset if the game has not started.
    if (model != null) {
      this.initializeGame(model.getDifficulty());
    }

  }

  public void stopGame() {

    random = null;

  }

  public void terminate() {

    model = null;

  }

  public Tile[] revealTile(Tile tile) {

    switch (tile.getState()) {
      case COVERED:
      case FLAGGED_UNSURE:

        tile.setState(TileState.OPENED);

        if (firstTile) {

          tile.setContent(TileContent.NONE);

          this.generateBoard();

          firstTile = false;

        }

        return this.tileRevealed(tile);

      case FLAGGED:
      case OPENED:
        break;
    }

    return new Tile[0];

  }

  private Tile[] tileRevealed(Tile tile) {

    if (tile.getContent() == TileContent.MINE) {
      // game over
    } else if (tile.getContent() == TileContent.NONE) {
      return this.reavealNeighboringTiles(tile);
    }

    return new Tile[] {tile};

  }

  /**
   * <p>
   * Goes through all the tiles neighboring {@code tile} and reveals them based on the following
   * rules:
   * <ol>
   * <li>We check the 8 tiles around the starting {@code tile}, by going from -1 to +1 x and -1 to
   * +1 y.</li>
   * <li>If the expected position is outside the range of the board, we skip that tile.</li>
   * <li>If the new tile is open or flagged we skip it. If it covered we open it.</li>
   * <li>If this newly opened tile has no neighbouring mines (a {@code TIleContent} of {@code NONE})
   * we will open all neighbouring tiles as well until only numbered tiles are opened or the edge is
   * reached.</li>
   * </ol>
   * </p>
   * 
   * <p>
   * This method will return all the tiles that were revealed.
   * </P>
   * 
   * @param tile
   * @return
   */
  private Tile[] reavealNeighboringTiles(Tile tile) {

    ArrayDeque<Tile> tilesToCheck = new ArrayDeque<Tile>();
    ArrayList<Tile> checkedTiles = new ArrayList<Tile>();
    ArrayList<Tile> updatedTiles = new ArrayList<Tile>();

    tilesToCheck.push(tile);
    updatedTiles.add(tile);

    while (!tilesToCheck.isEmpty()) {

      Tile currentTile = tilesToCheck.pop();

      for (int deltaX = -1; deltaX <= 1; deltaX++) {

        int x = currentTile.getX() + deltaX;

        if (!this.isXOnBoard(x)) {
          continue;
        }

        for (int deltaY = -1; deltaY <= 1; deltaY++) {

          int y = currentTile.getY() + deltaY;

          if (!this.isYOnBoard(y)) {
            continue;
          }

          // Skip if original, center tile which wass already previously opened.
          if (deltaX == 0 && deltaY == 0) {
            continue;
          }

          Tile tileToCheck = model.getBoard()[y][x];

          if (checkedTiles.contains(tileToCheck)) {
            continue;
          }

          // Only uncover COVERED and FLAGGED_UNSURE tiles.
          if (!(tileToCheck.getState() == TileState.COVERED
              || tileToCheck.getState() == TileState.FLAGGED_UNSURE)) {
            continue;
          }

          if (tileToCheck.getContent() == TileContent.NONE) {

            tileToCheck.setState(TileState.OPENED);
            // All tiles next an open tile are good so they should be opened as well.
            tilesToCheck.push(tileToCheck);

            updatedTiles.add(tileToCheck);

          } else if (tileToCheck.getContent() == TileContent.MINE) {
            continue;
          } else {

            tileToCheck.setState(TileState.OPENED);

            updatedTiles.add(tileToCheck);

          }

          checkedTiles.add(tileToCheck);

        }
      }

    }

    return updatedTiles.toArray(new Tile[updatedTiles.size()]);

  }

  public void toggleTileFlagged(Tile tile) {

    switch (tile.getState()) {
      case COVERED:
        tile.setState(TileState.FLAGGED);
        flaggedTiles++;
        break;
      case FLAGGED:
        tile.setState(TileState.FLAGGED_UNSURE);
        flaggedTiles--;
        break;
      case FLAGGED_UNSURE:
        tile.setState(TileState.COVERED);
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

  public void generateBoard() {

    Difficulty difficulty = model.getDifficulty();

    Tile[][] board = model.getBoard();

    for (int numberOfMinesGenerated = 0; numberOfMinesGenerated < difficulty.getNumberOfMines();) {

      int x = random.nextInt(difficulty.getWidth());
      int y = random.nextInt(difficulty.getHeight());

      Tile tile = board[y][x];

      if (tile.getState() == TileState.COVERED && tile.getContent() != TileContent.MINE) {

        tile.setContent(TileContent.MINE);

        numberOfMinesGenerated++;

        // Update all surrounding tiles (telling them there is another mine here).
        for (int deltaX = -1; deltaX <= 1; deltaX++) {

          int newX = x + deltaX;

          if (!this.isXOnBoard(newX)) {
            continue;
          }

          for (int deltaY = -1; deltaY <= 1; deltaY++) {

            int newY = y + deltaY;

            if (!this.isYOnBoard(newY)) {
              continue;
            }

            if (deltaX == 0 && deltaY == 0) {
              continue;
            }

            Tile tileToIncrement = board[newY][newX];

            if (tileToIncrement.getContent() != TileContent.MINE) {
              tileToIncrement.setContent(tileToIncrement.getContent() + 1);
            }

          }
        }

      }

    }

  }

  private boolean isXOnBoard(int x) {
    return x >= 0 && x < model.getDifficulty().getWidth();
  }

  private boolean isYOnBoard(int y) {
    return y >= 0 && y < model.getDifficulty().getHeight();
  }

  /**
   * @return the model
   */
  public GameModel getModel() {
    return model;
  }

  /**
   * @return the flaggedTiles
   */
  public int getFlaggedTiles() {
    return flaggedTiles;
  }

}
