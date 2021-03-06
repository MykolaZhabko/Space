package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class GeneralSprite implements Serializable {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private double hp;
    private boolean isAlive = true;
    transient private Image sprite;


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
    public double getHp() {
        return hp;
    }
    public void setHp(double hp) {
        this.hp = hp;
    }
    public Image getSprite() {
        return sprite;
    }
    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }
    public abstract void draw(GraphicsContext gc);
}
