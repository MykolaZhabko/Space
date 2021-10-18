package mz.game;

import javafx.application.Application;
import javafx.stage.Stage;
import mz.scenes.GameScene;
import mz.scenes.GeneralScene;

public class Game extends Application {

    public static final GeneralScene[] scenes = new GeneralScene[1];

    private static Stage root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = primaryStage;
        scenes[0] = new GameScene();
        setScene(0);

        root.setTitle("SPACE");
        root.show();

    }

    public void setScene(int numScene){
        root.setScene(scenes[numScene]);
        scenes[numScene].draw();
    }

    public static void exit(){
        root.hide();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
