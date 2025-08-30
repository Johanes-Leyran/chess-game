package src.com.chess.components;

import src.com.chess.game.GameState;
import src.com.chess.utils.FontHandler;
import src.com.chess.utils.Log;
import src.com.chess.utils.PopUpBuilder;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class TimerPanel extends JPanel {
    long startTime;
    long elapsedTime;
    long durationLimit;
    boolean running;
    boolean hasPop;
    int color;

    GameState gameState;
    final DecimalFormat secondsFormat = new DecimalFormat("0.00");
    final FontHandler fontHandler;

    public TimerPanel(GameState gameState, long durationLimit, int color) {
        this.fontHandler = new FontHandler();
        this.gameState = gameState;
        this.durationLimit = durationLimit;
        this.color = color;
        this.hasPop = false;

        setPreferredSize(new Dimension(100, 40));
        setBackground(new Color(30, 30, 30));

        Timer timer = new Timer(50, _ -> {
            boolean isTurn = color == gameState.getColorTurn();
            boolean isGameOn = gameState.getState() == GameState.State.ONGOING;

            if(gameState.getReset()) {
                this.reset();
                this.start();
            };

            if(isTurn && running && gameState.getStartGame() && isGameOn) {
                elapsedTime = System.currentTimeMillis() - startTime;
                repaint();

                if (elapsedTime <= durationLimit) return;

                elapsedTime = durationLimit;
                running = false;
                gameState.stateTimeOver(color);

                if(hasPop) return;

                hasPop = true;
                gameState.popUpState();

            } else {
                startTime = System.currentTimeMillis() - elapsedTime;
            }
        });
        timer.start();
    }

    public void reset() {
        running = false;
        elapsedTime = 0;
        repaint();
    }

    public void start() {
        startTime = System.currentTimeMillis() - elapsedTime;
        running = true;
        hasPop = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long remaining = durationLimit - elapsedTime;
        if (remaining < 0) remaining = 0;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);
        g2.setFont(fontHandler.getFont(30));

        String timeText;
        if (remaining >= 10_000) {
            long seconds = (remaining / 1000) % 60;
            long minutes = remaining / 60000;
            timeText = String.format("%02d : %02d", minutes, seconds);
        } else {
            double seconds = remaining / 1000.0;
            timeText = secondsFormat.format(seconds);
        }

        FontMetrics metrics = g2.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(timeText)) / 2;
        int y = (getHeight() + metrics.getAscent()) / 2 - 4;

        g2.drawString(timeText, x, y);
        g2.dispose();
    }
}
