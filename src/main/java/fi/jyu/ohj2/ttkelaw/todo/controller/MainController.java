package fi.jyu.ohj2.ttkelaw.todo.controller;

import fi.jyu.ohj2.ttkelaw.todo.App;
import fi.jyu.ohj2.ttkelaw.todo.model.Tehtava;
import fi.jyu.ohj2.ttkelaw.todo.model.Tehtavakokoelma;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button lisaaUusiTehtavaPainike;
    @FXML
    private TextField uusiTehtavaNimi;
    @FXML
    private TableView<Tehtava> tehtavaTaulu;
    @FXML
    private Button poistaValittuPainike;

    Tehtavakokoelma tehtavakokoelma = new Tehtavakokoelma();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        SortedList<Tehtava> tehtavatLajiteltu = tehtavakokoelma.getTehtavat().sorted(Comparator.comparing(Tehtava::isTehty));
        tehtavaTaulu.setItems(tehtavatLajiteltu);
        tehtavaTaulu.setEditable(true);

        TableColumn<Tehtava, Boolean> tehtySarake = new TableColumn<>("Tehty");
        tehtySarake.setCellValueFactory(cd -> cd.getValue().tehtyProperty());
        tehtySarake.setCellFactory(CheckBoxTableCell.forTableColumn(tehtySarake));
        tehtavaTaulu.getColumns().add(tehtySarake);

        TableColumn<Tehtava, String> tekstiSarake = new TableColumn<>("Teksti");
        tekstiSarake.setCellValueFactory(cd -> cd.getValue().otsikkoProperty());
        tehtavaTaulu.getColumns().add(tekstiSarake);

        tehtavaTaulu.setRowFactory(tv -> {
            TableRow<Tehtava> rivi = new TableRow<>();
            rivi.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 && !rivi.isEmpty()) {
                    Tehtava klikattuTehtava = rivi.getItem();
                    avaaTehtavanMuokkaus(klikattuTehtava);
                }
            });


            return rivi;
        });
        tehtavakokoelma.lataa();
        lisaaUusiTehtavaPainike.setOnAction(event -> lisaaTehtava());
        uusiTehtavaNimi.setOnAction(event -> lisaaTehtava());
        poistaValittuPainike.setOnAction(event -> poistaValittu());
    }

    private void avaaTehtavanMuokkaus(Tehtava muokattava) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("edit-tehtava.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            TehtavaEditController ohjain = loader.getController();
            ohjain.setTehtava(muokattava);

            Stage dialogi = new Stage();
            dialogi.setScene(scene);

            dialogi.setTitle("Muokkaa: " + muokattava.getOtsikko());
            dialogi.setMinWidth(400);
            dialogi.setMinHeight(300);
            dialogi.initModality(Modality.APPLICATION_MODAL);

            dialogi.showAndWait();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void poistaValittu() {
        Tehtava valittuTehtava = tehtavaTaulu.getSelectionModel().getSelectedItem();
        tehtavakokoelma.poistaTehtava(valittuTehtava);
    }



    private void lisaaTehtava() {
        String teksti = uusiTehtavaNimi.getText();
        tehtavakokoelma.lisaaTehtava(teksti);
        uusiTehtavaNimi.clear();
        uusiTehtavaNimi.requestFocus();
    }
}
