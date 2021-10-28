package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import mz.periferals.GameConstants;
import mz.scenes.GameScene;

public class Weapon extends GeneralSprite implements GameConstants {
    private int damage;

    public Weapon(String url){
        super(url);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(this,getX(),getY());
    }

    public Weapon(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth, int damge) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
        this.damage = damge;
    }

    // 1 = down , -1 = up
    public void moveUp(){
        setY(getY() - 12);
        if (getY() < 0) this.setAlive(false);
    }
    public void moveDown(){
        setY(getY() + 10);
        if (getY() > GameScene.GAME_HEIGHT) this.setAlive(false);
    }

    public int getDamage() {
        return damage;
    }
}
