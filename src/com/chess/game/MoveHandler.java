package src.com.chess.game;


import src.com.chess.constants.PiecesColors;
import src.com.chess.utils.Log;
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

    public void swap(Move move) {
        chessManager.updatePiece(move.new_row, move.new_col, move.piece);
        chessManager.updatePiece(move.prev_row, move.prev_col, move.captured);
    }

    public void capture(Move move) {
        Log.INFO(String.format(
                "%s Move Type: Capture",
                this.getClass().getSimpleName()
        ));
        // todo: more capture logics
        move.captured.setColor(-1);
        swap(move);
        SoundManager.play("capture");
    }

    public void castle(Move move) {
        Log.INFO(String.format(
                "%s  Move Type: Castle",
                this.getClass().getSimpleName()
        ));

        SoundManager.play("castle");
    }

    public void promote(Move move) {
        Log.INFO(String.format(
                "%s  Move Type: Promote",
                this.getClass().getSimpleName()
        ));

        SoundManager.play("promote");
    }

    public void check(Move move) {
        Log.INFO(String.format(
                "%s  Move Type: Check",
                this.getClass().getSimpleName()
        ));

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

        boolean isValidMove = MoveValidator.validateMove(move, chessManager.getChessBoard(), moveHistory);

        if(!isValidMove) {
            Log.INFO(String.format(
                    "%s Not A valid move! Aborting...",
                    this.getClass().getSimpleName()
            ));
            return;
        }

        if(move.isEnPassant || move.isCapture) {
            capture(move);
        }
    }
}
