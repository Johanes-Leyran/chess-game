package src.com.chess.adapter;

import src.com.chess.constants.PiecesColors;
import src.com.chess.game.GameState;
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
    GameState gameState;
    boolean hovering;


    public ChessMotionAdapter(
            ChessManager chessManager, StateAdapter stateAdapter, GameState gameState
    ) {
        this.chessManager = chessManager;
        this.cursorHandler = new CursorHandler();
        this.stateAdapter = stateAdapter;
        this.gameState = gameState;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(gameState.getState() != GameState.State.ONGOING)
            return;

        Piece piece = chessManager.checkBounds(e.getPoint());

        if(piece != null &&
                !hovering &&
                piece.getType() != PiecesColors.EMPTY
                && piece.getColor() == this.stateAdapter.colorTurn
        ) {
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
