package src.com.chess.components;

import src.com.chess.game.CursorHandler;
import src.com.chess.game.FontHandler;
import src.com.chess.utils.SoundManager;

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


    public MenuPanel(JPanel mainPanel, CardLayout cardLayout, JFrame frame, CursorHandler cursorHandler, FontHandler fontHandler){
        this.frame = frame;
        this.cursorHandler = cursorHandler;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.fontHandler = fontHandler;
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(45, 45, 45));

        // initialize sounds
        SoundManager.loadSounds();

        titleLabel = createLabel(
                "Chess Game", new EmptyBorder(200, 0, 30, 0), 52
        );
        groupLabel = createLabel(
                "Group 2 Project V 1.0.0", new EmptyBorder(0, 0, 25, 0), 16
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
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 220, 220));
        label.setFont(fontHandler.getFont(fontSize));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(emptyBorder);

        return label;
    }

    private JButton createButton(String text, String goTo) {
        JButton button = new JButton(text);

        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setFont(fontHandler.getFont(20));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(30, 30, 30, 30));

        button.getModel().addChangeListener(changeEvent -> {
            ButtonModel model = (ButtonModel) changeEvent.getSource();

            if (model.isRollover()) {
                button.setBackground(new Color(80, 80, 80));
            } else {
                button.setBackground(new Color(60, 60, 60));
            }
        });

        button.addActionListener(actionEvent -> {
            this.cursorHandler.setCursor("grab");

            cardLayout.show(mainPanel, goTo);
            SoundManager.play("move-self");

            final int CURSOR_RESET_DELAY_MS = 150;

            Timer cursorResetTimer = new Timer(
                    CURSOR_RESET_DELAY_MS, e -> this.cursorHandler.setCursor("normal")
            );
            cursorResetTimer.setRepeats(false); // Ensure it only runs once
            cursorResetTimer.start();
        });

        return button;
    }
}
