package src.com.chess.move;

import src.com.chess.constants.PiecesType;
import src.com.chess.game.Piece;

import java.util.ArrayList;

public class PseudoMoveValidator {

    public static boolean validate(Move move, Piece[][] board, ArrayList<Move> history) {
        boolean isValid  = switch (move.piece.getType()) {
            case PiecesType.PAWN -> validatePawn(move, board, history);
            case PiecesType.KNIGHT -> validateKnight(move, board);
            case PiecesType.ROOK, PiecesType.QUEEN, PiecesType.BISHOP -> validateSlidingPiece(move, board);
            case PiecesType.KING -> validateKing(move, board);
            default -> false;
        };

        return isValid && move.piece.getColor() != move.captured.getColor();
    }

    public static boolean validatePawn(Move move, Piece[][] board, ArrayList<Move> history) {
        return MoveRules.validatePawnMove(move, board, history);
    }

    public static boolean validateKnight(Move move, Piece[][] board) {
        return MoveRules.validateKnightMove(move, board);
    }

    public static boolean validateSlidingPiece(Move move, Piece[][] board) {
        return MoveRules.validateSlidingPiece(move, board);
    }

    public static boolean validateKing(Move move, Piece[][] board) {
        return MoveRules.validateKingMove(move, board);
    }
}
