package src.com.chess.game;

import src.com.chess.constants.PiecesColors;
import src.com.chess.constants.PiecesType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ChessManager {
    static Piece[][] chessBoard = new Piece[8][8];
    static int[] startingLine = {
            PiecesType.ROOK, PiecesType.KNIGHT, PiecesType.BISHOP, PiecesType.QUEEN, PiecesType.KING
    };
    static int[] pawnLine = {
            PiecesType.PAWN, PiecesType.PAWN, PiecesType.PAWN, PiecesType.PAWN,  PiecesType.PAWN
    };

    JPanel chessPanel;
    int chessBoardOffset;
    double chessBoardScale;
    int chessBoardSize;


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

    public void setUpLine(SpriteSheetHandler spriteSheet, int color, int row, int[] line, boolean empty
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

        setUpLine(blackPieces, PiecesColors.BLACK, 0, startingLine, false);
        setUpLine(blackPieces, PiecesColors.BLACK, 1, pawnLine, false);

        for(int row = 2;row < 6;row++)
            setUpLine(whitePieces, PiecesColors.EMPTY, row, pawnLine, true);

        setUpLine(whitePieces, PiecesColors.WHITE, 6, pawnLine, false);
        setUpLine(whitePieces, PiecesColors.WHITE, 7, startingLine, false);


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
                        getSnappedPos(col) + leftMargin,
                        getSnappedPos(row) - tileSize / 7,
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

    public Piece getPiece(int row, int col) {
        return chessBoard[row][col];
    }

    public Piece checkBounds(Point point) {
        for(int row = 0;row < 8 ;row++) {
            for(int col = 0;col < 8;col++) {
                Piece piece = getPiece(row, col);

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