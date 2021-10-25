package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mz.periferals.Weapon;
import mz.scenes.GameScene;

import java.util.Arrays;

public class Player extends GeneralSprite{
    private Image laser;
    private final int maxX = GameScene.GAME_WIDTH - (int)this.getWidth();
    private final int maxY = GameScene.GAME_HEIGHT - (int)this.getHeight();


    public Player(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        laser = new Image("weapon/laserGreen1.png");
        setX(GameScene.GAME_WIDTH/2 - (int)this.getWidth()/2);
        setY(GameScene.GAME_HEIGHT - (int)this.getHeight()*2);
    }

    public void move(int dx, int dy){
        setX(getX()+dx);
        setY(getY()+dy);

        if (getX()< 0) setX(0);
        else if (getX() >= maxX) setX(maxX);

        if (getY()<0) setY(0);
        else if(getY() >= maxY) setY(maxY);
    }

    public void shoot(){

    }
}
