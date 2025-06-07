package src.com.chess.game;


import src.com.chess.constants.PiecesColors;
import src.com.chess.pieces.Piece;
import src.com.chess.utils.SoundManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveHandler {
    static ArrayList<Move> moveHistory; // could use a linked list instead
    static HashMap<Integer, String> pieceName = new HashMap<>();

    ChessManager chessManager;

    public MoveHandler(ChessManager chessManager) {
        this.chessManager = chessManager;

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

    // todo: inconsistent dragging mechanics
    // todo: hovering mouse at same piece after moving does not show the toHold mouse cursor

    public void swap(Move move) {
        chessManager.updatePiece(move.new_row, move.new_col, move.piece);
        chessManager.updatePiece(move.prev_row, move.prev_col, move.captured);
    }

    public void capture(Move move) {
        move.captured.setColor(-1);
        swap(move);
        SoundManager.play("capture");
    }

    public void castle(Move move) {
        SoundManager.play("castle");
    }

    public void promote(Move move) {
        SoundManager.play("promote");
    }

    public void check(Move move) {
        SoundManager.play("move-check");
    }

    public void move(Move move) {
        swap(move);
        SoundManager.play("move-self");
    }

    public void validateMove(Piece selected, Piece target) {
        Move move = new Move(
                selected.getCol(),  selected.getRow(), target.getCol(), target.getRow(), selected, target
        );

        if(target.getColor() == PiecesColors.EMPTY || target.getColor() == selected.getColor()) {
            move(move);
        } else {
            capture(move);
        }
    }
}
