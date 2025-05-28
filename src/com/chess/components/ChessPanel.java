package src.com.chess.components;

import src.com.chess.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ChessPanel extends JPanel {
    Cursor normalCursor;
    Cursor toGrabCursor;
    Cursor grabCursor;
    BufferedImage chessBoardImg;
    CursorHandler cursorHandler;
    ComponentData componentData;
    int FPS;
    Point initialPoint;
    boolean hovering;
    boolean dragging;
    BasePiece selected;
    Timer udpateLoopTimer;
    ChessManager chessManager;


    public ChessPanel(ComponentData componentData, CursorHandler cursorHandler){
        this.componentData = componentData;

        // board sprite has an empty space around it
        this.FPS = 60; // set the fps
        this.initialPoint = new Point(0, 0);

        this.chessManager = new ChessManager(
                this,
                this.componentData.chessBoardOffset,
                this.componentData.chessBoardScale,
                this.componentData.chessBoardSize
        );
        this.chessManager.setUpPieces();

        this.chessBoardImg = new SpriteSheetHandler(
                "board.png",
                this.componentData.chessBoardSize,
                this.componentData.chessBoardSize,
                this.componentData.chessBoardScale
        ).getSprite(0, 0);

        this.setPreferredSize( new Dimension(
                chessBoardImg.getWidth(),
                chessBoardImg.getHeight()
        ));

        udpateLoopTimer = new Timer(1000 / this.FPS, actionEvent -> repaint());
        udpateLoopTimer.start();

         this.addMouseListener(new MouseAdapter() {
             @Override
             public void mousePressed(MouseEvent e) {
                 super.mouseClicked(e);

                 // check if the mouse clicked on a piece
                 Point point = e.getPoint();
                 BasePiece piece = chessManager.checkBounds(point);
                                                         
                 if(piece != null) {
                     initialPoint.setLocation(
                             (int) point.getX() - piece.getXPosition(),
                             (int) point.getY() - piece.getYPosition()
                     );

                     cursorHandler.setCursor("grab");
                     piece.setIsDragged(true);
                     dragging = true;
                     selected = piece;
                 }
             }

             @Override
             public void mouseReleased(MouseEvent e) {
                 cursorHandler.setCursor("normal");

                 if(dragging) {
                     selected.setIsDragged(false);
                     dragging = false;

                     int lastSnapXPosition = chessManager.getSnappedXPos(selected.getCol());
                     int lastSnapYPosition = chessManager.getSnappedYPos(selected.getRow());

                     selected.setPosition(
                             lastSnapXPosition, lastSnapYPosition
                     );

                     selected = null;
                 }
             }
         });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Check if the mouse point is inside the bounds of any piece
                BasePiece piece = chessManager.checkBounds(e.getPoint());

                if (piece != null && !hovering) {
                    cursorHandler.setCursor("toGrab");
                    hovering = true;
                }
                if(hovering && piece == null) {
                    cursorHandler.setCursor("normal");
                    hovering = false;
                }
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging && selected != null) {
                    selected.setPosition(
                            (int)(e.getX() - initialPoint.getX()),
                            (int)(e.getY() - initialPoint.getY())
                    );
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                this.chessBoardImg, 0, 0, null
        );
        chessManager.drawPieces(g);
    }
}
