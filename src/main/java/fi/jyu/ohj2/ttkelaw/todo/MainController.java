package fi.jyu.ohj2.ttkelaw.todo;

import fi.jyu.ohj2.ttkelaw.todo.data.Tehtava;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lataa();
        lisaaUusiTehtavaPainike.setOnAction(event -> lisaaTehtava());
        uusiTehtavaNimi.setOnAction(event -> lisaaTehtava());
    }



    private List<Tehtava> haeTehtavat(VBox sailio) {
        return sailio.getChildren().stream()
                .map(n -> (CheckBox) n)
                .map(cb -> new Tehtava(cb.getText(), cb.isSelected()))
                .toList();
    }
    private void tallenna() {
        List<Tehtava> tehtavat = new ArrayList<>();
        tehtavat.addAll(haeTehtavat(tekemattomat));
        tehtavat.addAll(haeTehtavat(tehdyt));
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
            List<Tehtava> tehtavat = mapper.readValue(path, new TypeReference<>() {});
            for (Tehtava t : tehtavat) {
                CheckBox cb = luoCheckBox(t.getTeksti(), t.isTehty());
                if (t.isTehty()) {
                    tehdyt.getChildren().add(cb);
                }
                else {
                    tekemattomat.getChildren().add(cb);
                }
            }

        } catch (JacksonException e) {
            IO.println("Virhe: " + e.getMessage());
        }
    }



    private CheckBox luoCheckBox(String teksti, boolean tehty) {
        CheckBox tehtava = new CheckBox(teksti);
        tehtava.setSelected(tehty);
        tehtava.setOnAction(event -> {
            if (tehtava.isSelected()) {
                tekemattomat.getChildren().remove(tehtava);
                tehdyt.getChildren().add(tehtava);
            } else {
                tehdyt.getChildren().remove(tehtava);
                tekemattomat.getChildren().add(tehtava);
            }
            tallenna();
        });
        return tehtava;
    }


    private void lisaaTehtava() {
        String teksti = uusiTehtavaNimi.getText();
        if (teksti == null || teksti.isBlank()) {
            uusiTehtavaNimi.requestFocus();
            return;
        }
        tekemattomat.getChildren().add(luoCheckBox(teksti, false));
        uusiTehtavaNimi.clear();
        uusiTehtavaNimi.requestFocus();
        tallenna();

    }
}
