package src.com.chess.game;


// prefer this method over extending the piece class
// separate this logic from move handler for better readability

// PAWN = 0
// KNIGHT = 1
// ROOK = 2
// BISHOP = 3
// QUEEN = 4
// KING = 5

public class MoveValidator {
    public static boolean validateMove(Move move) {
        return switch (move.piece.getType()) {
            case 0 -> validatePawnMove(move);
            case 1 -> validateKnightMove(move);
            case 2 -> validateRookMove(move);
            case 3 -> validateBishopMove(move);
            case 4 -> validateQueenMove(move);
            case 5 -> validateKingMove(move);
            default -> false;
        };
    }

    public static boolean validatePawnMove(Move move) {


        return false;
    }

    public static boolean validateKnightMove(Move move) {
        return false;
    }

    public static boolean validateRookMove(Move move) {
        return false;
    }

    public static boolean validateBishopMove(Move move) {
        return false;
    }

    public static boolean validateQueenMove(Move move) {
        return false;
    }

    public static boolean validateKingMove(Move move) {
        return false;
    }
}
