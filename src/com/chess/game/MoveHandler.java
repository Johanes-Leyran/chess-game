package src.com.chess.game;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MoveHandler {
//    int EMPTY = -1;
//    int PAWN = 0;
//    int KNIGHT = 1;
//    int ROOK = 2;
//    int BISHOP = 3;
//    int QUEEN = 4;
//    int KING = 5;
    static ArrayList<Move> moveHistory; // could use a linked list instead
    static HashMap<Integer, String> pieceName = new HashMap<>();

    ChessManager chessManager;
    JPanel chessPanel;

    public MoveHandler(ChessManager chessManager, JPanel chessPanel) {
        this.chessManager = chessManager;
        this.chessPanel = chessPanel;

        pieceName.put(-1, "EMPTY");
        pieceName.put(0, "PAWN");
        pieceName.put(1, "KNIGHT");
        pieceName.put(2, "ROOK");
        pieceName.put(3, "BISHOP");
        pieceName.put(4, "QUEEN");
        pieceName.put(5, "KING");
    }

    public int[][] showAvailableMoves(int type, int col, int row) {
        return null;
    }

    public void revertMove(Move move) {}

    // todo: black piece seem to be inconsistent compared to whites
    // todo: trying to move black pieces more than one time does not always work
    // todo: unable to move a piece and put it back at the last position
    // todo: inconsistent move mechanics
    // todo: hovering mouse at same piece after moving does not show the toHold mouse cursor
    // todo: changing of the rectangle of pieces is inconsistent
    // todo: fix inconsistent col and row

    public void move(Piece selected, Piece target) {
        if(selected.equals(target)) {
            return;
        }

        System.out.printf("Selected: %s %n", pieceName.get(selected.getType()));
        System.out.printf("Target: %s %n", pieceName.get(target.getType()));

        Point selectedPoint = selected.getLocation();
        Point targetPoint = target.getLocation();

        int selectedRow = selected.getRow();
        int selectedCol = selected.getCol();

        int targetRow = target.getRow();
        int targetCol = target.getCol();

        selected.setBounds(targetPoint);
        System.out.printf(
                "Set %s bounds to row: %s col: %s %n",
                pieceName.get(selected.getType()),
                chessManager.getColumnPos(targetPoint.x),
                chessManager.getRowPos(targetPoint.y)
        );
        target.setBounds(selectedPoint);
        System.out.printf(
                "Set %s bounds to row: %s col: %s %n",
                pieceName.get(target.getType()),
                chessManager.getColumnPos(selectedPoint.x),
                chessManager.getRowPos(selectedPoint.y)
        );

        selected.setPosition(targetPoint);
        target.setPosition(selectedPoint);

        chessManager.setPiece(targetRow, targetCol, selected);
        chessManager.setPiece(selectedRow, selectedCol, target);

        selected.setSnappedPosition(targetRow, targetCol);
        target.setSnappedPosition(selectedRow, selectedCol);

        this.chessPanel.repaint();
        SoundManager.play("move-self");
    }
}
