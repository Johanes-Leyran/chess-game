package src.com.chess.adapter;

import src.com.chess.constants.PiecesColors;
import src.com.chess.game.GameState;
import src.com.chess.move.MoveHandler;
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
    GameState gameState;


    public ChessMouseAdapter(
            ChessManager chessManager,
            CursorHandler cursorHandler,
            StateAdapter stateAdapter,
            MoveHandler moveHandler,
            GameState gameState
    ) {
        this.chessManager = chessManager;
        this.cursorHandler = cursorHandler;
        this.stateAdapter = stateAdapter;
        this.moveHandler = moveHandler;
        this.gameState = gameState;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(gameState.getState() != GameState.State.ONGOING)
            return;

        super.mouseClicked(e);

        Point point = e.getPoint();
        Piece piece = chessManager.checkBounds(point);

        if(piece == null || piece.getColor() == PiecesColors.EMPTY) return;
        if(piece.getColor() != this.stateAdapter.colorTurn) return;

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

        selected.setIsDragged(false);

        if(moveHandler.validateMove(selected, target))
            this.stateAdapter.colorTurn = (this.stateAdapter.colorTurn == PiecesColors.WHITE)
                        ? PiecesColors.BLACK
                        : PiecesColors.WHITE;

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

        Log.INFO(String.format(
                "%s %s Turn",
                this.getClass().getSimpleName(),
                this.stateAdapter.colorTurn == PiecesColors.WHITE ? "White" : "Black"
        ));
    }
}
