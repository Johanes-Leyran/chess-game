package src.com.chess.adapter;

import src.com.chess.constants.PiecesColors;
import src.com.chess.game.MoveHandler;
import src.com.chess.game.Piece;
import src.com.chess.game.ChessManager;
import src.com.chess.game.CursorHandler;
import src.com.chess.utils.Log;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessMouseAdapter extends MouseAdapter {
    ChessManager chessManager;
    CursorHandler cursorHandler;
    StateAdapter stateAdapter;
    MoveHandler moveHandler;


    public ChessMouseAdapter(
            ChessManager chessManager, CursorHandler cursorHandler, StateAdapter stateAdapter, MoveHandler moveHandler
    ) {
        this.chessManager = chessManager;
        this.cursorHandler = cursorHandler;
        this.stateAdapter = stateAdapter;
        this.moveHandler = moveHandler;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);

        Point point = e.getPoint();
        Piece piece = chessManager.checkBounds(point);

        if(piece == null || piece.getColor() == PiecesColors.EMPTY) {
            return;
        }

        cursorHandler.setCursor("grab");
        this.stateAdapter.getInitialPoint().setLocation(
                (int) point.getX() - piece.getXPosition(),
                (int) point.getY() - piece.getYPosition()
        );

        Log.DEBUG(String.format(
                "%s Mouse pressed positon at x: %s, y: %s",
                this.getClass().getSimpleName(),
                piece.getXPosition(),
                piece.getYPosition()
        ));

        piece.setIsDragged(true);
        this.stateAdapter.toggleDragging();
        this.stateAdapter.setSelected(piece);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        cursorHandler.setCursor("normal");

        if(!stateAdapter.getDragging()) {
            return;
        }

        Piece selected = stateAdapter.getSelected();
        Piece target = chessManager.getNearestRect(selected);

        Log.DEBUG(String.format(
                "%s Mouse released positon at x: %s, y: %s",
                this.getClass().getSimpleName(),
                selected.getXPosition(),
                selected.getYPosition()
        ));

        moveHandler.validateMove(selected, target);
        selected.setIsDragged(false);

        selected.setPosition(
                chessManager.getSnappedXPos(selected.getCol()),
                chessManager.getSnappedYPos(selected.getRow())
        );
        target.setPosition(
                chessManager.getSnappedXPos(target.getCol()),
                chessManager.getSnappedYPos(target.getRow())
        );

        stateAdapter.toggleDragging();
        stateAdapter.setSelected(null);
    }
}
