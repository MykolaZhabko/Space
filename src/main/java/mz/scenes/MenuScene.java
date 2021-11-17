package mz.scenes;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import mz.game.Game;
import mz.menu.MenuItem;
import mz.menu.Title;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class MenuScene extends GeneralScene implements Serializable {
    private Line line;
    private VBox menuBox = new VBox(-5);
    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("START", () -> {
                Game.setScene(0);

            }),
            new Pair<String, Runnable>("ARCHIVES", () -> {
                Game.setScene(2);
            }),
            new Pair<String, Runnable>("ABOUT", () -> {
            }),
            new Pair<String, Runnable>("EXIT", () -> {
                Game.exit();
            })
    );

    public MenuScene() {
        super();

    }

    @Override
    public void draw() {
        addBackground();
        addTitle();
        addMenu(UI_WIDTH / 2 - 200, UI_HEIGHT / 4 + 100);

        double lineX = 0;
        double lineY = 0;

        addLine(lineX, lineY);

        startAnimation();

    }

    private void addBackground() {
        ImageView imageView = new ImageView(new Image("UIBACK.png"));
        imageView.setFitWidth(UI_WIDTH);

        getGeneralRoot().getChildren().add(imageView);
    }

    private void addTitle() {
        Title title = new Title("VAMK SPACE");
        title.setTranslateX(UI_WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(UI_HEIGHT / 8);
        getGeneralRoot().getChildren().add(title);
    }

    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 170);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setTranslateY(-45);
        line.setTranslateX(-210);
        line.setScaleY(0);

        getGeneralRoot().getChildren().add(line);
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {
            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            MenuItem item = new MenuItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-500);

            Rectangle clip = new Rectangle(500, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);
            menuBox.getChildren().add(item);
        });

        getGeneralRoot().getChildren().add(menuBox);
    }
}
