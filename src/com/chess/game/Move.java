package src.com.chess.game;


public class Move {
    public int prev_col, prev_row, new_col, new_row;
    public Piece piece, captured;

    public Move(
            int prev_col, int prev_row,
            int new_col, int new_row,
            Piece piece, Piece captured
    ) {
        this.prev_col = prev_col;
        this.prev_row = prev_row;

        this.new_col = new_col;
        this.new_row = new_row;

        this.piece = piece;
        this.captured = captured;
    }
}
