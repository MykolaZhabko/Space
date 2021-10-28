package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mz.periferals.GameConstants;

public class Explosion implements GameConstants {
    private int[] animation = new int[]{0,512,1024,1536,2048,2560,3072,3584};
    private int sx;
    private int sy;
    private int x;
    private int y;
    private boolean isAlive;
    private final int ELEMENT_WIDTH = 512;
    private final int ELEMENT_HEIGHT = 512;
    private Image localExplocion;
    public Explosion(int x, int y, int type){
        setSx(0);
        setSy(0);
        setX(x);
        setY(y);
        setAlive(true);
        switch (type){
            case 0:
                localExplocion = explosion3;
                break;
            case 1:
                localExplocion = explosion4;
                break;
        }
    }



    public void draw(GraphicsContext gc) {
        if (isAlive) {
            gc.drawImage(localExplocion, animation[getSx()], animation[getSy()], ELEMENT_WIDTH, ELEMENT_HEIGHT, getX() - 80, getY() - 100, 200, 200);
            setSx(getSx() + 1);
            if (getSx() == 7 && getSy() == 7) {
                setSx(0);
                setSy(0);
                setAlive(false);
            }
            if (getSx() == 7) {
                setSx(0);
                setSy(getSy() + 1);
            }
        }

    }

    public int getSx() {
        return sx;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }

    public int getSy() {
        return sy;
    }

    public void setSy(int sy) {
        this.sy = sy;
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
}
