package com.rawad.view;

import java.awt.CardLayout;
import java.awt.Dimension;
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

  private static final String MENU = "menu";
  private static final String GAME = "game";

  private static final int DEFAULT_WIDTH = 640;
  private static final int DEFAULT_HEIGHT = 480;

  private JFrame frame;

  private JPanel basePanel;

  private JPanel menuPanel;
  private JPanel gamePanel;

  private CardLayout cardLayout;

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
    menuPanel = new JPanel();
    gamePanel = new JPanel();

    cardLayout = new CardLayout();

    basePanel.setLayout(cardLayout);

    basePanel.add(menuPanel, MENU);
    basePanel.add(gamePanel, GAME);

    basePanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

    frame.setVisible(true);

  }

}
