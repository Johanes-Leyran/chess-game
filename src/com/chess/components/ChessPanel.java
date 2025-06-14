package src.com.chess.components;

import src.com.chess.adapter.ChessMotionAdapter;
import src.com.chess.adapter.ChessMouseAdapter;
import src.com.chess.adapter.StateAdapter;
import src.com.chess.constants.PiecesColors;
import src.com.chess.game.*;
import src.com.chess.move.MoveHandler;
import src.com.chess.utils.FontHandler;
import src.com.chess.utils.SpriteSheetHandler;

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
    MoveHandler moveHandler;


    public ChessPanel(
            ComponentData componentData,
            CursorHandler cursorHandler,
            GameState gameState,
            StateAdapter stateAdapter,
            ChessManager chessManager,
            MoveHandler moveHandler
    ){
        this.componentData = componentData;
        this.FPS = Globals.getFps();
        this.stateAdapter = stateAdapter;
        this.chessManager = chessManager;
        this.chessManager.setUpPieces();
        this.chessBoardImg = new SpriteSheetHandler(
                "board.png",
                this.componentData.chessBoardSize,
                this.componentData.chessBoardSize,
                this.componentData.chessBoardScale
        ).getSprite(0, 0);
        this.cursorHandler = cursorHandler;
        this.setPreferredSize(new Dimension(
                chessBoardImg.getWidth(),
                chessBoardImg.getHeight()
        ));
        this.udpateLoopTimer = new Timer(1000 / this.FPS, _ -> repaint());
        this.udpateLoopTimer.start();

        this.moveHandler = moveHandler;
        this.addMouseListener(new ChessMouseAdapter(
                this.chessManager, this.cursorHandler, stateAdapter, moveHandler, gameState
        ));
        this.addMouseMotionListener(new ChessMotionAdapter(
                this.chessManager, this.cursorHandler, stateAdapter, gameState
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
                this.chessBoardImg, 0, 0, null
        );

        if(Globals.getShowRect())
            ChessPainter.drawAllRect(g, chessManager);

        ChessPainter.drawValidMoves(g, stateAdapter.getSelected(), chessManager, moveHandler.getMoveHistory());
        ChessPainter.drawRedSquare(g, chessManager, PiecesColors.WHITE);
        ChessPainter.drawRedSquare(g, chessManager, PiecesColors.BLACK);
        ChessPainter.drawPieces(g, chessManager);
        ChessPainter.drawSinglePiece(g, stateAdapter.getSelected());
    }
}
