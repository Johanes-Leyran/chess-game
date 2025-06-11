package src.com.chess.move;

import src.com.chess.game.Piece;

public class MoveSimulator {
    public static void apply(Move move, Piece[][] board) {
        board[move.new_row][move.new_col] = board[move.prev_row][move.prev_col];
        board[move.prev_row][move.prev_col] = null;
    }
}
