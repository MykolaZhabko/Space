package mz.backgrounds;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mz.scenes.GameScene;

public class GeneralBackground extends Image {
    private double t;
    private int x;
    private double y;

    public GeneralBackground(String url) {
        super(url);
        setX(0);
        setY(0);
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
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

    public void draw(GraphicsContext gc){
//        setT(getT() + 0.01);
        gc.drawImage(this,x,y, GameScene.GAME_WIDTH,GameScene.GAME_HEIGHT);
        gc.drawImage(this,x,y - GameScene.GAME_HEIGHT,GameScene.GAME_WIDTH, GameScene.GAME_HEIGHT);
        //if (getT() > 0.3){
            setY(getY() + 0.3);
            if (getY() > GameScene.GAME_HEIGHT) setY(0);


          //  setT(0);
       // }
    }
}
