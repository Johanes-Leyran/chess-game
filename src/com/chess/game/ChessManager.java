package src.com.chess.game;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;
import src.com.chess.utils.Log;
import src.com.chess.utils.SpriteSheetHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ChessManager {
    static int[] backLine = {
            PiecesType.ROOK, PiecesType.KNIGHT, PiecesType.BISHOP, PiecesType.QUEEN, PiecesType.KING
    };
    static int[] pawnLine = {
            PiecesType.PAWN, PiecesType.PAWN, PiecesType.PAWN, PiecesType.PAWN,  PiecesType.PAWN
    };

    Piece[][] chessBoard = new Piece[8][8];
    JPanel chessPanel;
    int chessBoardOffset; // chess board sprite have space around the board
    double chessBoardScale;
    int chessBoardSize;
    int rectOffset = 20;
    double distanceThreshold = 100;


    public ChessManager(
            JPanel chessPanel,
            int chessBoardOffset,
            double chessBoardScale,
            int chessBoardSize
    ) {
        this.chessPanel = chessPanel;
        this.chessBoardOffset = chessBoardOffset;
        this.chessBoardScale = chessBoardScale;
        this.chessBoardSize = chessBoardSize;
    }

    public Piece[][] getChessBoard() { return this.chessBoard; }

    public int getTileSize() { return (int)((chessBoardSize - (chessBoardOffset * 2)) * chessBoardScale) / 8; }

    public int getSnappedPos(int i) { return (int)(chessBoardOffset * chessBoardScale) + (getTileSize() * i); }

    public int getSnappedXPos(int col ){ return getSnappedPos(col) + getTileSize() / 4; }

    public int getSnappedYPos(int row ){ return getSnappedPos(row) - getTileSize() / 7; }

    public Rectangle getTileBounds(int col, int row) {
        return new Rectangle(
                getSnappedPos(col),
                getSnappedPos(row),
                getTileSize(),
                getTileSize()
        );
    }

    public Rectangle setUpRect(int x, int y, BufferedImage sprite) {
        return new Rectangle(x, y + this.rectOffset, sprite.getWidth(), sprite.getHeight() - this.rectOffset);
    }

    public void setUpLine(
            SpriteSheetHandler spriteSheet, int color, int row, int[] line, boolean empty
    ) {
        int indexStartingLine;

        for(int col = 0;col < 8;col++) {
            indexStartingLine = col;

            if(col > 4) indexStartingLine = 7 - col;

            BufferedImage sprite = spriteSheet.getSprite(0, line[indexStartingLine]);

            int positionX = getSnappedXPos(col);
            int positionY = getSnappedYPos(row);

            Rectangle rect = setUpRect(
                    positionX,
                    positionY,
                    sprite
            );
            chessBoard[row][col] = new Piece(
                    color,
                    line[indexStartingLine],
                    sprite,
                    col,
                    row,
                    rect,
                    positionX,
                    positionY
            );
        }
    }

    public void setUpPieces(){
        SpriteSheetHandler whitePieces = new SpriteSheetHandler(
                "white_pieces.png", 16, 32, 3
        );
        SpriteSheetHandler blackPieces = new SpriteSheetHandler(
                "black_pieces.png", 16, 32, 3
        );

        setUpLine(blackPieces, PiecesColors.BLACK, 0, backLine, false);
        setUpLine(blackPieces, PiecesColors.BLACK, 1, pawnLine, false);

        for(int row = 2;row < 6;row++)
            setUpLine(whitePieces, PiecesColors.EMPTY, row, pawnLine, true);

        setUpLine(whitePieces, PiecesColors.WHITE, 6, pawnLine, false);
        setUpLine(whitePieces, PiecesColors.WHITE, 7, backLine, false);
    }

    public Piece getPiece(int row, int col) { return chessBoard[row][col]; }

    public void setPiece(int row, int col, Piece piece) {
        chessBoard[row][col] = piece;
    }

    public void updatePiece(int new_row, int new_col, Piece piece) {
        piece.setBounds(setUpRect(getSnappedXPos(new_col), getSnappedYPos(new_row), piece.getSprite()));
        setPiece(new_row, new_col, piece);
        piece.setSnappedPosition(new_row, new_col);
        chessPanel.repaint();
    }

    public Piece checkBounds(Point point) {
        for(int row = 0;row < 8 ;row++) {
            for(int col = 0;col < 8;col++) {
                Piece piece = getPiece(row, col);

                if(piece.getBounds().contains(point)) return piece;
            }
        }

        return null;
    }

    public Piece getNearestRect(Piece piece) {
        Piece nearestPiece = null;
        double nearestDistance = Double.MAX_VALUE;

        for(int row = 0;row < 8 ;row++) {
            for(int col = 0;col < 8;col++) {
                Piece currentPiece = getPiece(row, col);

                double distance = piece.getMiddlePoint().distance(currentPiece.getRectMiddlePoint());

                if(distance < nearestDistance) {
                    nearestPiece = currentPiece;
                    nearestDistance = distance;
                }
            }
        }

        if(nearestDistance > this.distanceThreshold) {
            Log.INFO(String.format(
                    "%s Nearest piece exceeds threshold of %s < %.2f, disregarding move",
                    this.getClass().getSimpleName(),
                    this.distanceThreshold,
                    nearestDistance
            ));

            return piece;
        }

        return nearestPiece;
    }
}