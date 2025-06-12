package src.com.chess.game;

import src.com.chess.constants.PiecesColors;

import java.awt.*;

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

    // note to self: this does not change the actual position in chess board only animation and its x y pos
    public static void slidePiece(Graphics g, Piece piece, int target_col, int target_row, ChessManager chessManager) {
        Point startingPos = piece.getLocation();
        Point targetPos = new Point(chessManager.getSnappedXPos(target_col), chessManager.getSnappedYPos(target_row));
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
    // only for debug
    public static void drawRect(Graphics g, ChessManager chessManager) {
        if(Globals.getLevel() < 2) {
            return;
        }

        for(int row = 0;row < 8;row++) {
            for(int col = 0;col < 8;col++) {
                Rectangle rect = chessManager.getChessBoard()[row][col].getBounds();
                g.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }


}
