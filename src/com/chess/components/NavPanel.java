package src.com.chess.components;

import src.com.chess.adapter.StateAdapter;
import src.com.chess.constants.PiecesColors;
import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;
import src.com.chess.game.GameState;
import src.com.chess.utils.FontHandler;
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
    StateAdapter stateAdapter;

    public interface Action {
        void call();
    }

    public NavPanel(
            JPanel mainPanel,
            CardLayout cardLayout,
            CursorHandler cursorHandler,
            FontHandler fontHandler,
            GameState gameState,
            ChessManager chessManager
    ){
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.cursorHandler = cursorHandler;
        this.fontHandler = fontHandler;
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(80, 80, 80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        backBtn = createButton("Quit", () -> {
            SoundManager.play("capture");
            cardLayout.show(mainPanel, "MENU");

            // reset
            gameState.resetGame();
            chessManager.setUpPieces();
        });
        undoBtn = createButton("Undo", () -> {});
        redoBtn = createButton("Redo", () -> {});

//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 0.5;
//        gbc.weighty = 0.5;
//        this.add(undoBtn, gbc);
//
//        gbc.gridx = 1;
//        gbc.gridy = 0;
//        gbc.weightx = 0.5;
//        this.add(redoBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        this.add(backBtn, gbc);

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
