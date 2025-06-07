package src.com.chess.pieces;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;
import src.com.chess.game.ChessManager;
import src.com.chess.game.Move;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pawn extends Piece{
    boolean hasMoved = false;

    public Pawn(int color, BufferedImage sprite, int col, int row, Rectangle rect, int x, int y) {
        super(color, PiecesType.PAWN, sprite, col, row, rect, x, y);
    }

    @Override
    public boolean isValidMove(Move move, Piece[][] chessboard) {
        int direction = this.color == PiecesColors.WHITE ? -1 : 1;
        int startRow = this.color == PiecesColors.WHITE ? 6 : 1;

        int direction_row = move.new_row - move.prev_row;
        int direction_col = move.new_col - move.prev_col;

        if (direction_col == 0 && move.captured.getColor() == PiecesColors.EMPTY) {
            if (direction_row == direction) {
                return chessboard[move.new_row][move.new_col].getColor() == PiecesColors.EMPTY;
            }

            if (direction_row == 2 * direction && move.prev_row == startRow) {
                return (
                        chessboard[move.prev_row + direction][move.prev_col] == null &&
                        chessboard[move.new_row][move.new_col] == null
                );
            }
        }

        else if (Math.abs(direction_col) == 1 && direction_row == direction) {
            return move.captured.getColor() != PiecesColors.EMPTY && move.captured.getColor() != this.getColor();
        }

        return false;
    }
}
