package mz.game;

import javafx.application.Application;
import javafx.stage.Stage;
import mz.periferals.GameConstants;
import mz.scenes.*;

import java.io.Serializable;

/**
 * Main class for the game
 */
public class Game extends Application implements Serializable, GameConstants {

    private static Stage root;

    @Override
    public void start(Stage primaryStage) {
        root = primaryStage;
        setScene(MENU_SCENE);
        root.setTitle("SPACE");
        root.setResizable(false);
        root.show();
    }

    /**
     * This method is changing SCENES.
     * The method is static, so you are able to change the scene from any point of your game.
     * @param numScene
     */
    public static void setScene(int numScene){
        GeneralScene scene;
        switch (numScene){
            case GAME_SCENE:
                scene = new GameScene();
                root.setScene(scene);
                scene.draw();
                break;
            case MENU_SCENE:
                scene = new MenuScene();
                root.setScene(scene);
                scene.draw();
                break;
            case ARCHIVE_SCENE:
                scene = new ArchivesScene();
                root.setScene(scene);
                scene.draw();
                break;
            case ABOUT_SCENE:
                scene = new AboutScene();
                root.setScene(scene);
                scene.draw();
                break;
        }
    }

    /**
     * The method will perform exit the game
     */
    public static void exit(){
        root.hide();
    }

    /**
     * MAIN method
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
