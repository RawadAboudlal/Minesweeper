package com.rawad.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.rawad.controller.GameController;
import com.rawad.controller.UIController;
import com.rawad.model.GameModel;
import com.rawad.model.Tile;

/**
 * @author Rawad
 *
 */
public class GameView {

  private static final String GAME_NAME = "Minesweeper";

  private static final String MENU = "menu";
  private static final String GAME = "game";

  private static final int DEFAULT_WIDTH = 640;
  private static final int DEFAULT_HEIGHT = 480;

  private static final int TILE_GAP = 3;

  private JFrame frame;

  private JPanel basePanel;

  private JPanel menuPanel;
  private JPanel gamePanel;

  private CardLayout cardLayout;

  private UIController uiController;

  private GameModel gameModel;

  /**
   * Must be called from the AWT Event Dispatch Thread.
   * 
   * @param gameController
   */
  public void initialize(GameController gameController) {

    uiController = new UIController(gameController, this);

    EventQueue.invokeLater(() -> {
      GameView.this.initGui();
    });

  }

  private void initGui() {

    frame = new JFrame(GAME_NAME);

    basePanel = new JPanel();
    menuPanel = new JPanel();
    gamePanel = new JPanel();

    menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

    JButton easyDifficultyButton = new JButton("Easy");
    JButton mediumDifficultyButton = new JButton("Medium");
    JButton hardDifficultyButton = new JButton("Hard");
    JButton quitButton = new JButton("Quit");

    uiController.addEasyButton(easyDifficultyButton);
    uiController.addMediumButton(mediumDifficultyButton);
    uiController.addHardButton(hardDifficultyButton);

    menuPanel.add(easyDifficultyButton);
    menuPanel.add(mediumDifficultyButton);
    menuPanel.add(hardDifficultyButton);
    menuPanel.add(quitButton);

    cardLayout = new CardLayout();

    basePanel.setLayout(cardLayout);

    basePanel.add(menuPanel, MENU);
    basePanel.add(gamePanel, GAME);

    basePanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

    uiController.addFrame(frame);

    frame.pack();

    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setVisible(true);

  }

  public void createGamePanel(GameModel gameModel) {

    this.gameModel = gameModel;

    JPanel boardPanel = new JPanel();
    GridLayout boardLayout = new GridLayout(gameModel.getDifficulty().getHeight(),
        gameModel.getDifficulty().getWidth(), TILE_GAP, TILE_GAP);

    boardPanel.setLayout(boardLayout);

    Tile[][] board = gameModel.getBoard();

    for (int y = 0; y < board.length; y++) {

      Tile[] row = board[y];

      for (int x = 0; x < row.length; x++) {

        Tile tile = row[x];

        TileView tileView = new TileView(tile);

        uiController.addTileView(tileView);;

        boardPanel.add(tileView);

      }
    }

    cardLayout.show(basePanel, GAME);

  }

  public void showMenuPanel() {
    cardLayout.show(basePanel, MENU);
  }

}
