package src.com.chess.Adapter;

import src.com.chess.game.Piece;
import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessMouseAdapter extends MouseAdapter {
    ChessManager chessManager;
    CursorHandler cursorHandler;
    StateAdapter stateAdapter;


    public ChessMouseAdapter(ChessManager chessManager, CursorHandler cursorHandler, StateAdapter stateAdapter) {
        this.chessManager = chessManager;
        this.cursorHandler = cursorHandler;
        this.stateAdapter = stateAdapter;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);

        Point point = e.getPoint();
        Piece piece = chessManager.checkBounds(point);

        if(piece != null) {
            this.stateAdapter.getInitialPoint().setLocation(
                    (int) point.getX() - piece.getXPosition(),
                    (int) point.getY() - piece.getYPosition()
            );

            cursorHandler.setCursor("grab");
            piece.setIsDragged(true);
            this.stateAdapter.toggleDragging();
            this.stateAdapter.setSelected(piece);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        cursorHandler.setCursor("normal");

        if(stateAdapter.getDragging()) {
            Piece selected = stateAdapter.getSelected();

            selected.setIsDragged(false);
            stateAdapter.toggleDragging();

            int lastSnapXPosition = chessManager.getSnappedXPos(selected.getCol());
            int lastSnapYPosition = chessManager.getSnappedYPos(selected.getRow());

            selected.setPosition(lastSnapXPosition, lastSnapYPosition);
            stateAdapter.setSelected(null);
        }
    }
}
