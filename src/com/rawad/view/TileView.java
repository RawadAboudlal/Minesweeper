package com.rawad.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import com.rawad.model.Tile;
import com.rawad.model.TileContent;

/**
 * @author Rawad
 *
 */
public class TileView extends JButton {

  private static final int DEFAULT_WIDTH = 20;
  private static final int DEFAULT_HEIGHT = 20;

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
        break;
      case FLAGGED:
        g.setColor(Color.RED);
        g.drawOval(0, 0, this.getWidth(), this.getHeight());
        break;
      case OPENED:

        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), ROUNDNESS, ROUNDNESS);

        this.paintOpened(g);

        break;
    }

  }

  private void paintOpened(Graphics g) {

    String s = tile.getContent() + "";

    switch (tile.getContent()) {
      case TileContent.NONE:
        return;
      case TileContent.MINE:
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
