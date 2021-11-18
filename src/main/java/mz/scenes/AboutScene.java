package mz.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mz.menu.Title;
import java.io.IOException;

public class AboutScene extends GeneralScene{

    Parent root = null;

    public AboutScene(){
        super();

    }

    @Override
    public void draw() {
        addBackground();
        try {
            System.out.println("HERE");
            root = FXMLLoader.load(getClass().getClassLoader().getResource("about.fxml"));
            //this.getStylesheets().add(getClass().getClassLoader().getResource("table.css").toExternalForm());
            System.out.println(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addTitle();
        // addMenu(UI_WIDTH / 2 - 200, UI_HEIGHT / 6);
        getGeneralRoot().getChildren().add(root);
        //addTable();
    }

    private void addTitle() {
        Title title = new Title("ABOUT");
        title.setTranslateX(UI_WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(UI_HEIGHT / 8);
        getGeneralRoot().getChildren().add(title);
    }

    private void addBackground() {
        ImageView imageView = new ImageView(new Image("UIBACK.png"));
        imageView.setFitWidth(UI_WIDTH);

        getGeneralRoot().getChildren().add(imageView);
    }


}
