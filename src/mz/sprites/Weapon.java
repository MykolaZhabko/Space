package mz.sprites;

import javafx.scene.canvas.GraphicsContext;

public class Weapon extends GeneralSprite{


    public Weapon(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth);
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
