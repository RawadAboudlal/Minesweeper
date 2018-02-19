package com.minesweeper.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import com.minesweeper.model.Tile;
import com.minesweeper.view.textures.Textures;

/**
 * @author Rawad
 *
 */
public class TileView extends JPanel {

  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = 3353075477507007301L;

  private final Tile tile;

  private Image image;

  private boolean triggered = false;

  public TileView(Tile tile) {
    super();

    this.tile = tile;

    this.update();

  }

  /**
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(image, 0, 0, this);
  }

  public void update() {

    this.image = Textures.getTexture(tile, triggered);

    this.setPreferredSize(new Dimension(this.image.getWidth(this), this.image.getHeight(this)));

    this.repaint();

  }

  public void setTriggered() {
    this.triggered = true;
  }

  /**
   * @return the tile
   */
  public Tile getTile() {
    return tile;
  }

}
