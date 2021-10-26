package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import mz.scenes.GameScene;

import java.util.Random;

public class Enemy extends GeneralSprite{
    private int type;
    private double timer;
    private int direction;
    private boolean isOnBattleField;

    private final int[] ALL_DIRRECTION = new int[]{0,1,2,3,4,5};
    private final int[] LEFT_RIGHT_DOWN = new int[]{0,1,4,5};
    private final int[] LEFT_RIGHT_UP = new int[]{1,2,3,4};
    private final int[] LEFT_UP_DOWN = new int[]{0,1,2};
    private final int[] RIGHT_UP_DOWN = new int[]{3,4,5};



    public Enemy(String url,int type) {
        super(url, 42, 39, true, true);
        setType(type);
        setTimer(0);
        setOnBattleField(false);
    }

    public Enemy(String url){
        super(url);
    }


    public void move(){
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
                default:
                    break;
            }
        }
        else {
            setY(getY() + 1);
            if (getY() > this.getHeight()) setOnBattleField(true);
        }

    }

    private void navigation(int x, int y) {
        if (x < 0  && y >= 0)
        {
            setDirection(getRandom(RIGHT_UP_DOWN));
            return;
        }else if (x + this.getWidth() >= GameScene.GAME_WIDTH && y >= 0)
        {
            setDirection(getRandom(LEFT_UP_DOWN));
            return;
        } else if (y >= GameScene.GAME_HEIGHT/2)
        {
            setDirection(getRandom(LEFT_RIGHT_UP));
            return;
        }else if (y < 0)
        {
            setDirection(getRandom(LEFT_RIGHT_DOWN));
            return;
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
}
