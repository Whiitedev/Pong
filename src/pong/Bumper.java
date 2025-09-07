package pong;

import java.awt.Graphics;

public class Bumper {

    private int x;
    private int y;
    private int width;
    private int height;

    public Bumper(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }
    
    public void setX(int newX) {
        this.x = newX;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int newY) {
        this.y = newY;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    public void move(int deltaY) {
        this.y += deltaY;
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }
}
