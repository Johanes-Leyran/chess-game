package src.com.chess.utils;

import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;
import src.com.chess.game.GameState;
import src.com.chess.move.MoveHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PopUpBuilder {
    final static int WIDTH = 450;
    final static int HEIGHT = 250;

    public static void popMessage(
            String msg,
            CardLayout cardLayout,
            JPanel mainPanel,
            GameState gameState,
            ChessManager chessManager,
            MoveHandler moveHandler
    ) {
        FontHandler fontHandler = new FontHandler();
        CursorHandler cursorHandler = new CursorHandler();
        JFrame popUp = new JFrame("Game Over");
        cursorHandler.setCursor("normal", popUp);
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
            String text, FontHandler fontHandler, CursorHandler cursorHandler, ActionCall callback
    ) {
        return UIBuilder.buildButton(
                text, fontHandler.getFont(16), cursorHandler, callback
        );
    }
}
