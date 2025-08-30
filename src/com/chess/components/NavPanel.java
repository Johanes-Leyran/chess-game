package src.com.chess.components;

import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;
import src.com.chess.game.GameState;
import src.com.chess.utils.ActionCall;
import src.com.chess.utils.FontHandler;
import src.com.chess.utils.SoundManager;
import src.com.chess.utils.UIBuilder;

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

    public NavPanel(
            JPanel mainPanel,
            CardLayout cardLayout,
            GameState gameState,
            ChessManager chessManager
    ){
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.cursorHandler = new CursorHandler();
        this.fontHandler = new FontHandler();
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

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        this.add(backBtn, gbc);
    }

    private JButton createButton(String text, ActionCall callback) {
        return UIBuilder.buildButton(
                text, fontHandler.getFont(16), cursorHandler, callback
        );
    }
}
