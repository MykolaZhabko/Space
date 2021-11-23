package mz.menu;

import javafx.beans.binding.Bindings;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Class defines the menu Item.
 * The style settings are inside the constructor
 */
public class MenuItem extends Pane implements Serializable {
    private Text text;
    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 3);

    /**
     * Constructor requires descriptive string name of the menu item.
     * @param name - mebu item name ('OPTIONS','START GAME'... etc)
     */
    public MenuItem(String name) {
        Polygon bg = new Polygon(
                0, 0,
                430, 0,
                415, 15,
                400, 30,
                0, 30
        );
        bg.setStroke(Color.color(1, 1, 1, 0.75));
        bg.setEffect(new GaussianBlur());

        bg.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.color(0, 0, 0, 0.75))
                        .otherwise(Color.color(0, 0, 0, 0.25))

        );
        text = new Text(name);
        text.setTranslateX(5);
        text.setTranslateY(20);
        text.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        text.setFill(Color.WHITE);

        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(blur)
        );
        getChildren().addAll(bg,text);
    }

    /**
     * To fire an event for mouse click up on the menu item
     * @param action
     */
    public void setOnAction(Runnable action){
        setOnMouseClicked(e -> action.run());
    }
}
