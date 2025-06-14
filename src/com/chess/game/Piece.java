package src.com.chess.game;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Piece {
     int color;
     int type;
     int col, row;
     int x, y;

     Rectangle rect;
     boolean isDragged = false;
     boolean isMoved = false;
     BufferedImage sprite;

     // param x and y could be omitted in constructor and add a method
     // that returns the x, y position using getSnappedPosition instead
     public Piece(
             int color,
             int type,
             BufferedImage sprite,
             int col,
             int row,
             Rectangle rect,
             int x,
             int y
     ) {
          this.color = color;
          this.type = type;
          this.sprite = sprite;
          this.row = row;
          this.col = col;
          this.rect = rect;
          this.x = x;
          this.y = y;
     }

     public Piece(Piece other) {
          // only necessary for simulating the chess board
          this.color = other.color;
          this.type = other.type;
          this.isMoved = other.isMoved;
     }

     public BufferedImage getSprite() { return sprite; }

     public boolean isDragged() { return this.isDragged; }

     public void setSnappedPosition(int row, int col) {
          this.row = row;
          this.col = col;
     }

     public void setSprite(BufferedImage sprite) { this.sprite = sprite; }

     public void setType(int type) { this.type = type; }

     public Point getRectMiddlePoint() {
          Point point = this.rect.getLocation();
          point.setLocation(
                  point.getX() + (double) this.sprite.getWidth() / 2,
                  point.getY() + (double) this.sprite.getHeight() / 2
          );
          return point;
     }

     public Point getMiddlePoint() {
          Point point = new Point();
          point.setLocation(
                  this.getXPosition() + (double) this.sprite.getWidth() / 2,
                  this.getYPosition() + (double) this.sprite.getHeight() / 2
          );
          return point;
     }

     public Point getLocation() {
          Point point = new Point();
          point.setLocation(
                  this.getXPosition(),
                  this.getYPosition()
          );
          return point;
     }

     public int getColor() { return this.color; }

     public String getStringColor() {
          if(this.getColor() == 0) return "WHITE";
          if(this.getColor() == 1) return "BLACK";
          else return "EMPTY";
     }

     public void setColor(int color) { this.color = color; }

     public int getType() { return this.type; }

     public void setPosition(Point point) {
          this.x = point.x;
          this.y = point.y;
     }

     public void setPosition(int x, int y) {
          this.x = x;
          this.y = y;
     }

     public boolean isMoved() { return this.isMoved; }

     public void setIsMove(boolean b) { this.isMoved = b; }

     public int getXPosition() { return this.x; }

     public int getYPosition() { return this.y; }

     public int getRow() { return this.row; }

     public int getCol() { return this.col; }

     public void setIsDragged(boolean b) { this.isDragged = b; }

     public Rectangle getBounds() { return this.rect; }

     public void setBounds(Rectangle rect) { this.rect = rect; }
}