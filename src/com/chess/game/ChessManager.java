package src.com.chess.game;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ChessManager {
    static BasePiece[][] chessBoard = new BasePiece[8][8];
    static int[] startingLine = {
            PiecesType.ROOK, PiecesType.KNIGHT, PiecesType.BISHOP, PiecesType.QUEEN, PiecesType.KING
    };

    JPanel chessPanel;
    int chessBoardOffset;
    double chessBoardScale;
    int chessBoardSize;
    // gives move
    // update the game
    // handles pieces
    // handles logic of the game
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

    public int getTileSize() {
        return (int)((chessBoardSize - (chessBoardOffset * 2)) * chessBoardScale) / 8;
    }

    public int getSnappedPos(int i) {
        return (int)(chessBoardOffset * chessBoardScale) + (getTileSize() * i);
    }

    public int getSnappedXPos(int col ){
        return getSnappedPos(col) + getTileSize()/3;
    }

    public int getSnappedYPos(int row ){
        return getSnappedPos(row) - getTileSize()/7;
    }

    public Rectangle setUpRect(int x, int y, BufferedImage sprite) {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void setUpLine(SpriteSheetHandler spriteSheet, int color, int row
    ) {
        for(int col = 0;col < 8;col++) {
            int indexStartingLine;

            if(col > 4) indexStartingLine = 7 - col;
            else indexStartingLine = col;

            BufferedImage sprite = spriteSheet.getSprite(0, startingLine[indexStartingLine]);
            int positionX = getSnappedXPos(col);
            int positionY = getSnappedYPos(row);

            Rectangle rect = setUpRect(
                    positionX,
                    positionY,
                    sprite
            );

            chessBoard[row][col] = new BasePiece(
                    color,
                    startingLine[indexStartingLine],
                    sprite,
                    col,
                    row,
                    rect,
                    positionX,
                    positionY
            );
        }
    }

    public void setUpPawnLine(SpriteSheetHandler spriteSheet, int color, int row) {
        for(int col = 0;col < chessBoard[row].length;col++) {
            BufferedImage sprite = spriteSheet.getSprite(0, PiecesType.PAWN);
            int positionX = getSnappedXPos(col);
            int positionY = getSnappedYPos(row);

            Rectangle rect = setUpRect(
                    positionX,
                    positionY,
                    sprite
            );
            chessBoard[row][col] = new BasePiece(
                    color,
                    PiecesType.PAWN,
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

        // set up black pieces
        setUpLine(blackPieces, PiecesColors.BLACK, 0);
        setUpPawnLine(blackPieces, PiecesColors.BLACK, 1);

        // set up white pieces
        setUpLine(whitePieces, PiecesColors.WHITE, 7);
        setUpPawnLine(whitePieces, PiecesColors.WHITE, 6);
    }

    // load the sprites to the chess board
    public void drawPieces(Graphics g) {
        int tileSize = getTileSize();
        int leftMargin = tileSize / 4;

        for(int row = 0;row < 8;row++) {
            for(int col = 0;col < 8;col++) {
                BasePiece piece = chessBoard[row][col];

                if(piece == null)
                    continue;

                if(piece.isDragged) {
                    g.drawImage(piece.sprite, piece.x, piece.y, null);
                }

                // don't draw the sprite if it's being dragged
                if(!piece.isDragged) {
                    g.drawImage(
                            piece.sprite,
                            getSnappedPos(col) + leftMargin,
                            getSnappedPos(row) - tileSize/7,
                            null
                    );
                }
            }
        }
    }

    public BasePiece getPiece(int row, int col) {
        return chessBoard[row][col];
    }

    public BasePiece checkBounds(Point point) {
        for(int row = 0;row < 8 ;row++) {
            for(int col = 0;col < 8;col++) {
                BasePiece piece = getPiece(row, col);

                if(piece == null) {
                    continue;
                }

                if(piece.getBounds().contains(point)) {
                    return piece;
                }
            }
        }
        return null;
    }
}