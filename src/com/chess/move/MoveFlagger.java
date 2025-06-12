package src.com.chess.move;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;
import src.com.chess.game.Piece;

public class MoveFlagger {

    public static void markCheck(Move move, Piece[][] board) {
        int targetColor = move.captured.getColor();

        // make sure to disregard pieces who have empty color
        if(targetColor == PiecesColors.EMPTY) {
            return;
        }

        int[] kingPos = findKing(board, targetColor);
        if (kingPos == null) return;

        if (!MoveSafety.isSquareSafe(kingPos[0], kingPos[1], move.piece.getColor(), board)) {
            move.isCheck = true;
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
