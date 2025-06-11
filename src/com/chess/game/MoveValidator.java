package src.com.chess.game;


// prefer this method over extending the piece class
// separate this logic from move handler for better readability

// PAWN = 0
// KNIGHT = 1
// ROOK = 2
// BISHOP = 3
// QUEEN = 4
// KING = 5

import java.util.ArrayList;

// Be warned! my code will be nasty from here
public class MoveValidator {
    public static boolean validateMove(Move move, Piece[][] chessboard, ArrayList<Move> moveHistory) {
        boolean isLegal;

        // Validate move based on piece type
        isLegal = switch (move.piece.getType()) {
            case 0 -> validatePawnMove(move, chessboard, moveHistory);
            case 1 -> validateKnightMove(move, chessboard);
            case 2 -> validateRookMove(move, chessboard);
            case 3 -> validateBishopMove(move, chessboard);
            case 4 -> validateQueenMove(move, chessboard);
            case 5 -> validateKingMove(move, chessboard);
            default -> false;
        };

        if (!isLegal) return false;

        // Simulate the move and check if own king is in check
        Piece[][] simulatedBoard = deepCopyBoard(chessboard);
        applyMoveToBoard(simulatedBoard, move);

        // Find king
        int kingRow = 0;
        int kingCol = 0;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = simulatedBoard[r][c];
                if (p != null && p.getType() == 5 && p.getColor() == move.piece.getColor()) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
        }

