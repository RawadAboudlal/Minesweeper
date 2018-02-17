package com.minesweeper.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;
import com.minesweeper.model.Tile;
import com.minesweeper.model.TileContent;

/**
 * @author Rawad
 *
 */
public class TileView extends JPanel {

  private static final int DEFAULT_WIDTH = 21;
  private static final int DEFAULT_HEIGHT = 21;

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
        this.paintCovered(g);
        break;
      case FLAGGED:
        this.paintCovered(g);

        g.setColor(Color.RED);
        g.drawOval(0, 0, this.getWidth(), this.getHeight());
        break;
      case OPENED:

        this.paintOpened(g);

        break;
      case FLAGGED_UNSURE:
        this.paintCovered(g);

        g.setColor(Color.ORANGE);
        g.fillOval(this.getWidth() / 4, this.getHeight() / 4, this.getWidth() / 2,
            this.getHeight() / 2);

        break;

    }

  }

  private void paintCovered(Graphics g) {
    g.setColor(Color.GRAY);
    g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), ROUNDNESS, ROUNDNESS);
  }

  private void paintOpened(Graphics g) {

    g.setColor(Color.LIGHT_GRAY);
    g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), ROUNDNESS, ROUNDNESS);

    String s = tile.getContent() + "";

    switch (tile.getContent()) {
      case TileContent.NONE:
        return;
      case TileContent.MINE:
        g.setColor(Color.BLACK);
        g.fillOval(0, 0, this.getWidth(), this.getHeight());
        return;
    }

    FontMetrics fm = g.getFontMetrics();

    g.setColor(Color.BLACK);
    g.drawString(s, (this.getWidth() / 2) + (fm.stringWidth(s) / 2),
        (this.getHeight() / 2) + (fm.getHeight() / 2));

  }

  /**
   * @return the tile
   */
  public Tile getTile() {
    return tile;
  }

}
