package mz.periferals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mz.game.Game;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is a controller for TableView imported from FXML "accets/table.fxml"
 * The table shows the information about played game. Date, score, and time in seconds.
 * The Archive objects are used to fill up the table content.
 */
public class Controller implements Initializable {

    @FXML
    private Button btn_back;

    @FXML
    private TableColumn<Archive, String> col_date;

    @FXML
    private TableColumn<Archive, String> col_score;

    @FXML
    private TableColumn<Archive, String> col_time;

    @FXML
    private TableView<Archive> table_info;

    /**
     * Initialize the Table and attaches to the button an mouseClick event handler.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        btn_back.setOnMouseClicked(e ->{
            Game.setScene(1);
        });
    }

    /**
     * Initialize the table content with Archive object instances.
     * So the content of table is automatically filled using Observable list.
     * Here we read the file located at ./spaceGame/arch.json and parse it into array of JSON objects
     * from which the Archive object generated and added to Observable list for TableView.
     */
    private void initTable() {
        col_date.setCellValueFactory(new PropertyValueFactory<Archive, String>("DATE"));
        col_score.setCellValueFactory(new PropertyValueFactory<Archive, String>("SCORE"));
        col_time.setCellValueFactory(new PropertyValueFactory<Archive, String>("TIME"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_score.setCellValueFactory(new PropertyValueFactory<>("score"));
        ObservableList<Archive> archive = FXCollections.observableArrayList();

        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("spaceGame/arch.json");
            Object obj = parser.parse(reader);
            JSONArray archArr = (JSONArray) obj;
            for (int i = 0; i < archArr.size(); i++) {
                archive.add(new Archive(((JSONObject)archArr.get(i)).get("date").toString(), ((JSONObject)archArr.get(i)).get("score").toString(), ((JSONObject)archArr.get(i)).get("time").toString()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        table_info.setEditable(true);
        table_info.setItems(archive);
    }
}
