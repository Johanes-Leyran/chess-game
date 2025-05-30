package src.com.chess.components;

import src.com.chess.game.ComponentData;
import src.com.chess.game.CursorHandler;
import src.com.chess.game.FontHandler;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    JPanel mainPanel;
    JPanel chessPanel;
    ComponentData componentData;
    JFrame frame;


    public GamePanel(
            JPanel mainPanel,
            CardLayout cardLayout,
            JFrame frame,
            CursorHandler cursorHandler,
            FontHandler fontHandler
    ){
        this.mainPanel = mainPanel;
        this.frame = frame;
        this.setBackground(Color.black);
        this.setLayout(new BorderLayout());
        this.componentData = new ComponentData(
                142,
                7,
                16,
                32,
                5.5,
                2.7,
                this.frame
        );
        this.chessPanel = new ChessPanel(this.componentData, cursorHandler);
        this.add(chessPanel, BorderLayout.WEST);
        this.add(new InfoPanel(mainPanel, cardLayout, frame, cursorHandler, fontHandler), BorderLayout.CENTER);
    }
}
