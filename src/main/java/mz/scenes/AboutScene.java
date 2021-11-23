package mz.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mz.menu.Title;
import java.io.IOException;

/**
 * This is an informative scene. Contains the information about project.
 * Loaded from FXML.
 */
public class AboutScene extends GeneralScene{
    Parent root = null;
    public AboutScene(){
        super();
    }

    /**
     * Initialise/Draws actual scene to the screen
     */
    @Override
    public void draw() {
        addBackground();
        try {
            System.out.println("HERE");
            root = FXMLLoader.load(getClass().getClassLoader().getResource("about.fxml"));
            System.out.println(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addTitle();
        getGeneralRoot().getChildren().add(root);
    }

    /**
     * Adds title to the scene
     */
    private void addTitle() {
        Title title = new Title("ABOUT");
        title.setTranslateX(UI_WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(UI_HEIGHT / 8);
        getGeneralRoot().getChildren().add(title);
    }

    /**
     * Adds background to the scene
     */
    private void addBackground() {
        ImageView imageView = new ImageView(new Image("UIBACK.png"));
        imageView.setFitWidth(UI_WIDTH);
        getGeneralRoot().getChildren().add(imageView);
    }


}
