package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mz.periferals.GameConstants;

public class Player extends GeneralSprite implements GameConstants {
    private final int maxX = GAME_WIDTH - (int)this.getWidth();
    private final int maxY = GAME_HEIGHT - (int)this.getHeight();
    private Explosion explosion;


    public Player(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        setHp(100);
        setX(GAME_WIDTH/2 - (int)this.getWidth()/2);
        setY(GAME_HEIGHT - (int)this.getHeight()*2);
        explosion = new Explosion(getX(),getY(),1);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isAlive()) {
            gc.drawImage(this, getX(), getY());
            explosion.setX(getX());
            explosion.setY(getY());
        }else {
            explosion.draw(gc);
        }
    }

    public void move(int dx, int dy){
        if (isAlive()) {
            setX(getX() + dx);
            setY(getY() + dy);

            if (getX() < 0) setX(0);
            else if (getX() >= maxX) setX(maxX);

            if (getY() < 0) setY(0);
            else if (getY() >= maxY) setY(maxY);
        }
    }

    public void shoot(){

    }
}
