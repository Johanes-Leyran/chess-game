package src.com.chess.adapter;

import src.com.chess.constants.PiecesColors;
import src.com.chess.game.Piece;

import java.awt.*;

// not really an adapter but I don't know where to put it
// a class that holds data to be shared across adapters class
public class StateAdapter {
    Point initialPoint;
    Piece selected;
    boolean dragging;
    int colorTurn;


    public StateAdapter() {
        this.initialPoint = new Point(0, 0);
        this.dragging = false;
        this.colorTurn = PiecesColors.WHITE; // white moves first
    }

    public void toggleDragging() { this.dragging = !dragging; }

    public boolean getDragging() { return this.dragging; }

    public Point getInitialPoint() { return initialPoint; }

    public void setColorTurn(int color) { this.colorTurn = color; }

    public Piece getSelected() { return selected; }

    public void setSelected(Piece selected) { this.selected = selected; }
}
