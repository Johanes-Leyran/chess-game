package src.com.chess.game;

import java.awt.*;
import java.awt.image.BufferedImage;


public class BasePiece {
     int color;
     int type;
     int col, row;
     int x, y;

     Rectangle rect;
     boolean isDragged = false;
     BufferedImage sprite;


     public BasePiece(int color, int type, BufferedImage sprite, int col, int row, Rectangle rect, int x, int y) {
          this.color = color;
          this.type = type;
          this.sprite = sprite;
          this.col = col;
          this.row = row;
          this.rect = rect;
          this.x = x;
          this.y = y;
     }

     // position of the piece on the chess board 2d array
     public void setSnappedPosition(int col, int row) {
          this.col = col;
          this.row = row;
     }

     public String getColor() {
          if(this.color == 0) {
               return "WHITE";
          }
          return "BLACK";
     }

     public void setPosition(int x, int y) {
          this.x = x;
          this.y = y;
     }

     public int getXPosition() {
          return this.x;
     }

     public int getYPosition() {
          return this.y;
     }

     public int getRow() {
          return this.row;
     }

     public int getCol() {
          return this.col;
     }

     public void setIsDragged(boolean b) {
          this.isDragged = b;
     }

     public Rectangle getBounds() {
          return this.rect;
     }
}