package mz.scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

import java.util.HashSet;
import java.util.Set;

public abstract class GeneralScene extends Scene {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 1000;

    private StackPane root;
    protected GraphicsContext gc;
    protected Set<KeyCode> activeKeys;
    protected Set<KeyCode> releasedKeys;

    public GeneralScene(){
        super(new StackPane(), GAME_WIDTH, GAME_HEIGHT);

        this.root = new StackPane();
        this.setRoot(this.root);

        activeKeys = new HashSet<>();
        releasedKeys = new HashSet<>();

        this.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
        });

        this.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
            releasedKeys.add(event.getCode());
        });

        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        this.root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
    }

    public abstract void draw();

}
