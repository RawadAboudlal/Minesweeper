package com.minesweeper.utils;

/**
 * @author Rawad
 *
 */
public final class Difficulty {

  public static final Difficulty EASY = new Difficulty(8, 8, 10);
  public static final Difficulty MEDIUM = new Difficulty(16, 16, 40);
  public static final Difficulty HARD = new Difficulty(30, 16, 99);

  private final int width;
  private final int height;

  private final int numberOfMines;

  /**
   * @param width
   * @param height
   * @param numberOfMines
   */
  public Difficulty(int width, int height, int numberOfMines) {
    super();
    this.width = width;
    this.height = height;
    this.numberOfMines = numberOfMines;
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * @return the numberOfMines
   */
  public int getNumberOfMines() {
    return numberOfMines;
  }

}
