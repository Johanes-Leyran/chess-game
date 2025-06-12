package src.com.chess.move;

import src.com.chess.game.Piece;

import java.util.ArrayList;

public class MoveValidator {

    public static boolean isValid(Move move, Piece[][] board, ArrayList<Move> history) {
        if (!PseudoMoveValidator.validate(move, board, history)) return false;

        Piece[][] copy = deepCopyBoard(board);

        MoveSimulator.apply(move, copy);
        MoveFlagger.markCheck(move, copy);

        return MoveSafety.isKingSafe(copy, move.piece.getColor());
    }

    public static boolean hasAnyLegalMove(Piece[][] board, ArrayList<Move> history, int color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (MoveSafety.hasLegalMove(board, history, color, row, col)) return true;
            }
        }
        return false;
    }

    public static Piece[][] deepCopyBoard(Piece[][] original) {
        Piece[][] copy = new Piece[8][8];
        for (int row = 0; row < 8; row++)
            for (int col = 0; col < 8; col++)
                copy[row][col] = original[row][col] == null ? null : new Piece(original[row][col]);
        return copy;
    }
}
