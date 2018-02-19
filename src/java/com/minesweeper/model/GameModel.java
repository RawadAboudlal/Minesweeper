package com.minesweeper.model;

import com.minesweeper.utils.Difficulty;

/**
 * @author Rawad
 *
 */
public class GameModel {

  private Tile[][] board;

  private Difficulty difficulty;

  private GameState gameState;

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

  /**
   * @return the gameState
   */
  public GameState getGameState() {
    return gameState;
  }

  /**
   * @param gameState the gameState to set
   */
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

}
