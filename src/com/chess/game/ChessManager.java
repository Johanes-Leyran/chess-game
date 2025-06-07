package src.com.chess.game;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;
import src.com.chess.utils.SpriteSheetHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ChessManager {
    static Piece[][] chessBoard = new Piece[8][8];
    static int[] backLine = {
            PiecesType.ROOK, PiecesType.KNIGHT, PiecesType.BISHOP, PiecesType.QUEEN, PiecesType.KING
    };
    static int[] pawnLine = {
            PiecesType.PAWN, PiecesType.PAWN, PiecesType.PAWN, PiecesType.PAWN,  PiecesType.PAWN
    };

    JPanel chessPanel;
    int chessBoardOffset;
    double chessBoardScale;
    int chessBoardSize;
    int rectOffset = 20;


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

    public int getTileSize() { return (int)((chessBoardSize - (chessBoardOffset * 2)) * chessBoardScale) / 8; }

    public int getSnappedPos(int i) { return (int)(chessBoardOffset * chessBoardScale) + (getTileSize() * i); }

    public int getSnappedXPos(int col ){ return getSnappedPos(col) + getTileSize() / 4; }

    public int getSnappedYPos(int row ){ return getSnappedPos(row) - getTileSize() / 7; }

    public int getColumnPos(int x) {
        int base = x - getTileSize() / 3;
        int snappedStart = (int)(chessBoardOffset * chessBoardScale);
        return (base - snappedStart) / getTileSize();
    }

    public int getRowPos(int y) {
        int base = y + getTileSize() / 7;
        int snappedStart = (int)(chessBoardOffset * chessBoardScale);
        return (base - snappedStart) / getTileSize();
    }

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
        for(int col = 0;col < 8;col++) {
            int indexStartingLine;

            if(col > 4) indexStartingLine = 7 - col;
            else indexStartingLine = col;

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
                    empty ? PiecesType.EMPTY : line[indexStartingLine],
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

    public void drawPieces(Graphics g) {
        int tileSize = getTileSize();
        int leftMargin = tileSize / 4;

        for(int row = 0;row < 8;row++) {
            for(int col = 0;col < 8;col++) {
                Piece piece = chessBoard[row][col];

                if(piece == null || piece.getColor().equals("EMPTY") || piece.isDragged)
                    continue;

                g.drawImage(
                        piece.sprite,
                        getSnappedXPos(col),
                        getSnappedYPos(row),
                        null
                );
            }
        }
    }

    public void drawSinglePiece(Graphics g, Piece piece) {
        if(piece == null)
            return;

        g.drawImage(piece.sprite, piece.x, piece.y, null);
    }

    public void drawRect(Graphics g) {
        for(int row = 0;row < 8;row++) {
            for(int col = 0;col < 8;col++) {
                Rectangle rect = chessBoard[row][col].getBounds();
                g.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    public Piece getPiece(int row, int col) { return chessBoard[row][col]; }

    public void setPiece(int row, int col, Piece piece) {
        chessBoard[row][col] = piece;
    }

    public Piece checkBounds(Point point) {
        for(int row = 0;row < 8 ;row++) {
            for(int col = 0;col < 8;col++) {
                Piece piece = getPiece(row, col);

                if(piece.getBounds().contains(point)) {
                    return piece;
                }
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

                double distance = piece.getMiddlePoint().distance(
                        currentPiece.getRectMiddlePoint()
                );

                if(distance < nearestDistance) {
                    nearestPiece = currentPiece;
                    nearestDistance = distance;
                }
            }
        }
        return nearestPiece;
    }
}