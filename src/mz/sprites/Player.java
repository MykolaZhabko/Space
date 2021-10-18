package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Arrays;

public class Player extends GeneralSprite{
    private Image laser;

    public Player(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        laser = new Image("weapon/laserGreen1.png");
    }

    public void move(int dx, int dy){
        setX(getX()+dx);
        setY(getY()+dy);
    }

    public void shoot(){

    }
}
