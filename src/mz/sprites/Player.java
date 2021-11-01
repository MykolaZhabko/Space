package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mz.periferals.GameConstants;

public class Player extends GeneralSprite implements GameConstants {
    private int maxX;
    private int maxY;
    private Explosion explosion;
    private int initialHP;

    public Player() {
        setHp(100);
        setSprite(player);
        initialHP = (int) this.getHp();
        setX(GAME_WIDTH/2 - (int)getSprite().getWidth()/2);
        setY(GAME_HEIGHT - (int)getSprite().getHeight()*2);
        maxX = GAME_WIDTH - (int)getSprite().getWidth();
        maxY = GAME_HEIGHT - (int)getSprite().getHeight();

        explosion = new Explosion(getX(),getY(),1);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isAlive()) {
            gc.setFill(Color.GREEN);
            gc.strokeLine(getX(), getY()+getSprite().getHeight() +  2, getX() + getSprite().getWidth() * (getHp() / initialHP), getY()+getSprite().getHeight() + 2);
            gc.drawImage(getSprite(), getX(), getY());
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
