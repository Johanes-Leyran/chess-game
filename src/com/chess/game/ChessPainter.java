package src.com.chess.game;

import src.com.chess.constants.PiecesColors;
import src.com.chess.move.Move;
import src.com.chess.move.MoveSafety;
import src.com.chess.move.MoveValidator;
import src.com.chess.move.PseudoMoveValidator;

import java.awt.*;
import java.util.ArrayList;

public class ChessPainter {

    // draws the pieces in base on their col and row position
    public static void drawPieces(Graphics g, ChessManager chessManager) {
        for(int row = 0;row < 8;row++) {
            for(int col = 0;col < 8;col++) {
                Piece piece = chessManager.getChessBoard()[row][col];

                // don't draw the piece if its being dragged
                if(piece.getColor() == PiecesColors.EMPTY || piece.isDragged())
                    continue;

                g.drawImage(
                        piece.getSprite(),
                        chessManager.getSnappedXPos(piece.getCol()),
                        chessManager.getSnappedYPos(piece.getRow()),
                        null
                );
            }
        }
    }

    // draw a piece based on its x and y pos
    public static void drawSinglePiece(Graphics g, Piece piece) {
        if(piece == null) return;

        g.drawImage(piece.getSprite(), piece.getXPosition(), piece.getYPosition(), null);
    }

    // note to self: this does not change the actual position in chess board only the sprites positions
    public static void slidePiece(Graphics g, Piece piece, int target_col, int target_row, ChessManager chessManager) {
        Point startingPos = piece.getLocation();
        Point targetPos = new Point(chessManager.getSnappedPos(target_col), chessManager.getSnappedPos(target_row));
        piece.setIsDragged(true);

        int step = 5;
        double targetDistance = startingPos.distance(targetPos);
        double coveredDistance = 0;

        double vectorX = (startingPos.getX() - targetPos.getX()) / targetDistance;
        double vectorY = (startingPos.getY() - targetPos.getY()) / targetDistance;

        while(coveredDistance <= targetDistance) {
            double currentStep = Math.min(step, targetDistance - coveredDistance);

            piece.setPosition(
                    (int) (piece.getXPosition() + vectorX * currentStep),
                    (int) (piece.getYPosition() + vectorY * currentStep)
            );
            drawSinglePiece(g, piece);
            coveredDistance += currentStep;
        }

        piece.setPosition(targetPos);
        piece.setIsDragged(false);
    }
    public static void drawRect(Graphics g, ChessManager chessManager, int row, int col) {
        Rectangle rect = chessManager.getChessBoard()[row][col].getBounds();
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
    }

    // only for debugging
    public static void drawAllRect(Graphics g, ChessManager chessManager) {
        for(int row = 0;row < 8;row++) {
            for(int col = 0;col < 8;col++) {
                drawRect(g, chessManager, row, col);
            }
        }
    }

    // draw red square if a king piece is being attack
    public static void drawRedSquare(Graphics g, ChessManager chessManager, int color) {
        Piece[][] board = chessManager.getChessBoard();
        int[] kingPos = MoveSafety.findKing(board, color);

        if (kingPos == null) return; // Just in case

        int kingRow = kingPos[0];
        int kingCol = kingPos[1];

        if (MoveSafety.isSquareSafe(kingRow, kingCol, 1 - color, board)) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(255, 0, 0, 255));
        // does not perfectly align with the tile, prob because of uneven scaling
        g2d.fillRect(
                chessManager.getSnappedPos(kingCol),
                chessManager.getSnappedPos(kingRow),
                chessManager.getTileSize(),
                chessManager.getTileSize()
        );
        g2d.dispose();
    }

    public static void drawCircle(Graphics g, ChessManager chessManager, int row, int col) {
        Piece piece = chessManager.getChessBoard()[row][col];
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tileSize = chessManager.getTileSize();
        int centerX = chessManager.getSnappedPos(col) + tileSize / 2 + 2;
        int centerY = chessManager.getSnappedPos(row) + tileSize / 2 + 4;
        Color gray = new Color(69, 69, 69, 150);

        if (piece == null || piece.getColor() == PiecesColors.EMPTY) {
            int diameter = tileSize / 4;
            int x = centerX - diameter / 2;
            int y = centerY - diameter / 2;

            g2d.setColor(gray);
            g2d.fillOval(x, y, diameter, diameter);
        } else {
            int diameter = tileSize - 25;
            int x = centerX - diameter / 2;
            int y = centerY - diameter / 2 + 5;

            g2d.setColor(gray);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawOval(x, y, diameter, diameter);
        }

        g2d.dispose();
    }

    // draw all valid moves if there's any
    public static void drawValidMoves(Graphics g, Piece piece, ChessManager chessManager, ArrayList<Move> history) {
        if(piece == null) return;

        for(int row = 0;row < 8;row++) {
            for(int col = 0;col < 8;col++) {
                Move pseudoMove = new Move(
                        piece.getCol(), piece.getRow(), col, row, piece, chessManager.getChessBoard()[row][col]
                );

                if(!MoveValidator.isValid(pseudoMove, chessManager.getChessBoard(), history)) continue;

                drawCircle(g, chessManager, row, col);
            }
        }
    }
}
