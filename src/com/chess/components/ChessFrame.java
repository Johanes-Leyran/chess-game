package src.com.chess.components;

import src.com.chess.game.CursorHandler;
import src.com.chess.utils.CardLayoutHandler;
import src.com.chess.utils.FontHandler;
import src.com.chess.utils.ResourceHandler;
import src.com.chess.utils.UIBuilder;

import javax.swing.*;
import java.awt.*;

// todo: (optional) add revert move
// todo: add surrender button
// todo: implement stock fish ai model for win and linux
// todo: implement sliding piece animation for enemy ai and castling
// todo: inconsistent dragging mechanics
// todo: fix this shitty distorted icon


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
        Image icon = ResourceHandler.loadImage("icon.png");
        this.setIconImage(icon);

        // dependency injection ftw
        // initializing also calls the load cursor method
        CursorHandler cursorHandler = new CursorHandler();
        cursorHandler.loadCursor(this, this.getToolkit());
        cursorHandler.setCursor("normal");

        JPanel mainPanel = new JPanel(new CardLayout());
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        CardLayoutHandler cardLayoutHandler = new CardLayoutHandler();
        cardLayoutHandler.load(mainPanel, cardLayout);

        FontHandler fontHandler = new FontHandler();
        fontHandler.loadFont("Broken Console Bold.ttf");

        mainPanel.add(new MenuPanel(this), "MENU");
        mainPanel.add(new CreditsPanel(this), "CREDITS");
        mainPanel.add(new GamePanel(this), "GAME");

        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.pack();
    }
}
