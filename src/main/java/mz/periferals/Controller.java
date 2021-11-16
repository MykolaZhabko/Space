package mz.periferals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableColumn<Archive, String> col_date;

    @FXML
    private TableColumn<Archive, String> col_score;

    @FXML
    private TableColumn<Archive, String> col_time;

    @FXML
    private TableView<Archive> table_info;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        loadData();
    }

    private void initTable(){
        initCols();
    }

    private void initCols(){
        col_date.setCellValueFactory(new PropertyValueFactory<>("DATE"));
        col_score.setCellValueFactory(new PropertyValueFactory<>("SCORE"));
        col_time.setCellValueFactory(new PropertyValueFactory<>("TIME"));
        editableCols();
    }

    private void editableCols(){
        col_date.setCellFactory(TextFieldTableCell.forTableColumn());

        col_date.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDate(e.getNewValue());
        });

        col_score.setCellFactory(TextFieldTableCell.forTableColumn());

        col_score.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setScore(e.getNewValue());
        });

        col_time.setCellFactory(TextFieldTableCell.forTableColumn());

        col_time.setOnEditCommit(e ->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTime(e.getNewValue());
        });

        table_info.setEditable(true);
    }

    private void loadData(){
        ObservableList<Archive> table_data = FXCollections.observableArrayList();

        for (int i = 0; i < 7; i++) {
            table_data.add(new Archive(String.valueOf(i), "time" + i, "score" + i));
        }
        table_info.setItems(table_data);
    }
}
