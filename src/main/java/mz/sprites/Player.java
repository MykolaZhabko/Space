package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mz.periferals.GameConstants;

import java.io.Serializable;

import static mz.scenes.GameScene.playerWeapons;
import static mz.scenes.GameScene.soundManager;

public class Player extends GeneralSprite implements GameConstants, Serializable {
    private static final long serialVersionUID = 1L;
    private final int maxX;
    private final int maxY;
    private int score;
    private int lives;
    transient private final Explosion explosion;
    private final int initialHP;
    private int ammoType;
    private double timeCount;

    public Player() {
        setHp(100);
        setSprite(player);
        initialHP = (int) this.getHp();
        setX(GAME_WIDTH/2 - (int)getSprite().getWidth()/2);
        setY(GAME_HEIGHT - (int)getSprite().getHeight()*2);
        maxX = GAME_WIDTH - (int)getSprite().getWidth();
        maxY = GAME_HEIGHT - (int)getSprite().getHeight();
        explosion = new Explosion(getX(),getY(),1);
        setScore(0);
        setLives(0);
        setAmmoType(0);
        setTimeCount(0);
    }

    @Override
    public void draw(GraphicsContext gc) {
        setTimeCount(getTimeCount()+0.016);
        if (isAlive()) {
            gc.setFill(Color.GREEN);
            gc.drawImage(getSprite(), getX(), getY());
            explosion.setX(getX());
            explosion.setY(getY());
        }else {
            explosion.draw(gc);
            if (!explosion.isAlive() && getLives() > 0) {
                explosion.setAlive(true);
                setAlive(true);
                setAmmoType(0);
                setLives(getLives()-1);
                setHp(100);
            }
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getAmmoType() {
        return ammoType;
    }

    public void setAmmoType(int ammoType) {
        this.ammoType = ammoType;
    }

    public double getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(double timeCount) {
        this.timeCount = timeCount;
    }

    public Explosion getExplosion() {
        return explosion;
    }

    public void shoot(){
        switch (getAmmoType()){
            case 0:
                if(getTimeCount() > 0.2 && isAlive()) {
                    soundManager.playSound("laser1");
                    Weapon weapon = new Weapon(0, 7);
                    weapon.setX(getX() + (int) getSprite().getWidth() / 2);
                    weapon.setY(getY());
                    playerWeapons.add(weapon);
                    setTimeCount(0);
                }
                break;
            case 1:
                if(getTimeCount() > 0.2 && isAlive()) {
                    soundManager.playSound("laser1");
                    Weapon weapon1 = new Weapon(0, 5);
                    Weapon weapon2 = new Weapon(0, 5);
                    Weapon weapon3 = new Weapon(0, 5);
                    weapon1.setX(getX() + (int) getSprite().getWidth() / 2 - (int) weapon1.getSprite().getWidth() / 2);
                    weapon1.setY(getY());
                    weapon2.setX(getX() + (int) getSprite().getWidth() - (int) weapon1.getSprite().getWidth() / 2);
                    weapon2.setY(getY() + 20);
                    weapon3.setX(getX() - (int) weapon1.getSprite().getWidth() / 2);
                    weapon3.setY(getY() + 20);
                    playerWeapons.add(weapon1);
                    playerWeapons.add(weapon2);
                    playerWeapons.add(weapon3);
                    setTimeCount(0);
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "maxX=" + maxX +
                ", maxY=" + maxY +
                ", score=" + score +
                ", lives=" + lives +
                ", explosion=" + explosion +
                ", initialHP=" + initialHP +
                ", ammoType=" + ammoType +
                ", timeCount=" + timeCount +
                '}';
    }
}
