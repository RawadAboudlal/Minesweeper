package com.rawad.view;

import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.rawad.controller.GameController;
import com.rawad.model.GameModel;

/**
 * @author Rawad
 *
 */
public class GameView {

  private static final int DEFAULT_WIDTH = 640;
  private static final int DEFAULT_HEIGHT = 480;

  private JFrame frame;

  private JPanel basePanel;

  private JPanel menuPanel;
  private JPanel gamePanel;

  private GameModel gameModel;

  /**
   * @param gameModel
   */
  public GameView(GameModel gameModel) {
    super();
    this.gameModel = gameModel;
  }

  /**
   * Must be called from the AWT Event Dispatch Thread.
   * 
   * @param gameController
   */
  public void initialize(GameController gameController) {

    EventQueue.invokeLater(() -> {
      GameView.this.initGui();
    });

  }

  private void initGui() {

    frame = new JFrame("");

    basePanel = new JPanel();

    CardLayout cardLayout = new CardLayout();

    basePanel.setLayout(cardLayout);

    frame.setVisible(true);

  }

}
