package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mz.periferals.GameConstants;

import java.io.Serializable;

public class FeatureDrop extends GeneralSprite implements GameConstants, Serializable {
    private int type;
    Font font = Font.font("Arial", FontWeight.NORMAL,12);


    public FeatureDrop(int x, int y, int type){
        setX(x);
        setY(y);
        setType(type);
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFont(font);
        if (isAlive()) {
            switch (type) {
                case 0:
                    gc.setFill(Color.YELLOW);
                    gc.fillText("HP", getX(),getY()-2);
                    gc.fillRect(getX(), getY(), 20, 20);
                    break;
                case 1:
                    gc.setFill(Color.RED);
                    gc.fillText("new_weapon", getX(),getY()-2);
                    gc.fillRect(getX(), getY(), 20, 20);
                    break;
                case 2:
                    gc.setFill(Color.GREEN);
                    gc.fillText("shield", getX(),getY()-2);
                    gc.fillRect(getX(), getY(), 20, 20);
                    break;
                case 3:
                    gc.setFill(Color.LIGHTBLUE);
                    gc.fillText("extra_weapon", getX(),getY()-2);
                    gc.fillRect(getX(), getY(), 20, 20);
                    break;
            }
            move();
        }
    }

    private void move(){
        setY(getY() + 2);
        if (getY() > GAME_HEIGHT) setAlive(false);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    private boolean isCollide(FeatureDrop a, GeneralSprite b){
        return  a.getX() >= b.getX() &&
                a.getX() <= b.getX() + b.getSprite().getWidth() &&
                a.getY() >= b.getY() &&
                a.getY() <= b.getY() + b.getSprite().getHeight();
    }

    public void collideWithPlayer(Player player){
        if (isCollide(this, player)) {
            setAlive(false);
            switch (getType()) {
                case 0:
                player.setHp(Math.min(player.getHp() + 50, 100));
                break;
                case 1:
                    player.setAmmoType(1);
                    break;
            }
        }
    }
}
