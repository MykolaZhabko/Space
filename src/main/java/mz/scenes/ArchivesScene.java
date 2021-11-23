package mz.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mz.menu.Title;

import java.io.IOException;
import java.io.Serializable;

/**
 * Scene for ARCHIVE.
 * This scene will show the table with information about played games.
 */
public class ArchivesScene extends GeneralScene implements Serializable {
    Parent root = null;

    public ArchivesScene(){
        super();
    }

    /**
     * Initialise/Draws actual scene to the screen
     */
    @Override
    public void draw() {
        addBackground();
        try {
            //Here is the main trick of loading the scene build in SceneBuilder using FXML (Cool stuff)
            root = FXMLLoader.load(getClass().getClassLoader().getResource("table.fxml"));
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
        Title title = new Title("ARCHIVES");
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
