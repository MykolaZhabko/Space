package mz.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import mz.game.Game;
import mz.menu.MenuItem;
import mz.menu.Title;
import mz.periferals.Archive;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ArchivesScene extends GeneralScene implements Serializable {
    TableView table;
    Parent root = null;
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
        try {
            System.out.println("HERE");
            root = FXMLLoader.load(getClass().getClassLoader().getResource("table.fxml"));
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

    private void addTable() {
        TableColumn<Archive,String> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Archive,String> timeCol = new TableColumn<>("Time");
        timeCol.setMinWidth(200);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Archive,String> scoreCol = new TableColumn<>("Score");
        scoreCol.setMinWidth(200);
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        table = new TableView();
        table.setMaxWidth(600);
        table.setMaxHeight(600);
        table.setTranslateY(100);
        table.setItems(getArchive());
        table.getColumns().addAll(dateCol,timeCol,scoreCol);
        getGeneralRoot().getChildren().add(table);

    }

    private void addTitle() {
        Title title = new Title("ARCHIVES");
        title.setTranslateX(UI_WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(UI_HEIGHT / 8);
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

    public ObservableList<Archive> getArchive(){
        ObservableList<Archive> archive = FXCollections.observableArrayList();
        archive.add(new Archive("DATE","TIME","SCORE"));
        return archive;
    }
}
