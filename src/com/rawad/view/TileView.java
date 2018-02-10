package com.rawad.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import com.rawad.model.Tile;

/**
 * @author Rawad
 *
 */
public class TileView extends JButton {

  private static final int DEFAULT_WIDTH = 16;
  private static final int DEFAULT_HEIGHT = 16;

  private static final int ROUNDNESS = 5;

  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = 3353075477507007301L;

  private final Tile tile;

  public TileView(Tile tile) {
    super();
    this.tile = tile;

    this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

  }

  /**
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent(Graphics g) {

    switch (tile.getState()) {
      case COVERED:
        g.setColor(Color.GRAY);
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), ROUNDNESS, ROUNDNESS);
      case FLAGGED:
        g.setColor(Color.RED);
        g.drawOval(0, 0, this.getWidth(), this.getHeight());
        break;
      case OPENED:
        this.paintOpened(g);
    }

  }

  private void paintOpened(Graphics g) {

    String s = "";

    switch (tile.getContent()) {
      case NONE:
        break;
      case ONE:
        s = "1";
        break;
      case TWO:
        s = "2";
        break;
      case THREE:
        s = "3";
        break;
      case FOUR:
        s = "4";
        break;
      case FIVE:
        s = "5";
        break;
      case SIX:
        s = "6";
        break;
      case SEVEN:
        s = "7";
        break;
      case EIGHT:
        s = "8";
      case MINE:
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, this.getWidth(), this.getHeight());
        return;
    }

    g.setColor(Color.BLACK);
    g.drawString(s, 0, this.getHeight());

  }

  /**
   * @return the tile
   */
  public Tile getTile() {
    return tile;
  }

}
