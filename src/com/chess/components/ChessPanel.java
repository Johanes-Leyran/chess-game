package src.com.chess.components;

import src.com.chess.adapter.ChessMotionAdapter;
import src.com.chess.adapter.ChessMouseAdapter;
import src.com.chess.adapter.StateAdapter;
import src.com.chess.game.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ChessPanel extends JPanel {
    BufferedImage chessBoardImg;
    CursorHandler cursorHandler;
    ComponentData componentData;
    StateAdapter stateAdapter;
    int FPS;
    Timer udpateLoopTimer;
    ChessManager chessManager;


    public ChessPanel(ComponentData componentData, CursorHandler cursorHandler){
        this.componentData = componentData;
        this.FPS = 60;
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
        this.cursorHandler = cursorHandler;
        this.setPreferredSize( new Dimension(
                chessBoardImg.getWidth(),
                chessBoardImg.getHeight()
        ));

        this.udpateLoopTimer = new Timer(1000 / this.FPS, _ -> repaint());
        this.udpateLoopTimer.start();
        this.stateAdapter = new StateAdapter();

        this.addMouseListener(new ChessMouseAdapter(
                this.chessManager, this.cursorHandler, stateAdapter, new MoveHandler(chessManager, this))
        );
        this.addMouseMotionListener(new ChessMotionAdapter(this.chessManager, this.cursorHandler, stateAdapter));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                this.chessBoardImg, 0, 0, null
        );
        chessManager.drawPieces(g);
        chessManager.drawSinglePiece(g, stateAdapter.getSelected());
    }
}
