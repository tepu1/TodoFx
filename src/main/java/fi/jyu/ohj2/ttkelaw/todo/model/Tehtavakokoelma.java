package fi.jyu.ohj2.ttkelaw.todo.model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Tehtavakokoelma {

    private ObservableList<Tehtava> tehtavat = FXCollections.observableArrayList(
            tehtava -> new Observable[] {
                    tehtava.tehtyProperty(),
                    tehtava.otsikkoProperty(),
                    tehtava.kuvausProperty(),
                    tehtava.prioriteettiProperty()
            }
    );

    private final Path polku;

    public Tehtavakokoelma(Path polku) {
        this.polku = polku;
        tehtavat.addListener((ListChangeListener<Tehtava>) change -> {
            tallenna();
        });
    }

    public ObservableList<Tehtava> getTehtavat() {
        return this.tehtavat;
    }

    public void tallenna() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(polku, tehtavat);
    }

    public void lataa() {
        Path path = Path.of("tehtavat.json");
        if (Files.notExists(polku)) {
            return;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Tehtava> kaikkiTehtavat = mapper.readValue(polku.toFile(), new TypeReference<>() {});
            tehtavat.addAll(kaikkiTehtavat);

        } catch (JacksonException e) {
            IO.println("Virhe: " + e.getMessage());
        }
    }

    public void poistaTehtava(Tehtava tehtava) {
        if (tehtava == null) {
            return;
        }
        tehtavat.remove(tehtava);
    }

    public void lisaaTehtava(String otsikko) {
        if (otsikko == null || otsikko.isBlank()) {
            return;
        }
        tehtavat.add(new Tehtava(otsikko.trim(), false));
    }
}
