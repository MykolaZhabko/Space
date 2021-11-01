package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mz.periferals.GameConstants;
import mz.scenes.GameScene;

public class Weapon extends GeneralSprite implements GameConstants {
    private int damage;

    public Weapon(int type, int damage){
        switch (type){
            case 0:
                setSprite(playerWeapon);
                break;
            case 1:
                setSprite(enemyWeapon);
                break;
        }

        this.damage = damage;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(getSprite(),getX(),getY());
    }

    // 1 = down , -1 = up
    public void moveUp(){
        setY(getY() - 17);
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
