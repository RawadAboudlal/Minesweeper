package com.rawad.model;

import com.rawad.utils.Difficulty;

/**
 * @author Rawad
 *
 */
public class GameModel {

  private Tile[][] board;

  private Difficulty difficulty;

  /**
   * @return the board
   */
  public Tile[][] getBoard() {
    return board;
  }

  /**
   * @param board the board to set
   */
  public void setBoard(Tile[][] board) {
    this.board = board;
  }

  /**
   * @return the difficulty
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  /**
   * @param difficulty the difficulty to set
   */
  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

}
