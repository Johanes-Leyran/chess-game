package src.com.chess.components;

import src.com.chess.adapter.StateAdapter;
import src.com.chess.game.ChessManager;
import src.com.chess.game.ComponentData;
import src.com.chess.game.CursorHandler;
import src.com.chess.move.MoveHandler;
import src.com.chess.utils.FontHandler;
import src.com.chess.game.GameState;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel {
    JPanel mainPanel;
    ComponentData componentData;
    JFrame frame;
    StateAdapter stateAdapter;


    public GamePanel(
            JPanel mainPanel,
            CardLayout cardLayout,
            JFrame frame
    ){
        this.mainPanel = mainPanel;
        this.frame = frame;
        this.setLayout(new BorderLayout());
        this.componentData = new ComponentData(
                142,
                7,
                16,
                32,
                5.5,
                2.7,
                this.frame
        );
        this.stateAdapter = new StateAdapter();

        ChessManager chessManager= new ChessManager(
                this,
                this.componentData.chessBoardOffset,
                this.componentData.chessBoardScale,
                this.componentData.chessBoardSize
        );

        MoveHandler moveHandler = new MoveHandler(chessManager);
        GameState gameState = new GameState(mainPanel, chessManager, cardLayout, moveHandler);
        moveHandler.setGameState(gameState); // Circular import yay

        this.add(new ChessPanel(
                this.componentData,
                gameState,
                this.stateAdapter,
                chessManager,
                moveHandler
                ), BorderLayout.WEST
        );
        this.add(new InfoPanel(
                mainPanel,
                cardLayout,
                frame,
                gameState,
                chessManager
                ), BorderLayout.CENTER
        );
    }
}
