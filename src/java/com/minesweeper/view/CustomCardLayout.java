package com.minesweeper.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

/**
 * @author Rawad
 *
 */
public class CustomCardLayout extends CardLayout {

  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = 8474814417712616402L;

  /**
   * @see java.awt.CardLayout#preferredLayoutSize(java.awt.Container)
   */
  @Override
  public Dimension preferredLayoutSize(Container parent) {

    Component current = findCurrentComponent(parent);

    if (current != null) {

      Insets insets = parent.getInsets();

      Dimension pref = current.getPreferredSize();

      pref.width += insets.left + insets.right;
      pref.height += insets.top + insets.bottom;

      return pref;

    }

    return super.preferredLayoutSize(parent);

  }

  public Component findCurrentComponent(Container parent) {

    for (Component comp : parent.getComponents()) {
      if (comp.isVisible()) {
        return comp;
      }
    }

    return null;

  }

}
