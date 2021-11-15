package mz.scenes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import mz.game.Game;
import mz.menu.MenuItem;
import mz.menu.Title;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ArchivesScene extends GeneralScene implements Serializable {

    private VBox menuBox = new VBox(-5);
    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("BACK TO MAIN MENU", () -> {
                Game.setScene(1);
            })
    );

    public ArchivesScene(){
        super();
    }

    @Override
    public void draw() {
        addBackground();
        addTitle();
        addMenu(UI_WIDTH / 2 - 200, UI_HEIGHT / 4 + 100);
    }

    private void addTitle() {
        Title title = new Title("ARCHIVES");
        title.setTranslateX(UI_WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(UI_HEIGHT / 4);
        getGeneralRoot().getChildren().add(title);
    }

    private void addBackground() {
        ImageView imageView = new ImageView(new Image("UIBACK.png"));
        imageView.setFitWidth(UI_WIDTH);

        getGeneralRoot().getChildren().add(imageView);
    }

    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            menuBox.getChildren().add(item);
        });

        getGeneralRoot().getChildren().add(menuBox);
    }
}
