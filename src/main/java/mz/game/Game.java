package mz.game;

import javafx.application.Application;
import javafx.stage.Stage;
import mz.scenes.GameScene;
import mz.scenes.GeneralScene;
import mz.scenes.MenuScene;

import java.io.Serializable;

public class Game extends Application implements Serializable {

    private static Stage root;

    @Override
    public void start(Stage primaryStage) {
        root = primaryStage;
        setScene(1);
        root.setTitle("SPACE");
        root.setResizable(false);
        root.show();


    }

    public static void setScene(int numScene){
        GeneralScene scene;
        switch (numScene){
            case 0:
                scene = new GameScene();
                root.setScene(scene);
                scene.draw();
                break;
            case 1:
                scene = new MenuScene();
                root.setScene(scene);
                scene.draw();
                break;
        }
    }

    public static void exit(){
        root.hide();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
