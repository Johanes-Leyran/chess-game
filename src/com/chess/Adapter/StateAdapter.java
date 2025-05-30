package src.com.chess.Adapter;

import src.com.chess.game.Piece;

import java.awt.*;

// not really an adapter but I don't know where to put it
// a class that holds data to be shared across adapters
public class StateAdapter {
    Point initialPoint;
    Piece selected;
    boolean dragging;


    public StateAdapter(Point initialPoint) {
        this.initialPoint = initialPoint;
        this.dragging = false;
    }

    public void toggleDragging() {
        this.dragging = !dragging;
    }

    public boolean getDragging() {
        return this.dragging;
    }

    public Point getInitialPoint() {
        return initialPoint;
    }

    public Piece getSelected() {
        return selected;
    }

    public void setSelected(Piece selected) {
        this.selected = selected;
    }
}
