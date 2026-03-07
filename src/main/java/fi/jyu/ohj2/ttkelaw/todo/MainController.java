package fi.jyu.ohj2.ttkelaw.todo;

import fi.jyu.ohj2.ttkelaw.todo.data.Tehtava;
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
import javafx.scene.layout.VBox;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.awt.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    private ObservableList<Tehtava> tehtavat = FXCollections.observableArrayList(
            tehtava -> new Observable[] {
                    tehtava.tehtyProperty()
            }
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tehtavat.addListener((ListChangeListener<Tehtava>) change -> {
            tallenna();
        });

        SortedList<Tehtava> tehtavatLajiteltu = tehtavat.sorted(Comparator.comparing(Tehtava::isTehty));
        tehtavaTaulu.setItems(tehtavatLajiteltu);
        tehtavaTaulu.setEditable(true);

        TableColumn<Tehtava, Boolean> tehtySarake = new TableColumn<>("Tehty");
        tehtySarake.setCellValueFactory(cd -> cd.getValue().tehtyProperty());
        tehtySarake.setCellFactory(CheckBoxTableCell.forTableColumn(tehtySarake));
        tehtavaTaulu.getColumns().add(tehtySarake);

        TableColumn<Tehtava, String> tekstiSarake = new TableColumn<>("Teksti");
        tekstiSarake.setCellValueFactory(cd -> cd.getValue().tekstiProperty());
        tehtavaTaulu.getColumns().add(tekstiSarake);

        lataa();
        lisaaUusiTehtavaPainike.setOnAction(event -> lisaaTehtava());
        uusiTehtavaNimi.setOnAction(event -> lisaaTehtava());

        poistaValittuPainike.setOnAction(event -> poistaValittu());
    }

    private void poistaValittu() {
        Tehtava valittuTehtava = tehtavaTaulu.getSelectionModel().getSelectedItem();
        if (valittuTehtava == null) {
            return;
        }
        tehtavat.remove(valittuTehtava);
    }

    private void tallenna() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(Path.of("tehtavat.json"), tehtavat);
    }

    private void lataa() {
        Path path = Path.of("tehtavat.json");
        if (Files.notExists(path)) {
            return;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Tehtava> kaikkiTehtavat = mapper.readValue(path, new TypeReference<>() {});
            tehtavat.addAll(kaikkiTehtavat);

        } catch (JacksonException e) {
            IO.println("Virhe: " + e.getMessage());
        }
    }

    private void lisaaTehtava() {
        String teksti = uusiTehtavaNimi.getText();
        if (teksti == null || teksti.isBlank()) {
            uusiTehtavaNimi.requestFocus();
            return;
        }
        tehtavat.add(new Tehtava(teksti, false));
        uusiTehtavaNimi.clear();
        uusiTehtavaNimi.requestFocus();
    }
}
