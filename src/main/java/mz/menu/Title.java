package mz.menu;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Class for the any Title for the scene
 */

public class Title extends Pane implements Serializable {
    private Text text;

    /**
     * Main constructor.
     * @param name - title name
     */
    public Title(String name){
        String spread = "";
        for(char c: name.toCharArray()){
            spread += c + " ";
        }

        text = new Text(spread);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        text.setFill(Color.WHITE);
        text.setEffect(new DropShadow(30, Color.BLACK));

        getChildren().addAll(text);
    }

    /**
     * To get width of the title
     * @return
     */
    public double getTitleWidth(){ return text.getLayoutBounds().getWidth();}

    /**
     * To get heught of the title
     * @return
     */
    public double getTitleHeight(){ return text.getLayoutBounds().getHeight();}
}
