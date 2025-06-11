package src.com.chess.move;

import src.com.chess.constants.PiecesType;
import src.com.chess.game.Piece;

import java.util.ArrayList;

public class PseudoMoveValidator {

    public static boolean validate(Move move, Piece[][] board, ArrayList<Move> history) {
        return switch (move.piece.getType()) {
            case PiecesType.PAWN -> validatePawn(move, board, history);
            case PiecesType.KNIGHT -> validateKnight(move, board);
            case PiecesType.ROOK -> validateRook(move, board);
            case PiecesType.BISHOP -> validateBishop(move, board);
            case PiecesType.QUEEN -> validateQueen(move, board);
            case PiecesType.KING -> validateKing(move, board);
            default -> false;
        };
    }

    public static boolean validatePawn(Move move, Piece[][] board, ArrayList<Move> history) {
        return MoveRules.validatePawnMove(move, board, history);
    }

    public static boolean validateKnight(Move move, Piece[][] board) {
        return MoveRules.validateKnightMove(move, board);
    }

    public static boolean validateRook(Move move, Piece[][] board) {
        return MoveRules.validateRookMove(move, board);
    }

    public static boolean validateBishop(Move move, Piece[][] board) {
        return MoveRules.validateBishopMove(move, board);
    }

    public static boolean validateQueen(Move move, Piece[][] board) {
        return MoveRules.validateQueenMove(move, board);
    }

    public static boolean validateKing(Move move, Piece[][] board) {
        return MoveRules.validateKingMove(move, board);
    }
}
