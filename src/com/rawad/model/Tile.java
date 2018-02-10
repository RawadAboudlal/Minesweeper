package com.rawad.model;


/**
 * @author Rawad
 *
 */
public class Tile {

  private int x;
  private int y;

  private TileContent content;

  private TileState state;


  /**
   * @return the x
   */
  public int getX() {
    return x;
  }


  /**
   * @param x the x to set
   */
  public void setX(int x) {
    this.x = x;
  }


  /**
   * @return the y
   */
  public int getY() {
    return y;
  }


  /**
   * @param y the y to set
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * @return the content
   */
  public TileContent getContent() {
    return content;
  }


  /**
   * @param content the content to set
   */
  public void setContent(TileContent content) {
    this.content = content;
  }


  /**
   * @return the state
   */
  public TileState getState() {
    return state;
  }


  /**
   * @param state the state to set
   */
  public void setState(TileState state) {
    this.state = state;
  }

}
