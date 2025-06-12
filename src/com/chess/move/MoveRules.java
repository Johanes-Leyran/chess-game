package src.com.chess.move;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;
import src.com.chess.game.Piece;

import java.util.ArrayList;


// Be warned! Dirty code incoming
public class MoveRules {

    public static boolean validatePawnMove(Move move, Piece[][] board, ArrayList<Move> history) {
        int dir = move.piece.getColor() == 0 ? -1 : 1;
        int startRow = move.piece.getColor() == 0 ? 6 : 1;
        int rowDiff = move.new_row - move.prev_row;
        int colDiff = Math.abs(move.new_col - move.prev_col);

        // Normal move
        if (colDiff == 0) {
            if (rowDiff == dir && board[move.new_row][move.new_col].getColor() == PiecesColors.EMPTY) return true;
            if (move.prev_row == startRow && rowDiff == 2 * dir &&
                    board[move.new_row][move.new_col].getColor() == PiecesColors.EMPTY &&
                    board[move.prev_row + dir][move.prev_col].getColor() == PiecesColors.EMPTY) return true;
        }

        // Capture
        if (colDiff == 1 && rowDiff == dir) {
            Piece target = board[move.new_row][move.new_col];
            if (target.getColor() != PiecesColors.EMPTY && target.getColor() != move.piece.getColor()) return true;

            // En passant
            if (target.getColor() == PiecesColors.EMPTY && history != null && !history.isEmpty()) {
                Move lastMove = history.getLast();
                return lastMove.piece.getType() == PiecesType.PAWN &&
                        lastMove.piece.getColor() != move.piece.getColor() &&
                        Math.abs(lastMove.new_row - lastMove.prev_row) == 2 &&
                        lastMove.new_row == move.prev_row &&
                        lastMove.new_col == move.new_col;
            }
        }

        return false;
    }

    public static boolean validateKnightMove(Move move, Piece[][] board) {
        int dr = Math.abs(move.new_row - move.prev_row);
        int dc = Math.abs(move.new_col - move.prev_col);

        return (dr == 2 && dc == 1) || (dr == 1 && dc == 2);
    }

    public static boolean validateSlidingPiece(Move move, Piece[][] board) {
        int dr = move.new_row - move.prev_row;
        int dc = move.new_col - move.prev_col;

        boolean validRook = dr == 0 || dc == 0;
        boolean validBishop = Math.abs(dr) == Math.abs(dc);

        if(move.piece.getType() == PiecesType.ROOK && !validRook) return false;
        if(move.piece.getType() == PiecesType.BISHOP && !validBishop) return false;
        if(move.piece.getType() == PiecesType.QUEEN && !validRook && !validBishop) return false;

        return MoveSafety.isLinearPathClear(move, board);
    }

    public static boolean validateKingMove(Move move, Piece[][] board) {
        int dr = Math.abs(move.new_row - move.prev_row);
        int dc = Math.abs(move.new_col - move.prev_col);

        // Normal king move
        if (dr <= 1 && dc <= 1) return true;

        // Castling
        if (dr == 0 && (dc == 2 || move.new_col == 2 || move.new_col == 6)) {
            int row = move.prev_row;
            int color = move.piece.getColor();
            boolean isValidCastle = false;

            // Cannot castle if king already moved
            if (move.piece.isMoved()) return false;

            // King must be on initial square
            if ((color == 0 && row != 7) || (color == 1 && row != 0)) return false;

            // Kingside castle
            if (move.new_col == 6) {
                isValidCastle = validateCastle(board, 7, new int[]{5, 6}, new int[]{4, 5, 6}, row, color);
            }

            // Queenside castle
            if (move.new_col == 2) {
                isValidCastle = validateCastle(board, 0, new int[]{1, 2, 3}, new int[]{4, 3, 2}, row, color);
            }

            if(isValidCastle) {
                move.isCastle = true; // I know this a side effect but this will do
                return true;
            }
        }

        return false;
    }

    public static boolean validateCastle(
            Piece[][] board,
            int rook_col,
            int[] clearCols,
            int[] safeCols,
            int row,
            int color
    ) {
        Piece rook = board[row][rook_col];
        if (rook.getColor() == PiecesColors.EMPTY ||
                rook.getType() != PiecesType.ROOK ||
                rook.getColor() != color ||
                rook.isMoved())
            return false;

        for (int col : clearCols)
            if (board[row][col].getColor() != PiecesColors.EMPTY) return false;

        for (int col : safeCols)
            if (!MoveSafety.isSquareSafe(row, col, 1 - color, board)) return false;

        return true;
    }
}
