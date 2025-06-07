package src.com.chess.adapter;

import src.com.chess.pieces.Piece;

import java.awt.*;

// not really an adapter but I don't know where to put it
// a class that holds data to be shared across adapters class
public class StateAdapter {
    Point initialPoint;
    Piece selected;
    boolean dragging;


    public StateAdapter() {
        this.initialPoint = new Point(0, 0);
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
