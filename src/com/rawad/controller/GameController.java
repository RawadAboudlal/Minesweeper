package com.rawad.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.rawad.model.GameModel;
import com.rawad.model.Tile;

/**
 * @author Rawad
 *
 */
public class GameController implements ActionListener {

  private GameModel model;

  /**
   * {@code startingTileX}, {@code startingTileY} should not be a bomb.
   * 
   * @param width
   * @param height
   * @param startingTileX
   * @param startingTileY
   * @return
   */
  public static Tile[][] generateBoard(int width, int height, int startingTileX,
      int startingTileY) {

    Tile[][] board = new Tile[height][width];

    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        // TODO: Board generator.
      }
    }

    return board;

  }

  /**
   * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
   */
  @Override
  public void actionPerformed(ActionEvent e) {



  }

}
