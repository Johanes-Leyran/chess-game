package src.com.chess.move;

import src.com.chess.constants.PiecesType;
import src.com.chess.game.Piece;

public class MoveFlagger {

    public static void markCheck(Move move, Piece[][] board) {
        // make sure to disregard pieces who have empty color
        int enemyColor = 1 - move.piece.getColor();
        int[] kingPos = findKing(board, enemyColor);
        if (kingPos == null) return;

        if (!MoveSafety.isSquareSafe(kingPos[0], kingPos[1], move.piece.getColor(), board)) {
            move.isCheck = true;

            // if it is a check, check if the player has any valid moves to counter it
            // otherwise that player lost

        }
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
}
