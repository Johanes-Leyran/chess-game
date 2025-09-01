package src.com.chess.components;

import src.com.chess.constants.PiecesType;
import src.com.chess.game.Piece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class CapturedPanel extends JPanel {
    private final ArrayList<Piece> capturedList;

    public CapturedPanel(ArrayList<Piece> capturedList) {
        this.capturedList = capturedList;
        this.setBackground(new Color(80, 80, 80));
        this.setPreferredSize(new Dimension(150, 100));
    }

    // Sort from most valuable to least
    public void sort() {
        this.capturedList.sort(Comparator.comparingInt(this::getPieceValue).reversed());
    }

    private int getPieceValue(Piece p) {
        // todo: add score which one is on the advantage
        if (p == null) return -1; // null safety
        return switch (p.getType()) {
            case PiecesType.PAWN -> 1;
            case PiecesType.KNIGHT, PiecesType.BISHOP -> 3;
            case PiecesType.ROOK -> 5;
            case PiecesType.QUEEN -> 9;
            default -> 0;
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (capturedList == null) return;

        sort(); // sort from highest to lowest

        Graphics2D g2 = (Graphics2D) g;
        int x = 10;
        int y = 10;
        int spriteWidth = 16;
        int overlap = 3;

        for (Piece piece : capturedList) {
            if (piece == null) continue;
            g2.drawImage(piece.getSprite(), x, y, null);
            x += spriteWidth - overlap;

            // wrap if going off the panel width, should never happen though
            if (x + spriteWidth > getWidth()) {
                x = 10;
                y += 32 - 10;
            }
        }
    }
}
