package src.com.chess.components;

import src.com.chess.adapter.StateAdapter;
import src.com.chess.constants.PiecesColors;
import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;
import src.com.chess.utils.FontHandler;
import src.com.chess.game.GameState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InfoPanel extends JPanel {
    JPanel mainPanel;
    CardLayout cardLayout;
    JFrame frame;
    TimerPanel blackTimer;
    TimerPanel whiteTimer;

    long duration = 600000;

    public InfoPanel(
            JFrame frame,
            GameState gameState,
            ChessManager chessManager
    ){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.frame = frame;
        this.setBackground(new Color(80, 80, 80));

        JPanel blackPanel = new JPanel(new GridLayout(3, 1));
        blackPanel.setPreferredSize(new Dimension(0, 300));
        blackPanel.setBackground(new Color(80, 80, 80));
        blackPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.blackTimer = new TimerPanel(gameState, duration, PiecesColors.BLACK);
        this.blackTimer.start();
        blackPanel.add(this.blackTimer);
        this.add(blackPanel);

        JPanel blackCapturedPanel = new CapturedPanel(gameState.getBlackCaptured());
        blackPanel.add(blackCapturedPanel);

        JPanel whitePanel = new JPanel(new GridLayout(3, 1));
        whitePanel.setPreferredSize(new Dimension(0, 300));
        whitePanel.setBackground(new Color(80, 80, 80));
        whitePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(80, 80, 80));
        whitePanel.add(panel);

        JPanel whiteCapturedPanel = new CapturedPanel(gameState.getWhiteCaptured());
        whitePanel.add(whiteCapturedPanel);

        this.whiteTimer = new TimerPanel(gameState, duration, PiecesColors.WHITE);
        this.whiteTimer.start();
        whitePanel.add(this.whiteTimer);
        this.add(whitePanel);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 1));
        bottomPanel.add(new NavPanel(gameState, chessManager));

        this.add(bottomPanel);
    }
}
