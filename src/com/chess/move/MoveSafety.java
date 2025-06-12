package src.com.chess.move;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;
import src.com.chess.game.Piece;

import java.util.ArrayList;

public class MoveSafety {

    public static boolean isKingSafe(Piece[][] board, int color) {
        int[] king = findKing(board, color);
        if (king == null) return false;
        return isSquareSafe(king[0], king[1], 1 - color, board);
    }

    public static int[] findKing(Piece[][] board, int color) {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p != null && p.getType() == PiecesType.KING && p.getColor() == color) {
                    return new int[]{r, c};
                }
            }

        return null;
    }

    public static boolean isSquareSafe(int row, int col, int attackerColor, Piece[][] board) {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++) {
                Piece attacker = board[r][c];
                if (attacker != null && attacker.getColor() == attackerColor) {
                    Move pseudo = new Move(c, r, col, row, attacker, board[row][col]);
                    if (PseudoMoveValidator.validate(pseudo, board, null)) {
                        return false;
                    }
                }
            }
        return true;
    }

    public static boolean hasLegalMove(Piece[][] board, ArrayList<Move> history, int color, int r, int c) {
        Piece piece = board[r][c];
        if (piece == null || piece.getColor() != color) return false;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Move move = new Move(c, r, col, row, piece, board[row][col]);

                if (!PseudoMoveValidator.validate(move, board, history)) continue;

                Piece[][] copy = MoveValidator.deepCopyBoard(board);
                MoveSimulator.apply(move, copy);

                if (isKingSafe(copy, color)) return true;
            }
        }

        return false;
    }

    public static boolean isEmpty(Piece piece) {
        return piece == null || piece.getColor() == PiecesColors.EMPTY;
    }

    public static boolean isLinearPathClear(Move move, Piece[][] board) {
        int dr = move.new_row - move.prev_row;
        int dc = move.new_col - move.prev_col;

        int stepRow = Integer.signum(dr);
        int stepCol = Integer.signum(dc);

        int row = move.prev_row + stepRow;
        int col = move.prev_col + stepCol;

        while (row != move.new_row || col != move.new_col) {
            if (!isEmpty(board[row][col])) return false; // It should be not empty but for some case it is

            row += stepRow;
            col += stepCol;
        }

        return true;
    }
}
