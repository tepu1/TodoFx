package fi.jyu.ohj2.ttkelaw.todo.controller;

import fi.jyu.ohj2.ttkelaw.todo.model.Prioriteetti;
import fi.jyu.ohj2.ttkelaw.todo.model.Tehtava;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TehtavaEditController implements Initializable {
    @FXML
    private TextField otsikkoKentta;
    @FXML
    private ComboBox<Prioriteetti> prioriteettiCombo;
    @FXML
    private TextArea kuvausKentta;
    @FXML
    private Button tallennaPainike;
    @FXML
    private Button peruutaPainike;

    private Tehtava muokattavaTehtava;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        prioriteettiCombo.setItems(FXCollections.observableArrayList(Prioriteetti.values()));
        peruutaPainike.setOnAction(event -> sulje());
        tallennaPainike.setOnAction(event -> {
            if (!validoi()) {
                return;
            }
            muokattavaTehtava.setOtsikko(otsikkoKentta.getText());
            muokattavaTehtava.setKuvaus(kuvausKentta.getText());
            muokattavaTehtava.setPrioriteetti(prioriteettiCombo.getValue());
            sulje();
        });
    }

    private boolean validoi() {

        otsikkoKentta.setStyle("");
        otsikkoKentta.setPromptText("");

        String otsikko = otsikkoKentta.getText();

        if (otsikko == null || otsikko.isBlank()) {
            otsikkoKentta.setStyle("-fx-border-color: red;");
            otsikkoKentta.setText("");
            otsikkoKentta.setPromptText("Syötä otsikko!");
            return false;
        }
        return true;
    }

    private void sulje() {
        Scene scene = otsikkoKentta.getScene();
        Stage ikkuna = (Stage) scene.getWindow();
        ikkuna.close();
    }

    public void setTehtava(Tehtava muokattavaTehtava) {
        this.muokattavaTehtava = muokattavaTehtava;
        otsikkoKentta.setText(muokattavaTehtava.getOtsikko());
        kuvausKentta.setText(muokattavaTehtava.getKuvaus());
        prioriteettiCombo.setValue(muokattavaTehtava.getPrioriteetti());

    }
}
