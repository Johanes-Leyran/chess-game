package src.com.chess.components;

import src.com.chess.utils.FontHandler;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class TimerPanel extends JPanel {
    private long startTime;
    private long elapsedMillis;
    private boolean running;

    private final DecimalFormat secondsFormat = new DecimalFormat("0.00");
    private final FontHandler fontHandler;

    public TimerPanel(FontHandler fontHandler) {
        this.fontHandler = fontHandler;

        this.setPreferredSize(new Dimension(100, 40));
        this.setBackground(new Color(30, 30, 30));

        Timer timer = new Timer(50, e -> {
            if (running) {
                elapsedMillis = System.currentTimeMillis() - startTime;
                repaint();
            }
        });
        timer.start();
    }

    public void start() {
        startTime = System.currentTimeMillis() - elapsedMillis;
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void reset() {
        running = false;
        elapsedMillis = 0;
        repaint();
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        long millis = elapsedMillis;
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(Color.WHITE);
        g2.setFont(fontHandler.getFont(22));

        String timeText;
        if (millis >= 10_000) {
            long seconds = (millis / 1000) % 60;
            long minutes = millis / 60000;
            timeText = String.format("%02d:%02d", minutes, seconds);
        } else {
            double seconds = millis / 1000.0;
            timeText = secondsFormat.format(seconds);
        }

        FontMetrics metrics = g2.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(timeText)) / 2;
        int y = (getHeight() + metrics.getAscent()) / 2 - 4;

        g2.drawString(timeText, x, y);
        g2.dispose();
    }
}
