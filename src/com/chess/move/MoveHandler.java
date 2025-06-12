package src.com.chess.move;


import src.com.chess.constants.PiecesColors;
import src.com.chess.game.ChessManager;
import src.com.chess.game.GameState;
import src.com.chess.game.Piece;
import src.com.chess.utils.Log;
import src.com.chess.utils.SoundManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveHandler {
    static ArrayList<Move> moveHistory = new ArrayList<>(); // could use a linked list instead
    static HashMap<Integer, String> pieceName = new HashMap<>();

    ChessManager chessManager;
    GameState gameState;

    public MoveHandler(ChessManager chessManager, GameState gameState) {
        this.chessManager = chessManager;
        this.gameState = gameState;

        pieceName.put(-1, "EMPTY");
        pieceName.put(0, "PAWN");
        pieceName.put(1, "KNIGHT");
        pieceName.put(2, "ROOK");
        pieceName.put(3, "BISHOP");
        pieceName.put(4, "QUEEN");
        pieceName.put(5, "KING");
    }

    public ArrayList<Move> getMoveHistory() { return moveHistory; }

    public void resetMoveHistory() { moveHistory.clear(); }

    public void revertMove(Move move) {} // will not implement this, not enough time

    public void swap(Move move) {
        chessManager.updatePiece(move.new_row, move.new_col, move.piece);
        chessManager.updatePiece(move.prev_row, move.prev_col, move.captured);
    }

    public void capture(Move move) {
        Log.INFO(String.format(
                "%s Move Type: Capture",
                this.getClass().getSimpleName()
        ));
        // todo: more capture logics
        move.captured.setColor(-1);
        swap(move);
        SoundManager.play("capture");
    }

    public void enPassant(Move move, Piece[][] board) {
        Log.INFO(String.format(
                "%s Move Type: En Passant",
                this.getClass().getSimpleName()
        ));

        int direction = move.piece.getColor() == 0 ? 1 : -1;
        int capturedRow = move.new_row + direction;
        int capturedCol = move.new_col;

        move.captured = board[capturedRow][capturedCol];
        board[capturedRow][capturedCol].setColor(PiecesColors.EMPTY);

        swap(move); // Move capturing pawn
        SoundManager.play("capture");
    }

    public void castle(Move move) {
            Log.INFO(String.format("%s Castling...", this.getClass().getSimpleName()));

            int row = move.new_row; // The king and rook are on the same row
            int oldKingCol = move.prev_col;
            int newKingCol = move.new_col;

            boolean isKingSide = newKingCol > oldKingCol;

            int rookStartCol = isKingSide ? 7 : 0;
            int rookTargetCol = isKingSide ? newKingCol - 1 : newKingCol + 1;

            Piece[][] board = chessManager.getChessBoard();
            Piece rook = board[row][rookStartCol];

            if (rook == null) {
                Log.DEBUG("Rook not found for castling");
                return;
            }

            // Move the King
            Move kingMove = new Move(oldKingCol, row, newKingCol, row, move.piece, board[row][newKingCol]);
            swap(kingMove);

            // Move the Rook
            Move rookMove = new Move(rookStartCol, row, rookTargetCol, row, rook, board[row][rookTargetCol]);
            // Set the x y pos of the rook since it did not go through ChessMouseAdapter
            rook.setPosition(chessManager.getSnappedXPos(rookTargetCol), chessManager.getSnappedYPos(row));
            swap(rookMove);

            SoundManager.play("castle");
    }

    public void promote(Move move) {
        Log.INFO(String.format(
                "%s  Move Type: Promote",
                this.getClass().getSimpleName()
        ));

        // add a pop-up menu here

        SoundManager.play("promote");
    }

    public void move(Move move) {
        swap(move);
        SoundManager.play("move-self");
    }

    public boolean validateMove(Piece selected, Piece target) {
        if(selected == target) {
            return false;
        }

        Move move = new Move(
                selected.getCol(),  selected.getRow(), target.getCol(), target.getRow(), selected, target
        );

        Log.DEBUG(String.format(
                "%s Piece Selected Type: %s Color: %s",
                this.getClass().getSimpleName(),
                pieceName.get(move.piece.getType()),
                move.piece.getStringColor()
        ));
        Log.DEBUG(String.format(
                "%s Piece Target Type: %s Color: %s",
                this.getClass().getSimpleName(),
                pieceName.get(move.captured.getType()),
                move.captured.getStringColor()
        ));
        Log.DEBUG(String.format(
                "%s Make a move: from row: %s col: %s to row: %s col: %s",
                this.getClass().getSimpleName(),
                move.prev_col, move.prev_row,
                move.new_col, move.new_row
        ));

        if(!MoveValidator.isValid(move, chessManager.getChessBoard(), moveHistory)) {
            Log.INFO(String.format(
                    "%s Invalid move, aborting", this.getClass().getSimpleName()
            ));
            return false;
        };

        if (move.isCastle) {
            castle(move);
        } else if (move.isEnPassant) {
            enPassant(move, chessManager.getChessBoard());
        } else if (move.captured.getColor() != PiecesColors.EMPTY) {
            capture(move);
        } else {
            move(move);
        }

        moveHistory.add(move);
        gameState.setColorTurn(gameState.getColorTurn() == PiecesColors.WHITE ? PiecesColors.BLACK : PiecesColors.WHITE);
        gameState.checkState(moveHistory, chessManager);
        return true;
    }
}
