package fi.jyu.ohj2.ttkelaw.todo;

import fi.jyu.ohj2.ttkelaw.todo.data.Tehtava;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import  javafx.scene.control.CheckBox;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.awt.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    private ObservableList<Tehtava> tehtavat = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tehtavat.addListener((ListChangeListener<Tehtava>) change -> {
            paivitaNakyma();
            tallenna();
        });

        lataa();
        lisaaUusiTehtavaPainike.setOnAction(event -> lisaaTehtava());
        uusiTehtavaNimi.setOnAction(event -> lisaaTehtava());
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

    private CheckBox luoCheckBox(Tehtava t) {
        CheckBox tehtava = new CheckBox(t.getTeksti());
        tehtava.setSelected(t.isTehty());
        tehtava.setOnAction(event -> {
            tehtavat.remove(t);
            tehtavat.add(new Tehtava(t.getTeksti(), !t.isTehty()));
        });
        return tehtava;
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

    private void paivitaNakyma() {

        tekemattomat.getChildren().clear();
        tehdyt.getChildren().clear();

        for (Tehtava tehtava : tehtavat) {
            CheckBox cb = luoCheckBox(tehtava);
            if (tehtava.isTehty()) {
                tehdyt.getChildren().add(cb);
            } else {
                tekemattomat.getChildren().add(cb);
            }
        }

    }

}
