package mz.periferals;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import mz.game.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class About implements Initializable {

    @FXML
    private Button about_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        about_btn.setOnMouseClicked(e ->{
            Game.setScene(1);
        });
    }
}
