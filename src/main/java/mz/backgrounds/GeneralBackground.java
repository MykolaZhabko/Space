package mz.backgrounds;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mz.scenes.GameScene;

/**
 * Creates a background for your game
 */
public class GeneralBackground extends Image {
    private int x;
    private double y;

    public GeneralBackground(String url) {
        super(url);
        setX(0);
        setY(0);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Logic of drawing the background.
     * @param gc - Canvas graphical context
     */
    public void draw(GraphicsContext gc){
        gc.drawImage(this,x,y, GameScene.GAME_WIDTH,GameScene.GAME_HEIGHT);
        gc.drawImage(this,x,y - GameScene.GAME_HEIGHT,GameScene.GAME_WIDTH, GameScene.GAME_HEIGHT);
            setY(getY() + 5);
            if (getY() > GameScene.GAME_HEIGHT) setY(0);
    }
}
