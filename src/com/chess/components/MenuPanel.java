package src.com.chess.components;

import src.com.chess.game.CursorHandler;
import src.com.chess.utils.FontHandler;
import src.com.chess.utils.SoundManager;
import src.com.chess.utils.UIBuilder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class MenuPanel extends JPanel {
    JLabel titleLabel;
    JLabel groupLabel;
    JFrame frame;
    JPanel mainPanel;
    JPanel buttonPanel;
    JButton creditsBtn;
    JButton playVSHumanBtn;
    JButton playVSAIBtn;
    CursorHandler cursorHandler;
    FontHandler fontHandler;
    CardLayout cardLayout;


    public MenuPanel(JPanel mainPanel, CardLayout cardLayout, JFrame frame){
        this.frame = frame;
        this.cursorHandler = new CursorHandler();
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.fontHandler = new FontHandler();
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(45, 45, 45));

        SoundManager.loadSounds();

        titleLabel = createLabel(
                "Chess Game", new EmptyBorder(200, 0, 30, 0), 52
        );
        groupLabel = createLabel(
                "Group 2 Project V 1.0.0", new EmptyBorder(0, 0, 25, 0), 18
        );

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(getBackground());
        buttonPanel.setBorder(new EmptyBorder(70, 300, 200, 300));

        playVSHumanBtn = this.createButton("Player vs Player", "GAME");
        playVSAIBtn = this.createButton("Player vs AI", "GAME");
        creditsBtn = this.createButton("Credits", "CREDITS");

        buttonPanel.add(playVSHumanBtn);
        buttonPanel.add(playVSAIBtn);
        buttonPanel.add(creditsBtn);

        this.add(buttonPanel, BorderLayout.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);
        this.add(groupLabel, BorderLayout.SOUTH);
    }
    private JLabel createLabel(String text, EmptyBorder emptyBorder, int fontSize) {
        return UIBuilder.buildLabel(
                text, emptyBorder, fontHandler.getFont(fontSize)
        );
    }

    private JButton createButton(String text, String goTo) {
        return UIBuilder.buildNavButton(
                text, goTo, fontHandler.getFont(20), cursorHandler, cardLayout, mainPanel
        );
    }
}
