package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mz.periferals.GameConstants;
import mz.scenes.GameScene;

import java.io.Serializable;
import java.util.Random;

public class Enemy extends GeneralSprite implements GameConstants, Serializable {
    private int type;
    private int initialHP;
    private double timer;
    private double shootTimer;
    private int direction;
    private boolean isOnBattleField;
    private int points;
    private Explosion explosion;

    private final int[] ALL_DIRRECTION = new int[]{0, 1, 2, 3, 4, 5};
    private final int[] LEFT_RIGHT_DOWN = new int[]{0, 1, 4, 5};
    private final int[] LEFT_RIGHT_UP = new int[]{1, 2, 3, 4};
    private final int[] LEFT_UP_DOWN = new int[]{0, 1, 2};
    private final int[] RIGHT_UP_DOWN = new int[]{3, 4, 5};


    public Enemy(int type) {
        switch (type) {
            case 1:
                setHp(20);
                setSprite(enemy);
                setPoints(10);
                explosion = new Explosion(getX(), getY(), 0);
                break;
            case 2:
                setHp(60);
                setSprite(enemy2);
                setPoints(25);
                explosion = new Explosion(getX(), getY(), 0);
                break;
        }
        initialHP = (int) this.getHp();
        setType(type);
        setTimer(0);
        setShootTimer(0);
        setOnBattleField(false);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isAlive()) {
            gc.setStroke(Color.GREEN);
            gc.setLineWidth(3);
            gc.strokeLine(getX(), getY() - 2, getX() + getSprite().getWidth() * (getHp() / initialHP), getY() - 2);
            gc.drawImage(getSprite(), getX(), getY());
            explosion.setX(getX());
            explosion.setY(getY());
        } else {
            this.explosion.draw(gc);
        }


    }


    public void move() {
        if (isAlive()) {
            setTimer(getTimer() + 0.02);
            if (isOnBattleField()) {
                switch (getType()) {
                    case 1:
                        if (getTimer() > 2) {
                            setDirection(getRandom(ALL_DIRRECTION));
                            setTimer(0);
                        }
                        switch (getDirection()) {
                            case 0:
                                setX(getX() - 1);
                                setY(getY() + 1);
                                break;
                            case 1:
                                setX(getX() - 2);
                                break;
                            case 2:
                                setX(getX() - 1);
                                setY(getY() - 1);
                                break;
                            case 3:
                                setX(getX() + 1);
                                setY(getY() - 1);

                            case 4:
                                setX(getX() + 2);
                                break;
                            case 5:
                                setX(getX() + 1);
                                setY(getY() + 1);
                                break;
                            default:
                                break;
                        }
                        navigation(getX(), getY());
                        break;
                    case 2:
                        if (getTimer() > 2) {
                            setDirection(getRandom(ALL_DIRRECTION));
                            setTimer(0);
                        }
                        switch (getDirection()) {
                            case 0:
                                setX(getX() - 1);
                                setY(getY() + 1);
                                break;
                            case 1:
                                setX(getX() - 2);
                                break;
                            case 2:
                                setX(getX() - 1);
                                setY(getY() - 1);
                                break;
                            case 3:
                                setX(getX() + 1);
                                setY(getY() - 1);

                            case 4:
                                setX(getX() + 2);
                                break;
                            case 5:
                                setX(getX() + 1);
                                setY(getY() + 1);
                                break;
                            default:
                                break;
                        }
                        navigation(getX(), getY());
                        break;
                    default:
                        break;
                }
            } else {
                setY(getY() + 1);
                if (getY() > getSprite().getHeight()) setOnBattleField(true);
            }
        }
    }

    private void navigation(int x, int y) {
        if (x < 0 && y >= 0) {
            setDirection(getRandom(RIGHT_UP_DOWN));
            return;
        } else if (x + getSprite().getWidth() >= GameScene.GAME_WIDTH && y >= 0) {
            setDirection(getRandom(LEFT_UP_DOWN));
            return;
        } else if (y >= GameScene.GAME_HEIGHT / 2) {
            setDirection(getRandom(LEFT_RIGHT_UP));
            return;
        } else if (y < 0) {
            setDirection(getRandom(LEFT_RIGHT_DOWN));
            return;
        }

    }

    public void shoot() {
        Weapon weapon;
        switch (getType()) {
            case 1:
                weapon = new Weapon(1, 10);
                weapon.setX(this.getX() + (int) getSprite().getWidth() / 2);
                weapon.setY(this.getY() + (int) getSprite().getHeight() / 2);
                GameScene.enemyWeapons.add(weapon);
                GameScene.soundManager.playSound("laser2");
                break;
            case 2:
                weapon = new Weapon(1, 30);
                weapon.setX(this.getX() + (int) getSprite().getWidth() / 2);
                weapon.setY(this.getY() + (int) getSprite().getHeight() / 2);
                GameScene.enemyWeapons.add(weapon);
                GameScene.soundManager.playSound("laser2");
                break;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTimer() {
        return timer;
    }

    public void setTimer(double timer) {
        this.timer = timer;
    }

    public double getShootTimer() {
        return shootTimer;
    }

    public void setShootTimer(double shootTimer) {
        this.shootTimer = shootTimer;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isOnBattleField() {
        return isOnBattleField;
    }

    public void setOnBattleField(boolean onBattleField) {
        isOnBattleField = onBattleField;
    }

    public int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public Explosion getExplosion() {
        return explosion;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
