package mz.sprites;

import javafx.scene.canvas.GraphicsContext;

public class Enemy extends GeneralSprite{


    public Enemy(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
    }

    public Enemy(String url){
        super(url);
    }
    public void moveDown(){
        setY(getY() + 1);
    }

}