        // If move leaves own king in check, it's illegal
        return isSquareSafe(
                kingRow, kingCol, 1 - move.piece.getColor(), simulatedBoard
        );
    }

    public static void applyMoveToBoard(Piece[][] board, Move move) {
        board[move.new_row][move.new_col] = board[move.prev_row][move.prev_col];
        board[move.prev_row][move.prev_col] = null;
    }


    private static Piece[][] deepCopyBoard(Piece[][] original) {
        Piece[][] copy = new Piece[8][8];

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = original[r][c];
                if (p != null) {
                    copy[r][c] = new Piece(p);
                }
            }
        }

        return copy;
    }

    public static boolean isSquareSafe(int row, int col, int attackingColor, Piece[][] board) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];

                if (p == null || p.getColor() != attackingColor) {
                    continue;
                }

                Move pseudoMove = new Move(c, r, col, row, p, board[row][col]);
                if (MoveValidator.validateMove(pseudoMove, board, null)) {
                    return false;
                }
            }
        }

        return true;
    }

    // for the record I don't know what I'm doing at this point
    private static void markCheck(Move move, Piece[][] board) {
        // Simulate the board after the move
        Piece[][] simulatedBoard = deepCopyBoard(board);
        simulatedBoard[move.new_row][move.new_col] = move.piece;
        simulatedBoard[move.prev_row][move.prev_col] = null;

        // Find the opponent's king
        int enemyColor = 1 - move.piece.getColor();
        int kingRow = -1;
        int kingCol = -1;

        outerLoop: // I don't know this feature exists
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = simulatedBoard[r][c];
                if (p != null && p.getType() == 5 && p.getColor() == enemyColor) {
                    kingRow = r;
                    kingCol = c;
                    break outerLoop;
                }
            }
        }

        if (kingRow == -1) return; // Should never happen

        // If enemy king is not safe, the move gives check
        if (!isSquareSafe(kingRow, kingCol, move.piece.getColor(), simulatedBoard)) {
            move.isCheck = true;
        }
    }

    public static boolean validatePawnMove(Move move, Piece[][] chessboard, ArrayList<Move> moveHistory) {
        int color = move.piece.getColor();
        int dir = color == 0 ? -1 : 1; // white = -1, black = +1
        int startRow = color == 0 ? 6 : 1;
        int promoteRow = color == 0 ? 0 : 7;
        int rowDiff = move.new_row - move.prev_row;
        int colDiff = move.new_col - move.prev_col;
        int absColDiff = Math.abs(colDiff);

        Piece destination = chessboard[move.new_row][move.new_col];

        // Promotion check
        if (move.new_row == promoteRow) {
            move.isPromote = true;
        }

        // Regular one-step forward
        if (colDiff == 0 && rowDiff == dir && destination == null) {
            return true;
        }

        // Double move from starting row
        if (colDiff == 0 && rowDiff == 2 * dir && move.prev_row == startRow && !move.piece.isMoved()) {
            if (chessboard[move.prev_row + dir][move.prev_col] == null && destination == null) {
                return true;
            }
        }

        if (absColDiff != 1 || rowDiff != dir) return false;

        // Normal capture
        if (destination != null && destination.getColor() != color) {
            move.isCapture = true;
            return true;
        }

        if (moveHistory.isEmpty()) return false;

        Move lastMove = moveHistory.getLast();
        Piece lastPiece = lastMove.piece;

        boolean isOpponentPawn = lastPiece.getType() == 0 && lastPiece.getColor() != color;
        boolean movedTwo = Math.abs(lastMove.new_row - lastMove.prev_row) == 2;
        boolean onSameRow = lastMove.new_row == move.prev_row;
        boolean onSameCol = lastMove.new_col == move.new_col;

        if (!isOpponentPawn || !movedTwo || !onSameRow || !onSameCol) return false;

        // Google en passant
        move.captured = lastPiece;
        move.isCapture = true;
        move.isEnPassant = true;

        return true;
    }

    public static boolean validateKnightMove(Move move, Piece[][] chessboard) {
        int dx = Math.abs(move.new_col - move.prev_col);
        int dy = Math.abs(move.new_row - move.prev_row);

        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            if (move.captured == null) return true;
            if (move.captured.getColor() != move.piece.getColor()) {
                move.isCapture = true;
                return true;
            }
            return false;
        }

        return false;
    }

    public static boolean validateRookMove(Move move, Piece[][] chessboard) {
        if (move.prev_col != move.new_col && move.prev_row != move.new_row) return false;

        int colStep = Integer.compare(move.new_col, move.prev_col);
        int rowStep = Integer.compare(move.new_row, move.prev_row);
        int col = move.prev_col + colStep;
        int row = move.prev_row + rowStep;

        while (col != move.new_col || row != move.new_row) {
            if (chessboard[row][col] != null) return false;
            col += colStep;
            row += rowStep;
        }

        if (move.captured != null && move.captured.getColor() != move.piece.getColor()) {
            move.isCapture = true;
            return true;
        }
        return move.captured == null;

    }

    public static boolean validateBishopMove(Move move, Piece[][] chessboard) {
        int dx = Math.abs(move.new_col - move.prev_col);
        int dy = Math.abs(move.new_row - move.prev_row);

        if (dx != dy) return false;

        int colStep = Integer.compare(move.new_col, move.prev_col);
        int rowStep = Integer.compare(move.new_row, move.prev_row);
        int col = move.prev_col + colStep;
        int row = move.prev_row + rowStep;

        while (col != move.new_col && row != move.new_row) {
            if (chessboard[row][col] != null) return false;
            col += colStep;
            row += rowStep;
        }

        if (move.captured != null && move.captured.getColor() != move.piece.getColor()) {
            move.isCapture = true;
            return true;
        }
        return move.captured == null;

    }

    public static boolean validateQueenMove(Move move, Piece[][] chessboard) {
        // Queen's moves are essentially a combined rook and bishop
        return validateRookMove(move, chessboard) || validateBishopMove(move, chessboard);
    }

    public static boolean validateKingMove(Move move, Piece[][] chessboard) {
        int dx = Math.abs(move.new_col - move.prev_col);
        int dy = Math.abs(move.new_row - move.prev_row);
        int row = move.prev_row;
        int color = move.piece.getColor();

        // Standard king move
        if (dx <= 1 && dy <= 1) {
            if (move.captured != null && move.captured.getColor() != move.piece.getColor()) {
                move.isCapture = true;
                return true;
            }
            return move.captured == null;
        }

        // Castling: Must not have moved, move must be horizontal by 2 squares
        if (move.piece.isMoved() || dy != 0 || dx != 2) return false;

        // Kingside castling
        if (move.new_col == 6) {
            Piece rook = chessboard[row][7];
            boolean rookValid = rook != null && rook.getType() == 2 && rook.getColor() == color && !rook.isMoved();
            if (!rookValid) return false;

            boolean pathClear = chessboard[row][5] == null && chessboard[row][6] == null;
            if (!pathClear) return false;

            boolean pathSafe = isSquareSafe(row, 4, 1 - color, chessboard) &&
                    isSquareSafe(row, 5, 1 - color, chessboard) &&
                    isSquareSafe(row, 6, 1 - color, chessboard);

            if (!pathSafe) return false;

            move.isCastle = true;
            return true;
        }

        // Queenside castling
        if (move.new_col == 2) {
            Piece rook = chessboard[row][0];
            boolean rookValid = rook != null && rook.getType() == 2 && rook.getColor() == color && !rook.isMoved();
            if (!rookValid) return false;

            boolean pathClear = chessboard[row][1] == null &&
                    chessboard[row][2] == null &&
                    chessboard[row][3] == null;
            if (!pathClear) return false;

            boolean pathSafe = isSquareSafe(row, 4, 1 - color, chessboard) &&
                    isSquareSafe(row, 3, 1 - color, chessboard) &&
                    isSquareSafe(row, 2, 1 - color, chessboard);

            if (!pathSafe) return false;

            move.isCastle = true;
            return true;
        }

        return false;
    }
}
