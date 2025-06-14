package src.com.chess.utils;

import src.com.chess.components.NavPanel;
import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;
import src.com.chess.game.GameState;
import src.com.chess.move.MoveHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PopUpBuilder {
    final static int WIDTH = 350;
    final static int HEIGHT = 150;

    public static void popMessage(
            String msg,
            CardLayout cardLayout,
            JPanel mainPanel,
            GameState gameState,
            ChessManager chessManager,
            FontHandler fontHandler,
            CursorHandler cursorHandler,
            MoveHandler moveHandler
    ) {
        JFrame popUp = new JFrame("Game Over");
        popUp.setResizable(false);
        popUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        popUp.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        popUp.getContentPane().setBackground(new Color(60, 60, 60));

        // Create inner panel with layout and border
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(80, 80, 80));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel label = new JLabel(msg);
        label.setForeground(new Color(220, 220, 220));
        label.setFont(fontHandler.getFont(24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(10, 10, 20, 10));

        contentPanel.add(label, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.ipady = 20;

        JButton retryBtn = createButton("Retry", fontHandler, cursorHandler, () -> {
            reset(gameState, chessManager, moveHandler);
            SoundManager.play("move-self");
            popUp.dispose();
        });

        contentPanel.add(retryBtn, gbc);

        gbc.gridx = 1;
        JButton backBtn = createButton("Back", fontHandler, cursorHandler, () -> {
            reset(gameState, chessManager, moveHandler);
            SoundManager.play("move-self");
            cardLayout.show(mainPanel, "MENU");
            popUp.dispose();
        });

        contentPanel.add(backBtn, gbc);

        popUp.getContentPane().add(contentPanel); // Add panel to frame

        popUp.pack();
        popUp.setLocationRelativeTo(null);
        popUp.setVisible(true);
    }

    private static void reset(GameState gameState, ChessManager chessManager, MoveHandler moveHandler) {
        gameState.resetGame();
        chessManager.setUpPieces();
        moveHandler.resetMoveHistory();
    }

    private static JButton createButton(
            String text,
            FontHandler fontHandler,
            CursorHandler cursorHandler,
            NavPanel.Action callback
    ) {
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

        button.addActionListener(_ -> {
            cursorHandler.setCursor("grab");
            callback.call();

            final int CURSOR_RESET_DELAY_MS = 150;

            Timer cursorResetTimer = new Timer(
                    CURSOR_RESET_DELAY_MS, _ -> cursorHandler.setCursor("normal")
            );
            cursorResetTimer.setRepeats(false); // Ensure it only runs once
            cursorResetTimer.start();
        });

        return button;
    }
}
