package src.com.chess.components;

import src.com.chess.game.CursorHandler;
import src.com.chess.game.FontHandler;
import src.com.chess.utils.ResourceHandler;

import javax.swing.*;
import java.awt.*;

// todo: made the move validation, implement it with the move handler
// todo: check for endgame status, check, stalemate, surrender, and time out
// todo: make a function that resets the status of the board
// todo: at a timer
// todo: (optional) add revert move
// todo: add surrender button
// todo: implement stock fish ai model for win and linux
// todo: implement sliding piece animation for enemy ai and castling


public class ChessFrame extends JFrame {
    public static final String TITLE = "Chess Game";
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 810;


    public void initUI(){
        this.setTitle(TITLE);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        // set icon for the game
        // todo: fix this shitty distorted icon
        Image icon = ResourceHandler.loadImage("icon.png");
        this.setIconImage(icon);

        // dependency injection ftw
        // initializing also calls the load cursor method
        CursorHandler cursorHandler = new CursorHandler(this, this.getToolkit());
        cursorHandler.setCursor("normal");

        // main panel
        JPanel mainPanel = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        FontHandler fontHandler = new FontHandler("Broken Console Bold.ttf");

        // menu panel
        mainPanel.add(new MenuPanel(mainPanel, cardLayout, this, cursorHandler, fontHandler), "MENU");
        mainPanel.add(new CreditsPanel(mainPanel, cardLayout, this, cursorHandler, fontHandler), "CREDITS");
        mainPanel.add(new GamePanel(mainPanel, cardLayout,this, cursorHandler, fontHandler), "GAME");

        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.pack();
    }
}
