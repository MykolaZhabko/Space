package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GeneralSprite extends Image {
    private int x;
    private int y;
    private boolean isAlive = true;

    public GeneralSprite(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
    }

    public GeneralSprite(String url) {
        super(url);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public void draw(GraphicsContext gc){
        gc.drawImage(this,getX(),getY());
    }
}
