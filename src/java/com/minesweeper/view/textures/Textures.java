package com.minesweeper.view.textures;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import com.minesweeper.model.Tile;
import com.minesweeper.model.TileContent;

/**
 * @author rawad
 *
 */
public final class Textures {

  private static final String EXTENSION_SEPARATOR = ".";
  private static final String TEXTURE_EXTENSION = "png";

  private static HashMap<String, Image> textures;

  private Textures() {}

  public static final void loadTextures() {

    textures = new HashMap<String, Image>();

    for (TexturesEnum texture : TexturesEnum.values()) {
      try {
        textures.put(texture.getName(), ImageIO.read(Textures.class
            .getResource(texture.getName() + EXTENSION_SEPARATOR + TEXTURE_EXTENSION)));
      } catch (IOException ex) {
        System.err.println("Could not load texture: " + texture.getName());
        ex.printStackTrace();
      }
    }

  }

  public static Image getTexture(Tile tile, boolean triggered) {

    switch (tile.getState()) {
      case FLAGGED_UNSURE:
        return textures.get(TexturesEnum.FLAGGED_UNSURE.getName());
      case FLAGGED:
        return textures.get(TexturesEnum.FLAGGED.getName());
      case COVERED:
        return textures.get(TexturesEnum.COVERED.getName());
      case OPENED:
      default:
        switch (tile.getContent()) {
          case TileContent.NONE:
          default:
            return textures.get(TexturesEnum.BLANK.getName());
          case TileContent.ONE:
            return textures.get(TexturesEnum.UNCOVERED_1.getName());
          case TileContent.TWO:
            return textures.get(TexturesEnum.UNCOVERED_2.getName());
          case TileContent.THREE:
            return textures.get(TexturesEnum.UNCOVERED_3.getName());
          case TileContent.FOUR:
            return textures.get(TexturesEnum.UNCOVERED_4.getName());
          case TileContent.FIVE:
            return textures.get(TexturesEnum.UNCOVERED_5.getName());
          case TileContent.SIX:
            return textures.get(TexturesEnum.UNCOVERED_6.getName());
          case TileContent.SEVEN:
            return textures.get(TexturesEnum.UNCOVERED_7.getName());
          case TileContent.EIGHT:
            return textures.get(TexturesEnum.UNCOVERED_8.getName());
          case TileContent.MINE:
            if (triggered) {
              return textures.get(TexturesEnum.TRIGGERED_MINE.getName());
            } else {
              return textures.get(TexturesEnum.UNCOVERED_MINE.getName());
            }
        }
    }

  }

}
