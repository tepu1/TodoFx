package fi.jyu.ohj2.ttkelaw.todo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import  javafx.scene.control.CheckBox;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button lisaaUusiTehtavaPainike;
    @FXML
    private TextField uusiTehtavaNimi;
    @FXML
    private VBox tekemattomat;
    @FXML
    private VBox tehdyt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lisaaUusiTehtavaPainike.setOnAction(event -> lisaaTehtava());
        uusiTehtavaNimi.setOnAction(event -> lisaaTehtava());
    }

    private void lisaaTehtava() {
        String teksti = uusiTehtavaNimi.getText();
        if (teksti == null || teksti.isBlank()) {
            uusiTehtavaNimi.requestFocus();
            return;
        }
        teksti = teksti.trim();
        CheckBox tehtava = new CheckBox(teksti);
        tehtava.setOnAction(event -> {
           if (tehtava.isSelected()) {
               tekemattomat.getChildren().remove(tehtava);
               tehdyt.getChildren().add(tehtava);
           } else {
               tehdyt.getChildren().remove(tehtava);
               tekemattomat.getChildren().add(tehtava);
           }


        });


        tekemattomat.getChildren().add(tehtava);
        uusiTehtavaNimi.clear();
        uusiTehtavaNimi.requestFocus();
    }
}
