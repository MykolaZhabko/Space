package mz.scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import mz.periferals.GameConstants;

import java.util.HashSet;
import java.util.Set;

public abstract class GeneralScene extends Scene implements GameConstants {
    private StackPane root;
    protected GraphicsContext gc;
    protected GraphicsContext gcUi;
    protected Set<KeyCode> activeKeys;
    protected Set<KeyCode> releasedKeys;

    public GeneralScene(){
        super(new StackPane(), UI_WIDTH, UI_HEIGHT);

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
        Canvas ui = new Canvas(GAME_WIDTH+100,GAME_HEIGHT+100);
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        root.getChildren().add(ui);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        gcUi = ui.getGraphicsContext2D();
    }

    public abstract void draw();

}
