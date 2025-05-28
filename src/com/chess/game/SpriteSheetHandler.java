package src.com.chess.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class SpriteSheetHandler {
    BufferedImage spriteSheet;
    int spriteWidth;
    int spriteHeight;
    double scale;


    public SpriteSheetHandler(String name, int spriteWidth, int spriteHeight, double scale){
        this.spriteSheet = ResourceHandler.loadImage(name);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

        if(scale <= 1) {
            this.scale = 1;
        } else {
            this.scale = scale;
            Image scaledSheet = this.spriteSheet.getScaledInstance(
                    (int) (this.spriteSheet.getWidth() * this.scale),
                    (int) (this.spriteSheet.getHeight() * this.scale),
                    BufferedImage.SCALE_SMOOTH
            );
            this.spriteSheet = new BufferedImage(
                    scaledSheet.getWidth(null),
                    scaledSheet.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = this.spriteSheet.createGraphics();
            g2d.drawImage(scaledSheet, 0, 0, null);
            g2d.dispose();
        }
    }

    public BufferedImage getSprite(int row, int column) throws IndexOutOfBoundsException {
        double scaledSpriteWidth = this.spriteWidth * this.scale;
        double scaledSpriteHeight = this.spriteHeight * this.scale;

        double maxRow = this.spriteSheet.getHeight() / scaledSpriteHeight;
        double maxColumn = this.spriteSheet.getWidth() / scaledSpriteWidth;

        if (row < 0 || row >= maxRow || column < 0 || column >= maxColumn) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Sprite Sheet has only max: row %s, col %s. Trying to index: %s, %s",
                            maxRow, maxColumn, row, column
                    )
            );
        }

        return this.spriteSheet.getSubimage(
                (int) (column * scaledSpriteWidth),
                (int) (row * scaledSpriteHeight),
                (int) scaledSpriteWidth,
                (int) scaledSpriteHeight
        );
    }
 }
