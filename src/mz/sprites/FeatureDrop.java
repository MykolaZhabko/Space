package mz.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mz.periferals.GameConstants;

public class FeatureDrop extends GeneralSprite implements GameConstants {
    private int type;

    public FeatureDrop(int x, int y, int type){
        setX(x);
        setY(y);
        setType(type);
    }
    @Override
    public void draw(GraphicsContext gc) {
        if (isAlive()) {
            switch (type) {
                case 0:
                    gc.setFill(Color.YELLOW);
                    gc.fillRect(getX(), getY(), 20, 20);
                    move();
                    break;
                case 1:
                    gc.setFill(Color.RED);
                    gc.fillRect(getX(), getY(), 20, 20);
                    move();
                    break;
                case 2:
                    gc.setFill(Color.GREEN);
                    gc.fillRect(getX(), getY(), 20, 20);
                    move();
                    break;
                case 3:
                    gc.setFill(Color.LIGHTBLUE);
                    gc.fillRect(getX(), getY(), 20, 20);
                    move();
                    break;
            }
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
}
