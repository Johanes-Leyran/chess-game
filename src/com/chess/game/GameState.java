package src.com.chess.game;

import src.com.chess.move.MoveValidator;
import src.com.chess.move.MoveSafety;
import src.com.chess.move.Move;
import src.com.chess.constants.PiecesColors;

import java.util.ArrayList;

public class GameState {

    public interface State {
        int ONGOING = 0;
        int STALEMATE = 1;
        int WHITE_WIN = 2;
        int BLACK_WIN = 3;
    }

    private int state;
    private int colorTurn;

    public GameState() {
        this.state = State.ONGOING;
        this.colorTurn = PiecesColors.WHITE;
    }

    public void checkState(ArrayList<Move> moveHistory, ChessManager chessManager) {
        Piece[][] board = chessManager.getChessBoard();

        boolean hasMoves = MoveValidator.hasAnyLegalMove(board, moveHistory, this.colorTurn);
        boolean isKingSafe = MoveSafety.isKingSafe(board, this.colorTurn);

        if (hasMoves) {
            state = State.ONGOING;
        } else {
            state = isKingSafe
                    ? State.STALEMATE
                    : (this.colorTurn == PiecesColors.WHITE
                        ? State.BLACK_WIN : State.WHITE_WIN);

        }

    }

    public int getColorTurn() { return this.colorTurn; }

    public void setColorTurn(int color) { this.colorTurn = color; }

    public int getState() { return this.state; }
}
