package mz.sprites;

import javafx.scene.canvas.GraphicsContext;

public class Bullet extends GeneralSprite{

    public Bullet(String url){
        super(url);
    }
    public Bullet(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
    }

    // 1 = down , -1 = up
    public void moveUp(){
        setY(getY() + 10);
    }

    public void moveDown(){
        setY(getY() - 10);
    }

}
