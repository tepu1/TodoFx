package fi.jyu.ohj2.ttkelaw.todo.controller;

import fi.jyu.ohj2.ttkelaw.todo.model.Tehtava;
import fi.jyu.ohj2.ttkelaw.todo.model.Tehtavakokoelma;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

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
        tekstiSarake.setCellValueFactory(cd -> cd.getValue().tekstiProperty());
        tehtavaTaulu.getColumns().add(tekstiSarake);

        tehtavakokoelma.lataa();
        lisaaUusiTehtavaPainike.setOnAction(event -> lisaaTehtava());
        uusiTehtavaNimi.setOnAction(event -> lisaaTehtava());

        poistaValittuPainike.setOnAction(event -> poistaValittu());
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
