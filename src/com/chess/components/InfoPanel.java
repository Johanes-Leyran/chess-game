package src.com.chess.components;

import src.com.chess.game.CursorHandler;
import src.com.chess.game.FontHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class InfoPanel extends JPanel {
    JPanel mainPanel;
    CardLayout cardLayout;
    JFrame frame;

    public InfoPanel(
            JPanel mainPanel,
            CardLayout cardLayout,
            JFrame frame,
            CursorHandler cursorHandler,
            FontHandler fontHandler
    ){
        this.setLayout(new BorderLayout());

        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.frame = frame;

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        bottomPanel.setPreferredSize(new Dimension(0, 100));
        bottomPanel.add(new NavPanel(mainPanel, cardLayout, cursorHandler, fontHandler));

        this.add(bottomPanel, BorderLayout.PAGE_END);
    }
}
