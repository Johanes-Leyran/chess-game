package src.com.chess.adapter;

import src.com.chess.game.MoveHandler;
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

        if(piece == null || piece.getColor().equals("EMPTY")) {
            return;
        }

        this.stateAdapter.getInitialPoint().setLocation(
                (int) point.getX() - piece.getXPosition(),
                (int) point.getY() - piece.getYPosition()
        );

        cursorHandler.setCursor("grab");
        piece.setIsDragged(true);
        this.stateAdapter.toggleDragging();
        this.stateAdapter.setSelected(piece);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        cursorHandler.setCursor("normal");

        if(!stateAdapter.getDragging()) {
            return;
        }

        Piece selected = stateAdapter.getSelected();
        Piece target = chessManager.getNearestRect(selected);

        System.out.println("---------");
        System.out.printf("Selected row: %s col: %s %n", selected.getRow(), selected.getCol());
        System.out.printf("Target row: %s col: %s %n", target.getRow(), target.getCol());
        moveHandler.move(selected, target);

        selected.setIsDragged(false);
        stateAdapter.toggleDragging();
        stateAdapter.setSelected(null);
    }
}
