package src.com.chess.components;

import src.com.chess.game.CursorHandler;
import src.com.chess.game.FontHandler;
import src.com.chess.utils.SoundManager;

import javax.swing.*;
import java.awt.*;


public class NavPanel extends JPanel {
    JPanel mainPanel;
    CardLayout cardLayout;
    CursorHandler cursorHandler;
    FontHandler fontHandler;
    JButton backBtn;
    JButton undoBtn;
    JButton redoBtn;

    public interface Action {
        void call();
    }

    public NavPanel(JPanel mainPanel, CardLayout cardLayout, CursorHandler cursorHandler, FontHandler fontHandler){
//        this.setBackground(Color.cyan);
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.cursorHandler = cursorHandler;
        this.fontHandler = fontHandler;
        this.setLayout(new GridLayout(1,3));
        this.setPreferredSize(new Dimension(0, 50));

        backBtn = createButton("Quit", () -> {
            SoundManager.play("capture");
            cardLayout.show(mainPanel, "MENU");
        });
        undoBtn = createButton("Undo", () -> SoundManager.play("move-self"));
        redoBtn = createButton("Redo", () -> SoundManager.play("move-self"));

        //  this.add(undoBtn);
        //  this.add(redoBtn);
        this.add(backBtn);
    }

    private JButton createButton(String text, Action callback) {
        JButton button = new JButton(text);
        button.setBackground(new Color(60, 60, 60));
        button.setForeground(Color.WHITE);
        button.setFont(fontHandler.getFont(16));
        button.setFocusPainted(false);

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
            // this took me more time than I want to admit to implement a callback function in java
            callback.call();

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
