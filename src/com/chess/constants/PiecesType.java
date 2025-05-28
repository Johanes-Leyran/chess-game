package src.com.chess.constants;


// could also use this as an order of priority of pieces
// adjust the values to correspond with the index of ths spritesheet
public interface PiecesType {
    int EMPTY = -1;
    int PAWN = 0;
    int KNIGHT = 1;
    int ROOK = 2;
    int BISHOP = 3;
    int QUEEN = 4;
    int KING = 5;
}