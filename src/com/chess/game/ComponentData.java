package src.com.chess.game;

import javax.swing.*;

public class ComponentData {
    public int chessBoardSize;
    public int chessSpriteWidth;
    public int chessSpriteHeight;
    public int chessBoardOffset;

    public double chessBoardScale;
    public double chessSpriteScale;

    public JFrame frame;

    public ComponentData(
            int chessBoardSize,
            int chessBoardOffset,
            int chessSpriteWidth,
            int chessSpriteHeight,
            double chessBoardScale,
            double chessSpriteScale,
            JFrame frame
    ) {
        this.chessBoardSize = chessBoardSize;
        this.chessSpriteWidth = chessSpriteWidth;
        this.chessSpriteHeight = chessSpriteHeight;
        this.chessBoardOffset = chessBoardOffset;
        this.chessBoardScale = chessBoardScale;
        this.chessSpriteScale = chessSpriteScale;
        this.frame = frame;
    }
}
