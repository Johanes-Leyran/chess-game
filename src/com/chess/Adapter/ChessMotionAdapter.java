package src.com.chess.Adapter;

import src.com.chess.game.Piece;
import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ChessMotionAdapter extends MouseMotionAdapter {
    ChessManager chessManager;
    CursorHandler cursorHandler;
    StateAdapter stateAdapter;
    boolean hovering;


    public ChessMotionAdapter(
            ChessManager chessManager, CursorHandler cursorHandler, StateAdapter stateAdapter
    ) {
        this.chessManager = chessManager;
        this.cursorHandler = cursorHandler;
        this.stateAdapter = stateAdapter;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Piece piece = chessManager.checkBounds(e.getPoint());

        if (piece != null && !hovering && !piece.getColor().equals("EMPTY")) {
            cursorHandler.setCursor("toGrab");
            hovering = true;
        }
        if(hovering && piece == null) {
            cursorHandler.setCursor("normal");
            hovering = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point initialPoint = stateAdapter.getInitialPoint();

        if (this.stateAdapter.getDragging() && stateAdapter.getSelected() != null) {
            stateAdapter.getSelected().setPosition(
                    (int)(e.getX() - initialPoint.getX()),
                    (int)(e.getY() - initialPoint.getY())
            );
        }
    }
}
