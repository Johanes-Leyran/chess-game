package src.com.chess.game;

import src.com.chess.move.MoveHandler;
import src.com.chess.move.MoveValidator;
import src.com.chess.move.MoveSafety;
import src.com.chess.move.Move;
import src.com.chess.constants.PiecesColors;
import src.com.chess.utils.CardLayoutHandler;
import src.com.chess.utils.FontHandler;
import src.com.chess.utils.Log;
import src.com.chess.utils.PopUpBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameState {
    public interface State {
        int ONGOING = 0;
        int STALEMATE = 1;
        int WHITE_WIN = 2;
        int BLACK_WIN = 3;
    }

    // don't know where to put this and too lazy to think
    // note: white captured means the captured piece of white side and vice versa
    ArrayList<Piece> whiteCaptured;
    ArrayList<Piece> blackCaptured;

    int whiteScore;
    int blackScore;

    int state;
    int colorTurn;
    boolean gameStart;
    boolean reset;

    ChessManager chessManager;
    MoveHandler moveHandler;

    public GameState(
            ChessManager chessManager,
            MoveHandler moveHandler
    ) {
        this.state = State.ONGOING;
        this.colorTurn = PiecesColors.WHITE;
        this.gameStart = false;

        this.whiteCaptured = new ArrayList<>();
        this.blackCaptured = new ArrayList<>();

        this.chessManager = chessManager;
        this.moveHandler = moveHandler;
    }

    public void updateCaptured(Piece captured) {
        if(this.colorTurn == PiecesColors.WHITE)
            this.whiteCaptured.add(captured);
        else if(this.colorTurn == PiecesColors.BLACK)
            this.blackCaptured.add(captured);
    }

    public void startGame() {
        if(!gameStart) {
            this.gameStart = true;
        }
    }

    public ArrayList<Piece> getWhiteCaptured() { return this.whiteCaptured; }

    public ArrayList<Piece> getBlackCaptured() {return this.blackCaptured; }

    public boolean getStartGame() { return this.gameStart; }

    public void resetGame() {
        this.whiteCaptured.clear();
        this.blackCaptured.clear();
        CardLayoutHandler.mainPanel.repaint();

        this.gameStart = false;
        this.reset = true;
        this.colorTurn = PiecesColors.WHITE;
        this.state = State.ONGOING;
    }

    public void checkState(ArrayList<Move> moveHistory, ChessManager chessManager) {
        Piece[][] board = chessManager.getChessBoard();

        boolean hasMoves = MoveValidator.hasAnyLegalMove(board, moveHistory, this.colorTurn);
        boolean isKingSafe = MoveSafety.isKingSafe(board, this.colorTurn);

        if (hasMoves) {
            this.state = State.ONGOING;
        } else {
            this.state = isKingSafe
                    ? State.STALEMATE
                    : (this.colorTurn == PiecesColors.WHITE
                        ? State.BLACK_WIN : State.WHITE_WIN);

        }

        Log.INFO(String.format(
                "%s check Game State: %s",
                this.getClass().getSimpleName(),
                this.getStringState()
        ));
    }

    public boolean getReset() { return this.reset; }

    public void setReset(boolean b) { this.reset = b; }

    public int getColorTurn() { return this.colorTurn; }

    public void setColorTurn(int color) { this.colorTurn = color; }

    public int getState() { return this.state; }

    public void popUpState() {
        PopUpBuilder.popMessage(getStringState(), this, chessManager, moveHandler);
    }

    public void stateTimeOver(int color) {
        if(color == PiecesColors.WHITE) {
            this.state = State.BLACK_WIN;
        } else if(color == PiecesColors.BLACK) {
            this.state = State.WHITE_WIN;
        }
    }

    public String getStringState() {
        return switch(getState()) {
            case State.ONGOING -> "ONGOING";
            case State.STALEMATE -> "STALEMATE";
            case State.WHITE_WIN -> "WHITE WIN";
            case State.BLACK_WIN -> "BLACK WIN";
            default -> throw new IllegalStateException("Unexpected value: " + getState());
        };
    }
}
